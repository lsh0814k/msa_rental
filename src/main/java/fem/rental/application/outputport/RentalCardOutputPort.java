package fem.rental.application.outputport;

import fem.rental.domain.model.RentalCard;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalCardOutputPort {
    Optional<RentalCard> findById(String userId);

    RentalCard save(RentalCard rentalCard);
}
