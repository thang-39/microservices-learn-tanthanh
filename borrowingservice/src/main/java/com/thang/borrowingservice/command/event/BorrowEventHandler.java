package com.thang.borrowingservice.command.event;

import com.thang.borrowingservice.command.data.BorrowRepository;
import com.thang.borrowingservice.command.data.Borrowing;
import com.thang.borrowingservice.command.model.Message;
import com.thang.borrowingservice.command.service.BorrowService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowEventHandler {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BorrowService borrowService;

    @EventHandler
    public void on(BorrowCreatedEvent event) {
        Borrowing borrowing = new Borrowing();
        BeanUtils.copyProperties(event,borrowing);
        borrowRepository.save(borrowing);
    }

    @EventHandler
    public void on(BorrowDeletedEvent event) {
        if (borrowRepository.findById(event.getId()).isPresent())
         borrowRepository.deleteById(event.getId());
    }

    @EventHandler
    public void on(BorrowSendMessageEvent event) {
        Message message = new Message(event.getEmployeeId(),event.getMessage());
        borrowService.sendMessage(message);
    }

    @EventHandler
    public void on(BorrowUpdateBookReturnEvent event) {
        Borrowing entity = borrowRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(event.getEmployee(), event.getBookId());
        entity.setReturnDate(event.getReturnDate());
        borrowRepository.save(entity);
    }
}
