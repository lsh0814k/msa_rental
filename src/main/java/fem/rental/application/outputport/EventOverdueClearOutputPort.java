package fem.rental.application.outputport;

import fem.rental.domain.model.event.OverdueCleared;

public interface EventOverdueClearOutputPort {
    void occurOverdueClearEvent(OverdueCleared overdueCleared);
}
