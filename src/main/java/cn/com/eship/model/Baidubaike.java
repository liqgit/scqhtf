package cn.com.eship.model;

import java.io.Serializable;

/**
 * Created by simon on 16/7/15.
 */
public class Baidubaike implements Serializable {
    private String id;
    private String pagUrl;
    private String imgUrl;
    private Epidemic epidemic;

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


    public String getPagUrl() {
        return pagUrl;
    }

    public void setPagUrl(String pagUrl) {
        this.pagUrl = pagUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Baidubaike that = (Baidubaike) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (pagUrl != null ? !pagUrl.equals(that.pagUrl) : that.pagUrl != null) return false;
        if (imgUrl != null ? !imgUrl.equals(that.imgUrl) : that.imgUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pagUrl != null ? pagUrl.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        return result;
    }
}
