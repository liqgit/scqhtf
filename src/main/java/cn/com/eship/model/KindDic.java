package cn.com.eship.model;

/**
 * Created by simon on 16/9/13.
 */
public class KindDic {
    private String id;
    private String kindName;
    private Integer level;


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KindDic kindDic = (KindDic) o;

        if (id != null ? !id.equals(kindDic.id) : kindDic.id != null) return false;
        if (kindName != null ? !kindName.equals(kindDic.kindName) : kindDic.kindName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (kindName != null ? kindName.hashCode() : 0);
        return result;
    }
}
