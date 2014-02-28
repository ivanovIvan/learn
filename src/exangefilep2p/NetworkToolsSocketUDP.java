/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dav
 */

public class NetworkToolsSocketUDP extends NetworkToolsImpl {
    //private Runnable dispetcherMessage;  
    private boolean isServer;
    final static int lenghtPaket = 5000;
    private MyInterfacePakageQueue myQueueRecive; // queue for reciveng pakage
    private MyInterfacePakageQueue myQueueSend; // queue for sending pakage
    private ExecutorService myExecutor;
    private MyInterfaceNodCollection myNodCollections;
    private HashSet<FactoryFile> listFactoryFile;
    private HashSet<FactoryFile> listOutputFactoryFile;

    public NetworkToolsSocketUDP() {
        super();
        listFactoryFile = new HashSet();
        listOutputFactoryFile = new HashSet();
        myQueueRecive = new MyPakageQueue();
        // implement dispetcher
        isServer = myParamenters.isServer();
        myExecutor = Executors.newFixedThreadPool(myParamenters.getCountThreadPool()+2);//+2 - services thread
        Runnable reciverMessage = new Runnable() {

            @Override
            public void run() {
                DatagramSocket mySocket;
                DatagramPacket myPaket = new DatagramPacket(new byte[lenghtPaket], lenghtPaket);
                MyPakage myPakage = new MyPakage();
                if (isServer) {
                    try {
                        mySocket = new DatagramSocket(myParamenters.getPortServer());
                    } catch (SocketException ex) {
                        myMessaging.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreateSocet"), ex);
                        return;
                    }
                } else {
                    try {
                        mySocket = new DatagramSocket(myParamenters.getPortServer(), InetAddress.getByName(myParamenters.getIPServer()));
                    } catch (SocketException | UnknownHostException ex) {
                        myMessaging.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreateSocet"), ex);
                        return;
                    } 
                }
                while (true) {
                    boolean pakageCorrect = true;
                    try {
                        mySocket.receive(myPaket);
                        // dispatch recieving pakage
                    } catch (SocketTimeoutException ex ){
                        // if we need reflect for interrupting
                        pakageCorrect = false;
                    } catch  (IOException ex) {
                        //Logger.getLogger(NetworkToolsSocketUDP.class.getName()).log(Level.SEVERE, null, ex);
                        pakageCorrect = false;
                        myMessaging.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorRecivengPakage"), ex);
                    }
                    if (pakageCorrect) {
                        try {
                            myPakage = MyPakage.loadFromByte(myPaket.getData());
                        } catch (ClassNotFoundException ex) {
                            pakageCorrect = false;
                            myMessaging.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorDeserializePakage"), ex);
                            //Logger.getLogger(NetworkToolsSocketUDP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (pakageCorrect) {
                            myQueueRecive.putPakage(myPakage);
                        }
                    }
                }
            }
        };
        myExecutor.execute(reciverMessage);
        // next dispatcher queue
        Runnable dispatchMessage = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    MyPakage myPakage = myQueueRecive.getPakage();
                    switch (myPakage.getTypeMessage()) {
                        case test: {
                            try {
                                // update live client or server
                                String uniquiID = myPakage.getDataAsString();
                                myNodCollections.updateStatusClient(uniquiID, true);
                            } catch (UnsupportedEncodingException ex) {
                                myMessaging.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorGetStringFromDataPakage"), ex);
                                //Logger.getLogger(NetworkToolsSocketUDP.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        }
                        case  data: case endOfFile: {
                            // recive data... send each filefactory, till factory not processing data
                            for (FactoryFile myFactory:listFactoryFile) {
                                if (myFactory.isActive()) {
                                    if (myFactory.processingPakage(myPakage)) {
                                        // delete pakage from queue
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case sendFile: {
                            // create new factory file
                            FactoryFile myFactory = new FactoryFile(myPakage);
                            listFactoryFile.add(myFactory);
                            break;
                        }
                        case requestSendFile: {
                            // temp implementation
                            //String localCatalog = "C:\\DebugProgramm\\";
                            MyPakage answerPakage = new MyPakage(myPakage);
                            answerPakage.setTypeMessage(MyPakage.TypeMessagePakage.acceptRequestSendFile);
                            answerPakage.setDataAsBoolean(true);
                            myQueueSend.putPakage(answerPakage);
                            break;
                        }
                        case acceptRequestSendFile: {
                            if (myPakage.getDataAsBoolean()) {
                                // accept send file
                                // in this is case name file it's full name file
                                FactoryFile temp = new FactoryFile(myPakage.getFileName());
                                listOutputFactoryFile.add(temp);
                                break;
                            } else {
                                // reject send file
                            }
                            break;
                        }    
                    }
                    // delete pakage from queue in all case
                    myQueueRecive.deletePakageFromQueue(myPakage);
                    
                }
            }
            
        };
        myExecutor.execute(dispatchMessage);
        Runnable senderPakage = new Runnable() {

            @Override
            public void run() {
                DatagramSocket mySocket;
                DatagramPacket myPaket = new DatagramPacket(new byte[lenghtPaket], lenghtPaket);
                MyPakage myPakage = new MyPakage();
                try {
                    mySocket = new DatagramSocket();
                } catch (SocketException ex) {
                    myMessaging.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreateSocet"), ex);
                    return;
                }
                while (true) {
                    MyPakage sendPakage = myQueueSend.getPakage();
                    myPaket.setAddress(sendPakage.getNodeDestination().getMyInetAddres());
                    myPaket.setPort(sendPakage.getNodeDestination().getPortNumber());
                    try {
                        mySocket.send(myPaket);
                    } catch (IOException ex) {
                        myMessaging.setMessage("Ошибка при посылке пакета", ex);
                        //Logger.getLogger(NetworkToolsSocketUDP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        myExecutor.execute(senderPakage);
        
    }
    
    
}
