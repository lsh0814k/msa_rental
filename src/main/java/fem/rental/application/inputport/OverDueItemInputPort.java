package fem.rental.application.inputport;

import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.OverdueItemUsecase;
import fem.rental.domain.model.RentalCard;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class OverDueItemInputPort implements OverdueItemUsecase {
    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public RentalCardOutputDTO overDueItem(UserItemInputDTO userItemDto, LocalDate now) {
        RentalCard rentalCard = rentalCardOutputPort.findByUserId(userItemDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));
        rentalCard.makeOverdueItems(now);

        return RentalCardOutputDTO.mapToDTO(rentalCard);
    }
}
