package fem.rental.application.inputport;

import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.CreateRentalCardUsecase;
import fem.rental.application.usecase.RentItemUsecase;
import fem.rental.domain.model.RentalCard;
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

    @Override
    public RentalCardOutputDTO rentItem(UserItemInputDTO userItemInputDTO) {
        RentalCard rentalCard = loadRentalCard(userItemInputDTO);
        rentalCard.rentItem(Item.createItem(userItemInputDTO.getItemId(), userItemInputDTO.getItemTitle()));

        return RentalCardOutputDTO.mapToDTO(rentalCard);
    }

    /**
     * rental card 없다면 생성하고 반환
     * @param userItemInputDto
     * @return
     */
    private RentalCard loadRentalCard(UserItemInputDTO userItemInputDto) {
        Optional<RentalCard> rentalCardOptional = rentalCardOutputPort.findById(userItemInputDto.getUserId());
        if (rentalCardOptional.isPresent()) {
            return rentalCardOptional.get();
        }

        createRentalCardUsecase.createRentalCard(UserInputDTO.createUserInputDTO(userItemInputDto));
        return rentalCardOutputPort.findById(userItemInputDto.getUserId()).get();
    }
}
