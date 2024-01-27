package fem.rental.application.inputport;

import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.InquiryUsecase;
import fem.rental.framework.web.dto.RentItemOutputDTO;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.ReturnItemOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryInputPort implements InquiryUsecase {

    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public Optional<RentalCardOutputDTO> findRentalCard(String userId) {
        return rentalCardOutputPort.findByUserId(userId)
                .map(RentalCardOutputDTO::mapToDTO);
    }

    @Override
    public List<RentItemOutputDTO> findAllRentItems(String userId) {
        return rentalCardOutputPort.findWithRentItemByUserId(userId)
                .map(r -> r.getRentalItems().stream()
                        .map(RentItemOutputDTO::mapToDTO)
                        .toList()
                ).orElse(new ArrayList<>());
    }

    @Override
    public List<ReturnItemOutputDTO> findAllReturnItems(String userId) {
        return rentalCardOutputPort.findWithReturnItemByUserId(userId)
                .map(r -> r.getReturnItems().stream()
                        .map(ReturnItemOutputDTO::mapToDTO)
                        .toList()
                ).orElse(new ArrayList<>());
    }
}
