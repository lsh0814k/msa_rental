package fem.rental.domain.model;


import fem.rental.domain.model.vo.Item;
import lombok.*;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter @Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = "item")
public class RentalItem {
    private Item item;
    private LocalDate rentDate;
    private boolean overdue;
    private LocalDate returnDueDate;

    public static RentalItem createRentalItem(Item item) {
        return RentalItem.builder()
                .item(item)
                .rentDate(LocalDate.now())
                .overdue(false)
                .returnDueDate(LocalDate.now().plusDays(14))
                .build();
    }

    public void changeOverdueItem() {
        this.overdue = true;
    }
}
