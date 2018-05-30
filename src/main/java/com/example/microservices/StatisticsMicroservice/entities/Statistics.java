package com.example.microservices.StatisticsMicroservice.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "latest_statistics")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DESCRIPTION")
    private @NotNull @NotBlank @NotEmpty String description;

    @Column(name = "DATE")
    private Date date;

    @Email
    @Column(name = "EMAIL")
    private @NotNull @NotBlank @NotEmpty String email;

    public Statistics() {
    }

    public Statistics(Integer id, String description, Date date, @Email String email) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.email = email;
    }

    @PrePersist
    private void getTimeOperation() {
        date = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
