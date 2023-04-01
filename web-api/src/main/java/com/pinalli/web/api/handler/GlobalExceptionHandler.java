package com.pinalli.web.api.handler;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import java.lang.reflect.UndeclaredThrowableException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Resource
    private MessageSource messageSource;
    /**
     * cabecalho da resposta em json
     */
    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    /**
     * @param message
     * @param statusCode
     * @return
     */
    private ResponseError responseError (String message, HttpStatus statusCode){
        ResponseError responseError = new ResponseError();
        responseError.setStatus("error");
        responseError.setError(message);
        responseError.setStatusCode(statusCode.value());
        return responseError;
    }

    /**
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleGeneral(Exception e, WebRequest request){
        if(e.getClass().isAssignableFrom(UndeclaredThrowableException.class)){
            UndeclaredThrowableException exception = (UndeclaredThrowableException)e;
            return handleBusinessException((BusinessException) exception.getUndeclaredThrowable(),request);
        } else {
            String error = messageSource.getMessage("error.server", new Object[]{e.getMessage()},null);
            return handleExceptionInternal(e,error,headers(),HttpStatus.INTERNAL_SERVER_ERROR,request);
        }
    }

    /**
     * @param e
     * @param request
     * @return
     */

    @ExceptionHandler({BusinessException.class})
    private ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request){
        ResponseError error =  responseError(e.getMessage(),HttpStatus.CONFLICT);
        return handleExceptionInternal(e,error,headers(),HttpStatus.CONFLICT, request);
    }
}
