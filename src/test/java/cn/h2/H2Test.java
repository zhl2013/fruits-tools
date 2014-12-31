/*  
 * @(#) H2Test.java Create on 2014-9-24 上午10:00:32   
 *   
 * Copyright 2014 by yhx.   
 */


package cn.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

/**
 * @H2Test.java
 * @created at 2014-9-24 上午10:00:32 by zhanghl
 *
 * @desc
 *
 * @author zhanghl({@link 253587517@qq.com})
 * @version $Revision$
 * @update: $Date$
 */
public class H2Test {
	private Server server;   
    private String port = "9094";   
    private String dbDir = "./h2db/sample";   
    private String user = "zhoujiang";   
    private String password = "123456";   
  
    public void startServer() {   
        try {   
            System.out.println("正在启动h2...");   
            server = Server.createTcpServer(   
                    new String[] { "-tcpPort", port }).start();   
        } catch (SQLException e) {   
            System.out.println("启动h2出错：" + e.toString());   
            // TODO Auto-generated catch block   
            e.printStackTrace();   
            throw new RuntimeException(e);   
        }   
    }   
  
    public void stopServer() {   
        if (server != null) {   
            System.out.println("正在关闭h2...");   
            server.stop();   
            System.out.println("关闭成功.");   
        }   
    }   
  
    public void useH2() {   
        try {   
            Class.forName("org.h2.Driver");   
            Connection conn = DriverManager.getConnection("jdbc:h2:" + dbDir,   
                    user, password);   
            Statement stat = conn.createStatement();   
            // insert data   
            stat.execute("CREATE TABLE TEST(NAME VARCHAR)");   
            stat.execute("INSERT INTO TEST VALUES('Hello World')");   
  
            // use data   
            ResultSet result = stat.executeQuery("select name from test ");   
            int i = 1;   
            while (result.next()) {   
                System.out.println(i++ + ":" + result.getString("name"));   
            }   
            result.close();   
            stat.close();   
            conn.close();   
        } catch (Exception e) {   
            // TODO Auto-generated catch block   
            e.printStackTrace();   
        }   
    }   
  
    public static void main(String[] args) {   
    	H2Test h2 = new H2Test();   
        h2.startServer();   
        h2.useH2();   
        h2.stopServer();   
        System.out.println("==END==");   
    }   
}
