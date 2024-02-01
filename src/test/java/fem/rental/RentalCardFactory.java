package fem.rental;

import fem.rental.domain.model.RentalCard;
import fem.rental.domain.model.vo.IDName;

public class RentalCardFactory {
    public static RentalCard create() {
        return RentalCard.createRentalCard(IDName.createIDName("id", "홍길동"));
    }
}
