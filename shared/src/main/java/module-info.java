module shared {
    requires chortlin.interception;
    requires java.net.http;
    requires gson;
    requires transitive java.sql;

    exports com.dickow.dtu.pay.example.shared.dto;
    exports com.dickow.dtu.pay.example.shared;
}