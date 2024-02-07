package fem.rental.domain.model;

import fem.rental.domain.model.vo.IDName;
import fem.rental.domain.model.vo.Item;
import fem.rental.domain.model.vo.LateFee;
import fem.rental.domain.model.vo.RentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RentalCardTest {

    @Test
    @DisplayName("대여 카드 생성")
    void createRentalCard() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        assertThat(rentalCard.getLateFee()).isEqualTo(LateFee.createLateFee(0L));
        assertThat(rentalCard.getRentStatus()).isEqualTo(RentStatus.RENT_AVAILABLE);
    }

    @Test
    @DisplayName("도서 대여")
    void rentItem() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        rentalCard.rentItem(Item.createItem(1L, "MSA"));

        assertThat(rentalCard.getRentalItems().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("도서 연체 발생_상태_대여불가능")
    void overdueItem() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        rentalCard.rentItem(Item.createItem(1L, "MSA"));

        rentalCard.makeOverdueItems(LocalDate.now().plusDays(15));

        assertThat(rentalCard.getRentStatus()).isEqualTo(RentStatus.RENT_UNAVAILABLE);
    }

    @Test
    @DisplayName("대여 불가능상태에서 도서 대여")
    void rentItem_rent_unavailable() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        rentalCard.rentItem(Item.createItem(1L, "MSA"));

        rentalCard.makeOverdueItems(LocalDate.now().plusDays(15));

        assertThatThrownBy(() -> rentalCard.rentItem(Item.createItem(2L, "JAP")))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isEqualTo("대여 불가 상태입니다.");
    }

    @Test
    @DisplayName("5권 이상 도서 대여")
    void rentItem_over_rent() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        for (int i = 0; i < 5; i++) {
            rentalCard.rentItem(Item.createItem(i + 1L, "book"));
        }

        assertThatThrownBy(() -> rentalCard.rentItem(Item.createItem(6L, "msa")))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isEqualTo("최대 5권 까지만 대여할 수 있습니다.");
    }

    @Test
    @DisplayName("도서 반납")
    void returnItem() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        Item item = Item.createItem(1L, "MSA");
        rentalCard.rentItem(item);

        rentalCard.returnItem(item, LocalDate.now());

        assertThat(rentalCard.getRentalItems().size()).isEqualTo(0);
        assertThat(rentalCard.getReturnItems().size()).isEqualTo(1);
        assertThat(rentalCard.getLateFee()).isEqualTo(LateFee.createLateFee(0L));
    }

    @Test
    @DisplayName("연체 상태의 도서 반납")
    void returnItem_overdue() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        Item item = Item.createItem(1L, "MSA");
        rentalCard.rentItem(item);

        rentalCard.makeOverdueItems(LocalDate.now().plusDays(15));
        rentalCard.returnItem(item, LocalDate.now().plusDays(15));

        assertThat(rentalCard.getRentalItems().size()).isEqualTo(0);
        assertThat(rentalCard.getReturnItems().size()).isEqualTo(1);
        assertThat(rentalCard.getLateFee()).isEqualTo(LateFee.createLateFee(10L));
    }

    @Test
    @DisplayName("상태 변경")
    void makeAvailableRental() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        Item item = Item.createItem(1L, "MSA");
        rentalCard.rentItem(item);

        rentalCard.makeOverdueItems(LocalDate.now().plusDays(15));
        rentalCard.returnItem(item, LocalDate.now().plusDays(15));

        rentalCard.makeAvailableRental();
        assertThat(rentalCard.getRentStatus()).isEqualTo(RentStatus.RENT_AVAILABLE);
    }

    @Test
    @DisplayName("상태 변경_모든 도서 반납 전")
    void makeAvailableRental_rentalIsPresent() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        Item item = Item.createItem(1L, "MSA");
        rentalCard.rentItem(item);

        rentalCard.makeOverdueItems(LocalDate.now().plusDays(15));
        assertThatThrownBy(() -> rentalCard.makeAvailableRental())
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isEqualTo("모든 도서가 반납되어야 정지를 해제할 수 있습니다.");
    }

    @Test
    @DisplayName("대여 취소")
    void rent_cancel() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        Item item = Item.createItem(1L, "MSA");
        rentalCard.rentItem(item);

        rentalCard.cancelRentItem(item);

        assertThat(rentalCard.getRentalItems().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("반납 취소")
    void return_cancel() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        Item item = Item.createItem(1L, "MSA");
        rentalCard.rentItem(item);

        LocalDate now = LocalDate.now();
        rentalCard.returnItem(item, now);
        assertThat(rentalCard.getRentalItems().size()).isEqualTo(0);
        assertThat(rentalCard.getReturnItems().size()).isEqualTo(1);


        rentalCard.cancelReturnItem(item);

        assertThat(rentalCard.getRentalItems().size()).isEqualTo(1);
        assertThat(rentalCard.getReturnItems().size()).isEqualTo(0);
        assertThat(rentalCard.getLateFee()).isEqualTo(LateFee.createLateFee(0L));
        assertThat(rentalCard.getRentalItems().get(0).getRentDate()).isEqualTo(now);
    }

    @Test
    @DisplayName("반납 취소 연체가 된 경우")
    void return_cancel_overdue() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        Item item = Item.createItem(1L, "MSA");
        rentalCard.rentItem(item);

        LocalDate now = LocalDate.now().plusDays(16);
        rentalCard.returnItem(item, now);
        assertThat(rentalCard.getLateFee()).isEqualTo(LateFee.createLateFee(20L));


        rentalCard.cancelReturnItem(item);
        assertThat(rentalCard.getRentalItems().size()).isEqualTo(1);
        assertThat(rentalCard.getReturnItems().size()).isEqualTo(0);
        assertThat(rentalCard.getLateFee()).isEqualTo(LateFee.createLateFee(0L));
        assertThat(rentalCard.getRentalItems().get(0).getRentDate()).isEqualTo(now);
    }

    @Test
    @DisplayName("연체 해제 취소")
    void overdueClear_cancel() {
        RentalCard rentalCard = RentalCard.createRentalCard(IDName.createIDName("id", "name"));
        Item item = Item.createItem(1L, "MSA");
        rentalCard.rentItem(item);
        rentalCard.makeOverdueItems(LocalDate.now().plusDays(16));
        LocalDate now = LocalDate.now().plusDays(16);
        rentalCard.returnItem(item, now);


        long lateFee = rentalCard.makeAvailableRental();


        rentalCard.cancelMakeAvailableRental(lateFee);
        assertThat(rentalCard.getLateFee()).isEqualTo(LateFee.createLateFee(lateFee));
        assertThat(rentalCard.getRentStatus()).isEqualTo(RentStatus.RENT_UNAVAILABLE);
    }
}