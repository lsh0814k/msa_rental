package fem.rental.application.inputport;

import fem.rental.ItemFactory;
import fem.rental.RentalCardFactory;
import fem.rental.UserItemInputDTOFactory;
import fem.rental.application.outputport.RentalCardOutputPort;
import fem.rental.application.usecase.RentItemUsecase;
import fem.rental.domain.model.RentalCard;
import fem.rental.domain.model.vo.Item;
import fem.rental.domain.model.vo.LateFee;
import fem.rental.framework.jpaadapter.RentalCardRepository;
import fem.rental.framework.web.dto.UserItemInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static fem.rental.domain.model.vo.RentStatus.RENT_AVAILABLE;
import static fem.rental.domain.model.vo.RentStatus.RENT_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OverdueItemInputPortTest {
    @Autowired private OverDueItemInputPort overdueItemInputPort;
    @Autowired private RentalCardRepository rentalCardRepository;
    @Autowired private RentalCardOutputPort rentalCardOutputPort;

    @BeforeEach
    void init() {
        rentalCardRepository.deleteAll();
    }

    @Test
    @DisplayName("기간이 지난 대여목록 연체 처리_연체가 있는 경우")
    void makeOverdue_exist() {
        RentalCard rentalCard = rentItem();
        Item item = ItemFactory.create();
        overdueItemInputPort.overDueItem(
                UserItemInputDTO.createUserItemInputDTO(rentalCard.getMember().getId(), rentalCard.getMember().getName(), item.getNo(), item.getTitle()),
                LocalDate.now().plusDays(15));
        RentalCard findRentalCard = rentalCardOutputPort.findByUserId(rentalCard.getMember().getId()).get();

        assertThat(findRentalCard.getRentStatus()).isEqualTo(RENT_UNAVAILABLE);
        assertThat(findRentalCard.getLateFee()).isEqualTo(LateFee.createLateFee(0L));
    }

    @Test
    @DisplayName("기간이 지난 대여목록 연체 처리_연체가 없는 경우")
    void makeOverdue_non_exist() {
        RentalCard rentalCard = rentItem();
        Item item = ItemFactory.create();
        overdueItemInputPort.overDueItem(
                UserItemInputDTO.createUserItemInputDTO(rentalCard.getMember().getId(), rentalCard.getMember().getName(), item.getNo(), item.getTitle()),
                LocalDate.now());

        RentalCard findRentalCard = rentalCardOutputPort.findByUserId(rentalCard.getMember().getId()).get();

        assertThat(findRentalCard.getRentStatus()).isEqualTo(RENT_AVAILABLE);
        assertThat(findRentalCard.getLateFee()).isEqualTo(LateFee.createLateFee(0L));
    }


    private RentalCard rentItem() {
        RentalCard rentalCard = RentalCardFactory.create();
        rentalCardRepository.save(rentalCard);

        return rentalCard.rentItem(ItemFactory.create());
    }
}
