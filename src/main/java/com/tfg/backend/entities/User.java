package com.tfg.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "username")
})
// Entity class representing a user in the system
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // User's unique identifier

    @Column(nullable = false, unique = true)
    private String username; // User's unique username

    @Column(nullable = false, unique = true)
    private String email; // User's unique email address

    private String password; // User's password

    private String aboutMe; // Brief description about the user

    private String profileImageUrl; // URL to the user's profile image

    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate; // Date and time of user registration

    @Enumerated(EnumType.STRING)
    private Role role; // User's role in the system (ADMIN or USER)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Publications> publications; // List of publications made by the user

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Comments> comments; // List of comments made by the user

    // Automatically sets the registration date before persisting a new user
    @PrePersist
    protected void onCreate() {
        this.registrationDate = new Date();
    }

    // Returns the authorities granted to the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // Indicates if the user's account has expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indicates if the user's account is locked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Indicates if the user's credentials (password) have expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indicates if the user is enabled or disabled
    @Override
    public boolean isEnabled() {
        return true;
    }
}
