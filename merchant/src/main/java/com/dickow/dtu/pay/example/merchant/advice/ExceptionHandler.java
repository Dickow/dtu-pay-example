package com.dickow.dtu.pay.example.merchant.advice;

import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException;
import com.dickow.dtu.pay.example.shared.exception.ChoreographyExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ChoreographyRuntimeException.class)
    protected ResponseEntity<ChoreographyExceptionDTO> handleChoreographyRuntimeException(
            ChoreographyRuntimeException exception)
    {
        var body = new ChoreographyExceptionDTO();
        body.setMessage(exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
