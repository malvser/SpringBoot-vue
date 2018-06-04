package com.boylegu.springboot_vue.controller.pagination;

import java.util.HashMap;
import java.util.Map;

import com.boylegu.springboot_vue.dao.EmployeeRepository;
import com.boylegu.springboot_vue.entities.Employee;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



/*
    Resolve due to @Autowired lead to NullPointerException problem

    Description：
    1. It's limited to general class to invoke spring bean Object.
    2. And This makes the sub package easy to scan by spring boot.

                                                      ———— @Boyle.gu
*/
@Component
class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        if (SpringUtil.applicationContext == null) {

            SpringUtil.applicationContext = applicationContext;

        }
    }

    public static ApplicationContext getApplicationContext() {

        return applicationContext;

    }

    public static Object getBean(String name) {

        return getApplicationContext().getBean(name);

    }

    public static <T> T getBean(Class<T> clazz) {

        return getApplicationContext().getBean(clazz);

    }

    public static <T> T getBean(String name, Class<T> clazz) {

        return getApplicationContext().getBean(name, clazz);

    }
}


interface Types {

    Page<Employee> query();

    Integer getCount();

    Integer getPageNumber();

    Long getTotal();

    Object getContent();
}

class BasePaginationInfo {

    public Pageable pageable;

    public EmployeeRepository instance = SpringUtil.getBean(EmployeeRepository.class);

    public String firstName, secondName;

    public BasePaginationInfo(String firstName, String secondName, Pageable pageable) {

        this.pageable = pageable;

        this.firstName = firstName;

        this.secondName = secondName;
    }
}

class AllType extends BasePaginationInfo implements Types {


    public AllType(String firstName, String secondName, Pageable pageable) { //String firstName, String secondName,

        super(firstName, secondName, pageable);

    }

    public Page<Employee> query() {

        return this.instance.findAll(

                this.pageable

        );
    }

    public Integer getCount() {
        return this.query().getSize();
    }

    public Integer getPageNumber() {

        return this.query().getNumber();

    }

    public Long getTotal() {
        return this.query().getTotalElements();
    }

    public Object getContent() {
        return this.query().getContent();
    }
}

class FirstSecondNameType extends BasePaginationInfo implements Types {

    public FirstSecondNameType(String firstName, String secondName, Pageable pageable) {

        super(firstName, secondName, pageable);

    }

    public Page<Employee> query() {

        return this.instance.findByFirstAndSecondName(

                this.firstName,

                this.secondName,

                this.pageable
        );
    }

    public Integer getCount() {
        return this.query().getSize();
    }

    public Integer getPageNumber() {

        return this.query().getNumber();

    }

    public Long getTotal() {
        return this.query().getTotalElements();
    }

    public Object getContent() {
        return this.query().getContent();
    }


}

class SexType extends BasePaginationInfo implements Types {

    public SexType(String firstName, String secondName, Pageable pageable) {

        super(firstName, secondName, pageable);
    }

    public Page<Employee> query() {

        return this.instance.findByFirstName(

                this.firstName,

                this.pageable
        );
    }

    public Integer getCount() {
        return this.query().getSize();
    }

    public Integer getPageNumber() {

        return this.query().getNumber();

    }

    public Long getTotal() {
        return this.query().getTotalElements();
    }

    public Object getContent() {
        return this.query().getContent();
    }
}


public class PaginationFormatting {

    private PaginationMultiTypeValuesHelper multiValue = new PaginationMultiTypeValuesHelper();

    private Map<String, PaginationMultiTypeValuesHelper> results = new HashMap<>();

    public Map<String, PaginationMultiTypeValuesHelper> filterQuery(String firstName, String secondName, Pageable pageable) {

        Types typeInstance;

        if (firstName.length() == 0 && secondName.length() == 0) {

            typeInstance = new AllType(firstName, secondName, pageable);

        } else if (firstName.length() > 0 && secondName.length() > 0) {

            typeInstance = new FirstSecondNameType(firstName, secondName, pageable);

        } else {
            typeInstance = new SexType(firstName, secondName, pageable);
        }

        this.multiValue.setCount(typeInstance.getCount());

        this.multiValue.setPage(typeInstance.getPageNumber() + 1);

        this.multiValue.setResults(typeInstance.getContent());

        this.multiValue.setTotal(typeInstance.getTotal());

        this.results.put("data", this.multiValue);

        return results;
    }

}