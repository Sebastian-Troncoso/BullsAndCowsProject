/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BullsAndCows.dao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author johnr
 */
public interface BullsAndCowsDao {
    ResultSet getAllGames() throws SQLException;
    ResultSet getRounds(String gameId) throws SQLException;
    ResultSet getGame(String gameId) throws SQLException;
    void addGame(String answer) throws SQLException;
    void addRound(String gameId, int round, String userGuess, int partialMatch, int ExactMatch ) throws SQLException;
    void updateGameStatus(String gameId) throws SQLException;
    void setUpDatabase();
}
