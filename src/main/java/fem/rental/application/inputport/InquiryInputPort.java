package fem.rental.application.inputport;

import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.InquiryUsecase;
import fem.rental.framework.web.dto.RentItemOutputDTO;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.ReturnItemOutputDTO;
import fem.rental.framework.web.dto.UserInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryInputPort implements InquiryUsecase {

    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public Optional<RentalCardOutputDTO> findRetalCard(UserInputDTO userInputDto) {
        return rentalCardOutputPort.findById(userInputDto.getUserId())
                .map(RentalCardOutputDTO::mapToDTO);
    }

    @Override
    public List<RentItemOutputDTO> findAllRentItems(UserInputDTO userInputDto) {
        return rentalCardOutputPort.findById(userInputDto.getUserId())
                .map(r -> r.getRentalItems().stream()
                        .map(RentItemOutputDTO::mapToDTO)
                        .toList()
                ).get();
    }

    @Override
    public List<ReturnItemOutputDTO> findAllReturnItems(UserInputDTO userInputDto) {
        return rentalCardOutputPort.findById(userInputDto.getUserId())
                .map(r -> r.getReturnItems().stream()
                        .map(ReturnItemOutputDTO::mapToDTO)
                        .toList()
                ).get();
    }
}
