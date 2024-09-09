package edu.northwestu.intc3283.datasourcestarter;

import org.springframework.boot.SpringApplication;

public class TestDatasourceStarterApplication {

    public static void main(String[] args) {
        SpringApplication.from(DatasourceStarterApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
