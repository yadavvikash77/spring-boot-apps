package com.example.employee.entityListener;

import com.example.employee.model.Employee;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import javax.persistence.*;

@Slf4j
public class EmployeeEntityListener {

    @PrePersist
    public void prePersist(Employee employee) {
        log.info("*** Execute Pre-persist Logic");
    }

    @PostPersist
    public void postPersist(Employee employee) {
        log.info("*** Execute Post Persist Logic");
    }

    @PreUpdate
    public void preUpdate(Employee employee) {
        log.info("*** Execute Pre Update Logic");
    }

    @PostUpdate
    public void postUpdate(Employee employee) {
        log.info("*** Execute Post Update");
    }

    @PreRemove
    public void preRemove(Employee employee) {
        log.info("*** Execute Pre Remove Logic");
    }

    @PostRemove
    public void postRemove(Employee employee) {
        log.info("*** Execute Post Remove Logic");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("**** Execute Pre Destroy Logic ");
    }

    @PostLoad
    public void postLoad(Employee employee) {
        log.info("*** Execute Post Load Logic");
    }
}
