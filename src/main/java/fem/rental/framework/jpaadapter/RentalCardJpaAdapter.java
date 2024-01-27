package fem.rental.framework.jpaadapter;

import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.domain.model.RentalCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RentalCardJpaAdapter implements RentalCardOutputPort {

    private final RentalCardRepository rentalCardRepository;

    @Override
    public Optional<RentalCard> findByUserId(String userId) {
        return rentalCardRepository.findByUserId(userId);
    }

    @Override
    public RentalCard save(RentalCard rentalCard) {
        return rentalCardRepository.save(rentalCard);
    }
}
