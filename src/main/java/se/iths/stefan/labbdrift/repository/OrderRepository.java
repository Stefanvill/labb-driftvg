package se.iths.stefan.labbdrift.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.stefan.labbdrift.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface OrderRepository extends JpaRepository<Order, Long> {


}
