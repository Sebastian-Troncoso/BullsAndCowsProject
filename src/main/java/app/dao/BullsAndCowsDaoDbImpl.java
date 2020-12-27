/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dao;

import app.dto.Game;
import app.dto.Round;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
@Profile("database")
public class BullsAndCowsDaoDbImpl implements BullsAndCowsDao {
    
    private static DataSource ds;



    @Override
    public void setUpDatabase() {
        try {
            ds = getDataSource();
        } catch (SQLException ex) {
            System.out.println("Error connection to database");
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    @Override
    public List<Game> getAllGames() throws SQLException{
        try ( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT GameID, Answer, finished FROM Game");
            List<Game> gameList = new ArrayList<>();
            while(rs.next()){
                gameList.add(new Game(rs.getString("GameID"),
                    rs.getString("Answer"),
                    rs.getBoolean("finished")));
            }
            return gameList;
        }
    }
    
    @Override
    public List<Round> getRounds(String gameId) throws SQLException{
        try( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Round WHERE GameId = " + gameId );
            ArrayList<Round> roundList = new ArrayList<>();
            while(rs.next()){
                roundList.add(new Round(rs.getString("RoundId"),
                    rs.getString("GameId"),
                    rs.getString("Guess"),
                    rs.getInt("PartialMatch"),
                    rs.getInt("ExactMatch")));
            }
        return roundList;
        }
    }
    
    @Override
    public Game getGame(String gameId) throws SQLException{
        try( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Game WHERE GameId = " + gameId );
            return new Game(rs.getString("GameId"),
                rs.getString("Answer"),
                rs.getBoolean("InProgress"));
        }
    }
    
    @Override
    public void addGame(String hiddenAnswer) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            String sql = "INSERT INTO Game(Answer, finished) VALUES(?, ?)";
            PreparedStatement pStmt = conn.prepareCall(sql);
            pStmt.setString(1, hiddenAnswer);
            pStmt.setBoolean(2, false);
            pStmt.executeUpdate();
            //System.out.println("Add Complete");
        }
    }
    
    @Override
    public void addRound(String gameId, String userGuess, int partialMatch, int exactMatch ) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            String sql = "INSERT INTO Round(GameID, guess, partialMatch, ExactMatch) VALUES(?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareCall(sql);
            pStmt.setString(1, gameId);
            pStmt.setString(2, userGuess);
            pStmt.setInt(3, partialMatch);
            pStmt.setInt(4, exactMatch);
            pStmt.executeUpdate();
            System.out.println("Add Complete");
        }
    }
    
    @Override
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

    @Override
    public String getAnswer(String gameId) throws SQLException {
        try( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            System.out.println("Game ID: " + gameId);
            ResultSet rs = stmt.executeQuery("SELECT * FROM Game WHERE GameID = " + gameId );
            rs.next();
            return rs.getString("Answer");
        }
    }
}
