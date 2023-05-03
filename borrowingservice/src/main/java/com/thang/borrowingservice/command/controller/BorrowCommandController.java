package com.thang.borrowingservice.command.controller;

import com.thang.borrowingservice.command.command.CreateBorrowCommand;
import com.thang.borrowingservice.command.model.BorrowRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowCommandController {

    @Autowired
    private CommandGateway commandGateway;

//    @Autowired
//    private MessageChannel output;

    @PostMapping
    public String addBookBorrowing(@RequestBody BorrowRequestModel model) {
        try {
            CreateBorrowCommand command = new CreateBorrowCommand(
                    UUID.randomUUID().toString(),
                    model.getBookId(),
                    model.getEmployeeId(),
                    new Date()
            );
            commandGateway.sendAndWait(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Book borrowing added";
    }
}
