package cn.com.eship.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by guoji on 2017/7/5 0005.
 */
@Entity
@Table(name = "oie_epidemiological_event", schema = "tjrsms", catalog = "")
@IdClass(OieEpidemiologicalEventEntityPK.class)
public class OieEpidemiologicalEventEntity {
    private int id;
    private String report;
    private String country;
    private String countryId;
    private OieWorldRegionEntity oieWorldRegionEntity;
    private Date date;
    private String disease;
    private String diseaseId;
    private String diseaseIdFK;
    private OieDiseasesEntity oieDiseasesEntity;
    private String reason;
    private String manifestation;
    private Integer outbreaks;
    private String dateRes;
    private String startDate;
    private String endDate;

    public String getDiseaseIdFK() {
        return diseaseIdFK;
    }

    public void setDiseaseIdFK(String diseaseIdFK) {
        this.diseaseIdFK = diseaseIdFK;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    public OieWorldRegionEntity getOieWorldRegionEntity() {
        return oieWorldRegionEntity;
    }

    public void setOieWorldRegionEntity(OieWorldRegionEntity oieWorldRegionEntity) {
        this.oieWorldRegionEntity = oieWorldRegionEntity;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "disease_id")
    public OieDiseasesEntity getOieDiseasesEntity() {
        return oieDiseasesEntity;
    }

    public void setOieDiseasesEntity(OieDiseasesEntity oieDiseasesEntity) {
        this.oieDiseasesEntity = oieDiseasesEntity;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "report")
    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Transient
    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "disease")
    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Transient
    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Basic
    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "manifestation")
    public String getManifestation() {
        return manifestation;
    }

    public void setManifestation(String manifestation) {
        this.manifestation = manifestation;
    }

    @Basic
    @Column(name = "outbreaks")
    public Integer getOutbreaks() {
        return outbreaks;
    }

    public void setOutbreaks(Integer outbreaks) {
        this.outbreaks = outbreaks;
    }

    @Basic
    @Column(name = "date_res")
    public String getDateRes() {
        return dateRes;
    }

    public void setDateRes(String dateRes) {
        this.dateRes = dateRes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OieEpidemiologicalEventEntity that = (OieEpidemiologicalEventEntity) o;

        if (id != that.id) return false;
        if (report != null ? !report.equals(that.report) : that.report != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (countryId != null ? !countryId.equals(that.countryId) : that.countryId != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (disease != null ? !disease.equals(that.disease) : that.disease != null) return false;
        if (diseaseId != null ? !diseaseId.equals(that.diseaseId) : that.diseaseId != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        if (manifestation != null ? !manifestation.equals(that.manifestation) : that.manifestation != null)
            return false;
        if (outbreaks != null ? !outbreaks.equals(that.outbreaks) : that.outbreaks != null) return false;
        if (dateRes != null ? !dateRes.equals(that.dateRes) : that.dateRes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (report != null ? report.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (countryId != null ? countryId.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (disease != null ? disease.hashCode() : 0);
        result = 31 * result + (diseaseId != null ? diseaseId.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (manifestation != null ? manifestation.hashCode() : 0);
        result = 31 * result + (outbreaks != null ? outbreaks.hashCode() : 0);
        result = 31 * result + (dateRes != null ? dateRes.hashCode() : 0);
        return result;
    }
}
