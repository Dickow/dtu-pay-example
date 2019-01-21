package com.dickow.dtu.pay.example.choreographychecker;

import com.dickow.chortlin.checker.checker.ChoreographyChecker;
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException;
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions;
import com.dickow.dtu.pay.example.shared.exception.ChoreographyExceptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("traces")
public class TraceController {
    private final ChoreographyChecker choreographyChecker;

    @Autowired
    public TraceController(ChoreographyChecker choreographyChecker) {
        this.choreographyChecker = choreographyChecker;
    }

    @PostMapping(value = "invocation", consumes = {"application/x-protobuf"})
    public ResponseEntity<Object> checkInvocation(@RequestBody DtoDefinitions.InvocationDTO invocationTrace) {
        try{
            choreographyChecker.check(invocationTrace);
        }
        catch(ChoreographyRuntimeException e){
            return buildErrorResponse(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "return", consumes = {"application/x-protobuf"})
    public ResponseEntity<Object> checkReturn(@RequestBody DtoDefinitions.ReturnDTO returnTrace) {
        try{
            choreographyChecker.check(returnTrace);
        }
        catch(ChoreographyRuntimeException e){
            return buildErrorResponse(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<Object> buildErrorResponse(ChoreographyRuntimeException e) {
        var exceptionDTO = new ChoreographyExceptionDTO();
        exceptionDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
}
