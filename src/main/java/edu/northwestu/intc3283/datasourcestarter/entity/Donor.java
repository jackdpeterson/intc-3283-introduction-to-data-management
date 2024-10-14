package edu.northwestu.intc3283.datasourcestarter.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;

@Table("donors")
@Data
public class Donor {

    @Id
    @Column("id")
    private Long id;

    @CreatedDate
    private Instant createdAt;

    private String email;

    private String phone;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String address1;

    @NotNull
    private String address2;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String zipCode;
}