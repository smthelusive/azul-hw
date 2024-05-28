package smthelusive.entity.security;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.RolesValue;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "role_registry")
public class RoleRegistry extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long roleId;
    @ManyToMany(mappedBy = "roles")
    private Set<UserRegistry> users;
    @RolesValue
    private String role;
}