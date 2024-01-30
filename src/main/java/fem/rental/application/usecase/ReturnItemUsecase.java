package fem.rental.application.usecase;

import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;

import java.time.LocalDate;

public interface ReturnItemUsecase {
    RentalCardOutputDTO returnItem(UserItemInputDTO userItemInputDTO, LocalDate now);
}
