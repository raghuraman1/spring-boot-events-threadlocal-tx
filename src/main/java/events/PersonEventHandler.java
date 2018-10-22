package events;

import org.springframework.context.ApplicationEvent;

public class PersonEventHandler extends BaseEventHandler<Person> {

	
	@Override
	protected void onBeforeSave(Person entity) {
		
		super.onBeforeSave(entity);
	}

	@Override
	protected void onAfterSave(Person entity) {
		
		if(entity.getFirstName().equals("e"))
		{
			throw new RuntimeException("e not allowed");
		}
		
		super.onAfterSave(entity);
	}

	

}
