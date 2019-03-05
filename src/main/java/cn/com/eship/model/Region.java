package cn.com.eship.model;

/**
 * Created by simon on 16/7/14.
 */
public class Region {
    private String id;
    private String regionCn;
    private String regionEn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionCn() {
        return regionCn;
    }

    public void setRegionCn(String regionCn) {
        this.regionCn = regionCn;
    }

    public String getRegionEn() {
        return regionEn;
    }

    public void setRegionEn(String regionEn) {
        this.regionEn = regionEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Region region = (Region) o;

        if (id != null ? !id.equals(region.id) : region.id != null) return false;
        if (regionCn != null ? !regionCn.equals(region.regionCn) : region.regionCn != null) return false;
        if (regionEn != null ? !regionEn.equals(region.regionEn) : region.regionEn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (regionCn != null ? regionCn.hashCode() : 0);
        result = 31 * result + (regionEn != null ? regionEn.hashCode() : 0);
        return result;
    }
}
