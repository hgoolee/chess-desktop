package util;

import board.Board;
import board.Square;
import pieces.Knight;
import pieces.Piece;
import pieces.PieceSet;

import java.util.List;

import static pieces.Piece.*;
import static pieces.Piece.Type.*;

public class MoveValidator {

    private static MoveValidator ourInstance = new MoveValidator();

    public static MoveValidator getInstance() {
        return ourInstance;
    }

    private MoveValidator() {
        currentMoveColor = Color.WHITE;
    }

    private static Color currentMoveColor;
    
    // Save/load
    public static void resetMoveColor() {
    	currentMoveColor = Color.WHITE;
    }
    // Save/load

    public static boolean validateMove(Move move) {
        return validateMove(move, false);
    }

    public static boolean validateMove(Move move, boolean ignoreColorCheck) {
        // check for out of bounds
        if (move.getDestinationFile() < 'a' || move.getDestinationFile() > 'h'
                || move.getDestinationRank() < 1 || move.getDestinationRank() > 8) {
            return false;
        }

        // check for valid origin
        if (move.getPiece() == null) {
            return false;
        }

        // check for valid color
        if (!move.getPiece().getColor().equals(currentMoveColor) && !ignoreColorCheck) {
            return false;
        }

        // check for valid destination
        if (move.getCapturedPiece() != null) {
            if (move.getPiece().getColor().equals(move.getCapturedPiece().getColor())) {
                return false;
            }
        }

        // check for piece rule
        if (!move.getPiece().validateMove(move)) {
            return false;
        }

        // check for clear path
        if (!validateClearPath(move)) {
            return false;
        }

        currentMoveColor = currentMoveColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
        return true;
    }

    public static boolean isCheckMove(Move move) {
        // TODO-check
    	/*
    	* The king is attacked by one opponent piece,
    	* either by the piece which just moved directly attacking the king,
    	* or alternatively a discovered check attacks the king by opening the ray in opponent king direction with another piece
    	*/
    	Piece.Color checkMoveColor;
    	checkMoveColor = currentMoveColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
    	
    	char destinationFile = PieceSet.getOpponentKingFile(checkMoveColor);
        int destinationRank = PieceSet.getOpponentKingRank(checkMoveColor);
    	
        // direct check
    	char movedPieceFile = move.getDestinationFile();
        int movedPieceRank = move.getDestinationRank();
        System.out.println("from " + movedPieceFile + ""  + movedPieceRank + " to " + destinationFile + "" + destinationRank);
        if(canCaptureKing(movedPieceFile, movedPieceRank, destinationFile, destinationRank)) return true;
        
        // discovered check
        for(int i=0; i<8; i++) {
        	char originFile = (char)(i+'a');
        	for(int j=0; j<8; j++) {
        		int originRank = j+1;
        		System.out.println("from " + originFile + ""  + originRank + " to " + destinationFile + "" + destinationRank);
        		if(canCaptureKing(originFile, originRank, destinationFile, destinationRank)) return true;
        	}
        }
        return false;
    }
    
    // overloading the original isCheckMove method
    static boolean isCheckMove(char destinationFile, int destinationRank) {
        for(int i=0; i<8; i++) {
        	char originFile = (char)(i+'a');
        	for(int j=0; j<8; j++) {
        		int originRank = j+1;
        		if(canCaptureKing(originFile, originRank, destinationFile, destinationRank)) return true;
        	}
        }
        return false;
    }
    
    static boolean canCaptureKing(char originFile, int originRank, char destinationFile, int destinationRank) {
        Piece piece = Board.getSquare(originFile, originRank).getCurrentPiece();
        System.out.println("(originFile : " + originFile + ", originRank : " + originRank + ")");
    	if(piece != null && !piece.getColor().equals(currentMoveColor)) {
    		Move checkMove = new Move(piece, originFile, originRank, destinationFile, destinationRank);
    		if(piece.validateMove(checkMove) && validateClearPath(checkMove)) return true;
    		else return false;
        }
    	return false;
    }
    
