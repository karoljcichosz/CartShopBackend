package kc.cartshop.backend.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Privilege(String name) {
        this.name = name;
    }

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}