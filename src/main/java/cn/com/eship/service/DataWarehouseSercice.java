package cn.com.eship.service;


/**
 * Created by simon on 16/9/13.
 */
public interface DataWarehouseSercice {
    public String makeDataWareHouseListJson(String word) throws Exception;

    public String makeDataWareHouseWordListJson(String rowKey) throws Exception;

    public String makeDataWareHouseDetailJson(String rowKey) throws Exception;

}
