package fem.rental.domain.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class IDName {
    @Column(name = "user_id")
    private String id;
    @Column(name = "user_name")
    private String name;

    public static IDName createIDName(String id, String name) {
        return new IDName(id, name);
    }
}
