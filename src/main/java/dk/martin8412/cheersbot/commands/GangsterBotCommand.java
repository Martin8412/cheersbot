package dk.martin8412.cheersbot.commands;

import dk.martin8412.cheersbot.Phrases;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GangsterBotCommand implements IBotCommand {
    private static final SecureRandom random = new SecureRandom();
    private static final Logger logger = Logger.getLogger(GangsterBotCommand.class.getName());
    @Override
    public void notifyListener(MultiUserChat muc, Message message, String[] bodyparts, String sender) {
        if(bodyparts.length >= 2 &&  bodyparts[0].equals("!G")) {
            try {
                muc.sendMessage(bodyparts[1] + ", " + Phrases.hardPhrases[random.nextInt(Phrases.hardPhrases.length)]);
            } catch (XMPPException | SmackException.NotConnectedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
