package edu.northwestu.intc3283.datasourcestarter.entity;

import jakarta.validation.constraints.*;
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




    @Column("username")
    @NotEmpty
    @Size(min = 3, max = 20)
    private String username;

    @Column("share_data")
    @NotNull
    private boolean shareData;

    @Column("date_of_birth")
    @NotEmpty
    private String dateOfBirth;

    //private int age;




  



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

   // public int getAge(){
     //   return age;
    //}

    //public void setAge(int age){
      //  this.age = age;
    //}




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

    public @NotEmpty @Size(min = 3, max = 20) String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty @Size(min = 3, max = 20) String username) {
        this.username = username;
    }

    public boolean isShareData() {
        return shareData;
    }

    public void setShareData(@NotEmpty boolean shareData) {
        this.shareData = shareData;
    }

    public @NotEmpty String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotEmpty String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

