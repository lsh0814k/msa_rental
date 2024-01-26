package fem.rental.domain.model.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class RentalCardNo {
    private String no;

    public static RentalCardNo createRentalCardNo() {
        UUID uuid = UUID.randomUUID();
        String year = String.valueOf(LocalDate.now().getYear());

        return new RentalCardNo(String.format("%s-%s", uuid, year));
    }
}
