package cn.com.eship.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by guoji on 2017/7/24 0024.
 */
public class CheckCodeUtils {
    public void getCheckCodeImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedImage image = new BufferedImage(100,30, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(new Color(255,255,255));
        g.fillRect(0, 0, 100, 30);
        Random r = new Random();
        g.setColor(new Color(r.nextInt(255), r.nextInt(255),r.nextInt(255)));
        g.setFont(new Font(null,Font.ITALIC,25));
        String number = getNumber(5);
        HttpSession session = request.getSession();
        session.setAttribute("checkCode", number);
        g.drawString(number, 2, 25);
        for(int i = 0; i < 8; i ++){
            g.setColor(new Color(r.nextInt(255), r.nextInt(255),r.nextInt(255)));
            g.drawLine(r.nextInt(80), r.nextInt(30), r.nextInt(80), r.nextInt(30));
        }
        response.setContentType("image/jpeg");
        OutputStream ops = response.getOutputStream();
        javax.imageio.ImageIO.write(image, "jpeg", ops);
        ops.close();
    }


    private String getNumber(int size) {
        String number = "";
        Random r = new Random();
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ" + "23456789";
        for(int i = 0; i < size; i ++){
            number += chars.charAt(r.nextInt(chars.length()));
        }
        return number;
    }
}
