package id.my.dansapps.common.util.messages;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomResponse<T> {
    private String message;
    private String detail;
    private Integer httpStatusCode;
    private T data;

}
