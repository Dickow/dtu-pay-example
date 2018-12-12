package com.dickow.dtu.pay.example.choreographychecker;

import com.dickow.chortlin.checker.receiver.ChortlinReceiver;
import com.dickow.chortlin.shared.trace.dto.InvocationDTO;
import com.dickow.chortlin.shared.trace.dto.ReturnDTO;
import com.dickow.dtu.pay.example.shared.Console;
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
    private final ChortlinReceiver chortlinReceiver;

    @Autowired
    public TraceController(ChortlinReceiver chortlinReceiver) {
        this.chortlinReceiver = chortlinReceiver;
    }

    @PostMapping(value = "invocation")
    public ResponseEntity<Void> checkInvocation(@RequestBody InvocationDTO invocationTrace){
        Console.invocation(this.getClass());
        chortlinReceiver.receive(invocationTrace);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "return")
    public ResponseEntity<Void> checkReturn(@RequestBody ReturnDTO returnTrace){
        Console.invocation(this.getClass());
        chortlinReceiver.receive(returnTrace);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
