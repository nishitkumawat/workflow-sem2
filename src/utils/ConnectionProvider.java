package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class ConnectionProvider {
    private static Connection con;
    private static String[] creds;
    
    public static Connection getConnection(){
        if(con!=null){
            return con;
        }else{
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\utils\\databaseCredentials.txt"));
                creds = new String[2];
                String line;
                int i=0;
                while((line = bufferedReader.readLine())!=null){
                creds[i++]=line;
            }
            bufferedReader.close();
                con = DriverManager.getConnection("jdbc:mysql:///WorkFlow", creds[0], creds[1]);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return con;
        }
    }
}
