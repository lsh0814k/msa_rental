package fem.rental.domain.model;


import fem.rental.domain.model.vo.Item;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter @Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = "item")
@Entity
public class RentalItem {
    @GeneratedValue @Id
    @Column(name = "rental_item_id")
    private Long id;
    @Embedded
    private Item item;
    private LocalDate rentDate;
    private boolean overdue;
    private LocalDate returnDueDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "rental_card_id")
    private RentalCard rentalCard;

    public static RentalItem createRentalItem(Item item, RentalCard rentalCard) {
        return RentalItem.builder()
                .item(item)
                .rentDate(LocalDate.now())
                .overdue(false)
                .returnDueDate(LocalDate.now().plusDays(14))
                .rentalCard(rentalCard)
                .build();
    }

    public void changeOverdueItem() {
        this.overdue = true;
    }
}
