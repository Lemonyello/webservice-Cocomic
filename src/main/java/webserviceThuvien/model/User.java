package webserviceThuvien.model;

public class User {
    String accountName;
    String password;
    String nickName;
    String id;

    public String getAccountName() {
        return accountName;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getId() {
        return id;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String accountName, String password, String nickName) {
        this.accountName = accountName;
        this.password = password;
        this.nickName = nickName;
    }

    public User() {}
}
