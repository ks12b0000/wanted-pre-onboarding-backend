package wantedpreonboardingbackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Postings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postingsId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @Column(length = 100, nullable = false)
    private String position;

    @Column(nullable = false)
    private Long compensation;

    @Column(columnDefinition = "LONGTEXT")
    private String contents;

    @Column(length = 50, nullable = false)
    private String technology;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Postings(Company company, String position, Long compensation, String contents, String technology, User user) {
        this.company = company;
        this.position = position;
        this.compensation = compensation;
        this.contents = contents;
        this.technology = technology;
        this.user = user;
    }

    public void update(String position, Long compensation, String contents, String technology) {
        this.position = position;
        this.compensation = compensation;
        this.contents = contents;
        this.technology = technology;
    }
}
