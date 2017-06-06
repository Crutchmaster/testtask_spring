package shop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypesRepository extends JpaRepository<ItemType, Long> {

    List<ItemType> findByName(String name);
}
