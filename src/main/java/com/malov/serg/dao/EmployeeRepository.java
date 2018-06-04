package com.malov.serg.dao;

import com.malov.serg.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by serg
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    String FIND_FIRSTNAME = "select DISTINCT firstName from Employee p where originalid is null";



    String FIND_ALL = "select p from Employee p where originalid is null";

    @Query(FIND_FIRSTNAME)
    List<Employee> firstName();


    @Query(FIND_ALL)
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByFirstNameAndSecondNameContains(String firstName, String secondName, Pageable pageable);


    Page<Employee> findByFirstName(String firstName, Pageable pageable);

    Employee findById(Long id);
}
