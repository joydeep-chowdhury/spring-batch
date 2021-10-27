package com.joydeep.springbatch.spring.batch.services.writers;

import com.joydeep.springbatch.spring.batch.models.Employee;
import com.joydeep.springbatch.spring.batch.repositiories.EmployeeRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDBWriter implements ItemWriter<Employee> {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public void write(List<? extends Employee> employees) throws Exception {
        employeeRepo.saveAll(employees);
        System.out.println("inside writer " + employees);
    }
}
