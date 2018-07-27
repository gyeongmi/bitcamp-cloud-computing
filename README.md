# bitcamp-cloud-computing
비트캠프 클라우드 컴퓨팅 과정

## web-01 : 웹 프로젝트 폴더 준비
- gradle을 이용하여 프로젝트 폴더를 만드는 방법 

## web-02 : 서블릿 + JDBC
- 서블릿으로 웹 애플리케이션을 만들고 배포하는 방법
- JDBC를 이용하여 데이터를 다루는 방법

## web-03 : DAO + DTO(VO) + ServletContextListener
- 데이터 처리를 전문으로 하는 DAO 객체를 도입
- ServletContextListener에서 DAO 객체를 준비한다.
- ServletContext 보관소에 DAO 객체를 저장한다.
- 서블릿은 ServletContext 보관소에서 DAO를 꺼내 사용한다.

## web-04 : 서블릿 + DAO + JSP = MVC 아키텍처
- JSP 기술을 사용하여 출력을 단순화하는 방법
- MVC 아키텍처의 개념 

## web-05 : Persistence Framework 도입 
- DAO에 mybatis 프레임워크를 적용하여 코드와 SQL문을 분리한다.
- 반복적으로 작성했던 JDBC 코드를 캡슐화한다.

## web-06 : Front Controller 도입
- 서블릿들이 공통으로 수행하는 작업을 프론트 컨트롤러에게 맡긴다.
- 나머지 서블릿들은 "Page Controller"로 부른다.

## web-07 : Page Controller를 POJO로 변환
- 프론트 컨트롤러가 도입되면 페이지 컨트롤러는 일반 자바 객체로 만들어도 된다. 

## web-08 : 애노테이션으로 요청 핸들러 다루기
- 요청 핸들러(Request Handler) : 클라이언트 요청이 들어 왔을 때 호출되는 메서드이다. 즉 클라이언트 요청을 처리하는 메서드.
- 인터페이스를 구현하는 대신에 애노테이션으로 요청 핸들러를 표시하여 프론트 컨트롤러가 찾게 한다.
- 이렇게 함으로써 페이지 컨트롤러를 만들 때 특정 인터페이스에 종속되지 않게 한다.

## web-09 : 객체 생성을 자동화하기 위해 IoC 컨테이너를 만들기
- IoC 컨테이너를 통해 페이지 컨트롤러의 객체를 자동 생성한다.
- 페이지 컨트롤러의 의존 객체를 자동으로 주입한다.

## web-10 :Spring Ioc 컨테이너 도입
- 기존 직접 만든 Ioc 컨테이너 대신에 Spring 컨테이너를 사용한다.

## 라이브러리 추가
- mvnrepository.com 에서 spring-context 라이브러를 찾는다.
- build.gradle에 의존 라이브러리 정보를 추가한다.
- 'gradle cleanEclipse'를 실행하여 기존 이클립스 설정을 제거한다.
- 'gradle eclipse'를 실행하여 이클립스 관련 설정 파일을 새로 만든다.
- 이때 추가한 의존 라이브러리가 자동으로 다운로드 될 것이다.
- 웹 프로젝트를 리프래시 하여 라이브러리 정보를 갱신한다. 

## ContextLoaderListener 에서 Spring IoC 컨테이너 준비하기
- 기존의 ApplicationContext 대신에 Spring IoC 컨테이너 객체를 생성한다.
- bitcamp/pms/config/application-context.xml 파일 생성한다

## DispatcherServlet에서 Spring IoC 컨테이너를 사용하기
- 기존의 ApplicationContext 대신에 Spring IoC 컨테이너에 들어 있는 페이지 컨트롤러를 찾아 실행한다.

## ApplicationContext 클래스 제거한다.

## 우리가 작성한 애노테이션 제거한다.
- @Component 애노테이션 제거
- @Controller 애노테이션 제거
- @Repository 애노테이션 제거
- @Autoworied 애노테이션 제거

## DAO와 페이지 컨트롤러에서 사용한 애노테이션 패키지를 변경한다.
- 스프링 애노테이션으로 변경한다.

