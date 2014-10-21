package dk.martin8412.cheersbot.commands;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class DanceSelfBotCommand implements IBotCommand {
    private static final Logger logger = Logger.getLogger(DanceSelfBotCommand.class.getName());
    @Override
    public void notifyListener(MultiUserChat muc, Message message, String[] bodyparts, String sender) {
        String body = message.getBody();
        if(body.startsWith("!dance") && bodyparts.length == 1 && bodyparts[0].length() == 6) {
            try {
                muc.sendMessage("/me danser sammen med " + sender.split("/")[1]);
            } catch (XMPPException | SmackException.NotConnectedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }

    }
}