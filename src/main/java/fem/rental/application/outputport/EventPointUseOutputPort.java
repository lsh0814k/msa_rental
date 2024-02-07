package fem.rental.application.outputport;

import fem.rental.domain.model.event.PointCommand;

public interface EventPointUseOutputPort {
    void occurPointUseCommand(PointCommand pointCommand);
}
