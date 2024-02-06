package fem.rental.application.inputport;

import fem.rental.application.outputport.EventReturnOutputPort;
import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.ReturnItemUsecase;
import fem.rental.domain.model.RentalCard;
import fem.rental.domain.model.event.ItemReturned;
import fem.rental.domain.model.vo.Item;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ReturnItemInputPort implements ReturnItemUsecase {

    private final RentalCardOutputPort rentalCardOutputPort;
    private final EventReturnOutputPort eventReturnOutputPort;

    @Override
    public RentalCardOutputDTO returnItem(UserItemInputDTO userItemInputDto, LocalDate now) {
        RentalCard rentalCard = rentalCardOutputPort.findByUserId(userItemInputDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));
        Item item = Item.createItem(userItemInputDto.getItemId(), userItemInputDto.getItemTitle());
        rentalCard.returnItem(item, now);

        ItemReturned itemReturnedEvent = RentalCard.createItemReturnedEvent(rentalCard.getMember(), item, 10);
        eventReturnOutputPort.occurReturnEvent(itemReturnedEvent);

        return RentalCardOutputDTO.mapToDTO(rentalCard);
    }
}
