package BullsAndCows.servicelayer;

import BullsAndCows.exception.InputGuessInvalidException;
import BullsAndCows.exception.InputGuessInvalidLength;

public interface ServiceLayer {


    void addGame();
    void getGameById(String id);
    void getAllGames();
    boolean addGuess(String inputGuess) throws InputGuessInvalidException, InputGuessInvalidLength;
    void getRoundBasedOnGameID();
}
