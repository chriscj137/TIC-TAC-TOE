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
    public boolean Playing;

    public GameUser(int idUser, String username) {
        this.idUser = idUser;
        this.username = username;
        this.currentStatus = true;
        this.currentStatus = false;
        this.Playing = false;
    }

    public GameUser(int idUser, String username, String password, Boolean cS, Boolean Playing) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.currentStatus = cS;
        this.Playing = Playing;
    }

    public GameUser(String GameUser) {
        String[] arrOfStr = GameUser.split("\n", 5);
        this.idUser = Integer.parseInt(arrOfStr[0]);
        this.username = arrOfStr[1];
        this.password = arrOfStr[2];
        this.currentStatus = Boolean.parseBoolean(arrOfStr[3]);
        this.Playing = Boolean.parseBoolean(arrOfStr[4]);
    }

    @Override
    public String toString() {
        return idUser + "\n"
                + username + "\n"
                + password + "\n"
                + currentStatus + "\n"
                + Playing;
    }

}
