package shop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AttrsRepository extends JpaRepository<Attr, Long> {
    List<Attr> findByName(String name);
}
