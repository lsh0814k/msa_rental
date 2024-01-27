package fem.rental.framework.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseInquiryDTO<T> {
    private boolean isEmpty;
    private T data;

}
