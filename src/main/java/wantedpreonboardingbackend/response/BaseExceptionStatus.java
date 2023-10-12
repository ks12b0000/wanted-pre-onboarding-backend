package wantedpreonboardingbackend.response;

import lombok.Getter;

@Getter
public enum BaseExceptionStatus {

    SERVER_INTERNAL_ERROR(2002, "서버 내부적인 에러"),
    NOT_EXIST_COMPANY(3001, "존재하지 않는 회사입니다.");

    private final int code;
    private final String message;

    private BaseExceptionStatus(int code, String msg){
        this.code = code;
        this.message = msg;
    }
}
