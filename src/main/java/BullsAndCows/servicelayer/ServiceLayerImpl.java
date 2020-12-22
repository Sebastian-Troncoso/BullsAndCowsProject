package BullsAndCows.servicelayer;


import BullsAndCows.dao.BullsAndCowsDao;
import BullsAndCows.dao.BullsAndCowsDaoDbImpl;
import BullsAndCows.dto.RandomNumberNoDuplicate;
import BullsAndCows.exception.InputGuessInvalidException;
import BullsAndCows.exception.InputGuessInvalidLength;
import com.mysql.cj.util.StringUtils;

import java.sql.SQLException;

public class ServiceLayerImpl implements ServiceLayer {

    RandomNumberNoDuplicate numberGenerator = new RandomNumberNoDuplicate();
    BullsAndCowsDao dao = new BullsAndCowsDaoDbImpl();
    String hiddenAnswer;

    private void checkValidation(String inputGuess)
            throws InputGuessInvalidException,
            InputGuessInvalidLength {
        if (!StringUtils.isStrictlyNumeric(inputGuess)){
            throw new InputGuessInvalidException("Error: enter numeric value.");
        }
        if(inputGuess.length() != 4){
            throw new InputGuessInvalidLength("Error: enter a four digit number.");
        }
    }

    private int checkExactMatch(String inputGuess ) throws SQLException {
        char [] input = inputGuess.toCharArray();
        char [] ans = hiddenAnswer.toCharArray();
        int count = 0;

        for (int i = 0; i < input.length ; i++) {
            if(input[i] == ans[i]) count++;
        }

        return count;
    }


    private int checkPartialMatch(String inputGuess) {
        char [] input = inputGuess.toCharArray();
        char [] ans = hiddenAnswer.toCharArray();

        int count = 0;

        for (int i = 0; i < ans.length; i++) {
            for(int j = 0; j < input.length; j++){
                if(ans[i] == input[j] && i != j) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public void addGame() throws SQLException {
        // Set hidden answer into memory
        hiddenAnswer = numberGenerator.getRandomNumber();

        // Adding game into memory
        dao.addGame(hiddenAnswer);
    }

    @Override
    public void getGameById(String gameId) throws SQLException {
        dao.getGame(gameId);
    }

    @Override
    public void getAllGames() throws SQLException {
        dao.getAllGames();
    }

    @Override
    public boolean addGuess(String gameId, String inputGuess)
            throws InputGuessInvalidException,
            InputGuessInvalidLength, SQLException {
        checkValidation(inputGuess);

        // Checks for exact match
        int exactMatchCount = checkExactMatch(inputGuess);
        // Checks for partial match
        int partialMatchCount = checkPartialMatch(inputGuess);


        if(exactMatchCount == 4) {
            // Ends game if exactMatchCount is four
            dao.addRound(gameId,inputGuess, partialMatchCount, exactMatchCount);
            // Updates the state of the game.
            dao.updateGameStatus(gameId);
            return true;
        }
        else{
            // Adds a round into the game
            dao.addRound(gameId, inputGuess, partialMatchCount, exactMatchCount);
            return false;
        }


    }

    @Override
    public void getRoundBasedOnGameID(String gameId) throws SQLException {
        dao.getRounds(gameId);
    }

    @Override
    public void setUpDatabase() {
        dao.setUpDatabase();
    }
}
