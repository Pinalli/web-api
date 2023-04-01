package com.pinalli.web.api.handler;

public class ObrigatoryFieldException extends BusinessException{

    public ObrigatoryFieldException(String field) {
        super("O campo %s é obrigatório",field);
    }


}