## web-11 : Spring + MyBatis 연동하기
- 기존에 직접 만든 SqlSessionFactoryBean 대신에 Mybatis가 제공하는 SqlSessionFactoryBean을 사용한다.
- DB 커넥션풀은 Mybatis 대신에 Spring에서 관리한다.
- 트랜잭션을 다룰 수 있도록 트랜잭션 관리자를 설정한다.
- Proxy 패턴을 사용한 DAO 자동 생성 기능을 이용한다.

## Mybatis에서 제공하는 SqlSessionFactoryBean 객체 사용하기
- 기존의 SqlSessionFactoryBean 대신에 Mybats-Spring에서 제공하는 클래스 사용.
- Mybatis를 Spring과 연동하면 DataSource는 Spring 설정된 것을 사용해야 한다.
- Mybatis 설정 파일에 등록된 DataSource는 사용하지 않는다.

## Spring에 DataSource 등록하기
- mvnrepository.com  에서 "commons dbcp"를 검색하여 라이브러리를 찾는다.
- 라이브러리를 build.gradle 파일에 등록하고, gradle을 이용해 가져 온다.
- 웹 프로젝트를 리프레시 한다.
- application-context.xml 스프링 설정 파일에 DataSource를 설정한다.
- Spring에서 DataSource를 설정할 때는 "spring-jdbc" 라이브러리를 추가해야 한다.
- 트랜잭션 관리자도 Spring에 등록한다.

##DAO 구현체를 자동 생성하는 MapperScannerConfiguer 등록하기
- Mybatis에서 제공하는 DAO 구현체 자동 생성기를 등록하면 개발자가 DAO 클래스를 직접 작성할 필요가 없다
- 대신 개발자는 DAO 인터페이스만 만들면 된다

## 기존의 DAO 클래스를 인터페이스로 변경하기
- 기존에 작성된 DAO 클래스를 인터페이스로 변경한다.
- 단 인터페이스 명과 SQL 맵퍼의 namespace가 같게 해야 한다.
- 인터페이스의 메서드명과 SQL의 id도 같아야 한다.
- 인터페이스의 메서드 파라미터는 한개여야 한다. 
- 물론 메서드의 파라미터는 SQL 의 parameterType과 같아야 한다.

## web-12 : Spring WebMVC의 ContextLoaderListener 사용하기
- 기존에 직접 만든 ContextLoaderListener 대신에 Spring WebMVC에서 제공하는 클래스를 사용한다.
- /WEB-INF/web.xml에 리스너를 등록해야 한다.

## Spring의 CharacterEncodingFilter 사용하기
- 기존에 직접 작성한 CharacterEncodingFilter 대신에 Spring의 필터를 사용하여 POST 한글 파라미터 값을 깨지지 않도록 처리한다.

## web-13 : Spring WebMVC의 DispatcherServlet 사용하기
- 기존에 직접 만든 DispatcherServlet 대신에 Spring WebMVC에서 제공하는 클래스를 사용한다.

## 프론트 컨트롤러를 Spring WebMVC 클래스로 교체한다.
- 기존에 직접 만든 DispatcherServlet을 Spring 클래스로 교체한다.
- web.xml에 servlet을 등록해야 한다.

## 페이지 컨트롤러의 @Controller에 지정한 URL을 @RequestMapping으로 옮긴다.
- 페이지 컨트롤러의 이름으로 지정했던 URL을 요청 핸들러의 @RequestMapping으로 옮긴다.

## @RequestMapping 애노테이션을 처리할 BeanPostProcessor를 등록한다.
- 요청 핸들러에 붙인 @RequestMapping을 처리할 객체를 Spring에 따로 등록해야 한다.
- 직접 해당 객체를 등록하지 말고, 단축 태그를 이용해 등록하라

```
<mvc:annotation-driven/>
애노테이션을 처리할 객체
```

## web-14 : JSP 경로를 /WEB-INF/ 폴더 아래로 옮긴다.
- /WEB-INF 폴더 아래로 JSP를 옮기면 클라이언트에서 직접 요청할 수 없다.
- 이렇게 하면 JSP를 실행하기 위해 반드시 페이지 컨트롤러를 경유하도록 제한할 수 있다.

