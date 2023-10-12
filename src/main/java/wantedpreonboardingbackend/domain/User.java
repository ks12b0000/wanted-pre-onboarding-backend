package wantedpreonboardingbackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(length = 50, nullable = false)
    private String loginId;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String role;

    public User(String loginId, String password, String email, String role) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
