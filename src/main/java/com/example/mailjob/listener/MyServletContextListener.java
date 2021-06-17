package com.example.mailjob.listener;
import com.example.mailjob.entity.User;
import com.example.mailjob.util.MailUtils;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


/**
 * @author Bear
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

    final SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
    @Override
    public void contextInitialized(ServletContextEvent arg0)  {
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<User> userList = new ArrayList<>();
                userList.add(new User("123","李四",new Date(),"iyanzixiong@163.com"));
                if (userList!=null ) {
                    for (User user : userList) {
                        String emailMsg="亲爱的"+user.getName()+"<br/>祝你生日快乐";
                        MailUtils.sendMail("生日祝福",emailMsg,user.getEmail());
                    }
                }
            }
        }, new Date(), 1000*60*60*24);
    }
    @Override
    public void contextDestroyed(ServletContextEvent arg0)  {
        // TODO Auto-generated method stub
    }


}
