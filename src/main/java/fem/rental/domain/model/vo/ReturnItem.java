package fem.rental.domain.model.vo;

import fem.rental.domain.model.RentalItem;
import lombok.*;

import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Getter @Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "rentalItem")
public class ReturnItem {
    private RentalItem rentalItem;
    private LocalDate returnDate;

    public static ReturnItem createReturnItem(RentalItem rentalItem) {
        return ReturnItem
                .builder()
                .rentalItem(rentalItem)
                .returnDate(LocalDate.now())
                .build();
    }
}
