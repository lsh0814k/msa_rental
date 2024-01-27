package fem.rental.framework.web.dto;

import fem.rental.domain.model.RentalCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class RentalResultOutputDTO {
    private String userId;
    private String userNm;
    private Integer rentedCount;
    private Long totalLateFee;

    public static RentalResultOutputDTO mapToDTO(RentalCard rentalCard) {
        return RentalResultOutputDTO
                .builder()
                .userId(rentalCard.getMember().getId())
                .userNm(rentalCard.getMember().getName())
                .rentedCount(rentalCard.getRentalItems().size())
                .totalLateFee(rentalCard.getLateFee().getPoint())
                .build();
    }
}
