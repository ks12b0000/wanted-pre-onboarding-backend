package wantedpreonboardingbackend.postings.dto;

import lombok.Getter;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;

import java.util.List;
import java.util.Optional;

@Getter
public class PostingsDetailResponse {

    private Long id;

    private String name;

    private String country;

    private String region;

    private String position;

    private Long compensation;

    private String technology;

    private String contents;

    private List<Long> otherPostingsId;

    public PostingsDetailResponse(Postings postings, Company company, List<Long> otherPostingsIdList) {
        this.id = postings.getId();
        this.name = company.getName();
        this.country = company.getCountry();
        this.region = company.getRegion();
        this.position = postings.getPosition();
        this.compensation = postings.getCompensation();
        this.technology = postings.getTechnology();
        this.contents = postings.getContents();
        this.otherPostingsId = otherPostingsIdList;
    }
}