## /webapp 에 존재하는 JSP 파일을 /WEB-INF 폴더로 옮긴다.
- /member 폴더를 /WEB-INF/jsp 폴더로 옮긴다.
- 프론트 컨트롤러(DispatcherServlet)에 기본으로 설정되어 있는 ViwResolver를 다른 뷰리졸버(InternalResourceViewResolver)로 교체한다.

## 페이지 컨트롤러의 리턴 값을 변경한다.
- 옮겨진 JSP 파일의 경로와 InternalResourceViewResolver에 설정된 접두사/접미사에 맞춰 view 이름을 리턴한다.

## web-15 : 요청 핸들러의 파라미터와 리턴 값 다루기
- 요청 핸들러의 파라미터 다루기.
- 요청 핸들러의 리턴 다루기.

## 요청 핸들러의 매트릭스 변수 활성화시키기
- application-context.xml에서 다음과 같이 설정을 변경한다.
```
<mvc:annotation-driven enable-matrix-variables="true">
```
## 각 페이지 컨트롤러에 대해 요청 핸들러의 파라미터를 정리한다.
## 각 페이지 컨트롤러에 대해 요청 핸들러의 리턴 값를 정리한다.
## 각 페이지 컨트롤러에 대해 요청 핸들러의 애노테이션을 정리한다.
## CRUD 관련 메서드는 한 개의 컨트롤러 클래스에 묶어 관리한다.

## web-16 : 서비스 컴포넌트 도입
- 페이지 컨트롤러에서 비지니스 로직을 분리하여 별도의 클래스로 정의한다.
- 이 클래스를 서비스 객체라 부른다.
- 서비스 객체는 비지니스 로직과 트랜잭션 처리를 담당한다.

## 서비스 객체 생성
- MemberService 클래스 작성

## 페이지 컨트롤러는 DAO 대신 Service 객체를 사용한다.
- 페이지 컨트롤러 변경

## 페이지 이동 처리
- /WEB-INF-jsp/member/list.jsp 변경
- MemberMapper.xml 변경
- MemberDao 변경
- MemberController 변경

## web-16 : 서비스 컴포넌트에 트랜잭션 적용
- 트랜잭션 관리자를 설정하여 서비스 메서드에 트랜잭션을 적용한다

## @Transactional 애노테이션으로 트랜잭션 관리하기
- 서비스 객체의 각 메서드에 대해 애노테이션을 붙여 트랜잭션 정책을 지정할 수 있따

## Spring Ioc 설정 파일에 트랜잭션 애노테이션을 처리할 객체를 등록한다.
```
<tx:annotation-driven transaction-manager="txManager"/>
```

## 서비스 객체의 메서드에 @Transactional 애노테이션을 붙인다

## XML로 트랜잭션 관리하기
## AOP 관련 의존 라이브러리 추가
- mvnrepository 에서 'aspectj weaver' 라이브러리 검색

## 스프링 IoC 컨테이너 설정 파일에 트랜잭션 추가
<tx:advice id="txAdvice" transaction-manager="txManager">
	        <tx:attributes>
	            <tx:method name="get*" read-only="true"/> 
	            <tx:method name="list*" read-only="true"/>
	            <tx:method name="*"/>
	        </tx:attributes>
        </tx:advice>

        <aop:config> 
            <aop:pointcut id="ServiceOperation" 
                expression="execution(* bitcamp.pms.service.*.*(..))"/>
            <aop:advisor advice-ref="txAdvice" 
                pointcut-ref="ServiceOperation"/> 
        </aop:config>


# web-18 : AJAX를 이용하여 Back-end와 Front-end 분리하기
- JSP에서 HTML을 랜더링하는 대신에 HTML의 자바스크립트를 이용하여 서버에서 데이터를 받아서 UI를 출력하는 방식으로 바꾼다.

## JSON
- ContextLoaderListener 에서 관리하는 객체 중에 웹 관련 컴포넌트들은 프론트 컨트롤러의 IoC가 관리하도록 이관한다.
- app.servlet.xml , json-servlet.xml 파일 생성
- web.xml에서 프론트 컨트롤러에 IoC 컨테이너 설정 파일을 등록한다.