module bank {
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.boot;
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires spring.beans;

    requires chortlin.interception;
    requires shared;

    exports com.dickow.dtu.pay.example.bank.controllers;
}