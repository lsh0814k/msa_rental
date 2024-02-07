package fem.rental.application.inputport;

import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.CompensationClearOverdueItemUsecase;
import fem.rental.domain.model.RentalCard;
import fem.rental.domain.model.vo.IDName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompensationClearOverdueItemInputPort implements CompensationClearOverdueItemUsecase {
    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public void cancelMakeAvailableRental(IDName idName, long point) {
        RentalCard rentalCard = rentalCardOutputPort.findByUserId(idName.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));
        rentalCard.cancelMakeAvailableRental(point);
    }
}
