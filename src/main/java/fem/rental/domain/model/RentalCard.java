package fem.rental.domain.model;

import fem.rental.domain.model.vo.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter @Builder(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of ="rentalCardNo")
public class RentalCard {
    private RentalCardNo rentalCardNo;
    private IDName member;
    private RentStatus rentStatus;
    private LateFee lateFee;
    private List<RentalItem> rentalItems;
    private List<ReturnItem> returnItems;

    public static RentalCard createRentalCard(IDName member) {
        return RentalCard.builder()
                .rentalCardNo(RentalCardNo.createRentalCardNo())
                .member(member)
                .rentStatus(RentStatus.RENT_AVAILABLE)
                .lateFee(LateFee.createLateFee(0L))
                .rentalItems(new ArrayList<>())
                .returnItems(new ArrayList<>())
                .build();
    }

    public RentalCard rentItem(Item item) {
        checkRentalAvailable();
        this.addRentalItem(RentalItem.createRentalItem(item));

        return this;
    }

    public RentalCard returnItem(Item item, LocalDate now) {
        RentalItem rentalItem = this.rentalItems.stream()
                .filter(i -> i.getItem().equals(item)).findFirst().orElseThrow(() -> new IllegalArgumentException("대여한 품목이 아닙니다."));
        calculateLateFee(rentalItem, now);
        this.addReturnItem(ReturnItem.createReturnItem(rentalItem));
        this.removeRentalItem(rentalItem);

        return this;
    }

    public void makeOverdueItems(LocalDate now) {
        changeOverdueItems(now);
        changeRentStatus();
    }

    public void makeAvailableRental(Long point) {
        if (this.rentalItems.size() > 0) throw new IllegalArgumentException("모든 도서가 반납되어야 정지를 해제할 수 있습니다.");
        if (this.lateFee.getPoint() != point) throw new IllegalArgumentException("해당 포인트로 연체를 해제할 수 없습니다.");

        this.lateFee = lateFee.removePoint(point);
        this.rentStatus = RentStatus.RENT_AVAILABLE;
    }

    private void changeRentStatus() {
        this.rentStatus = this.rentalItems.stream()
                .filter(RentalItem::isOverdue)
                .findFirst().isPresent() ?
                RentStatus.RENT_UNAVAILABLE : RentStatus.RENT_AVAILABLE;
    }

    private void changeOverdueItems(LocalDate now) {
        this.rentalItems.stream()
                .filter(r -> r.getReturnDueDate().isBefore(now))
                .forEach(RentalItem::changeOverdueItem);
    }

    private void calculateLateFee(RentalItem rentalItem, LocalDate now) {
        int days = Period.between(rentalItem.getReturnDueDate(), now).getDays();
        if (days > 0) this.lateFee = this.lateFee.addPoint(days * 10L);
    }

    private void addReturnItem(ReturnItem returnItem) {
        this.returnItems.add(returnItem);
    }

    private void removeRentalItem(RentalItem rentalItem) {
        this.rentalItems.remove(rentalItem);
    }

    private void checkRentalAvailable() {
        if (this.rentStatus == RentStatus.RENT_UNAVAILABLE) throw new IllegalArgumentException("대여 불가 상태입니다.");
        if (this.rentalItems.size() >= 5) throw new IllegalArgumentException("최대 5권 까지만 대여할 수 있습니다.");
    }

    private void addRentalItem(RentalItem rentalItem) {
        this.rentalItems.add(rentalItem);
    }
}
