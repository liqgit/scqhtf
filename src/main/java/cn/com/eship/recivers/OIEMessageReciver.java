package cn.com.eship.recivers;

import cn.com.eship.model.OieHtml;
import cn.com.eship.utils.DIUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class OIEMessageReciver implements MessageListener {
    private DIUtils diUtils;
    public OIEMessageReciver(DIUtils diUtils){
        this.diUtils = diUtils;
    }
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage txtMsg = (TextMessage) message;
            String messageJson = txtMsg.getText();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(messageJson, Map.class);
            OieHtml oieHtml = new OieHtml();
            BeanUtils.copyProperties(oieHtml, map);
            diUtils.getOieHtmlService().saveOrUpdateHtml(oieHtml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
