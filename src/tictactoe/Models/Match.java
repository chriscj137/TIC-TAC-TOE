/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Match(String match) throws ParseException {
        String[] arrOfStr = match.split("\n", 4);
        this.Date = new SimpleDateFormat("yyyy-MM-dd").parse(arrOfStr[0]);
        this.PlayerX = arrOfStr[1];
        this.PlayerO = arrOfStr[2];
        this.Result = arrOfStr[3];
    }

    @Override
    public String toString() {
        return Date + "\n"
                + PlayerX + "\n"
                + PlayerO + "\n"
                + Result;
    }
}
