package fem.rental.application.usecase;

import fem.rental.framework.web.dto.RentalResultOutputDTO;
import fem.rental.framework.web.dto.ClearOverdueInfoDTO;

public interface ClearOverdueItemUsecase {
    RentalResultOutputDTO clearOverdue(ClearOverdueInfoDTO clearOverdueInfoDTO);
}
