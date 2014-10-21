package dk.martin8412.cheersbot;

import dk.martin8412.cheersbot.commands.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class CheersBotMain {
    private static final Logger logger = Logger.getLogger(CheersBotMain.class.getName());
    public static void main(String[] args) {
        //Read properties file
        Properties prop = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream("config.properties");
            prop.load(is);
        } catch(IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

        CheersBot cb = new CheersBot(prop.getProperty("jid"), prop.getProperty("nickname"), prop.getProperty("password"), prop.getProperty("server"), Integer.parseInt(prop.getProperty("port")));
        String chanstring = prop.getProperty("channels");
        String chans[] = chanstring.split(",");
        for(String s : chans) {
            s = s.trim();
            cb.joinChannel(s);
        }

        //Add commands to bot
        cb.addCommand(new CauseBotCommand());
        cb.addCommand(new CheersSelfBotCommand());
        cb.addCommand(new CheersBotCommand());
        cb.addCommand(new DanceBotCommand());
        cb.addCommand(new DanceSelfBotCommand());
        cb.addCommand(new DrinkBotCommand());
        cb.addCommand(new DrinkSelfBotCommand());
        cb.addCommand(new GangsterBotCommand());
        cb.addCommand(new HugBotCommand());
        cb.addCommand(new JokeBotCommand());
        cb.addCommand(new NoBotCommand());
        cb.addCommand(new GangsterSelfBotCommand());
    }
}
