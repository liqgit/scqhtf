package cn.com.eship.utils.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomServletRequest extends HttpServletRequestWrapper {

    CustomServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        System.out.println(name);
        return XssStrReplaceUtils.stripXss(super.getParameter(XssStrReplaceUtils.stripXss(name)));

    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(XssStrReplaceUtils.stripXss(name));
        if(values != null){
            for (int i = 0; i < values.length; i++) {
                values[i] = XssStrReplaceUtils.stripXss(values[i]);
                System.out.println(values[i]);
            }
        }
        return values;
    }
}
