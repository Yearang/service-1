package com.editdining.service.service;

//import com.editdining.service.common.Uploader;
import com.editdining.response.CommonResult;
import com.editdining.response.ResponseService;
import com.editdining.service.common.S3Uploader;
import com.editdining.service.dto.Media;
import com.editdining.service.dto.Price;
import com.editdining.service.dto.ServiceDto;

import com.editdining.service.entity.ScrapEntity;
import com.editdining.service.entity.ServiceMediaEntity;
import com.editdining.service.entity.ServicePriceEntity;
import com.editdining.service.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class ServiceService {

    private final ServiceMasterRepository masterRepo;
    private final ServiceMediaRepository mediaRepo;
    private final ServicePriceRepository priceRepo;
    private final ScrapRepository scrapRepo;

    private final ServiceMasterRepositorySupport masterRepoSupport;

    private final ResponseService responseService = new ResponseService();

    @Autowired
    private final S3Uploader uploader;

    /* 2-1. 등록 */
    public CommonResult save(@Valid ServiceDto.Save saveDto) throws IOException {

        // 1. master에 insert
        String filepath = uploader.upload(saveDto.getThumbnail());
        saveDto.setThumbnail_path(filepath);
        int serviceId = masterRepo.save(saveDto.toEntity()).getService_id();

        System.out.println(serviceId);
        if(serviceId <= 0) {
            return responseService.getFailResult(100, "SERVICE_SAVE_ERROR");
        }

        // 2. insert 결과를 가지고 price랑 media insert
        List<Price> priceList = saveDto.getPriceList();
        List<ServicePriceEntity> spcList = new ArrayList<>();
        for(Price p : priceList) {
            p.setService_id(serviceId);
            spcList.add(p.toEntity());
        }
        List<ServicePriceEntity> priceRes = priceRepo.saveAll(spcList);

        // 3. 파일 업로드 하고, 소개용 사진 테이블에 isert
        List<Media.Request> mediaList = saveDto.getMediaList();
        List<ServiceMediaEntity> mediaSaveList = new ArrayList<>();
        for(Media.Request media : mediaList) {
            Media.Save tmp = new Media.Save();
            tmp.setOriginal_name(media.getMedia().getOriginalFilename());
            tmp.setService_id(serviceId);
            String path = uploader.upload(media.getMedia());
            tmp.setPath(path);
            mediaSaveList.add(tmp.toEntity());
        }
        List<ServiceMediaEntity> mediaRes = mediaRepo.saveAll(mediaSaveList);

        if(priceRes == null || priceRes.size() == 0 || mediaRes == null || mediaRes.size() == 0) {
            return responseService.getFailResult(100, "FAIL_DB_INSERT");
        }

        return responseService.getSuccessResult();
    }


    /* 2-2. 서비스 리스트 */
    public List<ServiceDto.Response> findByCategory(@Valid int category, Integer edit_type, String sort, int offset, int limit) {
        List<ServiceDto.Response> list = masterRepoSupport.findByCategory(category, edit_type, offset, limit);
        return list;
    }

    public CommonResult saveScrap(int member_id, ScrapEntity scrapEntity) {
        scrapEntity.setMemberId(member_id);
        int scrapId = scrapRepo.save(scrapEntity).getScrapId();
        if(scrapId == 0) {
            return responseService.getFailResult(100, "FAIL_DB_INSERT");
        }
        return responseService.getSuccessResult();
    }

    public CommonResult deleteScrap(int member_id, ScrapEntity scrapEntity) {
        int delete = scrapRepo.deleteByMemberIdAndServiceId(member_id, scrapEntity.getServiceId());
        if(delete == 0) {
            return responseService.getFailResult(100, "FAIL_DB_DELETE");
        }
        return responseService.getSuccessResult();
    }

}
