package events;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pet", path = "pet")
public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {

	List<Person> findByName(@Param("name") String name);

}