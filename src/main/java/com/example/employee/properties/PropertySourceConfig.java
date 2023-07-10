package com.example.employee.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:pagination.properties")
@Data
public class PropertySourceConfig {
    @Value("${pagination.pageno}")
    private int pageNo;
    @Value("${pagination.pagesize}")
    private int pageSize;
    @Value("${pagination.sort.property}")
    private String propertyName;
}
