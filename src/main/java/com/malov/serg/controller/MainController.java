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

        /*
         * @api {GET} /api/persons/sex Get all sexList
         * @apiName GetAllSexList
         * @apiGroup Info Manage
         * @apiVersion 1.0.0
         * @apiExample {httpie} Example usage:
         *
         *     http /api/persons/sex
         *
         * @apiSuccess {String} label
         * @apiSuccess {String} value
         */

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

        /*
         *   @api {GET} /api/persons   Get all or a part of person info
         *   @apiName GetAllInfoList
         *   @apiGroup Info Manage
         *   @apiVersion 1.0.0
         *
         *   @apiExample {httpie} Example usage: (support combinatorial search)
         *
         *       All person：
         *       http /api/persons
         *
         *       You can according to 'firstName | secondName' or 'firstName & secondName'
         *       http /api/persons?firstName=xxx&secondName=xx
         *       http /api/persons?firstName=xxx
         *       http /api/persons?secondName=xx
         *
         *   @apiParam {String} firstName
         *   @apiParam {String} secondName
         *
         *   @apiSuccess {String} create_datetime
         *   @apiSuccess {String} secondName
         *   @apiSuccess {String} id
         *   @apiSuccess {String} phone
         *   @apiSuccess {String} firstName
         *   @apiSuccess {String} username
         *   @apiSuccess {String} zone
         */

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

        /*
        *    @api {GET} /api/persons/detail/:id  details info
        *    @apiName GetPersonDetails
        *    @apiGroup Info Manage
        *    @apiVersion 1.0.0
        *
        *    @apiExample {httpie} Example usage:
        *
        *        http GET http://127.0.0.1:8000/api/persons/detail/1
        *
        *    @apiSuccess {String} email
        *    @apiSuccess {String} id
        *    @apiSuccess {String} phone
        *    @apiSuccess {String} sex
        *    @apiSuccess {String} username
        *    @apiSuccess {String} zone
        */

        Employee user = employeeRepository.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Employee updateUser(@PathVariable Long id, @RequestBody Employee data) {

        /*
         *  @api {PUT} /api/persons/detail/:id  update person info
         *  @apiName PutPersonDetails
         *  @apiGroup Info Manage
         *  @apiVersion 1.0.0
         *
         *  @apiParam {String} phone
         *  @apiParam {String} zone
         *
         *  @apiSuccess {String} create_datetime
         *  @apiSuccess {String} email
         *  @apiSuccess {String} id
         *  @apiSuccess {String} phone
         *  @apiSuccess {String} sex
         *  @apiSuccess {String} username
         *  @apiSuccess {String} zone

        */
       Employee employee = employeeRepository.findById(id);

        employee.setFirstName(data.getFirstName());

        employee.setSecondName(data.getSecondName());

        return employeeRepository.save(employee);
    }

}