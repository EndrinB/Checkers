package CheckersGame;

import java.util.*;

/* CheckersData is the Model in the architecture wich holds the data 
 * of the game ,positions ,values etc
 *
 *@author Endrin */
public class CheckersData implements PieceValues{
   int[][] data = new int[8][8];
   
   /* The Constructor calls the setNewGame() method wich give the data array the default values when a game starts */
   public CheckersData(){
      setNewGame();     
   }
   
   /* setNewGame() using loops gives the starting WHITE,BLACK,EMPTY values in the boxes of the checker board*/
   public void setNewGame(){
      for (int y = 0; y < 8; y++) {
         for (int x = 0; x < 8; x++) {
            if ( x % 2 == y % 2 ) {
               if (y < 3)
                  data[y][x] = WHITE;
               else if (y > 4)
                  data[y][x] = BLACK;
               else
                  data[y][x] = EMPTY;
            }
            else {
               data[y][x] = EMPTY;
            }
         }
      }
   }
   /* getPiece method returns the value stored in the array with the coordinations asked
    * @param x row of the array
    * @param y column of the array */
   public int getPiece(int x , int y){
      return data[x][y];
   }
   /* MovePiece moves the value stored in the first square to the second one and makes the first ones value EMPTY
    * @param x1 row of the first square
    * @param y1 column of the first square
    * @param x2 row of the second square
    * @param y2 column of the second square*/
   public void movePiece(int x1,int y1,int x2,int y2){
      data[x2][y2] = data[x1][y1];
      data[x1][y1] = EMPTY;
      if(isJump(y1,y2)){
         int x3 = (x1+x2)/2;
         int y3 = (y1+y2)/2;
         data[x3][y3] = EMPTY;
      }
      if(x2 == 7 && data[x2][y2] == WHITE){
         data[x2][y2] = WHITE_KING;
      }
      else if(x2 ==0 && data[x2][y2] == BLACK){
         data[x2][y2] = BLACK_KING;
      }    
   }
   /* isJump checks if the movement is considered a jump,it checks if the squares are 2 columns apart
    * @param y1 column of the first square
    * @param y2 column of the second square*/
   private boolean isJump(int y1,int y2){
      boolean answer = false;
      if(y2-y1 == 2 || y2-y1 == -2){
         answer = true;
      }
      return answer;
   }
   /* getAvailableMoves is the main method of this class wich returns all the available moves of the asked player
    * @param player the player whose moves need to be returned */
   public List getAvailableMoves(int player){
      if (player != WHITE && player != BLACK)          //checks if the parameter is player,if it isnt its returned null
         return null;
         
      int king;
      if(player == WHITE)                                     
         king = WHITE_KING;                          //variable wich holds the king for the specified color
      else
         king = BLACK_KING;
          
      List<CheckersMove> moves = new ArrayList<>();
      for(int y = 0 ; y <= 7 ; y++){
         for(int x =0; x<= 7 ;x++){
            if(data[y][x] == player || data[y][x] == king){     // creates a arraylist wich fill with CheckerMoves objects,using a for loop
               if(canJump(player,y,x,y+1,x+1,y+2,x+2))          // it checks the whole board for the values WHITE AND WHITE_KING stored
                  moves.add(new CheckersMove(y,x,y+2,x+2));     // and then for each of the pieces it checks in every direction if it can jump or not,if yes its stored in the moves array
                  
               if(canJump(player,y,x,y-1,x-1,y-2,x-2))
                  moves.add(new CheckersMove(y,x,y-2,x-2));
                  
               if(canJump(player,y,x,y+1,x-1,y+2,x-2))
                  moves.add(new CheckersMove(y,x,y+2,x-2));
                  
               if(canJump(player,y,x,y-1,x+1,y-2,x+2))
                  moves.add(new CheckersMove(y,x,y-2,x+2));
            }
         }
      }
      //if one jump or more was found ,the player must jump so the regular moves dont need to be stored aswell,else if none were found it stores the regular moves in the array
      if(moves.size() == 0){
         for(int y = 0 ; y <= 7 ; y++){
            for(int x =0; x<= 7 ;x++){
               if(data[y][x] == player || data[y][x] == king){
                  if(canMove(player,y,x,y+1,x+1))
                     moves.add(new CheckersMove(y,x,y+1,x+1));
                  
                  if(canMove(player,y,x,y-1,x-1))
                     moves.add(new CheckersMove(y,x,y-1,x-1));
                  
                  if(canMove(player,y,x,y+1,x-1))
                     moves.add(new CheckersMove(y,x,y+1,x-1));
                  
                  if(canMove(player,y,x,y-1,x+1))
                     moves.add(new CheckersMove(y,x,y-1,x+1));
               }
            }
         }
      }
      return moves; // if no moves are found the game will most likely be over
   }
   /* canJump method checks if the jump is valid from the square x1.y1 to x3,3
    * @param color the color of the piece that is jumping
    * @param x1 row of the first square
    * @param y1 column of the first square
    * @param x2 row of the second square
    * @param y2 column of the second square
    * @param x3 row of the third square
    * @param y3 column of the third square    */
   public boolean canJump(int color, int x1,int y1 , int x2 , int y2, int x3, int y3){
           
      if (x3 < 0 || x3 >= 8 || y3 < 0 || y3 >= 8) // if true the third square is out of the board
         return false;  
         
      if (data[x3][y3] != EMPTY) // if it isnt empty it cant jump there
         return false;

      if(color == WHITE){
         if(data[x1][y1] == WHITE && x3<x1)                       //if its white the third row must be higher since white can go only up
            return false;
         if(data[x2][y2] != BLACK && data[x2][y2] != BLACK_KING)  // second square must be the color of the enemy
            return false;
      }
      else if(color == BLACK){
         if(data[x1][y1] == BLACK && x3>x1)                       // the black color can go only down
            return false;
         if(data[x2][y2] != WHITE && data[x2][y2] != WHITE_KING)
            return false;
            
      }
      return true; 
   }
   /* canMove checks if the move is valid
    * @param color the color of the piece that is moving
    * @param x1 row of the first square
    * @param y1 column of the first square
    * @param x2 row of the second square
    * @param y2 column of the second square */
   public boolean canMove(int color,int x1,int y1, int x2 , int y2){ //same logic
      boolean answer = true;
      if (x2 < 0 || x2 >= 8 || y2 < 0 || y2 >= 8)
         return false;  
         
      if (data[x2][y2] != EMPTY)
         return false;
         
      if(color == WHITE){
         if(data[x1][y1] == WHITE && x2<x1)
            return false;
      }
      else if(color == BLACK){
         if(data[x1][y1] == BLACK && x2>x1)
            return false;
      }
      return answer;
   }
   /* jumpFrom checks for the jumps that can occurr fromt the specified postition
    * @param color the color of the piece that is jumping
    * @param x1 row of the first square
    * @param y1 column of the first square
    */
   public List<CheckersMove> jumpFrom(int player , int x , int y){
      if(data[y][x] == EMPTY)
         return null;
          
      int king;
      if(player == WHITE)
         king = WHITE_KING;
      else
         king = BLACK_KING;
          
      List<CheckersMove> moves = new ArrayList<>();    
      if(data[y][x] == player || data[y][x] == king){
         if(canJump(player,y,x,y+1,x+1,y+2,x+2))
            moves.add(new CheckersMove(y,x,y+2,x+2));
                  
         if(canJump(player,y,x,y-1,x-1,y-2,x-2))
            moves.add(new CheckersMove(y,x,y-2,x-2));
                  
         if(canJump(player,y,x,y+1,x-1,y+2,x-2))
            moves.add(new CheckersMove(y,x,y+2,x-2));
                  
         if(canJump(player,y,x,y-1,x+1,y-2,x+2))
            moves.add(new CheckersMove(y,x,y-2,x+2));
      }
      return moves;
   }
   
}
