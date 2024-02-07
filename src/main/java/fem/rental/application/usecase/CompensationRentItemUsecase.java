package fem.rental.application.usecase;

import fem.rental.domain.model.RentalCard;
import fem.rental.domain.model.vo.IDName;
import fem.rental.domain.model.vo.Item;

public interface CompensationRentItemUsecase {
    RentalCard cancelRentItem(IDName idName, Item item, long point);
}
