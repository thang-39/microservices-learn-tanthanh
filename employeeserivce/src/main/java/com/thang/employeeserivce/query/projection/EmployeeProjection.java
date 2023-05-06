package com.thang.employeeserivce.query.projection;

import com.thang.commonservice.model.EmployeeResponseCommonModel;
import com.thang.commonservice.query.GetDetailsEmployeeQuery;
import com.thang.employeeserivce.command.data.Employee;
import com.thang.employeeserivce.command.data.EmployeeRepository;
import com.thang.employeeserivce.query.model.EmployeeResponseModel;
import com.thang.employeeserivce.query.queries.GetAllEmployeesQuery;
import com.thang.employeeserivce.query.queries.GetEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeProjection {
    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public EmployeeResponseModel handle(GetEmployeeQuery query) {
        EmployeeResponseModel model = new EmployeeResponseModel();
        Employee employee = employeeRepository.findById(query.getEmployeeId()).get();
        BeanUtils.copyProperties(employee,model);
        return model;
    }

    @QueryHandler
    public EmployeeResponseCommonModel handle(GetDetailsEmployeeQuery query) {
        EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
        Employee entity = employeeRepository.findById(query.getEmployeeId()).get();
        BeanUtils.copyProperties(entity,model);
        return model;
    }

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeesQuery query) {
        List<EmployeeResponseModel> modelList = new ArrayList<>();
        List<Employee> entityList = employeeRepository.findAll();

        entityList.forEach(e -> {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(e,model);
            modelList.add(model);
        });

        return modelList;
    }


}
