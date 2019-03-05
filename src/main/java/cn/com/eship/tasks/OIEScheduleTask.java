package cn.com.eship.tasks;

import cn.com.eship.dao.OIEHtmlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OIEScheduleTask {
    @Autowired
    private OIEHtmlDao oieHtmlDao;

    @Scheduled(cron = "0 */1 * * * ?")
    public void importOieEventData() {
        System.out.println("aaaa");
        oieHtmlDao.insertOieData();
        System.out.println("bbbb");
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void updateCountryData() {
        try {
            System.out.println("开始执行11");
            oieHtmlDao.updateCountry();
            System.out.println("执行完毕111");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
