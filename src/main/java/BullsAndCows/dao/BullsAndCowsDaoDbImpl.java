/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BullsAndCows.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author johnr
 */
public class BullsAndCowsDaoDbImpl {
    
    private static DataSource ds;
    
    public ResultSet getAllGames() throws SQLException {
        try ( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Game");
            /*while (rs.next()) {
                System.out.printf("%s: %s -- %s -- %s\n",
                        rs.getString("id"),
                        rs.getString("todo"),
                        rs.getString("note"),
                        rs.getBoolean("finished"));
            }*/
            return rs;
        }
    }
    
    public ResultSet getRounds(String gameId) throws SQLException{
        try( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Round WHERE GameId = " + gameId );
            return rs;
        }
    }
    
    public ResultSet getGame(String gameId) throws SQLException{
        try( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Game WHERE GameId = " + gameId );
            return rs;
        }
    }

    public void addGame(String answer) throws SQLException {

        try (Connection conn = ds.getConnection()) {
            String sql = "INSERT INTO Game(GameID) VALUES(?)";
            PreparedStatement pStmt = conn.prepareCall(sql);
            pStmt.setString(1, answer);
            pStmt.executeUpdate();
            //System.out.println("Add Complete");
        }
    }

    public void addRound(String gameId, int round, String userGuess, int partialMatch, int ExactMatch ) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            String sql = "INSERT INTO Round(GameID, round, guess, partialMatch, ExactMatch) VALUES(?, ?,?,?,?)";
            PreparedStatement pStmt = conn.prepareCall(sql);
            pStmt.setString(1, gameId);
            pStmt.setInt(2, round);
            pStmt.setString(3, userGuess);
            pStmt.setInt(4, partialMatch);
            pStmt.setInt(5, ExactMatch);
            pStmt.executeUpdate();
            //System.out.println("Add Complete");
        }
    }

    public void updateGameStatus(String gameId) throws SQLException{
        try (Connection conn = ds.getConnection()) {
            String sql = "UPDATE Game SET finished = True WHERE id = ?";
            PreparedStatement pStmt = conn.prepareCall(sql);
            pStmt.setString(1, gameId);
            pStmt.executeUpdate();
            //System.out.println("Update Complete");
        }
    }
    
    private static DataSource getDataSource() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("BullsAndCowsDB");
        ds.setUser("root");
        ds.setPassword("RootRoot");
        ds.setServerTimezone("America/New_York");
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);

        return ds;
    }
}
