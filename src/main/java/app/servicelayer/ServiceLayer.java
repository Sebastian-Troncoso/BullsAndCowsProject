package app.servicelayer;

import app.dto.Game;
import app.exception.InputGuessInvalidException;
import app.exception.InputGuessInvalidLength;

import java.sql.SQLException;
import java.util.List;

public interface ServiceLayer {


    void addGame() throws SQLException;
    void getGameById(String gameId) throws SQLException;
    List<Game> getAllGames() throws SQLException;
    boolean addGuess(String gameId, String inputGuess) throws InputGuessInvalidException, InputGuessInvalidLength, SQLException;
    void getRoundBasedOnGameID(String gameId) throws SQLException;
    void setUpDatabase();
}