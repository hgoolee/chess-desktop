package util;

import board.Board;
import pieces.PieceSet;
import ui.LaunchFrame;
import ui.PreferencesFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Core {

    private static Core coreInstance = new Core();
    private static boolean inGame;

    private static GameModel gameModel;
    private static Preferences preferences;

    private static LaunchFrame launchFrame;
    private static PreferencesFrame preferencesFrame;

    // Save/load
    public static File loadFile = null;
    // Save/load
    
    private Core() {
    	
    }

    public static Core getInstance() {
        return coreInstance;
    }

    public static void launch() {
        inGame = false;
        preferences = new Preferences();
        launchFrame = new LaunchFrame();
    }

    public static void startGame() {
        inGame = true;
        gameModel = new GameModel();
        
        // Save/load
        if (loadFile != null) {
            FileReader reader = null;
            BufferedReader bufReader = null;
            try {
                reader = new FileReader(loadFile);
                bufReader = new BufferedReader(reader);

                gameModel.getTimerPanel().setWhiteTime(bufReader.readLine());
                gameModel.getTimerPanel().setBlackTime(bufReader.readLine());

                String line = "";
                while((line = bufReader.readLine()) != null){
                    char orf = line.charAt(0);
                    int orr = line.charAt(1) - '0';
                    char def = line.charAt(2);
                    int der = line.charAt(3) - '0';
                    System.out.println(""+orf+orr+def+der);
                    gameModel.getBoardPanel().submitMoveRequest(orf,orr,def,der);
                }
            } catch(IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    if(reader != null) reader.close();
                    if(bufReader != null) bufReader.close();
                } catch(IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        // Save/load
    }

    public static String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    public static LaunchFrame getLaunchFrame() {
        return launchFrame;
    }

    public static PreferencesFrame getPreferencesFrame() {
        return preferencesFrame;
    }

    public static boolean isInGame() {
        return inGame;
    }
}
