package edu.northwestu.intc3283.datasourcestarter.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.validation.annotation.Validated;
import java.time.Instant;

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

    @Column("favorite_color")
    @NotEmpty
    @Size(min = 3, max = 20)
    private String favoriteColor;

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

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }
}

