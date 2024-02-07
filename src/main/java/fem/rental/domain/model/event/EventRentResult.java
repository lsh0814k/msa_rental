package fem.rental.domain.model.event;

import fem.rental.domain.model.vo.IDName;
import fem.rental.domain.model.vo.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class EventRentResult {
    private boolean isSuccess;
    private IDName idName;
    private Item item;
    private long point;
}
