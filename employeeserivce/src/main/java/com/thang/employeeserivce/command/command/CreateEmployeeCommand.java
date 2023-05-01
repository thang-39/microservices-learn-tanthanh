package com.thang.employeeserivce.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateEmployeeCommand {

    @TargetAggregateIdentifier
    private String employeeId;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;
}
