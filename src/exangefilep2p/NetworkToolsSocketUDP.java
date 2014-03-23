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
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        myQueueSend   = new MyPakageQueue();
        // implement dispetcher
        isServer = myParamenters.isServer();
        myExecutor = Executors.newFixedThreadPool(myParamenters.getCountThreadPool()+4);//+4 - services thread
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
                        mySocket = new DatagramSocket();
                    } catch (SocketException  ex) {
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
                            String pathDestinations = getMyGUI().qeustionRecieveFIle(myPakage.getFileName(),myPakage.getNodeSourde() );
                            MyPakage answerPakage = new MyPakage(myPakage);
                            answerPakage.setTypeMessage(MyPakage.TypeMessagePakage.acceptRequestSendFile);
                            try {
                            answerPakage.setStringAsData(pathDestinations);
                            } catch (UnsupportedEncodingException ex) {
                            //    Logger.getLogger(NetworkToolsSocketUDP.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            answerPakage.setDataAsBoolean(!pathDestinations.isEmpty());
                            myQueueSend.putPakage(answerPakage);
                            break;
                        }
                        case acceptRequestSendFile: {
                            if (myPakage.getDataAsBoolean()) {
                                // accept send file
                                // in this is case name file it's full name file
                                FactoryFile temp=null;
                                String destinationsFile;
                                try {
                                     destinationsFile = myPakage.getDataAsString();
                                } catch (UnsupportedEncodingException ex) {
                                    //    Logger.getLogger(NetworkToolsSocketUDP.class.getName()).log(Level.SEVERE, null, ex);
                                    destinationsFile = "";
                                }
                                temp = new FactoryFile(myPakage.getFileName(), destinationsFile);
                                listOutputFactoryFile.add(temp);
                                break;
                            } else {
                                // reject send file
                            }
                            break;
                        }
                        case debugging: {
                        try {
                            getMyGUI().reciveDebugging(myPakage.getDataAsString());
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(NetworkToolsSocketUDP.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                    myPaket.setData(sendPakage.toByte());
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

    @Override
    public void sendDebugPakage() {
        //super.sendDebugPakage(); //To change body of generated methods, choose Tools | Templates.
        MyPakage myPakage = new MyPakage();
        myPakage.setTypeMessage(MyPakage.TypeMessagePakage.debugging);
        Node myDestanations = new Node();
        myDestanations.setActiv(true);
        try {
            myDestanations.setMyInetAddres(InetAddress.getByName(myParamenters.getIPServer()));
        } catch (UnknownHostException ex) {
            Logger.getLogger(NetworkToolsSocketUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        myDestanations.setPortNumber(myParamenters.getPortServer());
        myPakage.setNodeDestination(myDestanations);
        try {
            myPakage.setStringAsData("Test string");
        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(NetworkToolsSocketUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        myQueueSend.putPakage(myPakage);
    }

    /**
     * @return the myGUI
     */
    
    @Override
    public void sendFile(String fileName, Node node) {
        // send file for reciver
        Exception myEx = null;
        try {
        if (Files.exists(Paths.get(fileName), LinkOption.NOFOLLOW_LINKS)) {
            // its ok, file found. send pakage to destinations
            MyPakage myPakage = new MyPakage();
            myPakage.setTypeMessage(MyPakage.TypeMessagePakage.requestSendFile);
            myPakage.setNodeDestination(node);
            myPakage.setFileName(fileName);
            myQueueSend.putPakage(myPakage);
        } else
        {
            myEx = new FileSystemNotFoundException("File not found");
        }
        }catch (SecurityException ex){
            myEx = ex;
        }
        if (myEx!=null) getMyGUI().errorAccesToFile(fileName, myEx);
        
    }
    
}
