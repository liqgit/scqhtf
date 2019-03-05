package cn.com.eship.model;

import javax.persistence.*;

/**
 * Created by guoji on 2017/7/5 0005.
 */
@Entity
@Table(name = "oie_diseases", schema = "tjrsms", catalog = "")
public class OieDiseasesEntity {
    private int id;
    private String diseaseNameEng;
    private String diseaseNameCn;
    private String diseaseClass;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "disease_name_eng")
    public String getDiseaseNameEng() {
        return diseaseNameEng;
    }

    public void setDiseaseNameEng(String diseaseNameEng) {
        this.diseaseNameEng = diseaseNameEng;
    }

    @Basic
    @Column(name = "disease_name_cn")
    public String getDiseaseNameCn() {
        return diseaseNameCn;
    }

    public void setDiseaseNameCn(String diseaseNameCn) {
        this.diseaseNameCn = diseaseNameCn;
    }

    @Basic
    @Column(name = "disease_class")
    public String getDiseaseClass() {
        return diseaseClass;
    }

    public void setDiseaseClass(String diseaseClass) {
        this.diseaseClass = diseaseClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OieDiseasesEntity that = (OieDiseasesEntity) o;

        if (id != that.id) return false;
        if (diseaseNameEng != null ? !diseaseNameEng.equals(that.diseaseNameEng) : that.diseaseNameEng != null)
            return false;
        if (diseaseNameCn != null ? !diseaseNameCn.equals(that.diseaseNameCn) : that.diseaseNameCn != null)
            return false;
        if (diseaseClass != null ? !diseaseClass.equals(that.diseaseClass) : that.diseaseClass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (diseaseNameEng != null ? diseaseNameEng.hashCode() : 0);
        result = 31 * result + (diseaseNameCn != null ? diseaseNameCn.hashCode() : 0);
        result = 31 * result + (diseaseClass != null ? diseaseClass.hashCode() : 0);
        return result;
    }
}
