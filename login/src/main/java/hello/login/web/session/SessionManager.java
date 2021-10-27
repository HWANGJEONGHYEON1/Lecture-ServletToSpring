package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();
    private static final String SESSION_COOKIE_NAME = "mySessionId";

    /**
     * 세션생성
     *  * sessionId 생성(추정 불가능한 임의의 값)
     *  * 세션 저장소에 sessionId를 보관할 값 저장
     *  * sessionId로 응답 쿠키를 생성해서 클라이언트에 저장
     */
    public void createSession(Object value, HttpServletResponse response) {

        final String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        final Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    public Object getSession(HttpServletRequest request) {
        final Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }

        return sessionStore.get(sessionCookie.getValue());
    }

    public void expire(HttpServletRequest request) {
        final Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
