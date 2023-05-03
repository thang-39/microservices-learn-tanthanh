package com.thang.borrowingservice.command.saga;

import com.thang.borrowingservice.command.command.DeleteBorrowCommand;
import com.thang.borrowingservice.command.event.BorrowCreatedEvent;
import com.thang.commonservice.command.UpdateStatusBookCommand;
import com.thang.commonservice.model.BookResponseCommonModel;
import com.thang.commonservice.query.GetDetailsBookQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
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

    private void rollBackBorrowRecord(String id) {
        commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }
}
