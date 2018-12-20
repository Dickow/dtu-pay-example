package com.dickow.dtu.pay.example.bank.controllers;

import com.dickow.chortlin.shared.annotations.TraceInvocation;
import com.dickow.chortlin.shared.annotations.TraceReturn;
import com.dickow.dtu.pay.example.shared.Console;
import com.dickow.dtu.pay.example.shared.dto.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank")
public class BankController {

    @PostMapping("transactions")
    @TraceInvocation
    @TraceReturn
    public ResponseEntity<Boolean> transfer(@RequestBody TransactionDTO transaction){
        Console.invocation(this.getClass());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
