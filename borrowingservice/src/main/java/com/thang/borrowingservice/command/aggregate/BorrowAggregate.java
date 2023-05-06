package com.thang.borrowingservice.command.aggregate;

import com.thang.borrowingservice.command.command.*;
import com.thang.borrowingservice.command.event.*;
import com.thang.commonservice.command.RollBackStatusBookCommand;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class BorrowAggregate {

    @AggregateIdentifier
    private String id;

    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;
    private String message;

    @CommandHandler
    public BorrowAggregate(CreateBorrowCommand command) {
        BorrowCreatedEvent event = new BorrowCreatedEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateBorrowCommand command) {
        BorrowCreatedEvent event = new BorrowCreatedEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteBorrowCommand command) {
        BorrowDeletedEvent event = new BorrowDeletedEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(SendMessageCommand command) {
        BorrowSendMessageEvent event = new BorrowSendMessageEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateBookReturnCommand command) {
        BorrowUpdateBookReturnEvent event = new BorrowUpdateBookReturnEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowCreatedEvent event) {
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.borrowingDate = event.getBorrowingDate();
    }

    @EventSourcingHandler
    public void on(BorrowUpdatedEvent event) {
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.borrowingDate = event.getBorrowingDate();
        this.returnDate = event.getReturnDate();
    }

    @EventSourcingHandler
    public void on(BorrowDeletedEvent event) {
        this.id = event.getId();
    }

    @EventSourcingHandler
    public void on(BorrowSendMessageEvent event) {
        this.id = event.getId();
        this.employeeId = event.getEmployeeId();
        this.message = event.getMessage();
    }

    @EventSourcingHandler
    public void on(BorrowUpdateBookReturnEvent event) {

        this.bookId = event.getBookId();
        this.employeeId = event.getEmployee();
        this.returnDate = event.getReturnDate();
    }


}
