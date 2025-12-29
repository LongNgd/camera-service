package vn.atdigital.cameraservice.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResult<T> {
    private int code; // 1 = success, others = error
    private String message;
    private T data;

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> r = new ApiResult<>();
        r.code = 1;
        r.data = data;
        r.message = "success";
        return r;
    }

    public static <T> ApiResult<T> error(String message) {
        ApiResult<T> r = new ApiResult<>();
        r.code = -1;
        r.message = message;
        return r;
    }

}
