package com.dickow.dtu.pay.example.choreographychecker;

import com.dickow.chortlin.checker.checker.ChoreographyChecker;
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions;
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

    @PostMapping(value = "invocation")
    public ResponseEntity<Void> checkInvocation(@RequestBody DtoDefinitions.InvocationDTO invocationTrace){
        choreographyChecker.check(invocationTrace);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "return")
    public ResponseEntity<Void> checkReturn(@RequestBody DtoDefinitions.ReturnDTO returnTrace){
        choreographyChecker.check(returnTrace);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
