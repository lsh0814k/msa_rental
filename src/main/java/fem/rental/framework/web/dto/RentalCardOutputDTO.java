package fem.rental.framework.web.dto;

import fem.rental.domain.model.RentalCard;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class RentalCardOutputDTO {
    private String rentalCardId;
    private String memberId;
    private String memberName;
    private String rentStatus;
    private Long totalLateFee;
    private Integer totalRentCnt;
    private Integer totalReturnCnt;
    private Integer totalOverDueCnt;

    public static RentalCardOutputDTO mapToDTO(RentalCard rentalCard) {
        return RentalCardOutputDTO
                .builder()
                .rentalCardId(rentalCard.getRentalCardNo().getNo())
                .memberId(rentalCard.getMember().getId())
                .memberName(rentalCard.getMember().getName())
                .rentStatus(rentalCard.getRentStatus().toString())
                .totalLateFee(rentalCard.getLateFee().getPoint())
                .totalRentCnt(rentalCard.getRentalItems().size())
                .totalReturnCnt(rentalCard.getReturnItems().size())
                .totalOverDueCnt(rentalCard.overdueItems().size())
                .build();
    }
}
