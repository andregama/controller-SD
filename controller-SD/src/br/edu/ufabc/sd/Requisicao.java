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
public class Requisicao implements java.io.Serializable{
    
    // tipos de mensagem
    public static final int NEW_PLAYER = 0;
    public static final int GET_PLAYER = 1;
    
    private int    messageType;
    private Player playerContent;

    /**
     * @return the messageType
     */
    public int getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
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
    
    
    
}
