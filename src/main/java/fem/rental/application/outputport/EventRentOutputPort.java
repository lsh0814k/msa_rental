package fem.rental.application.outputport;

import fem.rental.domain.model.event.ItemRented;

public interface EventRentOutputPort {
    void occurRentalEvent(ItemRented itemRented);
}