    /*
    public static boolean isCheckMate(Move move) {
        // TODO-check
    	if(!isCheckMove(move)) return false;
    	
    	Piece.Color checkMoveColor;
    	checkMoveColor = currentMoveColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
    	
    	char destinationFile = PieceSet.getOpponentKingFile(checkMoveColor);
        int destinationRank = PieceSet.getOpponentKingRank(checkMoveColor);
        
        if((destinationFile+1) <= 'h') { // right
        	if(!isCheckMove((char)(destinationFile+1), destinationRank)) return false;
        }
        if((destinationFile+1) <= 'h' && (destinationRank+1) <= 8) { // up right
        	if(!isCheckMove((char)(destinationFile+1), destinationRank+1)) return false;
        }
        if((destinationFile+1) <= 'h' && (destinationRank-1) >= 1) { // down right
        	if(!isCheckMove((char)(destinationFile+1), destinationRank-1)) return false;
        }
        if((destinationFile-1) >= 'a' ) { // left
        	if(!isCheckMove((char)(destinationFile-1), destinationRank)) return false;
        }
        if((destinationFile-1) >= 'a' && (destinationRank+1) <= 8) { // up left
        	if(!isCheckMove((char)(destinationFile-1), destinationRank+1)) return false;
        }
        if((destinationFile-1) >= 'a' && (destinationRank-1) >= 1) { // down left
        	if(!isCheckMove((char)(destinationFile-1), destinationRank-1)) return false;
        }
        if((destinationRank+1) <= 8) { // up
        	if(!isCheckMove(destinationFile, destinationRank+1)) return false;
        }
        if((destinationRank-1) >= 1) { // down
        	if(!isCheckMove(destinationFile, destinationRank-1)) return false;
        }
        return true;
    }
    */
    
    /*
    public static boolean isCheckMate(Move move) {
        // TODO-check
       if(!isCheckMove(move)) return false;
       
       Piece.Color checkMoveColor;
       checkMoveColor = currentMoveColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
       
       char destinationFile = PieceSet.getOpponentKingFile(checkMoveColor);
        int destinationRank = PieceSet.getOpponentKingRank(checkMoveColor);

        int[][] arr = {{1,0},{-1,0},{0,1},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
        // move right / left / up / down / diagonal
        for(int n = 0; n<arr.length; n++) {
            int i = arr[n][0];
            int j = arr[n][1];
            if(  ((destinationFile+i) <= 'h')&&
                    ('a'<=(destinationFile+i))&&
                    ((destinationRank+j) <= 8)&&
                    (1<=(destinationRank+j))  ) {
                //king can move
                if(Board.getSquare((char)(destinationFile+i), destinationRank+j).getCurrentPiece()==null){
                    if(!isCheckMove((char)(destinationFile+i), destinationRank+j)) return false;
                }
                //king can capture
                if(Board.getSquare((char)(destinationFile+i), destinationRank+j).getCurrentPiece()!=null){
                    if(Board.getSquare((char)(destinationFile+i), destinationRank+j).getCurrentPiece().getColor()==checkMoveColor){
                        if(!isCheckMove((char)(destinationFile+i), destinationRank+j)) return false;
                    }
                }
            }
        }

        return true;
    }
    */
    
