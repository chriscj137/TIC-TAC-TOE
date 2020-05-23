/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import tictactoe.DB;
import tictactoe.Models.GameUser;
import tictactoe.Models.Match;

/**
 *
 * @author chris
 */
public class ClientHandler implements Runnable {

    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private final Server server;
    private final Connection con;
    private GameUser gu;

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public Socket getS() {
        return s;
    }

    public GameUser getGu() {
        return gu;
    }

    /**
     *
     * @param socket the Socket of the client
     * @param dis the DataInputStream of the socket, used to hook an Input
     * Stream to the Socket communication
     * @param dos the DataOutputStream of the socket, used for posting to the
     * other end of the communication
     * @param server
     * @throws java.sql.SQLException
     */
    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos, Server server) throws SQLException {
        this.s = socket;
        this.dis = dis;
        this.dos = dos;
        this.server = server;
        con = DB.getConnection();
    }

    /**
     * Main code for the ClientHandler. Ensure there's a way to end execution
     * (no infinite loops), and properly close the socket before returning
     */
    @Override
    public void run() {
        String received;
        Boolean endCommunication = false;

        while (!endCommunication) {
            try {
                received = dis.readUTF();

                switch (received) {
                    case "record":
                        record();
                        break;
                    case "login":
                        login();
                        break;
                    case "register":
                        dos.writeBoolean(register());
                        break;
                    case "logout":
                        logout();
                        break;
                    case "allGUCon":
                        allGUCon();
                        break;
                    case "duel":
                        GameUser gameUser = new GameUser(dis.readUTF());
                        Boolean accepted = duel(gameUser);
                        break;
                    case "exit":
                        dis.close();
                        dos.close();
                        s.close();
                        logout();
                        con.close();
                        endCommunication = true;
                        break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     *
     * @throws IOException if the communication in the DataInputStream failed
     * @throws SQLException if the communication with the database connection
     * pool failed
     */
    private void login() throws IOException, SQLException {
        if (gu == null) {
            String username = dis.readUTF();
            String password = dis.readUTF();

            GameUser gameUser = DB.getUser(username, password, con);

            if (gameUser != null && gameUser.currentStatus == false) {
                gu = gameUser;
                DB.updateConnectionUser(gu.idUser, true, con);
                dos.writeBoolean(true);
                dos.writeUTF(gameUser.toString());
            } else {
                dos.writeBoolean(false);
            }
        }
    }

    private void logout() {
        if (gu != null) {
            DB.updateConnectionUser(gu.idUser, false, con);
        }
    }

    /**
     *
     * @throws IOException if the communication in the DataInputStream failed
     * @throws SQLException if the communication with the database connection
     * pool failed
     */
    private Boolean register() throws IOException, SQLException {
        String username = dis.readUTF();
        String password = dis.readUTF();

        return DB.newUser(username, password, con);
    }

    private void allGUCon() throws IOException {
        ArrayList<GameUser> allGUConnected = DB.getUsersConnected(con);

        dos.writeInt(allGUConnected.size());

        for (GameUser GU : allGUConnected) {
            dos.writeUTF(GU.toString());
        }
    }
        private void record() throws IOException {
        ArrayList<Match> matches = DB.getRecord(gu.idUser, con);
        
        dos.writeInt(matches.size());
        
        for (Match m : matches) {
            dos.writeUTF(m.toString());
        }
    }

    private Boolean duel(GameUser gameUser) throws IOException {
        GameUser gameUserToDuel = DB.getUser(gameUser.username, gameUser.password, con);
        // System.out.println(gameUser);
        if (gameUserToDuel.Playing) {
            return false;
        } else {
            DB.updatePlayingStatus(gu.idUser, true, con);
            server.challengeClientHandlerFromGameUser(gameUserToDuel, gu);

            return true;
        }
    }
}
