package cn.com.eship.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by simon on 16/7/14.
 */
public class EpidemicAppear implements Serializable {
    private String id;
    private Date appearDate;
    private Integer appearTimes;
    private Epidemic epidemic;
    public Region region;
    private String rowKey;

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Epidemic getEpidemic() {
        return epidemic;
    }

    public void setEpidemic(Epidemic epidemic) {
        this.epidemic = epidemic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Date getAppearDate() {
        return appearDate;
    }

    public void setAppearDate(Date appearDate) {
        this.appearDate = appearDate;
    }

    public Integer getAppearTimes() {
        return appearTimes;
    }

    public void setAppearTimes(Integer appearTimes) {
        this.appearTimes = appearTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EpidemicAppear that = (EpidemicAppear) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (appearDate != null ? !appearDate.equals(that.appearDate) : that.appearDate != null) return false;
        if (appearTimes != null ? !appearTimes.equals(that.appearTimes) : that.appearTimes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (appearDate != null ? appearDate.hashCode() : 0);
        result = 31 * result + (appearTimes != null ? appearTimes.hashCode() : 0);
        return result;
    }
}
