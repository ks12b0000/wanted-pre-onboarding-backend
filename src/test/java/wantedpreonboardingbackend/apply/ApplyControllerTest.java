package wantedpreonboardingbackend.apply;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import wantedpreonboardingbackend.apply.dto.ApplyRequest;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;
import wantedpreonboardingbackend.domain.User;
import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ApplyControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final User user = new User("example123", "example123", "example12@gmail.com", "company");
    private static final Company company = new Company("원티드", "한국", "판교", user);
    private static final Postings postings = new Postings(company, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python", user);


    @Test
    @DisplayName("채용 공고 지원 테스트")
    void postingsApply() throws Exception {
        // given
        ApplyRequest request = new ApplyRequest(5L, 6L);
        String content = new ObjectMapper().writeValueAsString(request);

        // when
        ResultActions resultActions = mvc.perform(post("/api/postings/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("message").value("채용 공고 지원에 성공했습니다."));
    }

    @Test
    @DisplayName("채용 공고 지원 실패 테스트")
    void postingsApplyFail() throws Exception {
        // given
        ApplyRequest request = new ApplyRequest(5L, 1L);
        String content = new ObjectMapper().writeValueAsString(request);

        // when
        ResultActions resultActions = mvc.perform(post("/api/postings/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("5001"))
                .andExpect(jsonPath("message").value("이미 지원한 채용 공고입니다."));
    }

}
