package fem.rental.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = {"id", "name"})
public class IDName {
    private String id;
    private String name;

    public static IDName createIDName(String id, String name) {
        return new IDName(id, name);
    }
}
