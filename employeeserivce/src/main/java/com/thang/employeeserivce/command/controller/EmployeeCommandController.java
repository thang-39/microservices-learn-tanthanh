package com.thang.employeeserivce.command.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thang.employeeserivce.command.command.CreateEmployeeCommand;
import com.thang.employeeserivce.command.command.DeleteEmployeeCommand;
import com.thang.employeeserivce.command.command.UpdateEmployeeCommand;
import com.thang.employeeserivce.command.model.EmployeeRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;



import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@EnableBinding(Source.class)
public class EmployeeCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private MessageChannel output;

    @PostMapping
    public String addEmployee(@RequestBody EmployeeRequestModel model) {
        CreateEmployeeCommand command =
                new CreateEmployeeCommand(
                        UUID.randomUUID().toString(),
                        model.getFirstName(),
                        model.getLastName(),
                        model.getKin(),
                        false
                );
        commandGateway.sendAndWait(command);
        return "employee added";
    }


    @PutMapping
    public String updateEmployee(@RequestBody EmployeeRequestModel model) {
        UpdateEmployeeCommand command =
                new UpdateEmployeeCommand(
                        model.getEmployeeId(),
                        model.getFirstName(),
                        model.getLastName(),
                        model.getKin(),
                        model.getIsDisciplined()
                );
        commandGateway.sendAndWait(command);
        return "employee updated";
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable String employeeId) {
        DeleteEmployeeCommand command =
                new DeleteEmployeeCommand(employeeId);
        commandGateway.sendAndWait(command);
        return "employee deleted";
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(message);
            output.send(MessageBuilder.withPayload(json).build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}