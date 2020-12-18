package BullsAndCows.servicelayer;

import BullsAndCows.dao.BullsAndCowsDao;
import BullsAndCows.dao.BullsAndCowsDaoDbImpl;
import BullsAndCows.dto.RandomNumberNoDuplicate;
import BullsAndCows.exception.InputGuessInvalidException;
import BullsAndCows.exception.InputGuessInvalidLength;
import com.mysql.cj.util.StringUtils;

import java.util.Random;

public class ServiceLayerImpl implements ServiceLayer {
    BullsAndCowsDao dao = new BullsAndCowsDaoDbImpl();
    RandomNumberNoDuplicate r = new RandomNumberNoDuplicate();

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

    private boolean checkExactMatch(String inputGuess, String answer) {
        char [] input = inputGuess.toCharArray();
        char [] ans = answer.toCharArray();
        int count = 0;

        for (int i = 0; i < input.length ; i++) {
            if(input[i] == ans[i]) count++;
        }

        if(count == 4) {

            // Update the inputAnswer
            // update the status of the game

            return true;
        }
        else {
            // Update the inputAnswer in DAOdb
            // Update the status of the game


            return false;
        }




    }


    private void checkPartialMatch(String inputGuess, String answer) {
        char [] input = inputGuess.toCharArray();
        char [] ans = answer.toCharArray();


    }

    @Override
    public void addGame() {
        String value = r.getRandomNumber();
        // Calling addGame from the database.

    }

    @Override
    public void getGameById(String id) {

    }

    @Override
    public void getAllGames() {

    }

    @Override
    public boolean addGuess(String inputGuess)
            throws InputGuessInvalidException,
            InputGuessInvalidLength {
        checkValidation(inputGuess);
        // Get database answer and compare it to input guess.
        String answer = null;  // Need to call getAnswer from DAOdb
        checkPartialMatch(inputGuess, answer);

        // pass answer and compare it to input guess.
        boolean isExactMatch = checkExactMatch(inputGuess, answer);

        if(isExactMatch) return true;
        else return false;


    }

    @Override
    public void getRoundBasedOnGameID() {

    }
}
