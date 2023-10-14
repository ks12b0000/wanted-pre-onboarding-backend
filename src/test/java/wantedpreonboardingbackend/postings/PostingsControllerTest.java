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
import org.springframework.transaction.annotation.Transactional;
import wantedpreonboardingbackend.postings.dto.PostingsUpdateRequest;
import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PostingsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("채용 공고 등록 테스트")
    void postingsSave() throws Exception {
        // given
        // company 데이터 1개, user 데이터 1개 직접 저장 시켜놔서 테스트 가능.
        PostingsWriteRequest request = new PostingsWriteRequest(1L, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        String content = new ObjectMapper().writeValueAsString(request);

        // when
        ResultActions resultActions = mvc.perform(post("/api/postings/save/1")
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
        // company 데이터 1개, user 데이터 1개 직접 저장 시켜놔서 테스트 가능.
        PostingsWriteRequest request = new PostingsWriteRequest(2L, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        String content = new ObjectMapper().writeValueAsString(request);

        // when
        ResultActions resultActions = mvc.perform(post("/api/postings/save/1")
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

    @Test
    @DisplayName("채용 공고 수정 테스트")
    void postingsUpdate() throws Exception {
        // given
        PostingsUpdateRequest request = new PostingsUpdateRequest("백엔드 주니어 개발자1", 1000001L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은1..", "Python1");
        String content = new ObjectMapper().writeValueAsString(request);

        // when
        ResultActions resultActions = mvc.perform(patch("/api/postings/5/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("message").value("채용 공고 수정에 성공했습니다."));
    }

    @Test
    @DisplayName("채용 공고 수정 실패 테스트")
    void postingsUpdateFail() throws Exception {
        // given
        PostingsUpdateRequest request = new PostingsUpdateRequest("백엔드 주니어 개발자1", 1000001L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은1..", "Python1");
        String content = new ObjectMapper().writeValueAsString(request);

        // when
        // 존재하지 않는 채용 공고 아이디를 넣었을 경우.
        ResultActions resultActions = mvc.perform(patch("/api/postings/0/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // 채용 공고 작성자와 다른 유저 아이디를 넣었을 경우.
        ResultActions resultActions2 = mvc.perform(patch("/api/postings/5/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("4004"))
                .andExpect(jsonPath("message").value("존재하지 않는 채용 공고입니다."));

        resultActions2.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("4002"))
                .andExpect(jsonPath("message").value("접근 권한이 없는 유저입니다."));
    }

    @Test
    @DisplayName("채용 공고 삭제 테스트")
    void postingsDelete() throws Exception {
        // given
        // when
        ResultActions resultActions = mvc.perform(delete("/api/postings/5/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("message").value("채용 공고 삭제에 성공했습니다."));
    }

    @Test
    @DisplayName("채용 공고 등록 실패 테스트")
    void postingsDeleteFail() throws Exception {
        // given
        // when
        // 존재하지 않는 채용 공고 넣었을 경우
        ResultActions resultActions = mvc.perform(delete("/api/postings/0/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // 채용 공고 작성자와 다른 유저 아이디를 넣었을 경우.
        ResultActions resultActions2 = mvc.perform(delete("/api/postings/5/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("4004"))
                .andExpect(jsonPath("message").value("존재하지 않는 채용 공고입니다."));

        resultActions2.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("4002"))
                .andExpect(jsonPath("message").value("접근 권한이 없는 유저입니다."));
    }

    @Test
    @DisplayName("채용 공고 목록 조회 테스트")
    void postingsList() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(get("/api/postings/all/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("message").value("채용 공고 목록 조회에 성공했습니다."));
    }

    @Test
    @DisplayName("채용 공고 검색 테스트")
    void postingsSearchList() throws Exception {
        // given
        String keyword = "Python";
        // when
        ResultActions resultActions = mvc.perform(get("/api/postings/search/list?keyword=" + keyword)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("message").value("채용 공고 검색에 성공했습니다."));
    }

    @Test
    @DisplayName("채용 공고 상세 조회 테스트")
    void postingsDetail() throws Exception {
        // given
        // when
        ResultActions resultActions = mvc.perform(get("/api/postings/5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("message").value("채용 공고 상세 조회에 성공했습니다."));
    }
}
