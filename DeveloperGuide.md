# Developer Guide

Here is a brief explanation about how each directory and file are implemented. Bold files are important files to understand the flow of the source code. Reading bold files first is recommended.

## root(`src` directory)
### Launcher
* The starting point of the entire program

## board
### Board
* Set of Squares
* **board.Board >> initializePieces()**:
The purpose of these methods is to initialize pieces on a game board, so we inserted all kinds of pieces on their proper position.

### Square
* Box containing a piece

## pieces
### Bishop
* **pieces.Bishop >> validateMove(Move move)**:
Bishop can only move diagonally. When there is no unit on its diagonal way, it can move anywhere on its diagonal way. When there is an opposite color unit on its diagonal way, it can move and capture the unit. So, on these cases, we designed Bishop’s valid movement not only when original file and destination file is different but also when original rank and destination rank is different. In addition, the number of steps moving along file and the number of steps moving along rank must be equal.

### King
* **pieces.King >> validateMove(Move move)**:
King can only move one step to any directions. It can move when there is no unit on its destination. When there is an opposite color unit on its destination, it can move and capture the unit. In King’s valid movement along a straight line, we designed it when either original rank or file is same with its destination and the other’s destination is different from its original. In King’s valid movement for a diagonal line, we designed it when original and destination rank differ by one step, and original and destination file also differ by one step.

### Knight
* **pieces.Knight >> validateMove(Move move)**:
Knight can only move two steps at once: one step in a straight line and one step in a diagonal line. It can move when there is no unit on its destination. When there is an opposite color unit on its destination, it can move and capture the unit. Especially, except for the destination, it does not care the existence of unit on its way. So, on these cases, we divided Knight’s valid movement into four cases. Along upper diagonal, it can move two steps forward along rank and one step either forward or backward along file. Along lower diagonal, it can move two steps backward along rank and one step either forward or backward along file. Along right diagonal, it can move two steps forward along file and one step either forward or backward along rank. Along left diagonal, it can move two steps backward along file and one step either forward or backward along rank.

### Pawn
* **pieces.Pawn >> validateMove(Move move)**:
Because Pawn can only move one step forward and it can move two steps in its first movement, we distinguished Pawn into white and black using *move.getPiece.getColor* method. For white Pawn, when its rank is 2 and there is no unit on its destination, we made it move plus two steps along rank. And, when there is no unit on its destination, we made it move plus one step along rank. When there is a black color unit only on its one step upper diagonal destination, it can both move and capture. For black Pawn, when its rank is 7 and there is no unit on its destination, we made it move minus two steps along rank. And, when there is no unit on its destination, we made it move minus one step along rank. When there is a white color unit only on its one step lower diagonal destination, it can both move and capture.

### Piece
* Base class for pieces

### PieceSet
* Business logic for pieces(e.g. allPieces, capturedPieces)
* **pieces.PieceSet >> initializePieceSet()**:
To initialize pieces and put them to pieceSet, we designed this method to append all pieces for each generic type set by colors.

### Queen
* **pieces.Queen >> validateMove(Move move)**:
Queen can move straight and diagonally. It can move when there is no unit on its destination. When there is an opposite color unit on its destination, it can move and capture the unit. In Queen’s valid movement along a straight line, we used same way with Rook. In its valid movement along diagonal, we used same way with Bishop.

### Rook

## ui
### BoardPanel
* **ui.BoardPanel >> initializePieces()**:
The purpose of these methods is to initialize pieces on a game board, so we inserted all kinds of pieces on their proper position.

### ControlPanel

### GameFrame
* Game window including ‘BoardPanel’ , ‘TimePanel’, ‘ControlPanel’

### LaunchFrame
* Launch window shown at first

### MoveHistoryPanel

### PieceDragAndDropListener

### PreferencesFrame
* Preference window when clicking ‘New Game’ on the Launch window

### SpringUtilities
* Spring utilities made by oracle, don’t erase license on top

### TimerPanel

## util
### Core

### GameModel
* Business logic for the game
* **util.GameModel >> switchTimer(Move move)**:
At the beginning, this method gives 2 hours for each gamer and the time decreases in each gamer’s turn.

### Move
* Important information about Move

### MoveLogger

