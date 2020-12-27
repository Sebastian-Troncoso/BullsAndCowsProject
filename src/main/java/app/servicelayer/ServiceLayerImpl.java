package BullsAndCows.servicelayer;


import BullsAndCows.dao.BullsAndCowsDao;
import BullsAndCows.dao.BullsAndCowsDaoDbImpl;
import BullsAndCows.dto.Game;
import BullsAndCows.dto.RandomNumberNoDuplicate;
import BullsAndCows.dto.Round;
import BullsAndCows.exception.InputGuessInvalidException;
import BullsAndCows.exception.InputGuessInvalidLength;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class ServiceLayerImpl implements ServiceLayer {

    RandomNumberNoDuplicate numberGenerator = new RandomNumberNoDuplicate();
    BullsAndCowsDao dao;
    String hiddenAnswer;

    @Autowired
    ServiceLayerImpl(BullsAndCowsDao dao) {
        this.dao = dao;
    }

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
    public Game getGameById(String gameId) throws SQLException {
        Game outputGame = dao.getGame(gameId);
        if (outputGame.isInProgress()){
            outputGame.setAnswer("----");
        }
        return outputGame;
    }

    @Override
    public List<Game> getAllGames() throws SQLException {
        List<Game> outputGames= dao.getAllGames();
        for(Game g: outputGames){
            if (g.isInProgress()){
                g.setAnswer("----");
            }
        }
        return outputGames;
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
    public List<Round> getRoundBasedOnGameID(String gameId) throws SQLException {
        return dao.getRounds(gameId);
    }

    @Override
    public void setUpDatabase() {
        dao.setUpDatabase();
    }
}