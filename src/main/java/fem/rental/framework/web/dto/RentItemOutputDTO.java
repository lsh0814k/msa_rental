package fem.rental.framework.web.dto;

import fem.rental.domain.model.RentalItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class RentItemOutputDTO {
    private Long itemId;
    private String itemTitle;
    private LocalDate rentDate;
    private boolean overdue;
    private LocalDate returnDueDate;

    public static RentItemOutputDTO mapToDTO(RentalItem rentalItem) {
        return RentItemOutputDTO.builder()
                .itemId(rentalItem.getItem().getNo())
                .itemTitle(rentalItem.getItem().getTitle())
                .rentDate(rentalItem.getRentDate())
                .overdue(rentalItem.isOverdue())
                .returnDueDate(rentalItem.getReturnDueDate())
                .build();
    }
}
