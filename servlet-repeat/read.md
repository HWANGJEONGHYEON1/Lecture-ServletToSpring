# 정리

## servlet-package
 * 서블릿과 자바코드만으로 html을 만들어서 동적으로 원하는 html을 만들 수 있다.(화면이 계속달라지는..)
 * 코드에서보면 오타날 확률이 매우 높다.
 * 그래서 동적으로 변경되어야 하는 부분만 자바코드를 넣을 수 있다면 편리할 것 같다 => 템플렛 엔진(jsp, thymeleaf)
 
MVC 패턴 등장
 * JSP 파일안에 너무 많은 역할이 있다.(java, html)
 * 변경의 라이프 사이클
   * 사실 이게 정말 중요한데, 진짜 문제는 둘 사이에 변경의 라이프 사이클이 다르다는 점이다. 예를 들어서 UI 를 일부 수정하는 일과 비즈니스 로직을 수정하는 일은 각각 다르게 발생할 가능성이 매우 높고 대부분 서로에게 영향을 주지 않는다. 이렇게 변경의 라이프 사이클이 다른 부분을 하나의 코드로 관리하는 것은 유지보수하기 좋지 않다.
 * 기능 특화
   특히 JSP 같은 뷰 템플릿은 화면을 렌더링 하는데 최적화 되어 있기 때문에 이 부분의 업무만 담당하는 것이 가장 효과적이다.

## Model View Controller
- 컨트롤러: HTTP 요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행한다. 그리고 뷰에 전달할 결과 데이터를 조회해서 모델에 담는다.
- 모델: 뷰에 출력할 데이터를 담아둔다. 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나 데이터 접근을 몰라도 되고, 화면을 렌더링 하는 일에 집중할 수 있다.
-  뷰: 모델에 담겨있는 데이터를 사용해서 화면을 그리는 일에 집중한다. 여기서는 HTML을 생성하는 부분을 말한다.

redirect vs foward
 * 리다이렉트는 실제 클라이언트에 응답이 나갔다가, 클라이언트가 redirect 경로로 다시 요청. 클라이언트가 인지할 수 있고, URL 경로도 실제로 변경된다.
 * 포워드는 서버내부에서 일어나는 호출이기 때문에 클라이언트가 전혀 인지하지 못한다.

## MVC 패턴의 한계 (servletmvc - package)
- 컨트롤러는 중복이 많다.
- 공통 처리가 어렵다.

```java
    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
    dispatcher.forward(request, response);// 다른 서블릿이나 JSP로 이동할 수 있는 기능
```

## 프론트 컨트롤러 패턴 등장 (입구를 하나로) - FrontControllerServletV1
### v1
- 프론트 컨트롤러 서블릿 하나로 컨트롤러의 요청을 받음
- 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아 호출
- 공통 처리가능
- 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도됨.
- 스프링의 DispatcherServlet 이렇게 구현되어있음

### v2
- view 분리
