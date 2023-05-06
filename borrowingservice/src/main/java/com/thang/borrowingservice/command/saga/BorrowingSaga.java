package com.thang.borrowingservice.command.saga;

import com.thang.borrowingservice.command.command.DeleteBorrowCommand;
import com.thang.borrowingservice.command.command.SendMessageCommand;
import com.thang.borrowingservice.command.command.UpdateBookReturnCommand;
import com.thang.borrowingservice.command.event.BorrowCreatedEvent;
import com.thang.borrowingservice.command.event.BorrowDeletedEvent;
import com.thang.borrowingservice.command.event.BorrowUpdateBookReturnEvent;
import com.thang.commonservice.command.RollBackStatusBookCommand;
import com.thang.commonservice.command.UpdateStatusBookCommand;
import com.thang.commonservice.event.BookRollBackStatusEvent;
import com.thang.commonservice.event.BookUpdateStatusEvent;
import com.thang.commonservice.model.BookResponseCommonModel;
import com.thang.commonservice.model.EmployeeResponseCommonModel;
import com.thang.commonservice.query.GetDetailsBookQuery;
import com.thang.commonservice.query.GetDetailsEmployeeQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BorrowingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreatedEvent event) {
        System.out.println("BorrowCreateEvent is Saga for BookId : "
        + event.getBookId() + " : EmployeeId : " + event.getEmployeeId());

        try {
            SagaLifecycle.associateWith("bookId",event.getBookId());
            GetDetailsBookQuery getDetailBookQuery = new GetDetailsBookQuery(event.getBookId());

            BookResponseCommonModel bookResponseCommonModel = queryGateway
                    .query(getDetailBookQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class))
                    .join();
            if (bookResponseCommonModel.getIsReady()) {
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(
                        event.getBookId(),
                        false,
                        event.getEmployeeId(),
                        event.getId() );
                commandGateway.sendAndWait(command);
            } else {
                throw new Exception("This Book has been borrowed");
            }

        } catch (Exception e) {
            rollBackBorrowRecord(event.getId());
            System.out.println(e.getMessage());
        }

    }
    
    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookUpdateStatusEvent event) {
        System.out.println("BookUpdateStatusEvent in Saga for BookId : " + event.getBookId());
        try {
            GetDetailsEmployeeQuery getDetailsEmployeeQuery = new GetDetailsEmployeeQuery(event.getEmployeeId());

            EmployeeResponseCommonModel employeeResponseCommonModel = queryGateway
                    .query(getDetailsEmployeeQuery,
                            ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
                    .join();
            
            if (employeeResponseCommonModel.getIsDisciplined()) {
                throw new Exception("Nhan Vien Bi Ky Luat");
            } else {
                commandGateway.sendAndWait(new SendMessageCommand(event.getBorrowId(),
                        event.getEmployeeId(),
                        "Da muon sach thanh cong!"));
                SagaLifecycle.end();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rollBackBookStatus(event.getBookId(),event.getEmployeeId(),event.getBorrowId());
        }
    }

    private void rollBackBookStatus(String bookId, String employeeId, String borrowId) {
        SagaLifecycle.associateWith("bookId", bookId);
        RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId,true,employeeId,borrowId);
        commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "bookId")
    public void handleRollBackBookStatus(BookRollBackStatusEvent event) {
        System.out.println("BookRollBackStatusEvent in Saga for book Id : " + event.getBookId());
        rollBackBorrowRecord(event.getBorrowId());
    }

    private void rollBackBorrowRecord(String id) {
        commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void handle(BorrowDeletedEvent event) {
        System.out.println("BorrowDeletedEvent in Saga for Borrowing Id: " + event.getId());
        SagaLifecycle.end();
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowUpdateBookReturnEvent event) {
        System.out.println("BorrowingUpdateBookReturnEvent in Saga for borrowing Id : " + event.getId());
        try {
            UpdateStatusBookCommand command = new UpdateStatusBookCommand(
                    event.getBookId(),
                    true,
                    event.getEmployee(),
                    event.getId()
            );
            commandGateway.sendAndWait(command);
            commandGateway.sendAndWait(new SendMessageCommand(
                    event.getId(),
                    event.getEmployee(),
                    "Đã Trả Sách Thành Công!"
            ));
            SagaLifecycle.end();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
