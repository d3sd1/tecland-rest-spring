package model;

public class User {
    private long id;
    private String email;
    private String password;
    private String loginJWT;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginJWT() {
        return loginJWT;
    }

    public void setLoginJWT(String loginJWT) {
        this.loginJWT = loginJWT;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", loginJWT='" + loginJWT + '\'' +
                '}';
    }
}
