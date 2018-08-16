# 실기 시험 (2018-08-10)

# 평가내용	
- 주어진 주제의 시스템에 대한 기능 구현 여부를 평가한다.
- UI 프로토타입 : https://ovenapp.io/view/U1sBifsUKVsxN5CKRYj3NvrGupTBF54F/fTMo8

# 프로젝트 주제	
개인 명함관리시스템

# 구현기능	
- 회원가입(15점)
- 로그인(10점)
- 로그아웃(5점)
- 회원탈퇴(5점)
- 명함등록(15점)
- 명함목록조회(10점)
- 명함상세조회(10점)
- 명함변경(10점)
- 명함삭제(5점)
- 명함목록조회에서 검색 기능 추가(15점)


# 평가기준	
- 구현 완료(15점/10점/5점)
- 구현 부족(13점/9점/4점)
- 미구현(11점/8점/3점)

### 1단계 - 요구사항정의
- Use Case Model 작성
  - Acotr 식별
  - Actor를 통해 Use Case 식별
- User Case 명세서 작성
  - 회원가입탈퇴 명세서 작성
  - 회원인증 명세서 작성
  - 명함관리 명세서 작성
- DB 모델링
  - DB 모델 작성
  - Database에 테이블 생성




---------------------------------------------------------------------------
### 시스템을 개발하려고 하면 제안 요청서(RFP)가 필요.
- RFP란, 고객이 요구사항을 체계적으로 정리하여 제시함으로써 제안자가 제안서를 작성하는데 도움을 주기 위한 문서.
  - 제안요청서(제안을 요청하는 문서)
  - 제안자(개발회사들)
  - 제안을 위한 요청
  - 처음에는 RFP로 시작한다. 그 다음에는 시스템 제안서..

### 개인명함관리는 Actor가 한 명이다.
- Use Case는 시작과 끝이 명확, 카운트(셀수있어야함),시스템이 설정하는 업무 => 회원가입, 로그인(업무X 인증하는것이지만 시스템을 사용할떄 필요함으로써 use case다), 명함조회, 명함변경, 명함등록, 명함삭제, 명함검색, 탈퇴, 로그아웃
- CRUD인 경우 Use Case는 XXXManage..처럼 묶을수 있다
  - 명함관리(명함조회,변경,등록,삭제)+(검색을 포함시킬수도 있다.)
  - 사용자인증(로그인, 로그아웃)
  - 회원가입탈퇴(회원가입,탈퇴)
  



