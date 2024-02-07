package fem.rental.domain.model.event;

import fem.rental.domain.model.vo.IDName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class PointCommand {
    private IDName idName;
    private long point;

    public static PointCommand create(IDName idName, long point) {
        return new PointCommand(idName, point);
    }
}
