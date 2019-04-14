package com.oleg.restdemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"tasks"})
@EqualsAndHashCode(exclude = {"tasks"})
@NoArgsConstructor
@Table(name="users")
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Email
    private String email;
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @Fetch(value=FetchMode.SELECT)
    @JsonIgnore
    private Set<Task> tasks;

    public ApplicationUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
