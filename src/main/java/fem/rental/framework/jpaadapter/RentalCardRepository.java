package fem.rental.framework.jpaadapter;

import fem.rental.domain.model.RentalCard;
import fem.rental.domain.model.vo.RentalCardNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RentalCardRepository extends JpaRepository<RentalCard, RentalCardNo> {
    @Query(value = "select r from RentalCard r where r.member.id = :id")
    Optional<RentalCard> findByUserId(@Param("id") String id);

    @Query(value = "select r from RentalCard r join fetch r.rentalItems where r.member.id = :id")
    Optional<RentalCard> findWithRentItemByUserId(@Param("id") String id);

    @Query(value = "select r from RentalCard  r join fetch r.returnItems where r.member.id = :id")
    Optional<RentalCard> findWithReturnItemByUserId(@Param("id") String id);
}
