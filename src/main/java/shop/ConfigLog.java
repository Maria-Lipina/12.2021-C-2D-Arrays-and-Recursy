package shop;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class ConfigLog {
    static String SHOP_WAREHOUSE = "src/main/java/warehouse.txt";
    static String GAME_PRIZES_OUT = "src/main/java/prizes.txt";
    static String LOG_FILE = "src/main/java/buglog.txt";

    static void log (String message, String className) {
        Logger lg = Logger.getLogger(className);
        try{
            FileHandler fh = new FileHandler(LOG_FILE);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            lg.addHandler(fh);
            lg.severe(message);
        }
        catch(SecurityException | IOException e){
            e.printStackTrace();
        }
    }
}
