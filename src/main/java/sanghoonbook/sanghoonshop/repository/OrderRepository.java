package sanghoonbook.sanghoonshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sanghoonbook.sanghoonshop.domain.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o join o.member m where m.name = :name")
    Page<Order> findOrdersByMemberName(@Param("name") String name, Pageable pageable);
}
