module choreographychecker {
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires spring.beans;

    requires chortlin.checker;

    requires shared;
    requires com.fasterxml.jackson.core;

    opens com.dickow.dtu.pay.example.choreographychecker to spring.core, spring.beans, spring.context, spring.web;
}