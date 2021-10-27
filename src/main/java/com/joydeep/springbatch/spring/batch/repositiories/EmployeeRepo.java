package com.joydeep.springbatch.spring.batch.repositiories;

import com.joydeep.springbatch.spring.batch.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
}