    public static boolean isCheckMate(Move move) {
        // TODO-check
    	Piece.Color color = move.getPiece().getColor();
        Piece currentPiece = null;
        for(int originFile = 97; originFile < 105; originFile++)  {
            for(int originRank = 1; originRank < 9; originRank++) {
                currentPiece = Board.getSquare((char)originFile, originRank).getCurrentPiece();
                if(currentPiece == null || currentPiece.getColor() == color) return true;
                else {
                    for(int destinationFile = 97; destinationFile < 105; destinationFile++) {
                        for(int destinationRank = 1; destinationRank < 9; destinationRank++) {
                            if(validateMove(new Move(currentPiece, (char)originFile, originRank, (char)destinationFile, destinationRank))) return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    /*
    private static boolean validateClearPath(Move move) {
        // TODO-movement
    	int fileChange;
        int rankChange;
        
        if(move.getOriginFile() < move.getDestinationFile()) {
            fileChange = 1;
        }
        else if(move.getOriginFile() > move.getDestinationFile()) {
            fileChange = -1;
        }
        else {
            fileChange = 0;
        }
        
        if(move.getOriginRank() < move.getDestinationRank()) {
            rankChange = 1;
        }
        else if(move.getOriginRank() > move.getDestinationRank()) {
            rankChange = -1;
        }
        else {
            rankChange = 0;
        }
        
        
        if(move.getPiece().getType() == KNIGHT
        		|| move.getPiece().getType() == KING) {
            return true;
        }
        else if(move.getPiece().getType() == ROOK
        		|| move.getPiece().getType() == BISHOP
        		|| move.getPiece().getType() == QUEEN) {
            // move straight
        	char i = move.getOriginFile();
            int j = move.getOriginRank();
            System.out.println("(i : " + i + ", j : " + j + ")");
            
            while(i != move.getDestinationFile() || j != move.getDestinationRank()) {
                if(!(i == move.getOriginFile() && j == move.getOriginRank())) { // should not check for the origin coordinate of the piece
                    if(Board.getSquare(i, j).getCurrentPiece() != null) {
                        return false;
                    }
                }
                i += fileChange;
                j += rankChange;
            }
            return true;
        }
        else if(move.getPiece().getType() == PAWN) {
            if(move.getPiece().getColor() == Piece.Color.WHITE) {
                // move 2-step
                if(move.getDestinationFile() == move.getOriginFile() && move.getDestinationRank() == (move.getOriginRank()+2)) {
                    if(Board.getSquare(move.getDestinationFile(), move.getOriginRank()+1).getCurrentPiece() != null) {
                        return false;
                    }
                }
                return true;
            }
            else if(move.getPiece().getColor() == Color.BLACK) { 
                // move 2-step
                if(move.getDestinationFile() == move.getOriginFile() && move.getDestinationRank() == (move.getOriginRank()-2)) {
                    if(Board.getSquare(move.getDestinationFile(), move.getOriginRank()-1).getCurrentPiece() != null) {
                        return false;
                    }
                }
                return true;
            }
            return true; // unreachable code
        }
        
        return false;
    }
    */
    
    private static boolean validateClearPath(Move move) {
        // TODO-movement
       int fileChange;
        int rankChange;
        
        if(move.getOriginFile() < move.getDestinationFile()) {
            fileChange = 1;
        }
        else if(move.getOriginFile() > move.getDestinationFile()) {
            fileChange = -1;
        }
        else {
            fileChange = 0;
        }
        
        if(move.getOriginRank() < move.getDestinationRank()) {
            rankChange = 1;
        }
        else if(move.getOriginRank() > move.getDestinationRank()) {
            rankChange = -1;
        }
        else {
            rankChange = 0;
        }
        
        
        if(move.getPiece().getType() == KING) {
            if(move.getCapturedPiece() == null
                    || (move.getCapturedPiece() != null && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))){
                for(int I=0; I<8; I++) {
                    char originFile = (char)(I+'a');
                    for(int J=0; J<8; J++) {
                        int originRank = J+1;
                        Piece piece = Board.getSquare(originFile, originRank).getCurrentPiece();
                        System.out.println("(originFile : " + originFile + ", originRank : " + originRank + ")");
                        if(piece != null && !piece.getColor().equals(currentMoveColor)) {
                            //if PAWN
                            if(piece.getType()==PAWN){
                                if(piece.getColor()==Piece.Color.WHITE){
                                    if((move.getDestinationFile()==originFile+1 && move.getDestinationRank()==originRank+1)
                                    ||(move.getDestinationFile()==originFile-1 && move.getDestinationRank()==originRank+1)) return false;
                                }
                                if(piece.getColor()==Piece.Color.BLACK) {
                                        if ((move.getDestinationFile() == originFile + 1 && move.getDestinationRank() == originRank - 1)
                                        || (move.getDestinationFile() == originFile - 1 && move.getDestinationRank() == originRank - 1))return false;
                                }
                            }else {
                                //if other unit except PAWN
                                //from opponent to king
                                Move opponentMove = new Move(piece, originFile, originRank, move.getDestinationFile(), move.getDestinationRank());
                                //if opponent can capture king
                                if (piece.validateMove(opponentMove) && validateClearPath(opponentMove)) return false;
                            }
                        }
                    }
                }
                return true;
            }
            return true;
        }

        if(move.getPiece().getType() == KNIGHT) {
            return true;
        }


        else if(move.getPiece().getType() == ROOK
              || move.getPiece().getType() == BISHOP
              || move.getPiece().getType() == QUEEN) {
            // move straight
           char i = move.getOriginFile();
            int j = move.getOriginRank();
            System.out.println("(i : " + i + ", j : " + j + ")");
            
            while(i != move.getDestinationFile() || j != move.getDestinationRank()) {
                if(!(i == move.getOriginFile() && j == move.getOriginRank())) { // should not check for the origin coordinate of the piece
                    if(Board.getSquare(i, j).getCurrentPiece() != null) {
                        return false;
                    }
                }
                i += fileChange;
                j += rankChange;
            }
            return true;
        }
        else if(move.getPiece().getType() == PAWN) {
            if(move.getPiece().getColor() == Piece.Color.WHITE) {
                // move 2-step
                if(move.getDestinationFile() == move.getOriginFile() && move.getDestinationRank() == (move.getOriginRank()+2)) {
                    if(Board.getSquare(move.getDestinationFile(), move.getOriginRank()+1).getCurrentPiece() != null) {
                        return false;
                    }
                }
                return true;
            }
            else if(move.getPiece().getColor() == Color.BLACK) { 
                // move 2-step
                if(move.getDestinationFile() == move.getOriginFile() && move.getDestinationRank() == (move.getOriginRank()-2)) {
                    if(Board.getSquare(move.getDestinationFile(), move.getOriginRank()-1).getCurrentPiece() != null) {
                        return false;
                    }
                }
                return true;
            }
            return true; // unreachable code
        }
        
        return false;
    }
}
