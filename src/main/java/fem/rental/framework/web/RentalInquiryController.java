package fem.rental.framework.web;

import fem.rental.application.usecase.InquiryUsecase;
import fem.rental.framework.web.dto.RentItemOutputDTO;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.ResponseInquiryDTO;
import fem.rental.framework.web.dto.ReturnItemOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentalCard")
@RequiredArgsConstructor
public class RentalInquiryController {
    private final InquiryUsecase inquiryUsecase;

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseInquiryDTO> findRentalCard(@PathVariable("userId") String userId) {
        Optional<RentalCardOutputDTO> optional = inquiryUsecase.findRentalCard(userId);
        return ResponseEntity.ok(
            ResponseInquiryDTO.builder()
                    .isEmpty(optional.isEmpty())
                    .data(optional.orElse(null))
                    .build()
        );
    }

    @GetMapping("/{userId}/rent")
    public ResponseEntity<ResponseInquiryDTO> findAllRentItem(@PathVariable String userId) {
        List<RentItemOutputDTO> allRentItems = inquiryUsecase.findAllRentItems(userId);
        return ResponseEntity.ok(
                ResponseInquiryDTO.builder()
                        .isEmpty(allRentItems.isEmpty())
                        .data(allRentItems)
                        .build()
        );
    }

    @GetMapping("/{userId}/return")
    public ResponseEntity<ResponseInquiryDTO> findAllReturnItem(@PathVariable String userId) {
        List<ReturnItemOutputDTO> allReturnItems = inquiryUsecase.findAllReturnItems(userId);
        return ResponseEntity.ok(
                ResponseInquiryDTO.builder()
                        .isEmpty(allReturnItems.isEmpty())
                        .data(allReturnItems)
                        .build()
        );
    }
}
