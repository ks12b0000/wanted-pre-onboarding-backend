package wantedpreonboardingbackend.postings.dto;

import lombok.Builder;
import lombok.Getter;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;

import java.util.Optional;

@Getter
public class PostingsListResponse {

    private Long id;

    private String name;

    private String country;

    private String region;

    private String position;

    private Long compensation;

    private String technology;

    public PostingsListResponse(Postings postings, Optional<Company> company) {
        this.id = postings.getId();
        this.name = company.get().getName();
        this.country = company.get().getCountry();
        this.region = company.get().getRegion();
        this.position = postings.getPosition();
        this.compensation = postings.getCompensation();
        this.technology = postings.getTechnology();
    }
}
