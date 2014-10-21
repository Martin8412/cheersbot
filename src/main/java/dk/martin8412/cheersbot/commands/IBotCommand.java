package dk.martin8412.cheersbot.commands;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

public interface IBotCommand {
    public void notifyListener(MultiUserChat muc, Message message, String[] bodyparts, String sender);
}
