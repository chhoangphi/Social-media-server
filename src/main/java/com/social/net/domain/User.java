package com.social.net.domain;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.social.net.common.domain.AbstractEntity;
import com.social.net.common.utils.App;
import com.social.net.domain.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User extends AbstractEntity implements UserDetails, App {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.PRIVATE)
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    private Long createAt;

    private String password;
    private String firstname;
    private String lastname;

    @OneToOne(cascade = CascadeType.REMOVE)
    private File avatar;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Post> posts;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Profile profile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
