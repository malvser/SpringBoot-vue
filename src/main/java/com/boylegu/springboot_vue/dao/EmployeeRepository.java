package com.boylegu.springboot_vue.dao;

import com.boylegu.springboot_vue.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by serg
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    String FIND_FIRSTNAME = "select DISTINCT firstName from Employee p";

    @Query(FIND_FIRSTNAME)
    List<Employee> firstName();

    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByFirstAndSecondName(String firstName, String secondName, Pageable pageable);

    Page<Employee> findByFirstName(String firstName, Pageable pageable);

    Employee findById(Long id);
}
