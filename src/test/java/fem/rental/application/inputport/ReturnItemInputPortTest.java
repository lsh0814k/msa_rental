package fem.rental.application.inputport;


import fem.rental.UserItemInputDTOFactory;
import fem.rental.application.usecase.RentItemUsecase;
import fem.rental.framework.jpaadapter.RentalCardRepository;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ReturnItemInputPortTest {
    @Autowired private ReturnItemInputPort returnItemInputPort;
    @Autowired private RentItemUsecase rentItemUsecase;
    @Autowired private RentalCardRepository rentalCardRepository;

    @BeforeEach
    void init() {
        rentalCardRepository.deleteAll();
    }

    @Test
    @DisplayName("도서 반납")
    void returnItem() {
        UserItemInputDTO userItemInputDTO = UserItemInputDTOFactory.create();
        rentItemUsecase.rentItem(userItemInputDTO);
        RentalCardOutputDTO rentalCardOutputDTO = returnItemInputPort.returnItem(userItemInputDTO);

        assertThat(rentalCardOutputDTO.getTotalRentCnt()).isEqualTo(0);
        assertThat(rentalCardOutputDTO.getTotalReturnCnt()).isEqualTo(1);
        assertThat(rentalCardOutputDTO.getTotalLateFee()).isEqualTo(0);
    }
}