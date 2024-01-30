package fem.rental.framework.web;

import fem.rental.application.usecase.*;
import fem.rental.framework.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RentalController {
    private final RentItemUsecase rentItemUsecase;
    private final ReturnItemUsecase returnItemUsecase;
    private final CreateRentalCardUsecase createRentalCardUsecase;
    private final ClearOverdueItemUsecase clearOverdueItemUsecase;

    @PostMapping("/rentalCard")
    public ResponseEntity<RentalCardOutputDTO> createRentalCard(@RequestBody UserInputDTO userInputDTO) {
        RentalCardOutputDTO rentalCardOutputDTO = createRentalCardUsecase.createRentalCard(userInputDTO);

        return ResponseEntity.status(CREATED)
                .body(rentalCardOutputDTO);
    }

    @PostMapping("/rentalCard/{userId}/rent")
    public ResponseEntity<RentalCardOutputDTO> rentItem(@RequestBody UserItemInputDTO userItemInputDTO) {
        RentalCardOutputDTO rentalCardOutputDTO = rentItemUsecase.rentItem(userItemInputDTO);
        return ResponseEntity.status(CREATED)
                .body(rentalCardOutputDTO);
    }

    @PostMapping("/rentalCard/{userId}/return")
    public ResponseEntity<RentalCardOutputDTO> returnItem(@RequestBody UserItemInputDTO userItemInputDTO) {
        RentalCardOutputDTO rentalCardOutputDTO = returnItemUsecase.returnItem(userItemInputDTO, LocalDate.now());
        return ResponseEntity.status(CREATED)
                .body(rentalCardOutputDTO);
    }

    @PostMapping("/rentalCard/{userId}/clearOverdue")
    public ResponseEntity<RentalResultOutputDTO> clearOverdueItem(@RequestBody ClearOverdueInputDTO clearOverdueInputDTO) {
        RentalResultOutputDTO rentalResultOutputDTO = clearOverdueItemUsecase.clearOverdue(clearOverdueInputDTO);
        return ResponseEntity.status(CREATED)
                .body(rentalResultOutputDTO);
    }
}
