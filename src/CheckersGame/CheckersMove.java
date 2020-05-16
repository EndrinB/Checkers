package CheckersGame;

/* CheckersMove this class holds one valid move 
 * @author Endrin*/
class CheckersMove {
   int xFrom, yFrom; 
   int xTo, yTo;     
   /* constructor puts the values in the field variables 
    * @param x1 row where the piece is moving from
    * @param y1 column where the piece is moving from
    * @param x2 row where the piece is moving to
    * @param y2 column where the piece is moving to */
   CheckersMove(int x1, int y1, int x2, int y2) {
      xFrom = x1;
      yFrom = y1;
      xTo = x2;
      yTo = y2;
   }
   /* checks if this move is a jump */
   boolean isJump() {
      return (yFrom - yTo == -2 || yFrom - yTo == 2);
   }
}  


