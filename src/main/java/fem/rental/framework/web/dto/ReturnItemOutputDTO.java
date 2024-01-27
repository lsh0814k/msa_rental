package fem.rental.framework.web.dto;

import fem.rental.domain.model.vo.ReturnItem;
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
public class ReturnItemOutputDTO {
    private Long itemNo;
    private String itemTitle;
    private LocalDate returnDate;

    public static ReturnItemOutputDTO mapToDTO(ReturnItem returnItem) {
        return ReturnItemOutputDTO.builder()
                .itemNo(returnItem.getRentalItem().getItem().getNo())
                .itemTitle(returnItem.getRentalItem().getItem().getTitle())
                .returnDate(returnItem.getReturnDate())
                .build();
    }
}
