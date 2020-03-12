package pieces;

import util.Move;

public class King extends Piece {

	public King(Color color) {
		super(color);
		this.type = Type.KING;
	}

	@Override
	public boolean validateMove(Move move) {
		// executeMove or capture
		if(move.getCapturedPiece() == null
				|| (move.getCapturedPiece() != null && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {
			// move along file
			if((move.getDestinationFile() == (char)(move.getOriginFile()+1) || move.getDestinationFile() == (char)(move.getOriginFile()-1))
					&& move.getDestinationRank() == move.getOriginRank()) {
				return true;
			}
			// move along rank
			if(move.getDestinationFile() == move.getOriginFile()
					&& (move.getDestinationRank() == move.getOriginRank()+1 || move.getDestinationRank() == move.getOriginRank()-1)) {
				return true;
			}

			// move along diagonal
			if((move.getDestinationFile() == (char)(move.getOriginFile()+1) && move.getDestinationRank() == move.getOriginRank()+1)
					|| (move.getDestinationFile() == (char)(move.getOriginFile()-1) && move.getDestinationRank() == move.getOriginRank()+1)
					|| (move.getDestinationFile() == (char)(move.getOriginFile()+1) && move.getDestinationRank() == move.getOriginRank()-1)
					|| (move.getDestinationFile() == (char)(move.getOriginFile()-1) && move.getDestinationRank() == move.getOriginRank()-1)) {
				return true;
			}
		}

		// all other cases
		return false;
	}
}
