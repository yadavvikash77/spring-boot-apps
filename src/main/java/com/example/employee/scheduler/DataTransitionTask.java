package com.example.employee.scheduler;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
public class DataTransitionTask {

    private EmployeeService employeeService;

    public DataTransitionTask(@Qualifier("emp") EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Scheduled(cron = "0 29 21 ? * * ")
    public void transitionTask() throws IOException {
        List<EmployeeDto> employeeList= employeeService.getAllEmployees(0,10,"firstName");
        if (!employeeList.isEmpty()){
            Path filePath = Path.of("emp.txt");
            try(FileWriter fileWriter = new FileWriter(filePath.toFile())){
                fileWriter.write(employeeList.toString());
            }
        }
    }
}
