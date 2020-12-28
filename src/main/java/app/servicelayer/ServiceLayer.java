package app.servicelayer;


import app.dto.Game;
import app.dto.Round;
import app.exception.InputGuessInvalidException;
import app.exception.InputGuessInvalidLength;
import app.exception.InvalidIDException;

import java.sql.SQLException;
import java.util.List;

public interface ServiceLayer {

    void addGame() throws SQLException;

    Game getGameById(String gameId) throws SQLException, InvalidIDException;

    List<Game> getAllGames() throws SQLException;

    List<Round> addGuess(String gameId, String inputGuess)
            throws InputGuessInvalidException, InputGuessInvalidLength, SQLException, InvalidIDException;

    List<Round> getRoundBasedOnGameID(String gameId) throws SQLException, InvalidIDException;

    void setUpDatabase();
}
