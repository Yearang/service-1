package com.editdining.service.dto;


import com.editdining.service.entity.ServiceMediaEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class Media {

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Request {
        @ApiModelProperty(name = "media", example = "test", required = true)
        @NotBlank
        private MultipartFile media;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Save {
        private int service_id;
        private String path;
        private String original_name;

        public ServiceMediaEntity toEntity() {
            return ServiceMediaEntity.builder()
                    .serviceId(service_id)
                    .path(path)
                    .originalName(original_name)
                    .build();
        }

    }

}
