package fem.rental.domain.model;

import fem.rental.domain.model.vo.Item;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Getter @Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "rentalItem")
@Entity
public class ReturnItem {
    @GeneratedValue @Id
    @Column(name = "return_item_id")
    private Long id;
    @Embedded
    private Item item;
    private LocalDate rentDate;
    private boolean overdue;
    private LocalDate returnDueDate;
    private LocalDate returnDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "rental_card_id")
    private RentalCard rentalCard;

    public static ReturnItem createReturnItem(RentalItem rentalItem) {
        return ReturnItem
                .builder()
                .item(rentalItem.getItem())
                .rentDate(rentalItem.getRentDate())
                .overdue(rentalItem.isOverdue())
                .returnDueDate(rentalItem.getReturnDueDate())
                .returnDate(LocalDate.now())
                .build();
    }
}
