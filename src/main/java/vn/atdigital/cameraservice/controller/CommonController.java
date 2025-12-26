package vn.atdigital.cameraservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.atdigital.cameraservice.domain.message.ResponseMessage;

import static vn.atdigital.cameraservice.common.Constants.STATUS_COMMON.RESPONSE_STATUS_FALSE;
import static vn.atdigital.cameraservice.common.Constants.STATUS_COMMON.RESPONSE_STATUS_TRUE;


public abstract class CommonController {

    protected <T> ResponseEntity<?> toSuccessResult(T data, String returnCode, String successMessage) {
        ResponseMessage<T> message = new ResponseMessage<>();

        message.setSuccess(RESPONSE_STATUS_TRUE);
        message.setStatus(returnCode);
        message.setMessage(successMessage);
        message.setData(data );

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    protected <T> ResponseEntity<?> toExceptionResult(String errorMessage, String returnCode) {
        ResponseMessage<T> message = new ResponseMessage<>();

        message.setSuccess(RESPONSE_STATUS_FALSE);
        message.setStatus(returnCode);
        message.setMessage(errorMessage);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    protected <T> ResponseEntity<?> toSuccessResultNull(String returnCode) {
        ResponseMessage<T> message = new ResponseMessage<>();

        message.setSuccess(RESPONSE_STATUS_TRUE);
        message.setStatus(returnCode);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
