package wantedpreonboardingbackend.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "companyId")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String country;

    @Column(length = 50, nullable = false)
    private String region;

    public Company(String name, String country, String region) {
        this.name = name;
        this.country = country;
        this.region = region;
    }
}
