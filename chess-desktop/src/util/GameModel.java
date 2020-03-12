package util;

import board.Board;
import pieces.Piece;
import pieces.PieceSet;
import ui.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;


public class GameModel extends Observable {

    private GameFrame gameFrame;
    private BoardPanel boardPanel;
    private TimerPanel timerPanel;
    private ControlPanel controlPanel;
    private MoveHistoryPanel moveHistoryPanel;

    private Timer whiteTimer;
    private Timer blackTimer;
    
    private static int countSwitch;

    public GameModel() {
        initialize();
        countSwitch = 0;
    }
    
    // Save/load
    public void resetTimerCount() {
        countSwitch = 0;
        whiteTimer.stop();
        blackTimer.stop();
    }
    // Save/load

    private void initialize() {
        initializeTimers();
        initializeUIComponents();
    }
    
    public void onMoveRequest(char originFile, int originRank, char destinationFile, int destinationRank) {
        onLocalMoveRequest(originFile, originRank, destinationFile, destinationRank);
    }

    private void onLocalMoveRequest(char originFile, int originRank, char destinationFile, int destinationRank) {
        Move move = new Move(originFile, originRank, destinationFile, destinationRank);
        if (MoveValidator.validateMove(move)) {
            executeMove(move);
        } else {
            //
        }
    }

    private void executeMove(Move move) {
        MoveLogger.addMove(move);
        Board.executeMove(move);
        moveHistoryPanel.printMove(move);
        boardPanel.executeMove(move);
        switchTimer(move);
        if(move.getPiece().getType() == Piece.Type.KING) { // update the king coordinate in PieceSet
        	if(move.getPiece().getColor() == Piece.Color.BLACK) {
        		PieceSet.setOpponentKingFile(Piece.Color.WHITE, move.getDestinationFile());
        		PieceSet.setOpponentKingRank(Piece.Color.WHITE, move.getDestinationRank());
        	}
        	else {
        		PieceSet.setOpponentKingFile(Piece.Color.BLACK, move.getDestinationFile());
        		PieceSet.setOpponentKingRank(Piece.Color.BLACK, move.getDestinationRank());
        	}
        }
        if (MoveValidator.isCheckMove(move)) {
            if (MoveValidator.isCheckMate(move)) {
                stopTimer();
                gameFrame.showCheckmateDialog();
            } else {
                gameFrame.showCheckDialog();
            }
        }
    }

    public Piece queryPiece(char file, int rank) {
        return Board.getSquare(file, rank).getCurrentPiece();
    }

    private void initializeUIComponents() {
        boardPanel = new BoardPanel(this);
        timerPanel = new TimerPanel(this);
        controlPanel = new ControlPanel(this);
        moveHistoryPanel = new MoveHistoryPanel(this);
        gameFrame = new GameFrame(this);
    }

    private void initializeTimers() {
        whiteTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerPanel.whiteTimerTikTok();
            }
        });
        blackTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerPanel.blackTimerTikTok();
            }
        });
    }

    private void switchTimer(Move move) { // applied FIDE Laws of Chess
        /*
        TODO-timer
            start and stop whiteTimer and blackTimer
         */
    	if(countSwitch == 40) { // set the timer for 50 minutes
    		timerPanel.getWhiteTime().setTime(3000000);
    		timerPanel.getWhiteTimerDigitsLabel().setText(timerPanel.getWhiteTime().toString());
    		timerPanel.getBlackTime().setTime(3000000);
    		timerPanel.getBlackTimerDigitsLabel().setText(timerPanel.getBlackTime().toString());
    	}
    	else if(countSwitch == 60) { // set the timer for 15 minutes
    		timerPanel.getWhiteTime().setTime(900000);
    		timerPanel.getWhiteTimerDigitsLabel().setText(timerPanel.getWhiteTime().toString());
    		timerPanel.getBlackTime().setTime(900000);
    		timerPanel.getBlackTimerDigitsLabel().setText(timerPanel.getBlackTime().toString());
    	}
    	
    	if(move.getPiece().getColor() == Piece.Color.WHITE) {
    		blackTimer.start();
    		whiteTimer.stop();
    		if(countSwitch > 60) {
    			timerPanel.getWhiteTime().setTime(timerPanel.getWhiteTime().getTime() + 1000 * 30);
    			timerPanel.getWhiteTimerDigitsLabel().setText(timerPanel.getWhiteTime().toString());
    		}
    	}
    	else if(move.getPiece().getColor() == Piece.Color.BLACK) {
    		whiteTimer.start();
    		blackTimer.stop();
    		if(countSwitch > 60) {
    			timerPanel.getBlackTime().setTime(timerPanel.getBlackTime().getTime() + 1000 * 30);
    			timerPanel.getBlackTimerDigitsLabel().setText(timerPanel.getBlackTime().toString());
    		}
    	}
    	countSwitch++;
    }

    private void stopTimer() {
        // TODO-timer: stop timers
    	whiteTimer.stop();
    	blackTimer.stop();
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public TimerPanel getTimerPanel() {
        return timerPanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public MoveHistoryPanel getMoveHistoryPanel() {
        return moveHistoryPanel;
    }

}
