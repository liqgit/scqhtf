package cn.com.eship.model;

import javax.persistence.*;

/**
 * Created by guoji on 2017/7/24 0024.
 */
@Entity
@Table(name = "t_authority", schema = "tjrsms", catalog = "")
public class AuthorityEntity {
    private int id;
    private String name;
    private String menuId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "menu_id")
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorityEntity that = (AuthorityEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        return result;
    }
}
