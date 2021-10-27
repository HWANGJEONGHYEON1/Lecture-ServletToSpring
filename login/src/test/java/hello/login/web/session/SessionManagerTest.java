package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        //생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        //요청에 응답 쿠키 저장
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // session 조회
        final Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(member);

        //만료
        sessionManager.expire(request);
        final Object session = sessionManager.getSession(request);
        Assertions.assertThat(session).isNull();
    }
}