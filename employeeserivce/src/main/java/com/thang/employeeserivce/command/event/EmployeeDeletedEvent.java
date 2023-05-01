package com.thang.employeeserivce.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDeletedEvent {
    private String employeeId;
}
