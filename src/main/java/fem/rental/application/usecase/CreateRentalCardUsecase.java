package fem.rental.application.usecase;

import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserInputDTO;

public interface CreateRentalCardUsecase {
    RentalCardOutputDTO createRentalCard(UserInputDTO userInputDto);
}
