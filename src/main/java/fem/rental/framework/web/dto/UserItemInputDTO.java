package fem.rental.framework.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Builder(access = PRIVATE)
public class UserItemInputDTO {
    private String userId;
    private String userNm;
    private Long itemId;
    private String itemTitle;

    public static UserItemInputDTO createUserItemInputDTO(String userId, String userNm, Long itemId, String itemTitle) {
        return UserItemInputDTO
                .builder()
                .userId(userId)
                .userNm(userNm)
                .itemId(itemId)
                .itemTitle(itemTitle)
                .build();
    }
}
