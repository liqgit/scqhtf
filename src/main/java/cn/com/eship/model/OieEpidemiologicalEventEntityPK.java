package cn.com.eship.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by guoji on 2017/7/5 0005.
 */
public class OieEpidemiologicalEventEntityPK implements Serializable {
    private int id;
    private String report;

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "report")
    @Id
    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OieEpidemiologicalEventEntityPK that = (OieEpidemiologicalEventEntityPK) o;

        if (id != that.id) return false;
        if (report != null ? !report.equals(that.report) : that.report != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (report != null ? report.hashCode() : 0);
        return result;
    }
}
