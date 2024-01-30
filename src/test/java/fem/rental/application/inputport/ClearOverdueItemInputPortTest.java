package fem.rental.application.inputport;

import fem.rental.ClearOverdueInputDTOFactory;
import fem.rental.UserItemInputDTOFactory;
import fem.rental.application.usecase.OverdueItemUsecase;
import fem.rental.application.usecase.RentItemUsecase;
import fem.rental.application.usecase.ReturnItemUsecase;
import fem.rental.framework.jpaadapter.RentalCardRepository;
import fem.rental.framework.web.dto.RentalResultOutputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ClearOverdueItemInputPortTest {
    @Autowired private RentalCardRepository rentalCardRepository;
    @Autowired private ClearOverdueItemInputPort clearOverdueItemInputPort;
    @Autowired private RentItemUsecase rentItemUsecase;
    @Autowired private ReturnItemUsecase returnItemUsecase;
    @Autowired private OverdueItemUsecase overDueItem;


    @BeforeEach
    void init() {
        rentalCardRepository.deleteAll();
    }

    @Test
    @DisplayName("연체 해제_연체된 도서를 반납하지 않은 경우")
    void clearOverdue_not_return() {
        UserItemInputDTO userItemInputDTO = UserItemInputDTOFactory.create();
        rentItemUsecase.rentItem(userItemInputDTO);

        overDueItem.overDueItem(userItemInputDTO, LocalDate.now().plusDays(15));

        assertThatThrownBy(() -> clearOverdueItemInputPort.clearOverdue(ClearOverdueInputDTOFactory.create()))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isEqualTo("모든 도서가 반납되어야 정지를 해제할 수 있습니다.");
    }

    @Test
    @DisplayName("연체 해제_포인트 부족")
    void clearOverdue_not_enough() {
        UserItemInputDTO userItemInputDTO = UserItemInputDTOFactory.create();
        rentItemUsecase.rentItem(userItemInputDTO);

        overDueItem.overDueItem(userItemInputDTO, LocalDate.now().plusDays(16));
        returnItemUsecase.returnItem(userItemInputDTO, LocalDate.now().plusDays(16));

        assertThatThrownBy(() -> clearOverdueItemInputPort.clearOverdue(ClearOverdueInputDTOFactory.create()))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isEqualTo("해당 포인트로 연체를 해제할 수 없습니다.");
    }

    @Test
    @DisplayName("연체 해제")
    void clearOverdue() {
        UserItemInputDTO userItemInputDTO = UserItemInputDTOFactory.create();
        rentItemUsecase.rentItem(userItemInputDTO);

        overDueItem.overDueItem(userItemInputDTO, LocalDate.now().plusDays(15));
        returnItemUsecase.returnItem(userItemInputDTO, LocalDate.now().plusDays(15));

        RentalResultOutputDTO rentalResultOutputDTO = clearOverdueItemInputPort.clearOverdue(ClearOverdueInputDTOFactory.create());
        assertThat(rentalResultOutputDTO.getRentedCount()).isEqualTo(0);
        assertThat(rentalResultOutputDTO.getTotalLateFee()).isEqualTo(0);
        assertThat(rentalResultOutputDTO.getRentedCount()).isEqualTo(0);
    }
}