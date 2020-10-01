package com.inflearn.springsecurity.entity;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    public void encodePassword () {
        this.password = "{noop}" + this.password;
    }
}
