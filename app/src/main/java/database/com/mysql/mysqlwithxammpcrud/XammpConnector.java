package database.com.mysql.mysqlwithxammpcrud;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Rasel on 12/31/2019.
 */

public class XammpConnector {


    public Connection CONN() {
        Connection con = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.0.105:3306/Infor", "admin", "admin");

        } catch (SQLException e) {
            Log.e("1",e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.e("2",e.getMessage());
        }

        return con;
    }
}
