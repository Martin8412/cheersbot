package dk.martin8412.cheersbot.commands;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DrinkBotCommand implements IBotCommand{
    private static final SecureRandom random = new SecureRandom();
    private static final Logger logger = Logger.getLogger(DrinkBotCommand.class.getName());
    @Override
    public void notifyListener(MultiUserChat muc, Message message, String[] bodyparts, String sender) {
        if(bodyparts.length >= 2 && bodyparts[0].equals("!drink")) {
            int sips = random.nextInt(12) + 1;
            try {
                if(bodyparts[1].equals(muc.getNickname())) {
                    muc.sendMessage(sender.split("/")[1] + " skal bunde en øl");
                } else {
                    if (sips == 12) {
                        muc.sendMessage(bodyparts[1] + " skal bunde en øl");
                    } else {
                        if (sips == 1) {
                            muc.sendMessage(bodyparts[1] + " skal drikke en slurk");
                        } else {
                            muc.sendMessage(bodyparts[1] + " skal drikke " + sips + " slurke");
                        }
                    }
                }
            } catch(XMPPException | SmackException.NotConnectedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
