package cn.com.eship.model;

import java.io.Serializable;

/**
 * Created by simon on 16/7/14.
 */
public class Epidemic implements Serializable {
    private String id;
    private String epidemicName;
    private String rowKey;

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpidemicName() {
        return epidemicName;
    }

    public void setEpidemicName(String epidemicName) {
        this.epidemicName = epidemicName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Epidemic epidemic = (Epidemic) o;

        if (id != null ? !id.equals(epidemic.id) : epidemic.id != null) return false;
        if (epidemicName != null ? !epidemicName.equals(epidemic.epidemicName) : epidemic.epidemicName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (epidemicName != null ? epidemicName.hashCode() : 0);
        return result;
    }
}
