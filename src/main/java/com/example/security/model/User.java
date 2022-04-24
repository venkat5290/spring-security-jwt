package com.example.security.model;

import lombok.Data;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="rolestab",
            joinColumns = @JoinColumn(name="id")
    )
    @Column(name="role")
    private Set<String> roles;

}
