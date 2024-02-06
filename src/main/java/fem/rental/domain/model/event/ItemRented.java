package fem.rental.domain.model.event;

import fem.rental.domain.model.vo.IDName;
import fem.rental.domain.model.vo.Item;
import lombok.*;

import java.io.Serializable;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Builder(access = PROTECTED)
public class ItemRented implements Serializable {
    private IDName idName;
    private Item item;
    private long point;

    public static ItemRented create(IDName idName, Item item, long point) {
        return ItemRented
                .builder()
                .idName(idName)
                .item(item)
                .point(point)
                .build();
    }
}
