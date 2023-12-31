# wanted-pre-onboarding-backend

## ERD
<img width="663" alt="스크린샷 2023-10-14 오후 7 02 24" src="https://github.com/ks12b0000/wanted-pre-onboarding-backend/assets/102012155/f1de9bac-3e4d-41ee-904f-6bd20d733ca9">

- User : 회원 테이블(유저 아이디, 로그인 아이디, 패스워드, 이메일, 역할)
- Company : 회사 테이블(회사 아이디, 이름, 국가, 지역, 유저 아이디)
- Postings : 채용 공고 테이블(공고 아이디, 회사 아이디, 포지션, 보상금, 내용, 기술, 유저 아이디)
- ApplyHistory : 지원 내역 테이블(내역 아이디, 유저 아이디, 회사 아이디)

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

##
2. 채용공고 수정
    1. 요구사항 분석
        1. 채용 공고 수정이 가능한 회사의 계정으로 로그인한다.
            - 계정의 권한이 채용 공고 수정이 불가능한 권한일 경우 채용 공고 수정 불가.
        2. 수정할 채용 공고를 선택한다.
            - 채용 공고가 없는 경우 수정 불가.
        3. 로그인한 유저와 채용 공고 작성자랑 다를 경우 수정 불가.
        4. 채용 공고를 수정 할 채용 포지션, 채용 보상금, 채용 내용, 사용 기술을 입력한다.
            - 빈 값이 들어올 경우 채용 공고 수정 불가.
   2. 구현 과정
       1. [PATCH] /api/postings/{postingsId}/{userId} 로 PathVariable에 postingsId, userId, RequestBody에 PostingsUpdateRequest(채용 포지션, 채용 보상금, 채용 내용, 사용 기술)을 입력해서 요청이 들어온다.
       2. @Validated를 사용하여 모든 값에 빈 값이 들어올 경우 예외처리.
       3. 입력 받은 유저 아이디로 유저 조회.
           - 유저가 존재하지 않으면 예외처리.
           - 유저의 권한이 채용 공고 수정이 불가능한 권한일 경우 예외처리.
       4. 입력 받은 공고 아이디로 채용 공고 조회.
           - 채용 공고가 존재하지 않으면 예외처리.
           - 로그인한 유저와 채용 공고 작성자랑 다를 경우 예외처리.
       5. 입력 받은 내용들을 채용 공고 DB에 수정.
 ```json
  # [PATCH] /api/postings/{postingsId}/{userId}
  {
    "compensation": 1000000,
    "contents": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
    "position": "백엔드 주니어 개발자",
    "technology": "Django" # 변경됨
  }
  # 성공
  {
    "code": 1000,
    "message": "채용 공고 수정에 성공했습니다."
  }
  # 실패 2-b @Validated를 사용하여 모든 값에 빈 값이 들어올 경우 예외처리.
  {
    "code": 2001,
    "message": "채용 포지션을 입력해주세요."
  }
  # 실패 2-c 유저가 존재하지 않거나, 채용 공고 수정이 불가능한 권한일 경우 예외처리.
  {
    "code": 4001,
    "message": "존재하지 않는 유저입니다."
  }
  OR
  {
    "code": 4002,
    "message": "접근 권한이 없는 유저입니다."
  }
  # 실패 2-d 채용 공고가 존재하지 않거나, 로그인한 유저와 채용 공고 작성자랑 다를 경우 예외처리.
  {
    "code": 4004,
    "message": "존재하지 않는 채용 공고입니다."
  }
  OR
  {
    "code": 4002,
    "message": "접근 권한이 없는 유저입니다."
  }
  ```