### MoveValidator
* Move logic including check and checkmate
* **util.MoveValidator >> validateClearPath(Move move)**:
For a unit to move more than two steps, there should be no other unit between original position and destination position. This method examines those situations and returns true if it is a clear path. At first, we distinguish a moving unit into King, Queen, Bishop, Rook, Knight and Pawn using *move.getPiece.getType* method.
In the case of King, it should not go to a place where it will be captured. Thus, King cannot suicide. So, we examined all of the opponent units on the Board, and we designed this method to return false if more than one opponent unit can reach the King’s destination. Also, King does not move more than two steps, so it can move freely excepting this situation. In this case, this method always returns true.
In the case of Knight, except for the destination, it does not care the existence of unit on its way, so there is no need for this examination. In this case, this method always returns true.
In the case of Pawn, we need to examine only when it moves two steps, thus in the first movement. So, we distinguish the moving Pawn into white and black. If it is white and it moves plus two steps along rank, this method will examine whether there is a unit on the plus one step along rank. If it is black and it moves minus two steps along rank, this method will examine whether there is a unit on the minus one step along rank.
For other units, we defined two variables: rankChange and fileChange. If original rank is smaller than, bigger than, or same with destination rank, rankChange will become 1, -1, or 0 for each. If original file is smaller than, bigger than, or same with destination file, fileChange will become 1, -1, or 0 for each. In this method, a sequence (i, j) gets (original file, original rank) of moving unit and then the while loop will activate until (i, j) will become (destination file, destination rank) of it. In the while loop, the first (i, j) sequence will examine the moving units original position and definitely there is a unit. Therefore, there is no need for this exanimation and the first examination will be ignored.
In Rook’s movement, either file or rank only changes. Then, among rankChange and fileChange, only one of them will become 1 or -1, and the other will become 0. Therefore, in the while loop, this method will examine a straight line where Rook unit moves.
In Bishop’s movement, both file and rank change. Then, rankChange and fileChange will become 1 or -1. Therefore, in the while loop, this method will examine a diagonal line where Bishop’s unit moves.
In Queen’s movement, Queen can move along a straight line or along a diagonal line. When it moves along a straight line, this method will act like examining Rook’s movement. When it moves along a diagonal line, this method will act like examining Bishop’s movement.
* **util.MoveValidator >> isCheckMove(Move move)**:
This method returns a Boolean value (true, false) showing whether a check happens or not after each move. If the king is in check, then the king should be moved to a position where it can avoid being attacked by its opponent.
Detecting a check can be divided into two cases; one is a direct check and the other is a discovered check. Direct check happens when the king is attacked by one opponent piece which just moved directly attacking the king, while a discovered check occurs when an opponent piece opens the ray in king direction allowing another piece to attack the king.
Our idea to detect the check condition is to look all 64 squares in the board and to see if the piece on each square can move to the king’s position. If there is at least one opponent piece that can move to king’s position, the king is in check as that piece can attack the king by either a direct check or a discovered check. We have used double for-loop to look all squares.
In each case inside the for-loop, we implemented *_canCaptureKing_* method. This method detects whether there is a piece on that square, and whether that piece is an opponent piece, and whether that piece can move to the king’s position. If all three of these conditions are satisfied, that piece can capture the king as is the literal meaning of this method.
* **util.MoveValidator >> isCheckMate(Move move)**:
This method returns a Boolean value (true, false) indicating whether a checkmate occurs or not after each move. If the king is in checkmate, then the game will end as there is no place for the king to move legally and this means the king will be attacked by its opponent in the next turn.
Since checkmate can occur only when the king is in check, we returned false when the check condition is not satisfied in the first line of the method.
King can move in straight direction (up, down, left, right) and in diagonal direction (up right, down right, up left, down left) by one square. If there are no pieces occupied in any of its adjacent squares, there are 8 places in total for the king to move. So we assume the king to move to each of these 8 squares and detect if there are any opponent that can move to each of its assumed position. This can be divided into 8 cases and we examine each case as shown in the code starting from right, up right, up left, and so on and so forth. This process is same as detecting the check for each case and if there is at least one case that the king is not in check, we return false since the king can make a legal move to avoid the check.
Also we implemented our own *_isCheckMove_* method (overloading the original method) to make our detecting process easier. This method functions exactly the same as the original *isCheckMove* method except that the king’s position is given as its parameter and the destination location can be changed. The destination location is not the actual positon of the king since we are only making an assumption that the king has moved to its adjacent squares for each 8 cases as stated above and checking if any of its opponent can move to that assumed king’s position. The rest of the method is identical to the process of detecting the check in the original method where the double for-loop and *canCaptureKing* method are utilized.

### Preferences
