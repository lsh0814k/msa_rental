package fem.rental.application.inputport;

import fem.rental.ItemFactory;
import fem.rental.RentalCardFactory;
import fem.rental.UserInputDTOFactory;
import fem.rental.domain.model.RentalCard;
import fem.rental.framework.jpaadapter.RentalCardRepository;
import fem.rental.framework.web.dto.RentItemOutputDTO;
import fem.rental.framework.web.dto.RentalCardOutputDTO;
import fem.rental.framework.web.dto.ReturnItemOutputDTO;
import fem.rental.framework.web.dto.UserInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class InquiryInputPortTest {
    @Autowired private RentalCardRepository rentalCardRepository;
    @Autowired private InquiryInputPort inquiryInputPort;

    @BeforeEach
    void init() {
        rentalCardRepository.deleteAll();
    }

    @Test
    @DisplayName("대여 카드 조회_대여 카드가 있는 경우")
    void findRentalCard_exist() {
        RentalCard rentalCard = RentalCardFactory.create();
        rentalCardRepository.save(rentalCard);

        Optional<RentalCardOutputDTO> rentalCardOutputDTO = inquiryInputPort.findRentalCard(rentalCard.getMember().getId());
        assertThat(rentalCardOutputDTO.isPresent()).isTrue();
        assertThat(rentalCardOutputDTO.get().getRentalCardId()).isNotNull();
    }

    @Test
    @DisplayName("대여 카드 조회_대여 카드가 없는 경우")
    void findRentalCard_non_exist() {
        UserInputDTO userInputDTO = UserInputDTOFactory.create();

        Optional<RentalCardOutputDTO> rentalCardOutputDTO = inquiryInputPort.findRentalCard(userInputDTO.getUserId());
        assertThat(rentalCardOutputDTO.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("대여 목록 조회_대여 목록이 없는 경우")
    void findRentalItems_non_exist() {
        RentalCard rentalCard = createRentalCard();

        List<RentItemOutputDTO> allRentItems = inquiryInputPort.findAllRentItems(rentalCard.getMember().getId());
        assertThat(allRentItems.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("대여 목록 조회_대여 목록이 있는 경우")
    void findRentalItems_exist() {
        RentalCard rentalCard = rentItem();
        List<RentItemOutputDTO> rentItems = inquiryInputPort.findAllRentItems(rentalCard.getMember().getId());

        assertThat(rentItems.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("대여 반환 목록 조회_반환 목록이 없는 경우")
    void findReturnItems_non_exist() {
        RentalCard rentalCard = rentItem();

        List<ReturnItemOutputDTO> returnItems = inquiryInputPort.findAllReturnItems(rentalCard.getMember().getId());

        assertThat(returnItems.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("대여 반환 목록 조회_반환 목록이 있는 경우")
    void findReturnItems_exist() {
        RentalCard rentalCard = returnItem();

        List<ReturnItemOutputDTO> returnItems = inquiryInputPort.findAllReturnItems(rentalCard.getMember().getId());

        assertThat(returnItems.size()).isEqualTo(1);
    }

    private RentalCard createRentalCard() {
        RentalCard rentalCard = RentalCardFactory.create();
        rentalCardRepository.save(rentalCard);

        return rentalCard;
    }

    private RentalCard rentItem() {
        RentalCard rentalCard = createRentalCard();
        return rentalCard.rentItem(ItemFactory.create());
    }

    private RentalCard returnItem() {
        RentalCard rentalCard = rentItem();
        return rentalCard.returnItem(ItemFactory.create(), LocalDate.now());
    }
}