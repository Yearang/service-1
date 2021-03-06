package com.editdining.service.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "tbl_service_media")
public class ServiceMediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int media_id;
    private int service_id;
    private String path;
    private String original_name;
    private String thumbnail;
}
