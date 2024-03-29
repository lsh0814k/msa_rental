package fem.rental.application.inputport;


import fem.rental.ItemFactory;
import fem.rental.RentalCardFactory;
import fem.rental.UserItemInputDTOFactory;
import fem.rental.application.usecase.RentItemUsecase;
import fem.rental.domain.model.RentalCard;
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

@SpringBootTest
@Transactional
class ReturnItemInputPortTest {
    @Autowired private ReturnItemInputPort returnItemInputPort;
    @Autowired private RentalCardRepository rentalCardRepository;

    @BeforeEach
    void init() {
        rentalCardRepository.deleteAll();
    }

    @Test
    @DisplayName("도서 반납")
    void returnItem() {
        rentItem();
        UserItemInputDTO userItemInputDTO = UserItemInputDTOFactory.create();
        RentalCardOutputDTO rentalCardOutputDTO = returnItemInputPort.returnItem(userItemInputDTO, LocalDate.now());

        assertThat(rentalCardOutputDTO.getTotalRentCnt()).isEqualTo(0);
        assertThat(rentalCardOutputDTO.getTotalReturnCnt()).isEqualTo(1);
        assertThat(rentalCardOutputDTO.getTotalLateFee()).isEqualTo(0);
    }

    private RentalCard rentItem() {
        RentalCard rentalCard = RentalCardFactory.create();
        rentalCardRepository.save(rentalCard);

        return rentalCard.rentItem(ItemFactory.create());
    }
}