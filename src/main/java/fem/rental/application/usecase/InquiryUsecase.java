package fem.rental.application.usecase;

import fem.rental.framework.web.dto.RentItemOutputDTO;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.ReturnItemOutputDTO;

import java.util.List;
import java.util.Optional;

public interface InquiryUsecase {
    Optional<RentalCardOutputDTO> findRentalCard(String userId);

    List<RentItemOutputDTO> findAllRentItems(String userId);

    List<ReturnItemOutputDTO> findAllReturnItems(String userId);
}
