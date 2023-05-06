package com.thang.borrowingservice.command.data;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "borrowing")
public class Borrowing {
    @Id
    private String id;

    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;

}
