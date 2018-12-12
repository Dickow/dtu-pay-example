package com.dickow.dtu.pay.example.choreographychecker;

import com.dickow.chortlin.checker.receiver.ChortlinResultCallback;
import com.dickow.chortlin.shared.trace.dto.InvocationDTO;
import com.dickow.chortlin.shared.trace.dto.ReturnDTO;

public class ErrorHandler implements ChortlinResultCallback {
    @Override
    public void error(InvocationDTO invocationDTO) {
        throw new RuntimeException(String.format("Error in observed choreography, following trace resulted in error", invocationDTO.toString()));
    }

    @Override
    public void error(ReturnDTO returnDTO) {
        throw new RuntimeException(String.format("Error in observed choreography, following trace resulted in error %s", returnDTO.toString()));
    }
}
