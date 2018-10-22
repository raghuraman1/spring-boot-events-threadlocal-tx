package events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice

public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private PlatformTransactionManager transactionManager;
	
  @ExceptionHandler(Throwable.class)
  public final ResponseEntity<String> handleUncaught(Throwable ex, WebRequest request) {
	  System.out.println("caught exception "+ex.getMessage());
	  ex.printStackTrace();
	  TransactionStatus currentTransactionStatus = BaseEventHandler.threadLocalValue.get();
	  currentTransactionStatus.setRollbackOnly();
	  if(!currentTransactionStatus.isCompleted())
		{
			if(!currentTransactionStatus.isRollbackOnly())
			{
				transactionManager.commit(currentTransactionStatus);	
			}
			else
			{
				transactionManager.rollback(currentTransactionStatus);
			}
		}
//    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
//        request.getDescription(false));
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}