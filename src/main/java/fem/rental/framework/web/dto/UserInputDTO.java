package fem.rental.framework.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class UserInputDTO {
    private String userId;
    private String userNm;

    public static UserInputDTO createUserInputDTO(UserItemInputDTO userItemInputDTO) {
        return new UserInputDTO(userItemInputDTO.getUserId(), userItemInputDTO.getUserNm());
    }

    public static UserInputDTO createUserInputDTO(String userId, String userNm) {
        return new UserInputDTO(userId, userNm);
    }
}
