package fem.rental.application.inputport;

import fem.rental.UserInputDTOFactory;
import fem.rental.domain.model.vo.RentStatus;
import fem.rental.framework.jpaadapter.RentalCardRepository;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserInputDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CreateRentalCardInputPortTest {
    @Autowired private RentalCardRepository rentalCardRepository;
    @Autowired private CreateRentalCardInputPort createRentalCardInputPort;

    @AfterEach
    void clear() {
        rentalCardRepository.deleteAll();
    }

    @Test
    @DisplayName("대여 카드 생성")
    void createRentalCard() {
        UserInputDTO userInputDTO = UserInputDTOFactory.create();
        RentalCardOutputDTO rentalCardOutputDTO = createRentalCardInputPort.createRentalCard(userInputDTO);

        assertThat(rentalCardOutputDTO.getRentalCardId()).isNotNull();
        assertThat(rentalCardOutputDTO.getRentStatus()).isEqualTo(RentStatus.RENT_AVAILABLE.toString());
        assertThat(rentalCardOutputDTO.getMemberId()).isEqualTo(userInputDTO.getUserId());
        assertThat(rentalCardOutputDTO.getTotalRentCnt()).isEqualTo(0);
        assertThat(rentalCardOutputDTO.getTotalReturnCnt()).isEqualTo(0);
        assertThat(rentalCardOutputDTO.getTotalOverDueCnt()).isEqualTo(0);
        assertThat(rentalCardOutputDTO.getTotalLateFee()).isEqualTo(0);
    }


}