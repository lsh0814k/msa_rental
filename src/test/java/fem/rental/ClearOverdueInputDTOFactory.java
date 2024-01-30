package fem.rental;

import fem.rental.framework.web.dto.ClearOverdueInputDTO;

public class ClearOverdueInputDTOFactory {
    public static ClearOverdueInputDTO create() {
        return ClearOverdueInputDTO.create("id", "홍길동", 10L);
    }
}
