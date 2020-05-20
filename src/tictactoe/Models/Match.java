/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Models;

import java.util.Date;

/**
 *
 * @author mauri
 */
public class Match {

    public Date Date;
    public String PlayerX;
    public String PlayerO;
    public String Result;

    public Match(Date Date, String PlayerX, String PlayerO, String Result) {
        this.Date = Date;
        this.PlayerX = PlayerX;
        this.PlayerO = PlayerO;
        this.Result = Result;
    }

    @Override
    public String toString() {
        return "Date: " + Date + "\n"
                + "PlayerX: " + PlayerX + "\n"
                + "PlayerO: " + PlayerO + "\n"
                + "Result: " + Result;
    }
}
