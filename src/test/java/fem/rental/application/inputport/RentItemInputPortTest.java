package fem.rental.application.inputport;

import fem.rental.UserItemInputDTOFactory;
import fem.rental.domain.model.vo.RentStatus;
import fem.rental.framework.jpaadapter.RentalCardRepository;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RentItemInputPortTest {
    @Autowired private RentItemInputPort rentItemInputPort;
    @Autowired private RentalCardRepository rentalCardRepository;

    @BeforeEach
    void init() {
        rentalCardRepository.deleteAll();
    }

    @Test
    @DisplayName("도서 대여")
    void rent_exist_rental_card() {
        UserItemInputDTO userItemInputDTO = UserItemInputDTOFactory.create();
        RentalCardOutputDTO rentalCardOutputDTO = rentItemInputPort.rentItem(userItemInputDTO);
        assertThat(rentalCardOutputDTO.getRentalCardId()).isNotNull();
        assertThat(rentalCardOutputDTO.getTotalLateFee()).isEqualTo(0);
        assertThat(rentalCardOutputDTO.getRentStatus()).isEqualTo(RentStatus.RENT_AVAILABLE.toString());
        assertThat(rentalCardOutputDTO.getTotalRentCnt()).isEqualTo(1);
    }
}