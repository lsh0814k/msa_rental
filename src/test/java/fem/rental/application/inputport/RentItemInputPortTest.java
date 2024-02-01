package fem.rental.application.inputport;

import fem.rental.UserItemInputDTOFactory;
import fem.rental.domain.model.RentalCard;
import fem.rental.domain.model.vo.RentStatus;
import fem.rental.framework.jpaadapter.RentalCardRepository;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.UserItemInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class RentItemInputPortTest {
    @Autowired private RentItemInputPort rentItemInputPort;
    @Autowired private RentalCardRepository rentalCardRepository;
    private static int MAX_SIZE = 5;

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

    @Test
    @DisplayName("도서 대여_5개 이상인 경우 오류")
    void rent_max_size() {
        for (int i = 0; i < MAX_SIZE; i++) {
            rentItemInputPort.rentItem(UserItemInputDTOFactory.create());
        }

        assertThatThrownBy(() -> rentItemInputPort.rentItem(UserItemInputDTOFactory.create()))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isEqualTo("최대 5권 까지만 대여할 수 있습니다.");
    }

    @Test
    @DisplayName("도서 대여_대여 불가능 상태")
    void rent_unavailable() {
        UserItemInputDTO userItemInputDTO = UserItemInputDTOFactory.create();
        rentItemInputPort.rentItem(userItemInputDTO);

        RentalCard rentalCard = rentalCardRepository.findByUserId(userItemInputDTO.getUserId()).get();
        rentalCard.makeOverdueItems(LocalDate.now().plusDays(15));

        assertThatThrownBy(() -> rentItemInputPort.rentItem(userItemInputDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isEqualTo("대여 불가 상태입니다.");
    }
}