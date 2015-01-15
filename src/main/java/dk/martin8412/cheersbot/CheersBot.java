package dk.martin8412.cheersbot;

import dk.martin8412.cheersbot.commands.*;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheersBot implements Commands {
    private XMPPConnection xmpp = null;
    private final String nickname;
    private boolean finished = false;

    private static final Logger logger = Logger.getLogger(CheersBot.class.getName());

    private final List<IBotCommand> commands = new ArrayList<>();


    public CheersBot(String username, String nickname, String password, String server, int port) {
        this.nickname = nickname;

        //Configure connection to XMPP server
        ConnectionConfiguration config = new ConnectionConfiguration(server, port);
        config.setCompressionEnabled(true);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);

        //Override SSL context since Java does not like StartCom SSL
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            if(sc != null) sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        config.setCustomSSLContext(sc);

        //Connect to XMPP server
        xmpp = new XMPPTCPConnection(config);
        try {
            xmpp.connect();
            xmpp.login(username, password);
        } catch(SmackException | IOException | XMPPException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        MultiUserChat.addInvitationListener(xmpp, (xmppConnection, room, inviter, reason, password1, message) -> {
            if(password1 != null && !password1.isEmpty()) {
		System.out.println("Joining MUC " + room);
                joinChannel(room, password1);
            } else {
		System.out.println("Joining MUC " + room);
                joinChannel(room);
            }
        });


    }

    @Override
    public void addCommand(IBotCommand command) {
        commands.add(command);
    }

    @Override
    public void removeCommand(IBotCommand command) {
        commands.remove(command);
    }

    @Override
    public void notifyUsers(MultiUserChat muc, Message message, String[] bodyparts, String sender) {
        for(IBotCommand command : commands) {
	    System.out.println("Notifying subscriber");
            command.notifyListener(muc, message, bodyparts, sender);
        }
    }

    void joinChannel(String room) {
        try {
            DiscussionHistory history = new DiscussionHistory();
            history.setMaxStanzas(0);
            MultiUserChat muc = new MultiUserChat(xmpp, room);
            muc.join(nickname, "", history, SmackConfiguration.getDefaultPacketReplyTimeout());
            new MessageProcessor(muc).start();
        } catch(XMPPException | SmackException.NotConnectedException | SmackException.NoResponseException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    void joinChannel(String room, String password) {
        try {
            DiscussionHistory history = new DiscussionHistory();
            history.setMaxStanzas(0);
            MultiUserChat muc = new MultiUserChat(xmpp, room);
            muc.join(nickname, password, history, SmackConfiguration.getDefaultPacketReplyTimeout());
            new MessageProcessor(muc).start();
        } catch(XMPPException | SmackException.NotConnectedException | SmackException.NoResponseException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private class MessageProcessor extends Thread {
        public MessageProcessor(final MultiUserChat muc) {
            muc.addMessageListener(packet -> {
                if(packet instanceof Message) {
                    Message mes = (Message) packet;
                    if(muc != null && mes.getFrom() != null && mes.getBody() != null) {
                        String sender = mes.getFrom();
                        String[] bodyparts = mes.getBody().split(" ");
                        if(!sender.split("/")[1].equals(nickname)) {
                            notifyUsers(muc, mes, bodyparts, sender);
                        }
                    }
                }
            });
        }

        @Override
        public void run() {
            while(!finished) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    finished = true;
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
    }
}
