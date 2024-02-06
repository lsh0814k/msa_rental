package fem.rental.domain.model.event;

import fem.rental.domain.model.vo.IDName;
import fem.rental.domain.model.vo.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Builder(access = PROTECTED)
public class ItemReturned implements Serializable {
    private IDName idName;
    private Item item;
    private long point;

    public static ItemReturned create(IDName idName, Item item, long point) {
        return ItemReturned
                .builder()
                .idName(idName)
                .item(item)
                .point(point)
                .build();
    }
}
