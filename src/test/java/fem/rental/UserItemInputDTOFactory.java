package fem.rental;

import fem.rental.framework.web.dto.UserItemInputDTO;

public class UserItemInputDTOFactory {
    public static UserItemInputDTO create() {
        return UserItemInputDTO.createUserItemInputDTO("id", "홍길동", 1L, "노인과 바다");
    }
}
