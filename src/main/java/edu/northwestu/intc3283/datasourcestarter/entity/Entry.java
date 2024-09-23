package edu.northwestu.intc3283.datasourcestarter.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.validation.annotation.Validated;
import java.time.Instant;
import java.util.Enumeration;

@Table("entries")
@Validated

public class Entry {

    @Id
    private Long id;

    @Size(min = 5, max=50)
    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    @PositiveOrZero
    @Max(135)
    @NotNull
    private Integer age;

    @Size(min = 5, max = 150)
    @NotEmpty
    private String address;

    private Boolean human;

    @CreatedDate
    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age  = age; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public Boolean getHuman() { return human; }

    public void setHuman(Boolean human) { this.human = human; }

}
