package m3.map.and.points.commons;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    //EXAMPLE_ERROR(1, "Пример сообщения об ошибке");
    ;

    private Integer code;
    private String message;

    ErrorCodes(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
