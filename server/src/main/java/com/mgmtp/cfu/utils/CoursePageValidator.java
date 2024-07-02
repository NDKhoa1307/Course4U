package com.mgmtp.cfu.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CoursePageValidator {

    @Getter
    private static int maxPageSize = 32;

    @Value("${course.page.max-size}")
    private void setMaxPageSize(int maxPageSize) {
        CoursePageValidator.maxPageSize = maxPageSize;
    }

    public static boolean isValidPageSize(int pageSize) {
        return pageSize >= 1 && pageSize <= maxPageSize;
    }
}