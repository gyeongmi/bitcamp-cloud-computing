### 3단계 - Spring WebMVC 프레임워크 적용
- 의존 라이브러리 설정
   - 'build.gradle' 파일 편집. 기존 프로젝트의 파일 내용을 복사하라!
   - 'gradle eclipse' 실행. 이클립스 설정 파일을 갱신하라.
   - 이클립스 프로젝트에서 로딩된 라이브러리를 확인하라!
   
- web.xml 파일 준비
   - 기존 프로젝트에서 복사해 온다.
   - 현재 프로젝트에 맞춰서 web.xml 파일을 편집한다.

- Spring 설정 파일 준비
   - 패키지 폴더(src/main/resources/bitcamp/assignment/config)를 생성한다.
   - 기존 프로젝트에서 복사해 온다.
   - 현재 프로젝트에 맞춰서 파일을 편집한다.
   
- 테스트 용 소스 작성 및 확인
   - 테스트 용 페이지 컨트롤러와 JSP 작성하여 실행 확인한다.
   - 톰캣 서버에 웹 프로젝트를 추가한다.
   - 톰캣 서버를 실행한다. 콘솔 창에 출력된 로그를 보고 설정 오류가 없는지 확인한다.
   - 웹 브라우저로 요청하여 실행 여부를 확인한다.