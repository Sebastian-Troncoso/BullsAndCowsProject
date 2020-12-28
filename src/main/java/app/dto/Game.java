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
public class Game {
    
    private String gameId, answer;
    private boolean inProgress;
    
    public Game(String newGameId, String newAnswer, boolean newInProgress){
        gameId = newGameId;
        answer = newAnswer;
        inProgress = newInProgress;
    }
    
    public String getGameId(){
        return gameId;
    }
    
    public String getAnswer(){
        return answer;
    }
    
    public boolean isInProgress(){
        return inProgress;
    }

    public void setAnswer(String newAnswer){
        answer = newAnswer;
    }

    public void setInProgress(Boolean newInProgress){
        inProgress = newInProgress;
    }
}