##
3. 채용공고 삭제
    1. 요구사항 분석
        1. 채용 공고 삭제가 가능한 회사의 계정으로 로그인한다.
            - 계정의 권한이 채용 공고 삭제가 불가능한 권한일 경우 채용 공고 삭제 불가.
        2. 삭제할 채용 공고를 선택한다.
            - 채용 공고가 없는 경우 삭제 불가.
        3. 로그인한 유저와 채용 공고 작성자랑 다를 경우 삭제 불가.
    2. 구현 과정
        1. [DELETE] /api/postings/{postingsId}/{userId} 로 PathVariable에 postingsId, userId를 입력해서 요청이 들어온다.
        2. 입력 받은 유저 아이디로 유저 조회.
            - 유저가 존재하지 않으면 예외처리.
            - 유저의 권한이 채용 공고 삭제가 불가능한 권한일 경우 예외처리.
        3. 입력 받은 공고 아이디로 채용 공고 조회.
           - 채용 공고가 존재하지 않으면 예외처리.
           - 로그인한 유저와 채용 공고 작성자랑 다를 경우 예외처리.
        4. 채용 공고 DB에서 삭제.
 ```json
  # [DELETE] /api/postings/{postingsId}/{userId}
  # 성공
  {
    "code": 1000,
    "message": "채용 공고 삭제에 성공했습니다."
  }
  # 실패 2-b 유저가 존재하지 않거나, 채용 공고 삭제가 불가능한 권한일 경우 예외처리.
  {
    "code": 4001,
    "message": "존재하지 않는 유저입니다."
  }
  OR
  {
    "code": 4002,
    "message": "접근 권한이 없는 유저입니다."
  }
  # 실패 2-c 채용 공고가 존재하지 않거나, 로그인한 유저와 채용 공고 작성자랑 다를 경우 예외처리.
  {
    "code": 4004,
    "message": "존재하지 않는 채용 공고입니다."
  }
  OR
  {
    "code": 4002,
    "message": "접근 권한이 없는 유저입니다."
  }
  ```

##
4. 4-1 채용공고 목록 조회
    1. 요구사항 분석
        1. 채용 공고 전체 목록을 조회한다.
        2. 조회한 채용 공고에 맞는 회사를 조회 후 리스트 목록에 필요한 값을 매핑에서 나타내준다.
    2. 구현 과정
        1. [GET] /api/postings/all/list 로 요청이 들어온다.
        2. 채용 공고 전체 목록을 조회.
        3. 조회한 채용 공고에 맞는 회사를 조회 후 PostingsListResponse에 맞는 값들을 매핑해서 나타내준다.
 ```json
  # [GET] /api/postings/all/list
  # 성공
    {
        "code": 1000,
        "message": "채용 공고 목록 조회에 성공했습니다.",
        "result": [
            {
                "id": 5,
                "name": "원티드",
                "country": "한국",
                "region": "판교",
                "position": "백엔드 주니어 개발자",
                "compensation": 1000000,
                "technology": "Python"
            },
            {
                "id": 6,
                "name": "원티드",
                "country": "한국",
                "region": "판교",
                "position": "백엔드 주니어 개발자",
                "compensation": 1000000,
                "technology": "Python"
            }
        ]
   }
  ```

##
4. 4-2 채용공고 검색 조회
    1. 요구사항 분석
        1. 검색어에 맞는 채용 공고 목록을 조회한다.
        2. 조회한 채용 공고에 맞는 회사를 조회 후 리스트 목록에 필요한 값을 매핑에서 나타내준다.
    2. 구현 과정
        1. [GET] /api/postings/search/list?keyword= 로 요청이 들어온다.
        2. 검색어에 맞는 채용 공고 목록을 조회.
        3. 조회한 채용 공고에 맞는 회사를 조회 후 PostingsListResponse에 맞는 값들을 매핑해서 나타내준다.
 ```json
  # [GET] /api/postings/search/list?keyword="Python"
  # 성공
    {
        "code": 1000,
        "message": "채용 공고 검색에 성공했습니다.",
        "result": [
            {
                "id": 5,
                "name": "원티드",
                "country": "한국",
                "region": "판교",
                "position": "백엔드 주니어 개발자",
                "compensation": 1000000,
                "technology": "Python"
            },
            {
                "id": 6,
                "name": "원티드",
                "country": "한국",
                "region": "판교",
                "position": "백엔드 주니어 개발자",
                "compensation": 1000000,
                "technology": "Python"
            }
        ]
   }
  ```

