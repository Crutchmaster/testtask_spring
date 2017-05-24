package shop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ItemAttrsRepository extends JpaRepository<ItemAttr, Long> {

}
