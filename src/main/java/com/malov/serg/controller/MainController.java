package com.malov.serg.controller;

import com.malov.serg.dao.EmployeeRepository;
import com.malov.serg.entity.Employee;


import com.malov.serg.controller.pagination.PaginationFormatting;
import com.malov.serg.controller.pagination.PaginationMultiTypeValuesHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.PageRequest;

import java.util.*;


@RestController
@RequestMapping("/api/employee")
public class MainController {

    private final EmployeeRepository employeeRepository;

    @Value(("${com.malov.serg.max-per-page}"))
    Integer maxPerPage;

    @Autowired
    public MainController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/firstName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFirstNameAll() {



        ArrayList<Map<String, String>> results = new ArrayList<>();

        for (Object value : employeeRepository.firstName()) {

            Map<String, String> firstName = new HashMap<>();

            firstName.put("label", value.toString());

            firstName.put("value", value.toString());

            results.add(firstName);
        }

        ResponseEntity<ArrayList<Map<String, String>>> responseEntity = new ResponseEntity<>(results,
                HttpStatus.OK);

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, PaginationMultiTypeValuesHelper> getEmployeeAll(
            @RequestParam(value = "page", required = false) Integer pages,
            @RequestParam("firstName") String firstName,
            @RequestParam("secondName") String secondName
    ) {


        if (pages == null) {

            pages = 1;

        }

        Sort sort = new Sort(Direction.ASC, "id");

        Pageable pageable = new PageRequest(pages - 1, maxPerPage, sort);

        PaginationFormatting paginInstance = new PaginationFormatting();

        return paginInstance.filterQuery(firstName, secondName, pageable);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getUserDetail(@PathVariable Long id) {


        Employee user = employeeRepository.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Employee updateUser(@PathVariable Long id, @RequestBody Employee data) {


       Employee employee = employeeRepository.findById(id);

        employee.setFirstName(data.getFirstName());

        employee.setSecondName(data.getSecondName());

        return employeeRepository.save(employee);
    }

}