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

### v2 - FrontControllerServletV2
- view 분리

### v3 - model 추가
- 컨트롤러입장에서는 파라미터 정보가 필요하지, HttpServletRequest, HttpServletResponse가 필용벗다.
- request 객체를 model로 사용하는 대신에 Model 객체를 만들어서 반환하면 된다.(request.setAttribute)
- 서블릿 코드가 필요하지 않다.
- ModelView
  - 기존 컨트롤러에서 HttpServletRequest를 사용
  - model도 setAttribute를 통해 데이터 저장 후 뷰로 전달
  - 서블릿에 종속적이지 않음
  - 각 컨틀롤러에는 필요한 로직만 들어감.
  - 뷰리졸버를 통해 논리적인 이름과 물리적인 이름을 나눠놓으면 frontcontroller만 수정하면 된다.

### v4 - 기본구조는 v3와 같지만 viewName만 반환


### v5 - 어뎁터 패턴
- 원하는 인터페이스를 다양하게 사용하고 싶을 때 사용
- 현재는 제한되어있다.
- 핸들러 어뎁터
  - 중간에서 어뎁터 역할. 다양한 종류의 컨트롤러 호출
- 핸들러 
  - 컨트롤러의 이름을 더 넓은 범위인 핸들로러 변경. 어뎁터가 있기 때문에  
- 정리
  - 핸들러 매핑정보를 찾는다
  - 핸들러 어뎁터 목록에 맞는 어뎁터 있는지 찾는다.
  - 핸들러 어뎁터로 컨트롤러를 반환
  - 모델 뷰 반환
  - 뷰 리졸버에 맞는 모델뷰 반 

# spring mvc 

## DispatcherServlet
  - 프론트 컨트롤러
  - 스프링 MVC 핵심
  - HttpServlet 상속받음
  - 스프링은 DispatcherServlet을 서블릿으로 자동으로 등록 (urlPattern = "/")에 대해 매핑
  - 요청 흐름
    - DispatcherServlet의 부모인 FrameworkServlet에서 service()가 호출 
    - Framework.service()를 시작으로 여러 메서드가 호출되면서 DispatcherServlet.doDispatch()가 호출

## 핸들러 매핑과 핸들러 어뎁터

- 컨트롤러가 호출되려면 2가지 필요
  - HandlerMapping
    - 스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요
    - 핸들러 매핑에서 이 컨트롤러를 찾을수 있어야한다.
  - HandlerAdapter
    - 핸들러 매핑을 통해 찾은 핸들러를 실행할 수 있는 핸들러 어뎁터가 필요
    - Controller 인터페이스를 실행할 수 있는 어뎁터를 찾고 실행해야한다.
- 스프링 부트가 자동으로 등록하는 핸들러 매핑과 핸들러 어뎁터
  - HandlerMapping
    - 0 : RequestMappingHandler => 애노테이션 기반의 핸들러인 @RequestMapping에서 사용
    - 1 : BeanNameUrlHandlerMapping => 스프링 빈의 이름으로 핸들러를 찾는다
  - HandlerAdapter
    - 0 : RequestMappingHandlerAdapter => 애노테이션 기반의 핸들러인 @RequestMapping에서 사
    - 1 : HttpRequestHandlerAdapter => HttpRequestHandler 처리
    - 2 : SimpleControllerHandlerAdapter => Controller 인터페이스 처리(과거에 사용)


## 뷰리졸버
- 자동 등록하는 뷰 리졸버(주요 빈)
  - BeanNameViewResolver => 빈이름으로 찾아서
  - InternalResourceViewResolver => JSP를 처리할 수 있는 뷰를 반환
- InternalResourceViewResolver
  - 스프링부트는 이 리졸버를 자동으로 등록한다.
  - 이 때 application.properties 등록한 prefix, suffix 설정정보를 사용해서 등록한다.

## 실행 흐름
1.핸들러 어뎁터 호출
  - 핸들러 어뎁터를 통해 new-form이라는 논리 이름을 획득
2. ViewResolver 호출
  - 뷰 이름으로 viewResolver를 순서대로 호출
  - InternalResourceViewResolver 호출
3. InternalResourceViewResolver
  - InternalResourceView를 반환
4. InternalResourceView
  - JSP 처럼 포워드 하는 기능이 있다.
5. view.render()
  - foward()를 통해 JSP 실행

## @RequestMapping
> 스프링은 애노테이션을 활용하여 매우 유연하고, 실용적인 컨트롤러를 만들었는대 바로 이것.
애노태이션을 사용하는 컨트롤러
- @Controller 
  - 스프링이 자동으로 스프링 빈으로 등록. 스프링 mvc에서 애노테이션 기반 컨트롤러로 인식
- @RequestMapping 
  - 요청 정보를 매핑.
  - Request
- ModelAndView
  - 모델과 뷰 정보를 담아 반환


