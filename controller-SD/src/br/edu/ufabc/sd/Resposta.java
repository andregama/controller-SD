/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sd;

/**
 *
 * @author isidro
 */
public class Resposta implements java.io.Serializable{
    public static final int NEW_PLAYER_OK    =  0;
    public static final int NEW_PLAYER_ERROR = -1;
    public static final int GET_PLAYER_OK    =  1;
    public static final int PLAYER_NOT_FOUND = -2;
    public static final int ALL_PLAYERS_OK = 2;
    public static final int ALL_PLAYERS_ERROR = -3;

    
    private int messageStatus;
    private Player playerContent;
    private int[] playersList;

    /**
     * @return the messageStatus
     */
    public int getMessageStatus() {
        return messageStatus;
    }

    /**
     * @param messageStatus the messageStatus to set
     */
    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * @return the playerContent
     */
    public Player getPlayerContent() {
        return playerContent;
    }

    /**
     * @param playerContent the playerContent to set
     */
    public void setPlayerContent(Player playerContent) {
        this.playerContent = playerContent;
    }
    
    public int[] getPlayersList()
    {
        return playersList;
    }
    
    public void setPlayersList(int[] playersList)
    {
        this.playersList = playersList;
    }
    
    
}
