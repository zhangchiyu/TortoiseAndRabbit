package com.example.doldrum.tortoiseandrabbit.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class UserData {

    /**
     * sessionToken : 13be6188aa494d4aa3175e274a91160e
     * userid : 5555
     * userbh : 55801
     * userName :
     * recommandUserbh : null
     * recommandCode : 17805
     * token : null
     * avatar : 122.14.213.233:1002/templates/main/images/user-avatar.png
     */

    @Id
    private Long id;
    private String sessionToken;
    private int userid;
    private String userbh;
    private String userName;
    private String recommandUserbh;
    private String recommandCode;
    private String token;
    private String avatar;
    @Generated(hash = 1326208781)
    public UserData(Long id, String sessionToken, int userid, String userbh,
            String userName, String recommandUserbh, String recommandCode,
            String token, String avatar) {
        this.id = id;
        this.sessionToken = sessionToken;
        this.userid = userid;
        this.userbh = userbh;
        this.userName = userName;
        this.recommandUserbh = recommandUserbh;
        this.recommandCode = recommandCode;
        this.token = token;
        this.avatar = avatar;
    }
    @Generated(hash = 1838565001)
    public UserData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSessionToken() {
        return this.sessionToken;
    }
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    public int getUserid() {
        return this.userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getUserbh() {
        return this.userbh;
    }
    public void setUserbh(String userbh) {
        this.userbh = userbh;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getRecommandUserbh() {
        return this.recommandUserbh;
    }
    public void setRecommandUserbh(String recommandUserbh) {
        this.recommandUserbh = recommandUserbh;
    }
    public String getRecommandCode() {
        return this.recommandCode;
    }
    public void setRecommandCode(String recommandCode) {
        this.recommandCode = recommandCode;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
