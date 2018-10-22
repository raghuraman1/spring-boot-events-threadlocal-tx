package events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class RepositoryConfiguration {
	@Bean
	PersonEventHandler personEventHandler() {
        return new PersonEventHandler();
    }
	
	@Bean
	PetEventHandler petEventHandler() {
        return new PetEventHandler();
    }
	
}
