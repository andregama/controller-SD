/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author André
 */
public class Controller {

    private static final int numServers = 2;
    private static final int[] endServers = new int[numServers];

    public static void main(String args[]) {
        setEndServers();
        inicServer();
    }

    private static void setEndServers() {
        endServers[0] = 21000;
        endServers[1] = 22000;
    }
    
    
    private static void inicServer() {
        try {
            ServerSocket server = new ServerSocket(20000);

            Socket srv1, srv2;

            do {
                Socket cli = server.accept();
                cli.setKeepAlive(true);

                ObjectInputStream input = new ObjectInputStream(cli.getInputStream());

                Requisicao req = (Requisicao) input.readObject();

                System.out.println("CONTROLLER - Requisicao Recebida");

                switch (req.getMessageType()) {
                    case Requisicao.GET_PLAYER:
                        for (int i = 0; i < numServers; i++) {
                            srv1 = new Socket("localhost", endServers[i]);
                            srv1.setKeepAlive(true);
                            ObjectOutputStream outs1 = new ObjectOutputStream(srv1.getOutputStream());
                            outs1.writeObject(req);
                            outs1.writeObject(req);
                            ObjectInputStream ins1 = new ObjectInputStream(srv1.getInputStream());
                            Resposta r1 = (Resposta) ins1.readObject();
                            if (r1.getMessageStatus() == Resposta.GET_PLAYER_OK) 
                            {
                                ObjectOutputStream outserver = new ObjectOutputStream(cli.getOutputStream());
                                outserver.writeObject(r1);
                                outserver.close();
                                outs1.close();
                                ins1.close();
                                input.close();
                                break;
                            }
                            else 
                            {
                                if(i != numServers-1)
                                {
                                    System.out.println("Falha ao obter arquivo no servidor " + i + ". Tentando obter no próximo.");
                                }
                                else
                                {
                                    ObjectOutputStream outserver = new ObjectOutputStream(cli.getOutputStream());
                                    outserver.writeObject(r1);
                                    outserver.close();
                                    outs1.close();
                                    ins1.close();
                                    input.close();
                                }
                            }
                        }
                        break;
                    case Requisicao.NEW_PLAYER:
                        srv1 = new Socket("localhost", 21000);
                        srv2 = new Socket("localhost", 22000);

                        srv1.setKeepAlive(true);
                        srv2.setKeepAlive(true);

                        ObjectOutputStream outs1 = new ObjectOutputStream(srv1.getOutputStream());
                        ObjectOutputStream outs2 = new ObjectOutputStream(srv2.getOutputStream());

                        outs1.writeObject(req);
                        outs2.writeObject(req);

                        ObjectInputStream ins1 = new ObjectInputStream(srv1.getInputStream());
                        ObjectInputStream ins2 = new ObjectInputStream(srv2.getInputStream());

                        Resposta r1 = (Resposta) ins1.readObject();
                        Resposta r2 = (Resposta) ins2.readObject();
                        ObjectOutputStream outserver = new ObjectOutputStream(cli.getOutputStream());
                        if (r1.getMessageStatus() != r2.getMessageStatus()) 
                        {
                            if(r1.getMessageStatus() == Resposta.NEW_PLAYER_OK)
                            {
                                outserver.writeObject(r1);
                            }
                            else
                            {
                                outserver.writeObject(r2);
                            }
                        }
                        else
                        {
                            outserver.writeObject(r1);
                        }
                        outserver.close();
                        outs1.close();
                        outs2.close();
                        ins1.close();
                        ins2.close();
                        input.close();
                        break;
                    case Requisicao.ALL_PLAYERS:
                        srv1 = new Socket("localhost", 21000);
                        srv2 = new Socket("localhost", 22000);

                        srv1.setKeepAlive(true);
                        srv2.setKeepAlive(true);

                        ObjectOutputStream outs3 = new ObjectOutputStream(srv1.getOutputStream());
                        ObjectOutputStream outs4 = new ObjectOutputStream(srv2.getOutputStream());

                        outs3.writeObject(req);
                        outs4.writeObject(req);

                        ObjectInputStream ins3 = new ObjectInputStream(srv1.getInputStream());
                        ObjectInputStream ins4 = new ObjectInputStream(srv2.getInputStream());

                        Resposta r3 = (Resposta) ins3.readObject();
                        Resposta r4 = (Resposta) ins4.readObject();
                        ObjectOutputStream outserver2 = new ObjectOutputStream(cli.getOutputStream());
                        if (r3.getMessageStatus() == Resposta.ALL_PLAYERS_ERROR || r4.getMessageStatus() == Resposta.ALL_PLAYERS_ERROR) 
                        {
                            r3.setMessageStatus(Resposta.ALL_PLAYERS_ERROR);
                            outserver2.writeObject(r3);
                        }
                        else
                        {
                            if(r3.getPlayersList().length != r4.getPlayersList().length)
                            {
                                if(r3.getPlayersList().length > r4.getPlayersList().length)
                                {
                                    r3.setMessageStatus(Resposta.INCONSISTENT_DATA);
                                    outserver2.writeObject(r3);
                                }
                                else
                                {
                                    r4.setMessageStatus(Resposta.INCONSISTENT_DATA);
                                    outserver2.writeObject(r4);
                                }
                            }
                            else
                            {
                                outserver2.writeObject(r3);
                            }
                        }
                        outserver2.close();
                        outs3.close();
                        outs4.close();
                        ins3.close();
                        ins4.close();
                        input.close();
                        break;
                }

            } while (true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
