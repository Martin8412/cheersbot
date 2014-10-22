package dk.martin8412.cheersbot.commands;

import dk.martin8412.cheersbot.Phrases;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CauseBotCommand implements IBotCommand {
    private static final Logger logger = Logger.getLogger(CauseBotCommand.class.getName());
    private static final SecureRandom random = new SecureRandom();
    @Override
    public void notifyListener(MultiUserChat muc, Message message, String[] bodyparts, String sender) {
        if(bodyparts.length >= 1 &&  bodyparts[0].equals("!cause")) {
            try {
                muc.sendMessage("The cause of the problem is that, " + Phrases.bofhPhrases[random.nextInt(Phrases.bofhPhrases.length)]);
            } catch (XMPPException | SmackException.NotConnectedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
