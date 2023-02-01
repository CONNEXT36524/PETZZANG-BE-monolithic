package gcu.connext.petzzang.petzzang.config.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Exceptionhandling() 클래스
// jwtRequestFilter()의 인증과정에서 에러가 발생했을 때 호출되는 클래스
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        //(1)jwtRequestFilter에서 exception발생시 request에 추가한 요소를 불러와 담아줌
        String exception = (String) request.getAttribute(JwtProperties.HEADER_STRING);
        String errorCode;

        if(exception.equals("토큰이 만료되었습니다.")) {
            errorCode = "토큰이 만료되었습니다.";
            setResponse (response, errorCode);
        }

        if(exception.equals("유효하지 않은 토큰입니다.")) {
            errorCode = "유효하지 않은 토큰입니다.";
            setResponse(response, errorCode);
        }
    }

    //(2)
    //status, contentType, errMsg를 담아 응답해주는 메소드
    private void setResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JwtProperties.HEADER_STRING + " : " + errorCode);
    }
}
