package com.example.mailjob.listener;
import com.example.mailjob.entity.User;
import com.example.mailjob.util.MailUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
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
                List<User> userList;
               userList =  getUsers();
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
    public List<User> getUsers() {
        List<User> temp= new ArrayList<>();
        File file =new File("src/main/resources/User.txt");
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            InputStreamReader objIn=new InputStreamReader (in);
            BufferedReader bfr=new BufferedReader(objIn);
            String line;
            while((line = bfr.readLine())!=null)
            {
                String[] split = line.split(",");
                Date date = sdf.parse(split[2]);
                User tempUser = new User(split[0],split[1],date,split[3]);
                temp.add(tempUser);
            }
            objIn.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return  temp.stream().filter(s->!sdf.format(s.getBirthday()).equals(sdf.format(new Date()))).collect(Collectors.toList());
    }

}
