package com.joydeep.springbatch.spring.batch.services.processors;

import com.joydeep.springbatch.spring.batch.dtos.EmployeeDTO;
import com.joydeep.springbatch.spring.batch.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee> {

    private static final Logger logger= LoggerFactory.getLogger(EmployeeProcessor.class);

    @Override
    public Employee process(EmployeeDTO employeeDTO) throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAge(employeeDTO.getAge());
        logger.info("Processing employee {}",employee);
        return employee;
    }
}
