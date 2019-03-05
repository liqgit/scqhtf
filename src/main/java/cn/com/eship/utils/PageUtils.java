package cn.com.eship.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Created by simon on 16/7/17.
 */
public class PageUtils {

    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?</script>";

    public static int getFirstPosition(Integer pageNo) {
        int result = 0;
        if (pageNo == null) {
            return 0;
        } else {
            result = (pageNo) * 10;
        }
        return result < 0?0:result;
    }

    public static String handlerHtml(String html) {
        if(StringUtils.isNotBlank(html)) {
            if(html.contains("<body>")) {
                html = html.substring(html.indexOf("<body>"),html.indexOf("</body>")+6).replace("<body>", "");
            }else if(html.contains("<BODY>")) {
                html = html.substring(html.indexOf("<BODY>"),html.indexOf("</BODY>")).replace("<BODY>", "");
            }
            html=html.replaceAll(regEx_script,"").replaceAll("<a href[^>]*>|</a>|<img[^>]*/>", "");
        }else {
            return "";
        }
        return html;
    }

}
