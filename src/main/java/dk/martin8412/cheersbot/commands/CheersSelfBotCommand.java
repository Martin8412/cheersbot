package dk.martin8412.cheersbot.commands;

import dk.martin8412.cheersbot.Phrases;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CheersSelfBotCommand implements IBotCommand {
    private static final SecureRandom random = new SecureRandom();
    private static final Logger logger = Logger.getLogger(CheersSelfBotCommand.class.getName());
    @Override
    public void notifyListener(MultiUserChat muc, Message message, String[] bodyparts, String sender) {
        if(bodyparts.length == 1 && bodyparts[0].equals("!s")) {
            try {
                muc.sendMessage(Phrases.cheersPhrases[random.nextInt(Phrases.cheersPhrases.length)]);
            } catch (XMPPException | SmackException.NotConnectedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
