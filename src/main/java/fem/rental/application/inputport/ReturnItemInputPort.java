package fem.rental.application.inputport;

import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.ReturnItemUsecase;
import fem.rental.domain.model.RentalCard;
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

    @Override
    public RentalCardOutputDTO returnItem(UserItemInputDTO userItemInputDto) {
        RentalCard rentalCard = rentalCardOutputPort.findById(userItemInputDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));
        rentalCard.returnItem(Item.createItem(userItemInputDto.getItemId(), userItemInputDto.getItemTitle()), LocalDate.now());

        return RentalCardOutputDTO.mapToDTO(rentalCard);
    }
}
