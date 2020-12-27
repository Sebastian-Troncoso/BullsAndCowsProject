package BullsAndCows.servicelayer;

import BullsAndCows.dto.Game;
import BullsAndCows.dto.Round;
import BullsAndCows.exception.InputGuessInvalidException;
import BullsAndCows.exception.InputGuessInvalidLength;
import java.sql.SQLException;
import java.util.List;

public interface ServiceLayer {


    void addGame() throws SQLException;
    Game getGameById(String gameId) throws SQLException;
    List<Game> getAllGames() throws SQLException;
    boolean addGuess(String gameId, String inputGuess) throws InputGuessInvalidException, InputGuessInvalidLength, SQLException;
    List<Round> getRoundBasedOnGameID(String gameId) throws SQLException;
    void setUpDatabase();
}
