#프로그래밍 준비
## 개발 도구 설치
- openjdk 10.0.1
- eclipse photon
- visual studio code
- git client
- scoop (package manager)
- scoop install gradle
- scoop install mysql

## mysql 설정
```
> mysql -uroot -p
Enter password: (암호 없기 때문에 그냥 엔터 친다.)

root 사용자의 암호 설정
mysql> update user set
update user set authentication_string=password('1111') where user='root';

암호 변경 후 권한 갱신
mysql> flush privileges;
mysql> quit

다시 접속
mysql -uroot -p
Enter password: 1111
```
애플리케이션에서 DB에 접속할 때 사용할 사용자를 추가한다.
mysql> create user 'study'@'localhost' identified by '1111';

study 사용자가 사용할 데이터베이스 생성한다.
mysql> create database studydb character set utf8 collate utf8_general_ci;

studydb 데이터베이스의 사용 권한을 study 사용자에게 부여한다.
mysql>  grant all privileges on studydb.* to 'study'@'localhost';

study 사용자로 접속한다.
mysql> quit 또는 exit
mysql> mysql -u study -p
Enter password: 1111
```
study 사용자가 사용할 수 있는 데이터베이스 목록 보기.
mysql> show databases;

사용할 데이터베이스 선택
mysql> use studydb;

studydb에 존재하는 테이블 목록 보기
mysql> show tables;

studydb에 테이블 생성
=> bitcamp-sql/pms-ddl.sql 실행
mysql> SQL을 복사하여 붙여 넣는다.


-- 게시판
CREATE TABLE pms2_board (
    bno  INT          NOT NULL COMMENT '번호', -- 번호
    titl VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
    cont TEXT         NULL     COMMENT '내용', -- 내용
    cdt  DATETIME     NOT NULL COMMENT '등록일' -- 등록일
)
부터 차례대로 복붙

```
## Tomcat 설치
```
1) 톰캣 다운로드
- tomcat.apache.org 에서 다운로드
2) 톰캣 설치
- c:\apps 폴더에 압축 푼다.
```




## eclipse 설정
```
워크스페이스 설정
1) 문자집합을 utf-8로 설정한다.
- Preferances/General/Workspace/Text file encoding
    - UTF-8로 설정한다.
2) 에디터 기본 환경 설정
- Preferences/Gesneral/Editors/Text Editors
- 탭 크기를 2 또는 4 지정
- 탭을 스페이스로 표현
- 한줄 크기는 80자 정도
- 탭이나 공백
insert spaces for tabs 체크
show print margint 체크
allow editors to override the .. 체크
show whitespace 체크 -> confiqure visibility 클릭
Trailing 에 Carriage Return, Line Feed 체크 해제 투명도 30

3) 자바 설정
- Preferences/Java
    - Installed JREs : JDK 위치 지정하기 jdk-10.0.1 체크
    - Code Style/Formatter : 자바 에디터 탭 정보 설정 new-> name: my -> spaces only 바꿔줌
    - Compiler: 기본 컴파일 버전 설정 (10)으로 설정

4) 웹 환경 설정
  - Preferences/Web
    - CSS Files : 문자 집합을 UTF-8 로 설정
    - HTML Files : 문자 집합을 UTF-8 로 설정
    - JSP Files : 문자 집합을 UTF-8 로 설정
5) WAS 서버 환경 설정
- Preferences/Servers/Runtime Environments: 톰캣 서버 위치 설정
Apache Tomcat v9.0 선택
톰캣 설치된 폴더를 선택
Servers 뷰에서 서버 클릭하고 server name을 pms로 바꾸고 -> finish

오라클 보조서버도 8080 써서 포트가 이미 사용중이라고 에러가 뜰 수있음
서버를 실행하면 홈네트워크 체크하고 엑세스 허용 -> 에러나는지 콘솔로 확인

6) 애플리케이션을 배포하고 테스트할 톰캣 실행 환경 설정
- Server 뷰
 - 테스트용

```


## 웹 프로젝트 폴더 준비

```
1) 예제 프로젝트 복사
- java106-java-project 를 bitcamp-cloud-computing에 복사해서 붙여넣기
- pms-project로 이름 바꾸고 src 빼고 src00~src54 까지 삭제
2) 프로젝트 폴더를 이클립스 프로젝트로 만든다.
-'gradle eclipse'를 실행하여 이클립스 설정 파일을 생성한다.
-.project, .classpath, .settings/ 등이 있어야만
이클립스는 임폴트 할 수 있다
```

