package fem.rental.domain.model.vo;

import jakarta.persistence.Embeddable;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "point")
@Embeddable
public class LateFee {
    private Long point;

    public LateFee addPoint(Long point) {
        return new LateFee(this.point + point);
    }

    public LateFee removePoint(Long point) {
        if (this.point < point) {
            throw new IllegalStateException("보유한 포인트가 부족합니다.");
        }
        return new LateFee(this.point - point);
    }

    public static LateFee createLateFee(Long point) {
        return new LateFee(point);
    }
}
