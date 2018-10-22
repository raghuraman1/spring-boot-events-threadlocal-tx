package events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import ch.qos.logback.classic.Logger;


public class BaseEventHandler<E> extends AbstractRepositoryEventListener<E> {

	@Autowired
	private PlatformTransactionManager transactionManager;
	public static ThreadLocal<TransactionStatus> threadLocalValue = new ThreadLocal<>();

	@Override
	protected void onBeforeSave(E entity) {
		System.out.println("before save using "+transactionManager.getClass().getName()+" of "+transactionManager);
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		threadLocalValue.set(status);
		
		super.onBeforeSave(entity);
	}

	@Override
	protected void onAfterSave(E entity) {
		System.out.println("after save using "+transactionManager.getClass().getName()+" of "+transactionManager);
		super.onAfterSave(entity);
		TransactionStatus currentTransactionStatus = threadLocalValue.get();
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
		
	}
	
	
}
