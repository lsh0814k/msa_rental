package fem.rental.application.inputport;

import fem.rental.application.outputport.EventRentOutputPort;
import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.CreateRentalCardUsecase;
import fem.rental.application.usecase.RentItemUsecase;
import fem.rental.domain.model.RentalCard;
import fem.rental.domain.model.event.ItemRented;
import fem.rental.domain.model.vo.Item;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserInputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RentItemInputPort implements RentItemUsecase {

    private final RentalCardOutputPort rentalCardOutputPort;
    private final CreateRentalCardUsecase createRentalCardUsecase;
    private final EventRentOutputPort EventRentOutputPort;

    @Override
    public RentalCardOutputDTO rentItem(UserItemInputDTO userItemInputDTO) {
        RentalCard rentalCard = loadRentalCard(userItemInputDTO);
        Item item = Item.createItem(userItemInputDTO.getItemId(), userItemInputDTO.getItemTitle());
        rentalCard.rentItem(item);

        // 대여 이벤트 생성 및 발행
        ItemRented itemRentedEvent = RentalCard.createItemRentedEvent(rentalCard.getMember(), item, 10);
        EventRentOutputPort.occurRentalEvent(itemRentedEvent);

        return RentalCardOutputDTO.mapToDTO(rentalCard);
    }

    /**
     * rental card 없다면 생성하고 반환
     * @param userItemInputDto
     * @return
     */
    private RentalCard loadRentalCard(UserItemInputDTO userItemInputDto) {
        Optional<RentalCard> rentalCardOptional = rentalCardOutputPort.findByUserId(userItemInputDto.getUserId());
        if (rentalCardOptional.isPresent()) {
            return rentalCardOptional.get();
        }

        createRentalCardUsecase.createRentalCard(UserInputDTO.createUserInputDTO(userItemInputDto));
        return rentalCardOutputPort.findByUserId(userItemInputDto.getUserId()).get();
    }
}
