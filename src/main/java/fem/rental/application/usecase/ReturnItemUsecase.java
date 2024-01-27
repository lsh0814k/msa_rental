package fem.rental.application.usecase;

import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;

public interface ReturnItemUsecase {
    RentalCardOutputDTO returnItem(UserItemInputDTO userItemInputDTO);
}
