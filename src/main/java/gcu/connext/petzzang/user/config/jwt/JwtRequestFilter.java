package gcu.connext.petzzang.user.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//유효성 체크용 필터
//한번의 요청마다 한번씩 실행되는 필터
//프론트 측에서 요청 헤더에 토큰을 넣어 보내면 여기서 검증
@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    @Override //(3)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //(4) 요청 헤더의 authorization 항목 값을 가져와 jwtHeader 변수에 담음
        String jwtHeader = ((HttpServletRequest)request).getHeader(JwtProperties.HEADER_STRING);

        //(5) 만약 jwtHeader가 null이거나 bearer로 시작하지 않으면 return으로 이후 로직 실행시키지 않고 넘김
        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        //(6) jwtHeader가 제대로 된 형식이라면 토큰 앞의 Bearer를 떼어내 token 변수에 담음
        String token = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");

        Long userCode = null;

        //(7) token을 비밀키로 복호화, 개인 클레임에 넣어놨던 id값을 가져옴 == 인증과정임
        try {
            userCode = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                    .getClaim("id").asLong();

        } catch (TokenExpiredException e) {
            e.printStackTrace();
            request.setAttribute(JwtProperties.HEADER_STRING, "토큰이 만료되었습니다.");
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            request.setAttribute(JwtProperties.HEADER_STRING, "유효하지 않은 토큰입니다.");
        }

        //(8) usercode에 값이 잘 담겼다면  값 넘김
        request.setAttribute("userCode", userCode);

        //(9)
        filterChain.doFilter(request, response);
    }
}