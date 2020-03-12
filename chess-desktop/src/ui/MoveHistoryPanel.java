package ui;

import util.GameModel;
import util.Move;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MoveHistoryPanel extends JPanel implements Observer {

    private GameModel gameModel;
    private JScrollPane moveHistoryScrollPane;
    private JTextArea moveHistoryTextArea;
    private String moveHistoryContent;
    // Save/load
    private String moveHistoryForSave;
    // Save/load

    public MoveHistoryPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        initialize();
    }

    public void printMove(Move move) {
    	// Save/load
        moveHistoryForSave += ("" + move.getOriginFile() + move.getOriginRank() + move.getDestinationFile() + move.getDestinationRank() + "\n");
        // Save/load
    	
        String newMoveEntry = "";
        newMoveEntry += move.getPiece().getColor().toString() + " ";
        newMoveEntry += move.getPiece().getType().toString() + ": ";
        newMoveEntry += move.getOriginFile();
        newMoveEntry += move.getOriginRank() + " - ";
        newMoveEntry += move.getDestinationFile();
        newMoveEntry += move.getDestinationRank() + " ";
        if (move.getCapturedPiece() != null) {
            newMoveEntry += "captures ";
            newMoveEntry += move.getCapturedPiece().getType().toString();
        }
        newMoveEntry += "\n";

        moveHistoryContent += newMoveEntry;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                moveHistoryTextArea.setText(moveHistoryContent);
            }
        });
    }

    private void initialize() {
    	// Save/load
    	moveHistoryForSave = "";
    	// Save/load
    	
        moveHistoryContent = new String("Game start!\n");
        moveHistoryTextArea = new JTextArea(moveHistoryContent);
        moveHistoryTextArea.setBackground(Color.GRAY);
        moveHistoryScrollPane = new JScrollPane(moveHistoryTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        moveHistoryScrollPane.setBorder(BorderFactory.createTitledBorder("Move History"));
        moveHistoryScrollPane.setViewportView(moveHistoryTextArea);
        moveHistoryScrollPane.setPreferredSize(new Dimension(300, 400));
        //this.setPreferredSize(new Dimension(300, 400));
        this.add(moveHistoryScrollPane);
    }
    
    // Save/load
    public String getMoveHistory() {
        return moveHistoryForSave;
    }
    public void clearMoveHistory() {
        moveHistoryForSave = "";
        moveHistoryContent = new String("Game start!\n");
    }
    // Save/load

    @Override
    public void update(Observable o, Object arg) {

    }

}
