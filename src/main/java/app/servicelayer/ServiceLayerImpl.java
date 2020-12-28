package app.servicelayer;


import app.dao.BullsAndCowsDao;
import app.dto.Game;
import app.dto.RandomNumberNoDuplicate;
import app.dto.Round;
import app.exception.InputGuessInvalidException;
import app.exception.InputGuessInvalidLength;
import app.exception.InvalidIDException;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.List;
@Component
public class ServiceLayerImpl implements ServiceLayer {

    RandomNumberNoDuplicate numberGenerator = new RandomNumberNoDuplicate();
    BullsAndCowsDao dao;

    @Autowired
    ServiceLayerImpl(BullsAndCowsDao dao) {
        this.dao = dao;
    }

    private void checkGuessValidation(String inputGuess)
            throws InputGuessInvalidException,
            InputGuessInvalidLength {

        if (!StringUtils.isStrictlyNumeric(inputGuess)){
            throw new InputGuessInvalidException("Error: enter a four digit number.");
        }
        if(inputGuess.length() != 4){
            throw new InputGuessInvalidLength("Error: enter a four digit number.");
        }
    }

    private int checkExactMatch(String gameId, String inputGuess) throws SQLException {
        char [] input = inputGuess.toCharArray();
        char [] ans = dao.getAnswer(gameId).toCharArray();

        int count = 0;

        for (int i = 0; i < input.length ; i++) {
            if(input[i] == ans[i]) count++;
        }

        return count;
    }


    private int checkPartialMatch(String gameId, String inputGuess) throws SQLException {
        char [] input = inputGuess.toCharArray();
        char [] ans = dao.getAnswer(gameId).toCharArray();

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
        dao.addGame(numberGenerator.getRandomNumber());
    }

    @Override
    public Game getGameById(String gameId) throws InvalidIDException {
        Game outputGame = checkGameExistence(gameId);
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
    public List<Round> addGuess(String gameId, String inputGuess)
            throws InputGuessInvalidException,
            InputGuessInvalidLength, SQLException, InvalidIDException {

        // Checks for correct syntax
        checkGuessValidation(inputGuess);

        // Checks for correct syntax and existence in table.
        checkForGameOver(gameId);

        // Checks for exact match
        int exactMatchCount = checkExactMatch(gameId, inputGuess);

        // Checks for partial match
        int partialMatchCount = checkPartialMatch(gameId, inputGuess);

        if(exactMatchCount == 4) {
            // Ends game if exactMatchCount is four
            dao.addRound(gameId,inputGuess, partialMatchCount, exactMatchCount);
            // Updates the state of the game.
            dao.updateGameStatus(gameId);
        }
        else{
            // Adds a round into the game
            dao.addRound(gameId, inputGuess, partialMatchCount, exactMatchCount);
        }

        return dao.getRounds(gameId);
    }

    private void checkForGameOver(String gameId) throws SQLException, InvalidIDException {
        Game g = checkGameExistence(gameId);
        if(!g.isInProgress())
            throw new InvalidIDException("ERROR: game is over.");
    }

    private Game checkGameExistence(String gameId) throws InvalidIDException {
        Game g;
        try {
            g = dao.getGame(gameId);
        } catch (Exception e) {
            throw new InvalidIDException("Game does not exist with id: " + gameId);
        }
        return g;
    }

    @Override
    public List<Round> getRoundBasedOnGameID(String gameId) throws SQLException, InvalidIDException {

        checkGameExistence(gameId);
        return dao.getRounds(gameId);
    }

    @Override
    public void setUpDatabase() {
        dao.setUpDatabase();
    }
}
