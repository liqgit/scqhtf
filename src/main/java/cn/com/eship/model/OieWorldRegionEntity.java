package cn.com.eship.model;

import javax.persistence.*;

/**
 * Created by guoji on 2017/7/5 0005.
 */
@Entity
@Table(name = "oie_world_region", schema = "tjrsms", catalog = "")
public class OieWorldRegionEntity {
    private int id;
    private String regionNameEng;
    private String regionNameCn;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "region_name_eng")
    public String getRegionNameEng() {
        return regionNameEng;
    }

    public void setRegionNameEng(String regionNameEng) {
        this.regionNameEng = regionNameEng;
    }

    @Basic
    @Column(name = "region_name_cn")
    public String getRegionNameCn() {
        return regionNameCn;
    }

    public void setRegionNameCn(String regionNameCn) {
        this.regionNameCn = regionNameCn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OieWorldRegionEntity that = (OieWorldRegionEntity) o;

        if (id != that.id) return false;
        if (regionNameEng != null ? !regionNameEng.equals(that.regionNameEng) : that.regionNameEng != null)
            return false;
        if (regionNameCn != null ? !regionNameCn.equals(that.regionNameCn) : that.regionNameCn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (regionNameEng != null ? regionNameEng.hashCode() : 0);
        result = 31 * result + (regionNameCn != null ? regionNameCn.hashCode() : 0);
        return result;
    }
}
