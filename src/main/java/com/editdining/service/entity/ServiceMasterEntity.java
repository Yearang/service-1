package com.editdining.service.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "tbl_service_master")
public class ServiceMasterEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int service_id;
	private int category;
	private int edit_type;
	private String title;
	private String thumbnail;
	private String description;
	private LocalDateTime rtime;
	private LocalDateTime mtime;
	private int member_id;
	

}
