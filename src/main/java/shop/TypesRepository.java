package shop;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TypesRepository extends CrudRepository<Type, Long> {

    List<Type> findByName(String name);
}
