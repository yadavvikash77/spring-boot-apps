package com.example.employee.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pagination")
@Data
public class ConfigProperty {
    private int pageNo;
    private int pageSize;
    private String sortBy;
}
