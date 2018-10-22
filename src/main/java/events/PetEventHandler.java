package events;

import org.springframework.context.ApplicationEvent;

public class PetEventHandler extends BaseEventHandler<Pet> {

	
	@Override
	protected void onBeforeSave(Pet entity) {
		
		super.onBeforeSave(entity);
	}

	@Override
	protected void onAfterSave(Pet entity) {
		
		if(entity.getName().equals("e"))
		{
			throw new RuntimeException("e not allowed");
		}
		
		super.onAfterSave(entity);
	}

	

}
