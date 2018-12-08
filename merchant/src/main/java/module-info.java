module merchant {
    requires spring.web;
    requires transitive jdk.incubator.httpclient;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires spring.beans;
    requires chortlin.core;
    requires shared;
}