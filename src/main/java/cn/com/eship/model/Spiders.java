package cn.com.eship.model;

import java.io.Serializable;

/**
 * Created by simon on 2016/10/26.
 */
public class Spiders implements Serializable {
    private String id;
    private String name;
    private String startUrl;
    private String titleXpath;
    private String contentXpath;
    private String pageUrlXpath;
    private String fetchUrlXpath;

    public String getFetchUrlXpath() {
        return fetchUrlXpath;
    }

    public void setFetchUrlXpath(String fetchUrlXpath) {
        this.fetchUrlXpath = fetchUrlXpath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public String getTitleXpath() {
        return titleXpath;
    }

    public void setTitleXpath(String titleXpath) {
        this.titleXpath = titleXpath;
    }

    public String getContentXpath() {
        return contentXpath;
    }

    public void setContentXpath(String contentXpath) {
        this.contentXpath = contentXpath;
    }

    public String getPageUrlXpath() {
        return pageUrlXpath;
    }

    public void setPageUrlXpath(String pageUrlXpath) {
        this.pageUrlXpath = pageUrlXpath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spiders spiders = (Spiders) o;

        if (id != null ? !id.equals(spiders.id) : spiders.id != null) return false;
        if (name != null ? !name.equals(spiders.name) : spiders.name != null) return false;
        if (startUrl != null ? !startUrl.equals(spiders.startUrl) : spiders.startUrl != null) return false;
        if (titleXpath != null ? !titleXpath.equals(spiders.titleXpath) : spiders.titleXpath != null) return false;
        if (contentXpath != null ? !contentXpath.equals(spiders.contentXpath) : spiders.contentXpath != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startUrl != null ? startUrl.hashCode() : 0);
        result = 31 * result + (titleXpath != null ? titleXpath.hashCode() : 0);
        result = 31 * result + (contentXpath != null ? contentXpath.hashCode() : 0);
        return result;
    }
}
