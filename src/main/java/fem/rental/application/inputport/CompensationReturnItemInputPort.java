package fem.rental.application.inputport;

import fem.rental.application.outputport.EventPointUseOutputPort;
import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.CompensationReturnItemUsecase;
import fem.rental.domain.model.RentalCard;
import fem.rental.domain.model.event.PointCommand;
import fem.rental.domain.model.vo.IDName;
import fem.rental.domain.model.vo.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompensationReturnItemInputPort implements CompensationReturnItemUsecase {
    private final RentalCardOutputPort rentalCardOutputPort;
    private final EventPointUseOutputPort eventPointUseOutputPort;

    @Override
    public RentalCard cancelReturnItem(IDName idName, Item item, long point) {
        RentalCard rentalCard = rentalCardOutputPort.findByUserId(idName.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));
        rentalCard.cancelReturnItem(item);
        eventPointUseOutputPort.occurPointUseCommand(PointCommand.create(idName, point));
        return rentalCard;
    }
}
