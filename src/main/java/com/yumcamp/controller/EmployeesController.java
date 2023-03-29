package com.yumcamp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yumcamp.common.R;
import com.yumcamp.entity.Employee;
import com.yumcamp.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeesController {

    @Autowired
    private EmployeeService employeesService;

    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
        log.info("employee login: {}", employee);

        // hash user password by md5
//        String password = DigestUtils.md5DigestAsHex(employee.getEmployeePassword().getBytes());
        String password = employee.getEmployeePassword();
        // search the username in database
        // sql: SELECT * FROM Employee WHERE username = '?'
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getEmployeeName, employee.getEmployeeName());
        Employee emp = employeesService.getOne(employeeLambdaQueryWrapper);

        // if there is no such username exists in the database
        if(emp == null){
            return R.error("The account does not exist!");
        }

        // check password
        if(!emp.getEmployeePassword().equals(password)){
            return R.error("Wrong password");
        }

        // if login, save employee info in session
        request.getSession().setAttribute("employee", emp.getEmployeeId());

        return R.success(emp);
    }


    @GetMapping("/{employeeId}")
    public R<Employee> getById(@PathVariable String employeeId){
        Employee emp = employeesService.getById(employeeId);
        return R.success(emp);
    }

    @GetMapping
    public R<List<Employee>> getAll(){
        List<Employee> list = employeesService.list();
        return R.success(list);
    }
}
