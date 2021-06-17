# MailJob

采用ServletContextListener  ，每天都定时查看该天过生日的员工，并发送邮件祝福，使用timer任务调度。从文件中读取user信息封装到list中对比日期进行过滤。

