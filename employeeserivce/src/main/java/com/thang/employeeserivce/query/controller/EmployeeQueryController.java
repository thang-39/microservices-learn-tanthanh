package com.thang.employeeserivce.query.controller;

import com.thang.employeeserivce.query.model.EmployeeResponseModel;
import com.thang.employeeserivce.query.queries.GetAllEmployeesQuery;
import com.thang.employeeserivce.query.queries.GetEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public EmployeeResponseModel getEmployeeDetail(@PathVariable String employeeId) {
        return queryGateway
                .query(new GetEmployeeQuery(employeeId),
                        ResponseTypes.instanceOf(EmployeeResponseModel.class))
                .join();
    }

    @GetMapping
    public List<EmployeeResponseModel> getAllEmployees() {
        return queryGateway
                .query(new GetAllEmployeesQuery(),
                        ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class))
                .join();
    }

}
