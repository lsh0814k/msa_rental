package fem.rental.application.usecase;

import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;

public interface OverdueItemUsecase {
    RentalCardOutputDTO overDueItem(UserItemInputDTO userItemDTO);
}
