/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Minh Thanh
 */
public class DBContext {

    public Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = ("jdbc:sqlserver://"
                + "localhost" + ":"
                + "1433" + ";databaseName="
                + "J2SP0021" + ";integratedSecurity=true;");

        return DriverManager.getConnection(url);
    }

}
