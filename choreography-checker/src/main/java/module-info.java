module choreographychecker {
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires spring.beans;

    requires chortlin.checker;

    requires shared;
}