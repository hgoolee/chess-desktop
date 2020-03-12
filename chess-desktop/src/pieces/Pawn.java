package pieces;

import util.Move;

public class Pawn extends Piece {

	public Pawn(Color color) {
		super(color);
		this.type = Type.PAWN;
	}

	@Override
	public boolean validateMove(Move move) {
		if(move.getPiece().getColor() == Piece.Color.WHITE) {
			// move 2-step
			if(move.getDestinationFile() == move.getOriginFile() && move.getDestinationRank() == (move.getOriginRank()+2)) {
				if(move.getOriginRank() == 2 && move.getCapturedPiece() == null) {
					return true;
				}
			}

			// move 1-step
			if(move.getCapturedPiece() == null) {
				if(move.getDestinationFile() == move.getOriginFile() && move.getDestinationRank() == (move.getOriginRank()+1)) {
					return true;
				}
			}

			// capture along upper diagonal
			if(move.getCapturedPiece() != null && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor())) {
				if((move.getDestinationFile() == (move.getOriginFile()+1) && move.getDestinationRank() == (move.getOriginRank()+1))
						|| (move.getDestinationFile() == (move.getOriginFile()-1) && move.getDestinationRank() == (move.getOriginRank()+1))) {
					return true;
				}
			}
		}

		if(move.getPiece().getColor() == Piece.Color.BLACK) {
			// move 2-step
			if(move.getDestinationFile() == move.getOriginFile() && move.getDestinationRank() == (move.getOriginRank()-2)) {
				if(move.getOriginRank() == 7 && move.getCapturedPiece() == null) {
					return true;
				}
			}

			// move 1-step
			if(move.getCapturedPiece() == null) {
				if(move.getDestinationFile() == move.getOriginFile() && move.getDestinationRank() == (move.getOriginRank()-1)) {
					return true;
				}
			}

			// capture along lower diagonal
			if(move.getCapturedPiece() != null && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor())) {
				if((move.getDestinationFile() == (move.getOriginFile()+1) && move.getDestinationRank() == (move.getOriginRank()-1))
						|| (move.getDestinationFile() == (move.getOriginFile()-1) && move.getDestinationRank() == (move.getOriginRank()-1))) {
					return true;
				}
			}
		}

		// all other cases
		return false;
	}
}
