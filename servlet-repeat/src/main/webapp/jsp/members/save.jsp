<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  // 자바코드 삽입
  // request, response 사용 가능
  MemberRepository memberRepository = MemberRepository.getInstance();
  System.out.println("MemberSaveServlet.service");
  final String username = request.getParameter("username");
  final int age = Integer.parseInt(request.getParameter("age"));

  Member member = new Member(username, age);
  memberRepository.save(member);
%>
<%-- 서블릿과 JSP의 한계 뷰를 생성하는 html은 깔끔하게 작업했찌만, 쭝간중간 동적으로 변경해야하는 부분에만 자바코드를 적용했는대, JSP에 모두 노출되어있다.
     JSP가 너무 많은 역할을 한다.
     => MVC 패턴의 등장
--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공 :
<ul>
  <li>id = <%=member.getId()%> </li>
  <li>name = <%= member.getUsername()%></li>
  <li>age = <%= member.getAge()%></li>
</ul>

<a href="/index.html">메인으로</a>

</body>
</html>
