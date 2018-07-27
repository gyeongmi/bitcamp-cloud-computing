<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 자바코드로 바뀐다. 출력문으로(값으로) -->
${param.a} ${param.op} ${param.b} =
<c:choose>
    <c:when test="${param.op == '+' }">
        ${param.a + param.b}
    </c:when>
    <c:when test="${param.op == '-' }">
        ${param.a - param.b}
    </c:when>
    <c:when test="${param.op == '*' }">
        ${param.a * param.b}
    </c:when>
    <c:when test="${param.op == '/' }">
        ${param.a / param.b}
    </c:when>
    
    <c:otherwise>해당 연산을 지원하지 않습니다.</c:otherwise>
    
</c:choose>

<%

//jsp를 실행하는 현재 쓰레드
Thread.currentThread().sleep(10000); //10초 정도 재움
//버퍼에 저장됐다가 완전히 호출이 끝났을때 클라이언트에게 저장된 버퍼가 보내지는 거임..
//기본 버퍼가 8kb, 만약 버퍼 꽉 차면 atuoFlush="true" 자동으로 8천 바이트 내보낸다.. false이면 버퍼를 넘어갔을때 에러 띄움


%>

<!-- 

클라이언트가 톰캣에 요청하면 쓰레드가 받는다
클라이언트 한명당 하나의 쓰레드
dispatcher 서블릿 객체는 한개. 서비스라는 메소드를 쓰레드가 호출한다..

jsp는 임시 버퍼에 저장됨 -->