/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Models;

/**
 *
 * @author mauri
 */
public class GameUser {
    
    public int idUser;
    public String username;
    public String password;
    public boolean currentStatus;

    public GameUser(int idUser, String username) {
        this.idUser = idUser;
        this.username = username;
        this.currentStatus = true;
    }
    
    @Override
    public String toString()
    {
        return "Username: " + username + "\nCurrentStauts: " + currentStatus;
    }
    
}
