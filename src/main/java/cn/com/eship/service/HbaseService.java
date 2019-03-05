package cn.com.eship.service;

/**
 * Created by simon on 16/8/30.
 */
public interface HbaseService {
    /**
     * 生成疫情百科json
     * @param rowkey
     * @return
     * @throws Exception
     */
    public String makeEpiedmicBaikeJson(String rowkey) throws Exception;

    public String makeEpidemicSourceJson(String rowKey) throws Exception;

}
