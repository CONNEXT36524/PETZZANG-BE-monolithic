package gcu.connext.petzzang.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Table(name = "user") //(1)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //(2)
    @Column(name = "user_code") //(3)
    private Long userCode;

    @Column(name = "kakao_id")
    private Long kakaoId;

    @Column(name = "kakao_profile_img")
    private String kakaoProfileImg;

    @Column(name = "kakao_nickname")
    private String kakaoNickname;

    @Column(name = "kakao_email")
    private String kakaoEmail;

    @Column(name = "user_role")
    private String userRole;


    public Long getUsercode() {
        return userCode;
    }
    public void setUsercode(Long userCode) {
        this.userCode = userCode;
    }

    public Long getKakaoid() {
        return kakaoId;
    }
    public void setKakaoid(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    public String getKakaoprofileimg() {
        return kakaoProfileImg;
    }
    public void setKakaoprofileimg(String kakaoProfileImg) {
        this.kakaoProfileImg = kakaoProfileImg;
    }

    public String getKakaonickname() {
        return kakaoNickname;
    }
    public void setKakaonickname(String kakaoNickname) {
        this.kakaoNickname = kakaoNickname;
    }

    public String getKakaoemail() {
        return kakaoEmail;
    }
    public void setKakaoemail(String kakaoEmail) {
        this.kakaoEmail = kakaoEmail;
    }

    public String getUserrole() {
        return userRole;
    }
    public void setUserrole(String userRole) {
        this.userRole = userRole;
    }
//    @Column(name = "create_time")
//    @CreationTimestamp //(4)
//    private Timestamp createTime;

    @Builder
    public User(Long kakaoId, String kakaoProfileImg, String kakaoNickname,
                String kakaoEmail, String userRole) {

        this.kakaoId = kakaoId;
        this.kakaoProfileImg = kakaoProfileImg;
        this.kakaoNickname = kakaoNickname;
        this.kakaoEmail = kakaoEmail;
        this.userRole = userRole;
    }


}