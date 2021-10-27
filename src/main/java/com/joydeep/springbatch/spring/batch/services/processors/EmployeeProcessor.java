package com.joydeep.springbatch.spring.batch.services.processors;

import com.joydeep.springbatch.spring.batch.dtos.EmployeeDTO;
import com.joydeep.springbatch.spring.batch.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProcessor implements ItemProcessor<Employee, EmployeeDTO> {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeProcessor.class);

    @Override
    public EmployeeDTO process(Employee employee) throws Exception {
        if (!isValid(employee)) {
            logger.error("Invalid employee very old {}", employee);

            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setAge(employee.getAge() + 10);
        System.out.println("inside processor " + employee.toString());
        return employeeDTO;
    }

    private boolean isValid(Employee employee) {
        return !(employee.getAge() > 60);
    }
}
