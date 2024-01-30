package fem.rental.application.inputport;

import fem.rental.UserInputDTOFactory;
import fem.rental.UserItemInputDTOFactory;
import fem.rental.application.usecase.CreateRentalCardUsecase;
import fem.rental.application.usecase.RentItemUsecase;
import fem.rental.application.usecase.ReturnItemUsecase;
import fem.rental.framework.jpaadapter.RentalCardRepository;
import fem.rental.framework.web.dto.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class InquiryInputPortTest {
    @Autowired private RentalCardRepository rentalCardRepository;
    @Autowired private InquiryInputPort inquiryInputPort;
    @Autowired private CreateRentalCardUsecase createRentalCardUsecase;
    @Autowired private RentItemUsecase rentItemUsecase;
    @Autowired private ReturnItemUsecase returnItemUsecase;

    @BeforeEach
    void init() {
        rentalCardRepository.deleteAll();
    }

    @Test
    @DisplayName("대여 카드 조회_대여 카드가 있는 경우")
    void findRentalCard_exist() {
        UserInputDTO userInputDTO = UserInputDTOFactory.create();
        createRentalCardUsecase.createRentalCard(userInputDTO);

        Optional<RentalCardOutputDTO> rentalCardOutputDTO = inquiryInputPort.findRentalCard(userInputDTO.getUserId());
        assertThat(rentalCardOutputDTO.isPresent()).isTrue();
        RentalCardOutputDTO rentalCard = rentalCardOutputDTO.get();
        assertThat(rentalCard.getRentalCardId()).isNotNull();
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
        UserInputDTO userInputDTO = UserInputDTOFactory.create();
        createRentalCardUsecase.createRentalCard(userInputDTO);

        List<RentItemOutputDTO> allRentItems = inquiryInputPort.findAllRentItems(userInputDTO.getUserId());
        assertThat(allRentItems.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("대여 목록 조회_대여 목록이 있는 경우")
    void findRentalItems_exist() {
        UserInputDTO userInputDTO = UserInputDTOFactory.create();
        createRentalCardUsecase.createRentalCard(userInputDTO);

        UserItemInputDTO rentItem = UserItemInputDTOFactory.create();
        rentItemUsecase.rentItem(rentItem);
        List<RentItemOutputDTO> rentItems = inquiryInputPort.findAllRentItems(userInputDTO.getUserId());

        assertThat(rentItems.size()).isEqualTo(1);
        assertThat(rentItems.get(0).getItemId()).isEqualTo(rentItem.getItemId());
    }

    @Test
    @DisplayName("대여 반환 목록 조회_반환 목록이 없는 경우")
    void findReturnItems_non_exist() {
        UserInputDTO userInputDTO = UserInputDTOFactory.create();
        createRentalCardUsecase.createRentalCard(userInputDTO);

        UserItemInputDTO rentItem = UserItemInputDTOFactory.create();
        rentItemUsecase.rentItem(rentItem);

        List<ReturnItemOutputDTO> returnItems = inquiryInputPort.findAllReturnItems(userInputDTO.getUserId());

        assertThat(returnItems.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("대여 반환 목록 조회_반환 목록이 있는 경우")
    void findReturnItems_exist() {
        UserInputDTO userInputDTO = UserInputDTOFactory.create();
        createRentalCardUsecase.createRentalCard(userInputDTO);

        UserItemInputDTO rentItem = UserItemInputDTOFactory.create();
        rentItemUsecase.rentItem(rentItem);

        returnItemUsecase.returnItem(rentItem);
        List<ReturnItemOutputDTO> returnItems = inquiryInputPort.findAllReturnItems(userInputDTO.getUserId());

        assertThat(returnItems.size()).isEqualTo(1);
        assertThat(returnItems.get(0).getItemNo()).isEqualTo(rentItem.getItemId());
    }
}