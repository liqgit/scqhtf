package cn.com.eship.model;

import java.sql.Date;
import java.util.Objects;

public class OieHtml {
    private String reportId;
    private String reportLink;
    private String country;
    private Date date;
    private String disease;
    private String reasonForNotification;
    private String diseaseManifestation;
    private Integer outbreaks;
    private String dateResolved;
    private String html;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportLink() {
        return reportLink;
    }

    public void setReportLink(String reportLink) {
        this.reportLink = reportLink;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getReasonForNotification() {
        return reasonForNotification;
    }

    public void setReasonForNotification(String reasonForNotification) {
        this.reasonForNotification = reasonForNotification;
    }

    public String getDiseaseManifestation() {
        return diseaseManifestation;
    }

    public void setDiseaseManifestation(String diseaseManifestation) {
        this.diseaseManifestation = diseaseManifestation;
    }

    public Integer getOutbreaks() {
        return outbreaks;
    }

    public void setOutbreaks(Integer outbreaks) {
        this.outbreaks = outbreaks;
    }

    public String getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(String dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OieHtml oieHtml = (OieHtml) o;
        return Objects.equals(reportId, oieHtml.reportId) &&
                Objects.equals(reportLink, oieHtml.reportLink) &&
                Objects.equals(country, oieHtml.country) &&
                Objects.equals(date, oieHtml.date) &&
                Objects.equals(disease, oieHtml.disease) &&
                Objects.equals(reasonForNotification, oieHtml.reasonForNotification) &&
                Objects.equals(outbreaks, oieHtml.outbreaks) &&
                Objects.equals(dateResolved, oieHtml.dateResolved) &&
                Objects.equals(html, oieHtml.html);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, reportLink, country, date, disease, reasonForNotification, outbreaks, dateResolved, html);
    }

    @Override
    public String toString() {
        return "OieHtml{" +
                "reportId='" + reportId + '\'' +
                ", reportLink='" + reportLink + '\'' +
                ", country='" + country + '\'' +
                ", date=" + date +
                ", disease='" + disease + '\'' +
                ", reasonForNotification='" + reasonForNotification + '\'' +
                ", outbreaks=" + outbreaks +
                ", dateResolved='" + dateResolved + '\'' +
                ", html='" + html + '\'' +
                '}';
    }
}
