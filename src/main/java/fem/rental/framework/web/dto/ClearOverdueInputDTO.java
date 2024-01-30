package fem.rental.framework.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class ClearOverdueInputDTO {
    private String userId;
    private String userNm;
    private Long point;

    public static ClearOverdueInputDTO create(String userId, String userNm, Long point) {
        return ClearOverdueInputDTO.builder()
                .userId(userId)
                .userNm(userNm)
                .point(point)
                .build();
    }
}
