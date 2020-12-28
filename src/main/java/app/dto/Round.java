/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dto;

/**
 *
 * @author johnr
 */
public class Round {
    private String roundId, gameId, guess;
    private int partialMatch, exactMatch;
    
    public Round(String newRoundId, String newGameId, String newGuess, int newPartialMatch, int newExactMatch){
        roundId = newRoundId;
        gameId = newGameId;
        guess = newGuess;
        partialMatch = newPartialMatch;
        exactMatch = newExactMatch;
    }
    
    public String getRoundId(){
        return roundId;
    }
    
    public String getGameId(){
        return gameId;
    }
    
    public String getGuess(){
        return guess;
    }
    
    public int getPartialMatch(){
        return partialMatch;
    }
    
    public int getExactMatch(){
        return exactMatch;
    }
    
    public void setGuessId(String newGameId){
        gameId = newGameId;
    }
    
    public void setGuess(String newGuess){
        guess = newGuess;
    }
    
    public void setPartialMatch(int newPartialMatch){
        partialMatch = newPartialMatch;
    }
    
    public void setExactMatch(int newExactMatch){
        exactMatch = newExactMatch;
    }
    
}
