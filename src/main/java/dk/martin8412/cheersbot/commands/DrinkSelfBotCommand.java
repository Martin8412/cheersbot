package dk.martin8412.cheersbot.commands;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DrinkSelfBotCommand implements IBotCommand {
    private static final SecureRandom random = new SecureRandom();
    private static final Logger logger = Logger.getLogger(DrinkSelfBotCommand.class.getName());
    @Override
    public void notifyListener(MultiUserChat muc, Message message, String[] bodyparts, String sender) {
        String body = message.getBody();
        if(body.startsWith("!drink") && bodyparts.length == 1 && bodyparts[0].length() == 6) {
            int sips = random.nextInt(12) + 1;
            try {
                if (sips == 12) {
                    muc.sendMessage("Du skal bunde en øl");
                } else {
                    if (sips == 1) {
                        muc.sendMessage("Du skal drikke en slurk");
                    } else {
                        muc.sendMessage("Du skal drikke " + sips + " slurke");
                    }
                }
            } catch(XMPPException | SmackException.NotConnectedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
