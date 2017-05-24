package shop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TypesRepository extends JpaRepository<Type, Long> {

    List<Type> findByName(String name);
}
