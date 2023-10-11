package m3.map.and.points.commons;

import lombok.Getter;

@Getter
public class HttpExceptionError extends RuntimeException {

    private ErrorCodes errorCodes;

    public HttpExceptionError(ErrorCodes errorCodes) {
        super(errorCodes.getMessage());
        this.errorCodes = errorCodes;
    }
}