##
5. 채용공고 상세 조회
    1. 요구사항 분석
        1. 채용 공고 아이디에 맞는 채용 공고를 조회한다.
           - 채용 공고가 존재하지 않는 경우 조회 불가.
        2. 조회한 채용 공고를 올린 회사가 올린 다른 채용 공고를 조회한다.
        3. 조회한 채용 공고에 맞는 회사를 조회 후 리스트 목록에 필요한 값을 매핑에서 나타내준다.
    2. 구현 과정
        1. [GET] /api/postings/{postingsId} 로 요청이 들어온다.
        2. 채용 공고 아이디에 맞는 채용 공고를 조회.
            - 채용 공고가 존재하지 않는 경우 예외처리.
        3. 조회한 채용 공고에서 회사 아이디를 찾아 회사를 조회. 
        4. 조회한 회사가 올린 다른 채용 공고 목록을 찾아 List에 채용 공고 아이디를 담는다.
        5. PostingsDetailResponse에 맞는 값들을 매핑해서 나타내준다.
 ```json
  # [GET] /api/postings/{postingsId}
  # 성공
    {
        "code": 1000,
        "message": "채용 공고 목록 조회에 성공했습니다.",
        "result": {
            "id": 5,
            "name": "원티드",
            "country": "한국",
            "region": "판교",
            "position": "백엔드 주니어 개발자",
            "compensation": 1000000,
            "technology": "Python",
            "contents": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
            "otherPostings": [5,6]
        }
    }
  # 실패 2-b 채용 공고가 존재하지 않는 경우
    {
        "code": 4004,
        "message": "존재하지 않는 채용 공고입니다."
    }
  ```

##
6. 채용공고 지원
    1. 요구사항 분석
        1. 채용 공고 아이디와 유저 아이디를 이용하여 채용 공고에 지원한다.
            - 채용 공고가 존재하지 않는 경우 지원 불가.
            - 유저가 존재하지 않는 경우 지원 불가.
        2. 채용 공고에 이미 지원한 유저라면 지원이 불가능 하다.
    2. 구현 과정
        1. [POST] /api/postings/apply 로 RequestBody에 ApplyRequest(채용 공고 아아디, 유저 아이디)을 입력해서 요청이 들어온다.
        2. @Validated를 사용하여 모든 값에 빈 값이 들어올 경우 예외처리.
        3. 입력 받은 유저 아이디로 유저 조회.
           - 유저가 존재하지 않으면 예외처리.
        4. 입력 받은 채용 공고 아이디로 채용 공고 조회.
           - 채용 공고가 존재하지 않으면 예외처리.
        5. 채용 공고에 이미 지원한 유저라면 예외처리.
 ```json
  # [POST] /api/postings/apply
  # 
    {
        "postingsId": 채용공고_id,
        "userId": 유저_id
    }      
  # 성공
    {
        "code": 1000,
        "message": "채용 공고 지원에 성공했습니다.",
    }
  # 실패 2-b @Validated를 사용하여 모든 값에 빈 값이 들어올 경우 예외처리.
    {
        "code": 2001,
        "message": "채용 공고 아이디를 입력해주세요."
    }
  # 실패 2-c 유저가 존재하지 않는 경우
    {
        "code": 4001,
        "message": "존재하지 않는 유저입니다."
    }
  # 실패 2-d 채용 공고가 존재하지 않는 경우
    {
        "code": 4004,
        "message": "존재하지 않는 채용 공고입니다."
    }
  # 실패 2-e 채용 공고에 이미 지원한 유저일 경우
    {
        "code": 5001,
        "message": "이미 지원한 채용 공고입니다."
    }
  ```
