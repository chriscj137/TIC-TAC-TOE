/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import tictactoe.Models.GameUser;
import tictactoe.Models.Match;

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
    
    /**
     * new User
     * Method used to insert a new row in the 'GameUser' table.
     * @param username The username input by the player.
     * @param password The password input by the player.
     * @return Returns a boolean if everthing was made in a correct way or not.
     * @throws SQLException It's thrown when a Username has been taken by another player.
     */
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
    
    /**
     * getUser
     * Method to Log into the TicTacToe game. Inside of it, it updates the status
     * of the player, it's changed to true.
     * @param username The username input of the player.
     * @param password The password input of the pleyer
     * @return Returns a GameUser with its IDUser and Username.
     */
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
    
    /**
     * updateConnectionUser
     * Method that is called when a user is connected or disconnected to the game.
     * Its purpose is to update its current status in the DB.
     * @param idUser The IDUser of the player that it's connected or disconnected.
     * @param connected The new status of the player.
     */
    public void updateConnectionUser(int idUser, boolean connected) {
        int number = connected ? 1 : 0;
<<<<<<< Updated upstream
        try
        {
            Statement stm = con.createStatement();
            stm.execute("UPDATE gameuser SET CurrentStatus = " + number +
                " WHERE IDUser = " + idUser);
=======
        try {
            Statement stm = getConnection().createStatement();
            stm.execute("UPDATE gameuser SET CurrentStatus = " + number
                    + " WHERE IDUser = " + idUser);
        } catch (SQLException e) {
            System.out.println("LOL");
>>>>>>> Stashed changes
        }
        catch(SQLException e){}
    }
    
    /**
     * updatePlayingStatus
     * Method that is caled when a user enters to a new game or leaves it.
     * @param idUser The user that is playing or stops playing.
     * @param playing The new playing status:
     * true - Available to play another game.
     * false - Not available to play because is busy.
     */
    public void updatePlayingStatus(int idUser, boolean playing) {
        int number = playing ? 1 : 0;
        try
        {
            Statement stm = con.createStatement();
            stm.execute("UPDATE gameuser SET Playing = " + number +
                " WHERE IDUser = " + idUser);
        }
        catch(SQLException e){}
    }
    
    /**
     * getUsersConnected
     * Method to retrieve all the users that are connected and waiting for a match.
     * @return The players with currentStatus = 1.
     */
    public ArrayList<GameUser> getUsersConnected() {
        ResultSet result;
        ArrayList<GameUser> players = new ArrayList<>();
        try
        {
            Statement stm = con.createStatement();
            result = stm.executeQuery("SELECT * FROM gameuser WHERE CurrentStatus = 1"
                    + " AND Playing = 0");
            while (result.next())
            {
                players.add(new GameUser(
                    result.getInt("IDUser"),
                    result.getString("Username")
                ));
            }
            return players;
        }
        catch (SQLException ex)
        {
            return null;
        }
    }
    
    /**
     * setMatchResult
     * Method to insert into the 'GameMatch' table the result of the match played
     * by 2 different users. To avoid processing more data, the DB is the responsable
     * of submitting the date.
     * @param idUserX The IDUser of the player playing with X.
     * @param idUserO The IDUser of the player playing with O.
     * @param result The result of the match. 
     * 1 - PlayerX Wins.
     * 2 - PlayerO Wins.
     * 3 - Tie.
     */
    public void setMatchResult(int idUserX, int idUserO, int result) {
        try{
            String sql = "INSERT INTO gamematch VALUES (NULL, NOW(),"
                    + idUserX + "," + idUserO + "," + result + ")";
            Statement stm = con.createStatement();
            stm.execute(sql);
        }
        catch(SQLException e){}
    }
    
    /**
     * getRecord
     * Method used to get the record of all of their matches with other players.
     * @param idUser The ID of the player that wants to know its record.
     * @return Returns a list with all of their matches played ordered by date.
     */
    public ArrayList<Match> getRecord(int idUser) {
        ResultSet result;
        ArrayList<Match> record = new ArrayList<>();
<<<<<<< Updated upstream
        try
        {
            String sql = "SELECT GM.Day AS Day, GU.Username AS PlayerX, "
=======
        try {
            String sql = "SELECT GU.Username AS PlayerX, "
>>>>>>> Stashed changes
                    + "GAU.Username AS PlayerO, R.Result AS Result FROM gamematch AS GM "
                    + "JOIN gameuser AS GU ON GM.IDUserX = GU.IDUser "
                    + "JOIN gameuser AS GAU ON GM.IDUserO = GAU.IDUser "
                    + "JOIN results AS R ON R.IDGameResult = GM.IDResult "
                    + "WHERE GU.IDUser = " + idUser + " OR GAU.IDUser = " + idUser
                    + " ORDER BY Day";
            
            Statement stm = con.createStatement();
            result = stm.executeQuery(sql);
            while (result.next())
            {
                record.add(new Match(
<<<<<<< Updated upstream
                    result.getDate("Day"),
                    result.getString("PlayerX"),
                    result.getString("PlayerO"),
                    result.getString("Result")
=======
                        result.getString("PlayerX"),
                        result.getString("PlayerO"),
                        result.getString("Result")
>>>>>>> Stashed changes
                ));
            }
            return record;
        }
        catch (SQLException ex)
        {
            return null;
        }
    }
    
    /**
     * shutDownPlayer
     * Method used if the server goes down, so the next time player can logIn 
     * without a problem.
     */
    public void shutDownPlayers()
    {
        try{
            Statement stm = con.createStatement();
            stm.execute("UPDATE gameuser SET Playing = 0, CurrentStatus = 0");
        }
        catch(SQLException e){}
    }
    
    
}
