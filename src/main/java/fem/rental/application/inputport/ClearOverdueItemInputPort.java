package fem.rental.application.inputport;

import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.ClearOverdueItemUsecase;
import fem.rental.domain.model.RentalCard;
import fem.rental.framework.web.dto.ClearOverdueInfoDTO;
import fem.rental.framework.web.dto.RentalResultOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClearOverdueItemInputPort implements ClearOverdueItemUsecase {

    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public RentalResultOutputDTO clearOverdue(ClearOverdueInfoDTO clearOverdueInfoDto) {
        RentalCard rentalCard = rentalCardOutputPort.findByUserId(clearOverdueInfoDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));
        rentalCard.makeAvailableRental(clearOverdueInfoDto.getPoint());

        return RentalResultOutputDTO.mapToDTO(rentalCard);
    }
}
