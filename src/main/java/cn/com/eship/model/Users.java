package cn.com.eship.model;

/**
 * Created by simon on 16/7/14.
 */
public class Users {
    private String id;
    private String userId;
    private String passWd;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        if (id != null ? !id.equals(users.id) : users.id != null) return false;
        if (userId != null ? !userId.equals(users.userId) : users.userId != null) return false;
        if (passWd != null ? !passWd.equals(users.passWd) : users.passWd != null) return false;
        if (name != null ? !name.equals(users.name) : users.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (passWd != null ? passWd.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
