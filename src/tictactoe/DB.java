/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import tictactoe.Models.GameUser;

/**
 *
 * @author mauri
 */
public class DB {
    private Connection con;
    /**
     * 
     * @throws ClassNotFoundException when there's an error while trying to 
     * connect to the instance where the DB is hosted.
     * @throws java.sql.SQLException when there's an error while trying to log
     * into de DB.
     */
    public DB() throws ClassNotFoundException, SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/tictactoe", "root", "");
        }
        catch (ClassNotFoundException ex){
            System.out.println("Fatal ERROR: Can't load the driver. " + ex.getMessage());
        }
        catch (SQLException ex){
            System.out.println("There was an error at the login of the DB." + ex.getMessage());
        }
    }
    
    
    public boolean newUser(String username, String password) throws SQLException {
        try{
            String sql = "INSERT INTO gameuser VALUES (NULL, '"
                    + username + "','" + password + "',0)";
            Statement stm = con.createStatement();
            stm.execute(sql);
            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("Error at creating a new user. Username: " + username
            + " is already taken");
        }
        return false;
    }
    public GameUser getUser(String username, String password) {
        ResultSet result;
        try{
            String sql = "SELECT * FROM gameuser WHERE Username = '"+
                username + "' AND Password = '" + password + "'";
            Statement stm = con.createStatement();
            result = stm.executeQuery(sql);
            
            while(result.next())
            {
                updateConnectionUser(result.getInt("IDUser"), true);
                return new GameUser(
                    result.getInt("IDUser"), 
                    result.getString("Username"));
            }
            return null;
        }
        catch (SQLException ex)
        {
            System.out.println("Internal Error. Please try again later");
            return null;
        }
    }
    public void updateConnectionUser(int idUser, boolean connected) {
        int number = connected ? 1 : 0;
        try
        {
            Statement stm = con.createStatement();
            stm.execute("UPDATE gameuser SET CurrentStatus = " + number +
                " WHERE IDUser = " + idUser);
        }
        catch(SQLException e){}
    }
}
