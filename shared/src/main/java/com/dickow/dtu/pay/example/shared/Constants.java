package com.dickow.dtu.pay.example.shared;

import com.dickow.chortlin.interception.configuration.HttpConfiguration;

public abstract class Constants {
    private static final String LOCALHOST = "http://localhost";

    public static final String BANK_PORT_NUMBER = "30000";
    public static final String MERCHANT_PORT_NUMBER = "31000";
    public static final String DTU_PAY_PORT_NUMBER = "32000";
    public static final String CHOREOGRAPHY_CHECKER_PORT_NUMBER = "33000";

    public static final String BANK_BASE_URL = LOCALHOST+":"+BANK_PORT_NUMBER+"/bank/";
    public static final String DTU_PAY_BASE_URL = LOCALHOST+":"+DTU_PAY_PORT_NUMBER+"/dtu/pay/";
    public static final String MERCHANT_BASE_URL = LOCALHOST+":"+MERCHANT_PORT_NUMBER+"/merchant/";
    public static final String CHOREOGRAPHY_CHECKER_BASE_URL = LOCALHOST+":"+CHOREOGRAPHY_CHECKER_PORT_NUMBER+"/traces/";

    public static final HttpConfiguration configuration = new HttpConfiguration(
                    CHOREOGRAPHY_CHECKER_BASE_URL+"invocation",
                    CHOREOGRAPHY_CHECKER_BASE_URL+"return");
}
