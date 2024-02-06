package fem.rental.application.outputport;

import fem.rental.domain.model.event.ItemReturned;

public interface EventReturnOutputPort {
    void occurReturnEvent(ItemReturned itemReturned);
}
