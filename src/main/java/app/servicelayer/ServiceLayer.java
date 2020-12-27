package app.servicelayer;


import app.dto.Game;
import app.dto.Round;
import app.exception.InputGuessInvalidException;
import app.exception.InputGuessInvalidLength;

import java.sql.SQLException;
import java.util.List;

public interface ServiceLayer {


    void addGame() throws SQLException;
    Game getGameById(String gameId) throws SQLException;
    List<Game> getAllGames() throws SQLException;
    List<Round> addGuess(String gameId, String inputGuess) throws InputGuessInvalidException, InputGuessInvalidLength, SQLException;
    List<Round> getRoundBasedOnGameID(String gameId) throws SQLException;
    void setUpDatabase();
}
