#!/usr/bin/env bash

java -jar bank/target/bank-1.0-SNAPSHOT-spring-boot.jar &
BANK=$!
java -jar merchant/target/merchant-1.0-SNAPSHOT-spring-boot.jar &
MERCHANT=$!
java -jar dtu-pay/target/dtu-pay-1.0-SNAPSHOT-spring-boot.jar &
DTUPAY=$!
java -jar choreography-checker/target/choreography-checker-1.0-SNAPSHOT-spring-boot.jar &
CHECKER=$!

wait $BANK $MERCHANT $DTUPAY $CHECKER