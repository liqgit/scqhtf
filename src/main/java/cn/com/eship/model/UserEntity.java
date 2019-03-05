package cn.com.eship.model;

import javax.persistence.*;

@Entity
@Table(name = "t_user", schema = "tjrsms", catalog = "")
public class UserEntity {
    private String id;
    private String userName;
    private String passwd;
    private int authorityId;
    private String email;
    private String department;

    @Id
    @Column(name = "id", nullable = false, length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "passwd", nullable = false, length = 255)
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Basic
    @Column(name = "authority_id", nullable = false)
    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "department", nullable = true, length = 255)
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (authorityId != that.authorityId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (passwd != null ? !passwd.equals(that.passwd) : that.passwd != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        result = 31 * result + authorityId;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }
}
