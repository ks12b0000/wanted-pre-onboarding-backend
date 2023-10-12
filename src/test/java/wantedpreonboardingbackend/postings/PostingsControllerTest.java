package wantedpreonboardingbackend.postings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PostingsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("채용 공고 등록 테스트")
    void postingsSave() throws Exception {
        // given
        // company 데이터 1개 직접 저장 시켜놔서 테스트 가능.
        PostingsWriteRequest request = new PostingsWriteRequest(1L, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        String content = new ObjectMapper().writeValueAsString(request);

        // when
        ResultActions resultActions = mvc.perform(post("/api/postings/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("message").value("채용 공고 등록에 성공했습니다."));
    }

    @Test
    @DisplayName("채용 공고 등록 실패 테스트")
    void postingsSaveFail() throws Exception {
        // given
        // company 데이터 1개 직접 저장 시켜놔서 테스트 가능.
        PostingsWriteRequest request = new PostingsWriteRequest(2L, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        String content = new ObjectMapper().writeValueAsString(request);

        // when
        ResultActions resultActions = mvc.perform(post("/api/postings/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("3001"))
                .andExpect(jsonPath("message").value("존재하지 않는 회사입니다."));
    }
}
