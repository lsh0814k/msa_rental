package fem.rental.application.inputport;

import fem.rental.ClearOverdueInputDTOFactory;
import fem.rental.ItemFactory;
import fem.rental.RentalCardFactory;
import fem.rental.domain.model.RentalCard;
import fem.rental.framework.jpaadapter.RentalCardRepository;
import fem.rental.framework.web.dto.RentalResultOutputDTO;
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
class ClearOverdueItemInputPortTest {
    @Autowired private RentalCardRepository rentalCardRepository;
    @Autowired private ClearOverdueItemInputPort clearOverdueItemInputPort;

    @Test
    @DisplayName("연체 해제_연체된 도서를 반납하지 않은 경우")
    void clearOverdue_not_return() {
        RentalCard rentalCard = rentItem();
        rentalCard.makeOverdueItems(LocalDate.now().plusDays(15));

        assertThatThrownBy(() -> clearOverdueItemInputPort.clearOverdue(ClearOverdueInputDTOFactory.create()))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isEqualTo("모든 도서가 반납되어야 정지를 해제할 수 있습니다.");
    }

    private RentalCard rentItem() {
        RentalCard rentalCard = RentalCardFactory.create();
        rentalCardRepository.save(rentalCard);
        rentalCard.rentItem(ItemFactory.create());

        return rentalCard;
    }

    @Test
    @DisplayName("연체 해제")
    void clearOverdue() {
        RentalCard rentalCard = rentItem();

        rentalCard.makeOverdueItems(LocalDate.now().plusDays(15));
        rentalCard.returnItem(ItemFactory.create(), LocalDate.now().plusDays(15));

        RentalResultOutputDTO rentalResultOutputDTO = clearOverdueItemInputPort.clearOverdue(ClearOverdueInputDTOFactory.create());
        assertThat(rentalResultOutputDTO.getRentedCount()).isEqualTo(0);
        assertThat(rentalResultOutputDTO.getTotalLateFee()).isEqualTo(0);
        assertThat(rentalResultOutputDTO.getRentedCount()).isEqualTo(0);
    }
}