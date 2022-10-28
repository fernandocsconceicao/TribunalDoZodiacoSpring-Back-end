package com.lemomcrab.TribunaldoZodiacoBackend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class StandardException extends Throwable {
    private static final long serialVersionUID = 1L;

    private Integer status;
    private String msg;
    private Long timeStamp;

    public StandardException(String msg) {
        super();
        this.status = 500;
        this.msg = msg;
        this.timeStamp =getTimeStamp() ;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }


}

