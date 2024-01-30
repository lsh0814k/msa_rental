package fem.rental;

import fem.rental.framework.web.dto.UserInputDTO;

public class UserInputDTOFactory {
    public static UserInputDTO create() {
        return UserInputDTO.createUserInputDTO("id", "홍길동");
    }
}
