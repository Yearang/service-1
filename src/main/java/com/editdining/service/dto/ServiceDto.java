package com.editdining.service.dto;

import com.editdining.service.entity.ServiceMasterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ServiceDto implements Serializable {

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @ApiModel(description = "서비스 등록")
    public static class Save {
        // master
        @ApiModelProperty(name = "category", value = "1:영상편집 2:영상소스 3:캐리커처/일러스트 4:채널아트/채널아이콘 5:번역/자막 6:사운드/녹음", required = true, example = "1")
        @NotNull
        private int category;

        @ApiModelProperty(name = "title", value = "제목", example = "제목 테스트", required = true)
        @NotBlank
        private String title;

        @ApiModelProperty(name = "edit_type", value = "1:일반편집 2:간단편집", example = "1")
        private int edit_type;

        @ApiModelProperty(name = "thumbnail", value = "썸네일")
        private MultipartFile thumbnail;

        @ApiModelProperty(name = "description", value = "설명", example = "서비스 설명")
        private String description;

        // price
        @ApiModelProperty(dataType="List", name = "priceList", required = true)
        @NotNull
        private List<Price> priceList;

        // introduce media
        @ApiModelProperty(name = "소개용 사진, 영상 정보")
        List<Media.Request> mediaList;

        private String thumbnail_path;
        private int member_id;

        public ServiceMasterEntity toEntity() {
            return ServiceMasterEntity.builder()
                    .member_id(member_id)
                    .category(category)
                    .edit_type(edit_type)
                    .title(title)
                    .thumbnail(thumbnail_path)
                    .description(description)
                    .mtime(LocalDateTime.now())
                    .rtime(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @ApiModel(description = "서비스 리스트")
    public static class Response {
        // master
        private int service_id;
        @ApiModelProperty(name = "category", value = "1:영상편집 2:영상소스 3:캐리커처/일러스트 4:채널아트/채널아이콘 5:번역/자막 6:사운드/녹음", required = true, example = "1")
        @NotNull
        private int category;

        @ApiModelProperty(name = "title", value = "제목", example = "제목 테스트", required = true)
        @NotBlank
        private String title;

        @ApiModelProperty(name = "edit_type", value = "1:일반편집 2:간단편집", example = "1")
        private int edit_type;

        @ApiModelProperty(name = "thumbnail", value = "썸네일")
        private String thumbnail;

        @ApiModelProperty(name = "description", value = "설명", example = "서비스 설명")
        private String description;

        // price
        private int price;

        // member
        private String name;

        private double grade;

        private int is_scrap;


    }


}
