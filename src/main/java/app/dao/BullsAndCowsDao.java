/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dao;

import app.dto.Game;
import app.dto.Round;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author johnr
 */
public interface BullsAndCowsDao {
    List<Game> getAllGames() throws SQLException;
    List<Round> getRounds(String gameId) throws SQLException;
    Game getGame(String gameId) throws SQLException;
    String getAnswer (String gameId) throws SQLException;
    void addGame(String answer) throws SQLException;
    void addRound(String gameId, String userGuess, int partialMatch, int ExactMatch ) throws SQLException;
    void updateGameStatus(String gameId) throws SQLException;
    void setUpDatabase();
}
