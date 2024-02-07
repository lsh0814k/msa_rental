package fem.rental.application.usecase;

import fem.rental.domain.model.vo.IDName;

public interface CompensationClearOverdueItemUsecase {
    void cancelMakeAvailableRental(IDName idName, long point);
}
