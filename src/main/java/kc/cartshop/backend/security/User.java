package kc.cartshop.backend.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "email")
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean tokenExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_email", referencedColumnName = "email"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    public User(String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public List<Privilege> getPrivileges() {
        List<Privilege> privileges = new LinkedList<>();
        roles.forEach(r->privileges.addAll(r.getPrivileges()));
        return privileges;
    }
}
