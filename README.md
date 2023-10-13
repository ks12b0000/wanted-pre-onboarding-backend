# wanted-pre-onboarding-backend

## ERD
<img width="664" alt="스크린샷 2023-10-13 오후 4 26 18" src="https://github.com/ks12b0000/wanted-pre-onboarding-backend/assets/102012155/b26a6109-08ae-40af-b6d4-400ec83671ee">

- User : 회원 테이블(유저 아이디, 로그인 아이디, 패스워드, 이메일, 역할)
- Company : 회사 테이블(회사 아이디, 이름, 국가, 지역, 유저 아이디)
- Postings : 채용 공고 테이블(공고 아이디, 회사 아이디, 포지션, 보상금, 내용, 기술)
- SupportHistory : 지원 내역 테이블(내역 아이디, 유저 아이디, 회사 아이디)

## Stack
- Java 11
- SpringBoot 2.7.16
- Spring Data JPA
- JUnit5
- Swagger 3.0.0
- MySQL 8.0.32

##
1. 채용공고 등록
    1. 요구사항 분석
        1. 채용 공고 등록이 가능한 회사의 계정으로 로그인한다.
            - 계정의 권한이 채용 공고 등록이 불가능한 권한일 경우 채용 공고 등록 불가.
        2. 채용 공고를 등록 할 회사 아이디, 채용 포지션, 채용 보상금, 채용 내용, 사용 기술을 입력한다.
            - 빈 값이 들어올 경우 채용 공고 등록 불가.
            - 회사가 존재하지 않을 경우 채용 공고 등록 불가.
            - 입력한 회사와 로그인한 계정의 회사와 일치하지 않는 경우 채용 공고 등록 불가.
    2. 구현 과정
        1. [POST] /api/postings/save/{userId} 로 PathVariable에 userId, RequestBody에 PostingsWriteRequest(회사 아이디, 채용 포지션, 채용 보상금, 채용 내용, 사용 기술)을 입력해서 요청이 들어온다.
        2. @Validated를 사용하여 모든 값에 빈 값이 들어올 경우 예외처리.
        3. 입력 받은 유저 아이디로 유저 조회.
            - 유저가 존재하지 않으면 예외처리.
            - 유저의 권한이 채용 공고 등록이 불가능한 권한일 경우 예외처리.
        4. 입력 받은 회사 아이디로 회사 조회.
            - 회사가 존재하지 않으면 예외처리.
            - 회사와 채용 공고를 등록하려는 유저의 회사와 다를 경우 예외처리.
        5. 입력 받은 내용들을 채용 공고 DB에 저장.
  ```json
  # [POST] /api/postings/save/{userId}
  {
    "companyId": 1,
    "compensation": 1000000,
    "contents": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
    "position": "백엔드 주니어 개발자",
    "technology": "Python"
  }
  # 성공
  {
    "code": 1000,
    "message": "채용 공고 등록에 성공했습니다."
  }
  # 실패 2-b @Validated를 사용하여 모든 값에 빈 값이 들어올 경우 예외처리.
  {
    "code": 2001,
    "message": "회사 아이디를 입력해주세요."
  }
  # 실패 2-c 유저가 존재하지 않거나, 채용 공고 등록이 불가능한 권한일 경우 예외처리.
  {
    "code": 4001,
    "message": "존재하지 않는 유저입니다."
  }
  OR
  {
    "code": 4002,
    "message": "접근 권한이 없는 유저입니다."
  }
  # 실패 2-d 회사가 존재하지 않거나, 회사와 채용 공고를 등록하려는 유저의 회사와 다를 경우 예외처리.
  {
    "code": 3001,
    "message": "존재하지 않는 회사입니다."
  }
  OR
  {
    "code": 4003,
    "message": "등록 하려는 회사를 확인 해주세요."
  }
  ```