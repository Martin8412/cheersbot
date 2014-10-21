package dk.martin8412.cheersbot.commands;

import dk.martin8412.cheersbot.Phrases;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JokeBotCommand implements IBotCommand {
    private static final SecureRandom random = new SecureRandom();
    private static final Logger logger = Logger.getLogger(JokeBotCommand.class.getName());
    @Override
    public void notifyListener(MultiUserChat muc, Message message, String[] bodyparts, String sender) {
        String body = message.getBody();

        if(body.startsWith("!joke") && bodyparts.length == 1 && bodyparts[0].length() == 5) {
            try {
                muc.sendMessage(Phrases.jokePhrases[random.nextInt(Phrases.jokePhrases.length)]);
            } catch (XMPPException | SmackException.NotConnectedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
