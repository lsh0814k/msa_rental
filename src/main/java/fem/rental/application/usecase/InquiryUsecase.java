package fem.rental.application.usecase;

import fem.rental.framework.web.dto.RentItemOutputDTO;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.ReturnItemOutputDTO;
import fem.rental.framework.web.dto.UserInputDTO;

import java.util.List;
import java.util.Optional;

public interface InquiryUsecase {
    Optional<RentalCardOutputDTO> findRetalCard(UserInputDTO userInputDto);

    List<RentItemOutputDTO> findAllRentItems(UserInputDTO userInputDto);

    List<ReturnItemOutputDTO> findAllReturnItems(UserInputDTO userInputDto);
}
