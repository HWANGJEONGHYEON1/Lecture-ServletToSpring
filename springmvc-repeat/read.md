# Spring mvc


## 로그 (Slf4j)
        logger.trace("trace log = {}", name);
        logger.debug("debug log = {}", name);
        logger.info("info log = {}", name); => defualt 
        logger.warn("warn log = {}", name);
        logger.error("error log = {}", name);
        
- 각 로그 레벨
- slf4j 인터페이스로 각 로그들을 구현체만 구현해주면 다 사용가능
> logging.level.hello.springmvc=debug
- 각 서버마다 (prod, stg, dev, local) 별 로그 레벨 설정하여 디버깅
- @Slf4j
- 장점
    - 쓰레드정보, 클래스 부가 정보를 같이 볼 수 있다.
    - 로그 레벨에 따라 적절할 로깅이 가능
    - 네트워크 파일등 별도의 로그를 남길 수 잇다.
    - 성능도 sysout보다 좋다.

## ModelAttribute

- 스프링 MVC는 @ModelAttribute가 있으면 실행한다
  1. HelloData 객체를 생성
  2. 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다.
  3. 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 바인딩

- requestParam, modelAttribute 둘 다 생략가능
  - String, int, Integer 같은 타입 => RequestParam
  - 나머지는 @ModelAttribute(argument resolver로 지정해둔 타입 외)

## Http 요청 메시지 - 단순 텍스트
    http api에서 주로 사용, json, xml, text
    데이터 형식은 주로 JSON
    POST, PUT, PATCH

- 요청 파라미터와는 다르게, HTTP 메시지 바디를 통해 데이터가 직접 넘어오는 경우는, @RequestParam, @ModelAttribute를 사용할 없다.
- `HttpEntity`
  - 메시지 바디 정보를 직접 조회
  - 요청 파라미터를 조회하는 기능과 관계없음(@RequestParam, @ModelAttribute)
  - 응답에도 사용 가능
    - 메시지 바디 정보 직접 반환
    - 헤더 정보 포함 가능
    - view 조회는 아님
- `@RequestBody`
  - Http 메시지 바디를 편하게 조회 가능
  - 참고로 헤더 정보가 필요하다면 @RequestHeader를 사용하면 된다.
  - 메시지 바디를 직접 조회하는 기능은 @RequestParam, @ModelAttribute와는 상관없다.

- 요청 파라미터 vs Http 메시지 바디
  - 요청파라미터를 조회하는 기능 : @RequestParam, @ModelAttribute
  - Http 메시지 바디를 직접 조회하는 기능 : @RequestBody

- `@ResponseBody`
  - 응답 결과를 Http 메시지 바디에 직접 담아서 전달할 수 있다. view는 사용하지 않는다.

## Http 요청 메시지 - JSON
    HttpEntity, @RequestBody를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 문자나 객체등으로 변환
    HTTP 메시지 컨버터는 문자 뿐만아니라 JSON 객체도 변환해준다.(@RequestBody는 생략 불가능)

## Http 응답 - 정적 리소스
    정적 리소스 : 웹 브라우저에 대한 html, css, js
    뷰 템플릿 : 동적인 html
    http 메시지 사용 : http api를 제공하는 경우, html이 아니라 데이터를 전달해야하므로, json 같은 형식으로 데이터를 실어 보낸다.

## Http 메시지 컨버터
> 응답의 경우 클라이언트의 HTTP Accept 헤더와 서버의 컨트롤러 반환 타입 정보들을 조합해서 HttpMessage가 선택
 

- @ResponseBody 사용
  - Http Body에 문자 내용을 직접 반환
  - viewResolver 대신에 HttpMessageConverter 동작
  - 기본 문자처리: SpringHttpMessageConverter
  - 기본 객체처리: MappingJackson2HttpMessageConverter

- HTTP 메시지 컨터버터는 요청, 응답 둘 다 사용
- 주요 메시지 컨버터
  - ByteArrayHttpMessageConverter : byte[] 데이터를 처리
    - 클래스 타입: byte[], 미디어타입 : */*
    - 요청) @RequestBody byte[] data
    - 응답) @ResponseBody return byte[] 미디어타입 : application/octet-stream  
    <br />
  - StringHttpMessageConverter : 문자 데이터를 처리
    - 클래스 타입: byte[], 미디어타입 : */*
    - 요청) @RequestBody String data
    - 응답) @ResponseBody return "ok" 미디어타입 : text/plain
    <br />
  - MappingJacksonHttpMessageConverter : application/json
    - 클래스 타입 : 객체, HashMap, 미디어타입 : application/json
    - 요청) @RequestBody HelloData data
    - 응답) @ResponseBody return data 미디어타입 : application/json
    <br />

- HTTP 요청 데이터 읽기
  1. 요청
  2. 컨트롤러에서 @RequestBody, HttpEntity 파라미터를 사용
  3. 메시지 컨버터가 메시지를 읽을 수 있는지 canRead() 호출 (RequestBody의 대상 클래스)
  4. 대상 클래스 타입을 지원하는가
  5. 요청의 Content-type 미디어 타입을 지원하는가
  6. canRead() 조건을 만족하면 객체를 생성 반환

- HTTP 응답 데이터 생성
  1. 컨트롤러에서 @ResponseBody, HttpEntity로 값이 반환
  2. 메시지 컨버터가 메시지를 쓸 수 있게 canWrite() 호출
  3. 대상 클래스 타입을 지원하는가
  4. 요청의 Accept 미디어 타입을 지원하는가(text/plain, application/json)   
  5. canWrite()를 만족하면 write() 호출하여 응답 메시지 바디에 데이터를 생성 

## ArgumentResolver
- 어노테이션 기반의 컨트롤러는 다양한 파라미터를 지원(@RequestParam, Model, HttpSerlvet..)
- 유연한 파라미터를 처리할 수 있는것이 `ArgumentResolver`
- 동작방식
  - supportParameter()를 호출해서 해당 파라미터를 지원하는지 체크
  - 지원하면 resolveArgument()를 호출해서 실제 객체를 생성 후 컨트롤러 호출 시 넘어간다.