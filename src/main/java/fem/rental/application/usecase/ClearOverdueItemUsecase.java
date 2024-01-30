package fem.rental.application.usecase;

import fem.rental.framework.web.dto.RentalResultOutputDTO;
import fem.rental.framework.web.dto.ClearOverdueInputDTO;

public interface ClearOverdueItemUsecase {
    RentalResultOutputDTO clearOverdue(ClearOverdueInputDTO clearOverdueInputDTO);
}
