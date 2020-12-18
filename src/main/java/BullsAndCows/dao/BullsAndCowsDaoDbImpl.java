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
    private static void displayList() throws SQLException {
        try ( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM todo");
            while (rs.next()) {
                System.out.printf("%s: %s -- %s -- %s\n",
                        rs.getString("id"),
                        rs.getString("todo"),
                        rs.getString("note"),
                        rs.getBoolean("finished"));
            }
            System.out.println("");
        }
    }

    public void addGame(String answer) throws SQLException {

        
        try (Connection conn = ds.getConnection()) {
            String sql = "INSERT INTO Round(GameID, ) VALUES(?)";
            PreparedStatement pStmt = conn.prepareCall(sql);
            pStmt.setString(1, answer);
            pStmt.executeUpdate();
            System.out.println("Add Complete");
        }
    }
    
    

    public void addRound(String GameId, int round, String userGuess, int partialMatch, int ExactMatch ) throws SQLException {

        
        try (Connection conn = ds.getConnection()) {
            String sql = "INSERT INTO Group(Answer) VALUES(?, True)";
            PreparedStatement pStmt = conn.prepareCall(sql);
            pStmt.setString(1, GameId);
            pStmt.executeUpdate();
            System.out.println("Add Complete");
        }
    }

    private static void removeItem() throws SQLException {
        System.out.println("Remove Item");
        System.out.println("Which item would you like to remove?");
        String itemId = sc.nextLine();
        
        try(Connection conn = ds.getConnection()) {
            String sql = "DELETE FROM todo WHERE id = ?";
            PreparedStatement pStmt = conn.prepareCall(sql);
            pStmt.setString(1, itemId);
            pStmt.executeUpdate();
            System.out.println("Remove Complete");
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
