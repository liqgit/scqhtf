package cn.com.eship.dao;

/**
 * Created by liq on 17/9/22.
 */
import java.util.List;

public interface OIETestDao {
    public List<Integer> getOutbreaks()throws Exception;
    public List<String> getDiseases() throws Exception;
}
