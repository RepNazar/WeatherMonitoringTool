package ua.Nazar.Rep.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    private boolean active;

    /**/
    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    private String activationCode;
    /**/

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Record> records;

    /**
     * @param o
     * @return o.id=user.id
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    /**
     * @return Hash by Id
     */
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    /**
     * @return Roles.contains(MAIN_ADMIN)
     */
    public boolean isAdmin() {
        return roles.contains(Role.MAIN_ADMIN);
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return isActive()
     */
    @Override
    public boolean isEnabled() {
        return isActive();
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Roles list
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * @return email
     */
    /**/
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return activationCode
     */
    public String getActivationCode() {
        return activationCode;
    }

    /**
     * @param activationCode
     */
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }/**/

    /**
     * @return
     */
    public Set<Record> getRecords() {
        return records;
    }

    /**
     * @param records
     */
    public void setRecords(Set<Record> records) {
        this.records = records;
    }
}
