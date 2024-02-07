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

    public static ClearOverdueInputDTO create(String userId, String userNm) {
        return ClearOverdueInputDTO.builder()
                .userId(userId)
                .userNm(userNm)
                .build();
    }
}
