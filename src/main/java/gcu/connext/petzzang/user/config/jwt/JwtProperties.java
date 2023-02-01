package gcu.connext.petzzang.user.config.jwt;

public interface JwtProperties {
    String SECRET = "connext"; //(2) jwt의  signature를 해싱할 때 사용되는 비밀키
    int EXPIRATION_TIME =  864000000; //(3) 토큰의 만료기간 초단위로 계산됨 10일 설정
    String TOKEN_PREFIX = "Bearer "; //(4) 토큰 앞에 붙는 정해진 형식 꼭 bearer 뒤에 한 칸 공백을 넣어줘야함
    String HEADER_STRING = "Authorization"; //(5) 헤더의 authorization이라는 항목에 토큰 넣어줌
}
