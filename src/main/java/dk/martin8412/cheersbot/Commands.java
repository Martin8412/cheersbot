package dk.martin8412.cheersbot;

import dk.martin8412.cheersbot.commands.IBotCommand;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

interface Commands {
    public void addCommand(IBotCommand command);
    public void removeCommand(IBotCommand command);
    public void notifyUsers(MultiUserChat muc, Message message, String[] bodyparts, String sender);
}
