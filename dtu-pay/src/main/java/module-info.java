module dtupay {
    requires spring.web;
    requires java.net.http;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires spring.beans;
    requires spring.webmvc;

    requires chortlin.interception;
    requires shared;

    exports com.dickow.dtu.pay.example.dtu;
}