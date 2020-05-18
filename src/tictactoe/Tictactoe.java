/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.sql.SQLException;

/**
 *
 * @author chris
 */
public class Tictactoe {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DB db = new DB();
        
        /*
            SignUp Process. Referr to DB.java to find declaration.
            1. Insert a new user checking for disponibility. 
                //boolean = db.newUser(username, password)
            2. Get the current User with the ConnectionStatus updated.
                //GameUser = db.getUser(username, password)
            This method is also used when the User wants to logIn.
            There's no need to update the status, it's make it by itself.
            3. If a user wants to LogOut just call this method with false value:
                //db.updateConnectionUser(username, false)
        */
        
        
        
    }

}
