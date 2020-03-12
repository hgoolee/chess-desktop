package pieces;

import util.Move;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
        this.type = Type.KNIGHT;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture
        if(move.getCapturedPiece() == null
                || (move.getCapturedPiece() != null && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {
            // move along upper diagonal
            if((move.getDestinationFile() == (char)(move.getOriginFile()+1) && move.getDestinationRank() == (move.getOriginRank()+2))
            		|| (move.getDestinationFile() == (char)(move.getOriginFile()-1) && move.getDestinationRank() == (move.getOriginRank()+2))) {
                return true;
            }
            // move along down diagonal
            if((move.getDestinationFile() == (char)(move.getOriginFile()+1) && move.getDestinationRank() == (move.getOriginRank()-2))
            		|| (move.getDestinationFile() == (char)(move.getOriginFile()-1) && move.getDestinationRank() == (move.getOriginRank()-2))) {
                return true;
            }
            // move along right diagonal
            if((move.getDestinationFile() == (char)(move.getOriginFile()+2) && move.getDestinationRank() == (move.getOriginRank()+1))
            		|| (move.getDestinationFile() == (char)(move.getOriginFile()+2) && move.getDestinationRank() == (move.getOriginRank()-1))) {
                return true;
            }
            // move along left diagonal
            if((move.getDestinationFile() == (char)(move.getOriginFile()-2) && move.getDestinationRank() == (move.getOriginRank()+1))
            		|| (move.getDestinationFile() == (char)(move.getOriginFile()-2) && move.getDestinationRank() == (move.getOriginRank()-1))) {
                return true;
            }
        }

        // all other cases
        return false;
    }
}
