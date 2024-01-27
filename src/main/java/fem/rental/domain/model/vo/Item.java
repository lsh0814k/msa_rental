package fem.rental.domain.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = "no")
@Embeddable
public class Item {
    @Column(name = "item_no")
    private Long no;
    @Column(name = "item_title")
    private String title;

    public static Item createItem(Long no, String title) {
       return new Item(no, title);
    }
}
