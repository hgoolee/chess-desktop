package ui;

import board.Board;
import pieces.Piece;
import util.Core;
import util.GameModel;
import pieces.PieceSet;
import util.MoveValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.io.*;

public class ControlPanel extends JPanel implements Observer {

    private GameModel gameModel;

    private JButton undoButton;
    private JButton saveButton;
    private JButton loadButton;

    public ControlPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        initialize();
        gameModel.addObserver(this);
    }

    private void initialize() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new GridLayout(0, 1));

        undoButton = new JButton("Request Undo");
        undoButton.setEnabled(true);
        saveButton = new JButton("Save Game");
        
        // Save/load
        saveButton.setEnabled(true);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("save button pressed~~");
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(new JFrame());
                if (option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    System.out.println("File Saved as: " + file.getName());
                    FileWriter writer = null;
                    try {
                        writer = new FileWriter(file);
                        //writePieces(writer);

                        writer.write(gameModel.getTimerPanel().getWhiteTime().toString() + "\n");
                        writer.write(gameModel.getTimerPanel().getBlackTime().toString() + "\n");

                        writer.write(gameModel.getMoveHistoryPanel().getMoveHistory());
                    } catch(IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        try {
                            if(writer != null) writer.close();
                        } catch(IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                else {
                    System.out.println("Save command canceled");
                }
            }
        });
        // Save/load

        loadButton = new JButton("Load Game");
        
        // Save/load
        loadButton.setEnabled(true);
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("load button pressed~~");
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(new JFrame());
                if (option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    System.out.println("File load from: " + file.getName());
                    FileReader reader = null;
                    BufferedReader bufReader = null;
                    try {
                        reader = new FileReader(file);
                        bufReader = new BufferedReader(reader);
                        //boolean blackTurn = false;
                        //if (bufReader.readLine().charAt(0) == 'b') blackTurn = true;

                        gameModel.getTimerPanel().setWhiteTime(bufReader.readLine());
                        gameModel.getTimerPanel().setBlackTime(bufReader.readLine());

                        gameModel.getMoveHistoryPanel().clearMoveHistory();
                        PieceSet.initialize();
                        //System.out.println("PieceSet size = " + PieceSet.getPieces(Piece.Color.WHITE).size());
                        Board.initialize();
                        gameModel.getBoardPanel().initializeBoardPanel();
                        gameModel.resetTimerCount();
                        MoveValidator.resetMoveColor();
                        //for (int r=1; r<=8; r++) {
                        //    for (char f = 'a'; f <= 'h'; f++) {
                        //        if (gameModel.queryPiece(f, r) != null) System.out.print("gm:" + gameModel.queryPiece(f, r).toString());
                        //        System.out.print(" (" + f + r + ") ");
                        //        if (Board.getSquare(f, r).getCurrentPiece() != null) System.out.println("bd:" + Board.getSquare(f, r).getCurrentPiece().toString());
                        //    }
                        //}

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
                else {
                    System.out.println("Save command cancelled");
                }
            }
        });
        // Save/load

        this.add(undoButton);
        this.add(saveButton);
        this.add(loadButton);
        this.setPreferredSize(new Dimension(300, 200));
    }

    // Save/load
    void writePieces(FileWriter writer) throws IOException {
        List<Piece> whiteCapPieces = PieceSet.getCapturedPieces(Piece.Color.WHITE);
        List<Piece> blackCapPieces = PieceSet.getCapturedPieces(Piece.Color.BLACK);
        for (Piece p: whiteCapPieces) {
            writer.write("c" + getPieceColor(p) + getPieceType(p) + " ");
        }
        for (Piece p: blackCapPieces) {
            writer.write("c" + getPieceColor(p) + getPieceType(p) + " ");
        }
        for (int r=1; r<=8; r++) {
            for (char f='a'; f<='h'; f++) {
                Piece p = gameModel.queryPiece(f,r);
                if (p!=null) {
                    writer.write("b" + getPieceColor(p) + getPieceType(p) + f + r + " ");
                }
            }
        }
        writer.flush();
    }
    char getPieceColor(Piece p) {
        if (p.getColor().equals(Piece.Color.WHITE))
            return 'w';
        else
            return 'b';
    }
    char getPieceType(Piece p) {
        switch (p.getType()) {
            case KING:
                return 'K';
            case ROOK:
                return 'R';
            case BISHOP:
                return 'B';
            case QUEEN:
                return 'Q';
            case KNIGHT:
                return 'N';
            case PAWN:
                return 'P';
        }
        return 'X';
    }
    // Save/load

    @Override
    public void update(Observable o, Object arg) {
    	
    }

}
