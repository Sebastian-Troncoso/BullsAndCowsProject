package BullsAndCows.servicelayer;

import BullsAndCows.exception.InputGuessInvalidException;
import BullsAndCows.exception.InputGuessInvalidLength;

import java.sql.SQLException;

public interface ServiceLayer {


    void addGame() throws SQLException;
    void getGameById(String gameId) throws SQLException;
    void getAllGames() throws SQLException;
    boolean addGuess(String gameId, String inputGuess) throws InputGuessInvalidException, InputGuessInvalidLength, SQLException;
    void getRoundBasedOnGameID(String gameId) throws SQLException;
    void setUpDatabase();
}
