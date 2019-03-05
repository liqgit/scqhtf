package cn.com.eship.utils;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by guoji on 2017/7/21 0021.
 */
public class CommenUtils {

    public static Boolean compareString(String str1,String str2){
        if(str1 !=null && str2 != null){
            String s1 = str1.replaceAll("\\pP|\\pS|\\pZ","").toLowerCase();
            String s2 = str2.replaceAll("\\pP|\\pS|\\pZ","").toLowerCase();
            if (s1.equals(s2)){
                return true;
            }else return false;
        }else return false;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replace("-", "");
        return str;
    }

    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose){
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(isClose){
                try {
                    in.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return byteArray;
    }

    public static ByteArrayInputStream convertToPng(String svgCode) throws Exception{
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            byte[] bytes = svgCode.getBytes("utf-8");
            PNGTranscoder t = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
            TranscoderOutput output = new TranscoderOutput(outputStream);
            t.transcode(input, output);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } finally {
            outputStream.close();
        }
    }

}
