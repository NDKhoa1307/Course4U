package com.mgmtp.cfu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mgmtp.cfu.enums.CourseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseRegistrationDTO {
    private Long id;
    private String name;
    private String link;
    private String platform;
    private String thumbnailUrl;
    private String teacherName;
    private LocalDate createdDate;
    private CourseStatus status;
}