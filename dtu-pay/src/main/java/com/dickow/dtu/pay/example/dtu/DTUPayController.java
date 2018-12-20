package com.dickow.dtu.pay.example.dtu;

import com.dickow.chortlin.shared.annotations.TraceInvocation;
import com.dickow.chortlin.shared.annotations.TraceReturn;
import com.dickow.dtu.pay.example.shared.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dtu/pay")
public class DTUPayController {
    private final DTUBankIntegration bankIntegration;

    @Autowired
    public DTUPayController(DTUBankIntegration bankIntegration) {
        this.bankIntegration = bankIntegration;
    }

    @PostMapping("{merchant}/{token}")
    @TraceInvocation
    @TraceReturn
    public ResponseEntity<Boolean> pay(
            @PathVariable String merchant, @RequestBody Integer amount, @PathVariable String token) {
        Console.invocation(this.getClass());
        var result = bankIntegration.transferMoney(merchant, token, amount);
        return ResponseEntity.ok(result);
    }
}
