package id.my.dansapps.common.util.messages;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CustomResponseGenerator {

    public <T> CustomResponse<T> successResponse(T dto, String detail) {
        CustomResponse<T> customResponse = new CustomResponse<T>();
        customResponse.setHttpStatusCode(HttpStatus.OK.value());
        customResponse.setMessage(HttpStatus.OK.getReasonPhrase());
        customResponse.setDetail(detail != null ? detail : HttpStatus.OK.toString());
        customResponse.setData(dto);
        return customResponse;
    }

    public <T> CustomResponse<T> errorResponse(String detail) {
        CustomResponse<T> customResponse = new CustomResponse<T>();
        customResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        customResponse.setMessage("Failed");
        customResponse.setDetail(detail);
        return customResponse;
    }

}
