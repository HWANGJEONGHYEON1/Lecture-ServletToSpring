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