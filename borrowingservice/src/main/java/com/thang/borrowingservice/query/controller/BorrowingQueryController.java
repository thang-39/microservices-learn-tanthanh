package com.thang.borrowingservice.query.controller;

import com.thang.borrowingservice.query.model.BorrowingResponseModel;
import com.thang.borrowingservice.query.queries.GetAllBorrowingQuery;
import com.thang.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowingQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public List<BorrowingResponseModel> getBorrowingByEmployee(@PathVariable String employeeId) {
        return queryGateway
                .query(new GetListBorrowingByEmployeeQuery(employeeId),
                        ResponseTypes.multipleInstancesOf(BorrowingResponseModel.class))
                .join();
    }

    @GetMapping
    public List<BorrowingResponseModel> getAllBorrowing() {
        return queryGateway
                .query(new GetAllBorrowingQuery(),
                        ResponseTypes.multipleInstancesOf(BorrowingResponseModel.class))
                .join();
    }
}
