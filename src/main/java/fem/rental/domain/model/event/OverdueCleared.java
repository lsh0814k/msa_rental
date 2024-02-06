package fem.rental.domain.model.event;

import fem.rental.domain.model.vo.IDName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Builder(access = PROTECTED)
public class OverdueCleared implements Serializable {
    private IDName idName;
    private long point;

    public static OverdueCleared create(IDName idName, long point) {
        return OverdueCleared
                .builder()
                .idName(idName)
                .point(point)
                .build();
    }
}
