package com.editdining.service.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Slf4j // 로깅을 위한 어노테이션
@RequiredArgsConstructor // final 변수에 대한 의존성을 추가합니다.
@Component // 빈 등록을 위한 어노테이션
//@Service
public class S3Uploader{

    private final static String TEMP_FILE_PATH = "/tmp/";

    @Autowired
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")  // 프로퍼티에서 cloude.aws.s3.bucket에 대한 정보를 불러옵니다.
    public String bucket;             // 저는 .properties가 아닌 .yml을 이용하였습니다!

    public String upload(MultipartFile multipartFile) throws IOException {
//        System.out.println(multipartFile);
        File convertedFile = convert(multipartFile);
//        System.out.println(convertedFile);
        return upload(convertedFile);
    }

    private String upload(File uploadFile) {
        LocalDate current = LocalDate.now();
        // 년 / 달 / 랜덤파일아이디
        String dirName  = current.getYear() + "/" + current.getMonthValue();
        String fileId = "" + System.currentTimeMillis() + (int)(Math.random()*1000000);

        String fileName = dirName + "/" + fileId;
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            return;
        }
        log.info("임시 파일이 삭제 되지 못했습니다. 파일 이름: {}", targetFile.getName());
    }

    private File convert(MultipartFile file) throws IOException {
        File convertFile = new File(TEMP_FILE_PATH + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return convertFile;
        }
        throw new IllegalArgumentException(String.format("파일 변환이 실패했습니다. 파일 이름: %s", file.getName()));
    }

}