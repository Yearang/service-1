package com.editdining.service.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_service_media")
public class ServiceMediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mediaId;
    private int serviceId;
    private String path;
    private String originalName;
    private String thumbnail;
}
