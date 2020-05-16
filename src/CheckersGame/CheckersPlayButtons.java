package CheckersGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/* CheckersPlayButtons take care of the input view and output view, using 
 * the buttons it displays for each button what value it holds and it take inputs if clicked 
 * @author Endrin */
public class CheckersPlayButtons implements ActionListener,PieceValues{
   int xFrom,yFrom,xTo,yTo;
   boolean inMotion,movedOnce,canMove = true;
   JButton[][] b = new JButton[8][8];
   JButton newGame,resign,endGame,undo,endTurn;
   CheckersTable t;
   CheckersData d;
   private int playerTurn;
   List<CheckersMove> moves;
   List<JButton> hilighted = new ArrayList<>();
   
   /* The contrsuctor creates all the buttons,and creates a new game
    * @param t the frame where the buttons will show
    * @param d the data this class will manipulate with */
   CheckersPlayButtons(CheckersTable t,CheckersData d){
      this.t = t;
      this.d = d;
      createSideButtons();
      buttons();
      createNewGame();  
   }
   /* getPlayerTurn return the player whose playing right now*/
   public int getPlayerTurn(){
      return playerTurn;
   }
   /* switchTurn changes the turn for the next player*/
   private void switchTurn(){
      refresh();
      if(playerTurn == WHITE){
         playerTurn = BLACK;
         moves = d.getAvailableMoves(BLACK);
         if(moves.size() == 0){
            winnerDialog(WHITE);        
         }
         makePieceHighlights();
         t.turn = BLACK;
         canMove = true;
         movedOnce = false;
         t.frame.repaint();
      }
      else{
         playerTurn = WHITE;
         moves = d.getAvailableMoves(WHITE);
         if(moves.size() == 0){
            winnerDialog(BLACK);
         }
         makePieceHighlights();
         t.turn = WHITE;
         canMove = true;
         movedOnce = false;
         t.frame.repaint();
      }
   }
   /* creteNewGame calls the setNewGame method wich creates the default values in their position  and gives the white color the priority to move*/
   public void createNewGame(){
      d.setNewGame();
      refresh();
      playerTurn = WHITE;
      moves = d.getAvailableMoves(WHITE);
      makePieceHighlights();
      t.turn = playerTurn;
      canMove = true;
      movedOnce = false;
      t.whiteTaken = 0;
      t.blackTaken = 0;
      
   }
   
   /* every action event happening is answered in this method
    * @param record any event happening to any component*/
   public void actionPerformed(ActionEvent e){
      refresh();
      if(e.getSource() == endTurn){
         if(!movedOnce){JOptionPane.showMessageDialog(null,"Make a move");}
         else
            switchTurn();
      }
      if(e.getSource() == undo){    
         if(movedOnce){
            undo();}
         else{
            JOptionPane.showMessageDialog(null,"Make a move first!");
            makePieceHighlights();
         }
      }
      if(e.getSource() == newGame){
         if(JOptionPane.showConfirmDialog(null,"Are you sure?","Warning",JOptionPane.YES_NO_OPTION) == 0){
            createNewGame();
            t.lastMove = null;}
      }
      if(e.getSource() == resign){
         if(JOptionPane.showConfirmDialog(null,"Are you sure?","Warning",JOptionPane.YES_NO_OPTION) == 0){
            if(playerTurn == WHITE)
               winnerDialog(BLACK);
            else
               winnerDialog(WHITE);
         }  
      }
      if(e.getSource() == endGame){
         int answer1 = JOptionPane.showConfirmDialog (null, "Would you like to end the game? Color White Confirm:","Warning",JOptionPane.YES_NO_OPTION);
         int answer2 = JOptionPane.showConfirmDialog (null, "Would you like to end the game? Color Black Confirm:","Warning",JOptionPane.YES_NO_OPTION);;
         if(answer1 == 0 && answer2 == 0){
            if(t.whiteTaken > t.blackTaken){
               winnerDialog(BLACK);}
            else if(t.whiteTaken < t.blackTaken){
               winnerDialog(WHITE);}
            else{
               JOptionPane.showMessageDialog(null,"It's a draw!");
               askNewGame();
            }
         }
      }
      int x = 0;
      int y = 0;
      for(y = 0 ; y < 8; y++){
         for(x = 0 ; x< 8 ; x++)
            if(b[x][y] == e.getSource()){
            makePieceHighlights();
               if(d.getPiece(y,x) != EMPTY && canMove){
                  if(playerTurn == WHITE){
                     xFrom = y;
                     yFrom = x;
                     inMotion = true;
                     refresh();
                     makePieceHighlights();
                     t.frame.repaint();
                     if(d.getPiece(y,x) == WHITE){
                        if(hilighted.contains(b[x][y])){
                           b[x][y].setIcon(WHITE_PICKED); 
                           makeAttackHighlights(yFrom,xFrom);                
                        }
                     }
                     if(d.getPiece(y,x) == WHITE_KING){
                        if(hilighted.contains(b[x][y])){
                           b[x][y].setIcon(WHITE_KING_PICKED); 
                           makeAttackHighlights(yFrom,xFrom);                
                        }
                     }
                  }
                  else{
                     xFrom = y;
                     yFrom = x;
                     inMotion = true;
                     refresh();
                     makePieceHighlights();
                     t.frame.repaint();
                     if(d.getPiece(y,x) == BLACK){
                        if(hilighted.contains(b[x][y])){
                           b[x][y].setIcon(BLACK_PICKED); 
                           makeAttackHighlights(yFrom,xFrom);                
                        }
                     }
                     if(d.getPiece(y,x) == BLACK_KING){
                        if(hilighted.contains(b[x][y])){
                           b[x][y].setIcon(BLACK_KING_PICKED); 
                           makeAttackHighlights(yFrom,xFrom);                
                        }
                     }
                  }
               }
               if(d.getPiece(y,x) == EMPTY && inMotion){
                  xTo =y;
                  yTo = x;
                  for (int i = 0; i < moves.size(); i++)
                     if (moves.get(i).xFrom == xFrom && moves.get(i).yFrom == yFrom
                     && moves.get(i).xTo == xTo && moves.get(i).yTo == yTo) {
                        d.movePiece(xFrom,yFrom,xTo,yTo);
                        setColor();
                        refresh();
                        if(moves.get(i).isJump()){
                           t.lastMove = moves.get(i);
                           pieceTaken();
                           setColor();
                           moves = d.jumpFrom(playerTurn,yTo,xTo);
                           if(moves.size() == 0){
                              inMotion= false;  
                              movedOnce = true;                 
                           }
                           else{
                              canMove=true;
                              makePieceHighlights();
                           }
                        }
                        else{
                           t.lastMove = moves.get(i);
                           setColor();
                           inMotion = false;
                           canMove = false;
                           movedOnce = true;
                        }
                     }
               }
            }
      
      } t.frame.repaint(); 
   }
   /* setColor sets the color in the checkersTable*/
   public void setColor(){
      if(playerTurn == WHITE){
         t.pMoveColor = Color.white;
         t.pJumpedColor = Color.black;}
      else{
         t.pMoveColor = Color.black;
         t.pJumpedColor = Color.white;
      }
   }
   /* makePieceHighlits highlits the pieces wich can move */
   public void makePieceHighlights(){
      hilighted.clear();
      for (int i = 0; i < moves.size(); i++) {
         int x = moves.get(i).yFrom;
         int y = moves.get(i).xFrom;
         JButton button = b[x][y];
         int value = d.getPiece(y,x);
         if(value == 1)
            button.setIcon(WHITE_HL_IMG);
         else if(value == 2)
            button.setIcon(BLACK_HL_IMG);
         else if(value == 3)
            button.setIcon(WHITE_KING_HL_IMG);
         else if(value == 4)
            button.setIcon(BLACK_KING_HL_IMG);
         hilighted.add(button);
      }
      t.frame.repaint();
   }
   /* makeAttackHighlights highlights the pieces where the specified piece with the coordinates can move/attack
    * @param x row coordinate
    * @param y column coordinate */
   public void makeAttackHighlights(int x , int y){
      for (int i = 0; i < moves.size(); i++) {
         if(moves.get(i).yFrom == x && moves.get(i).xFrom == y){
            JButton button = b[moves.get(i).yTo][moves.get(i).xTo];
            button.setIcon(EMPTY_HL_IMG);
         
         }
      }
      t.frame.repaint();
   }
   /* buttons creates the board buttons */
   public void buttons(){
      int x = 0;
      int y = 0;
      while(y <= 7 ){
         if(y%2 == 0){
            x = 0;
            while(x <= 6){
                
               JButton button = new JButton();
               button.setBounds(62+78*x,608 - 78*y,78,78);
               button.setIcon(EMPTY_IMG);
               b[x][y] = button;
               x+=2;
            }
         }else{
            x= 1;
            while(x <= 7){
                  
               JButton button = new JButton();
               button.setBounds(62+78*x,608 - 78*y,78,78);
               button.setIcon(EMPTY_IMG);
               b[x][y] = button;
               x+=2;
            }
         }
         y++;
         t.frame.repaint();
      }
   
   }
   /* resetData resets all the buttons icons to empty */
   public void resetData(){
      int x = 0;
      int y = 0;
      while(y <= 7 ){
         if(y%2 == 0){
            x = 0;
            while(x <= 6){             
               b[x][y].setIcon(EMPTY_IMG);
               x+=2;
            }
         }else{
            x= 1;
            while(x <= 7){     
               b[x][y].setIcon(EMPTY_IMG);
               x+=2;
            }
         }
         y++;
         t.frame.repaint();
      }
   
   }

   /* getData gets the data from the checkersData and depending on the values it gives the icons to the buttons*/
   public void getData(){
      int x = 0;
      int y = 0;
      while(y <= 7 ){
         if(y%2 == 0){
            x = 0;
            while(x <= 6){          
                  
               b[y][x].setIcon(paint(d.getPiece(x,y)));
               x+=2;
            }
         }else{
            x= 1;
            while(x <= 7){     
               b[y][x].setIcon(paint(d.getPiece(x,y)));
               x+=2;
            }
         }
         y++;
         t.frame.repaint();
      }
   }
   /* removeButtons it removes all the buttons from the frame*/
   public void removeButtons(){
      int x = 0;
      int y = 0;
      while(y <= 7 ){
         if(y%2 == 0){
            x = 0;
            while(x <= 6){
               t.frame.remove(b[x][y]);
               t.remove(b[x][y]);
               b[x][y].removeActionListener(this);
               x+=2;
            }
         }else{
            x= 1;
            while(x <= 7){
               t.frame.remove(b[x][y]);
               t.remove(b[x][y]);
               b[x][y].removeActionListener(this);
               x+=2;
            }
         }
         y++;
         t.frame.repaint();
      }
   }
   /* updateButton it adds all the buttons to the frame*/
   public void updateButtons(){
      int x = 0;
      int y = 0;
      while(y <= 7 ){
         if(y%2 == 0){
            x = 0;
            while(x <= 6){
               t.frame.add(b[x][y]);
               t.add(b[x][y]);
               b[x][y].addActionListener(this);
               x+=2;
            }
         }else{
            x= 1;
            while(x <= 7){
               t.frame.add(b[x][y]);
               t.add(b[x][y]);
               b[x][y].addActionListener(this);
               x+=2;
            }
         }
         y++;
         t.frame.repaint();
      }
   
   }
   /* refreshes the buttons and the frame */
   public void refresh(){
      removeButtons();
      resetData();
      getData();
      updateButtons();
      t.frame.repaint();
   }
   /* winnerDialog show a message dialog depending on the value if black won or white and ask for a new game*/
   public void winnerDialog(int a){
      if(a == WHITE){
         JOptionPane.showMessageDialog(null,"Congrats! White won!!");
      }else
         JOptionPane.showMessageDialog(null,"Congrats! Black won!!");
         
      askNewGame();
   
   }
   /* creates all the side buttons*/
   public void createSideButtons(){
      endTurn = new JButton("End Turn");
      resign = new JButton("Resign Game");
      newGame = new JButton("New Game");
      endGame = new JButton("End Game");
      undo = new JButton();
      newGame.setBounds(758,705,190,30); endGame.setBounds(758,645,190,30); resign.setBounds(758,675,190,30); undo.setBounds(762,390,50,50); endTurn.setBounds(820,400,120,30);
      newGame.setBackground(new Color(231,210,199)); resign.setBackground(new Color(231,210,199)); endGame.setBackground(new Color(231,210,199)); undo.setIcon(new ImageIcon("images/img/Undo.png"));  endTurn.setBackground(new Color(231,210,199));      
      t.frame.add(newGame); t.frame.add(resign); t.frame.add(endGame); t.frame.add(undo); t.frame.add(endTurn);
      t.add(newGame); t.add(resign); t.add(endGame);t.add(undo); t.add(endTurn);
      newGame.addActionListener(this); resign.addActionListener(this); endGame.addActionListener(this); undo.addActionListener(this); endTurn.addActionListener(this);
      
   }
   /* askNewGame ask the user if they want to play a new game*/
   public void askNewGame(){
      int answer = JOptionPane.showConfirmDialog (null, "Would you like to start a new game?","Warning",JOptionPane.YES_NO_OPTION);
      if(answer == 0 ) {
         createNewGame();
         t.lastMove=null;
      }else
         System.exit(0);
   }
   public void pieceTaken(){
      if(playerTurn == WHITE)
         t.blackTaken++;
      else
         t.whiteTaken++;
      
   }
   /* undo first ask for the confirmation of both players then return the last move back if both agree*/
   public void undo(){
      int answer1 = JOptionPane.showConfirmDialog (null, "Color White Confirm Undo:","Warning",JOptionPane.YES_NO_OPTION);
      int answer2 = JOptionPane.showConfirmDialog (null, "Color Black Confirm Undo:","Warning",JOptionPane.YES_NO_OPTION);;
      if(answer1 == 0 && answer2 == 0){
         if(t.lastMove != null && !t.lastMove.isJump()){
            d.movePiece(t.lastMove.xTo,t.lastMove.yTo,t.lastMove.xFrom,t.lastMove.yFrom);
            canMove = true;
            movedOnce = false;
            refresh();
            makePieceHighlights();
         }
           
      }
   }
   /* paint depending on the value returns an imageIcon
    * @param a a value for checkin */
   public ImageIcon paint(int a){
      if(a == 0)
         return EMPTY_IMG;
      if(a == 1)
         return WHITE_IMG;
      if(a==2)
         return BLACK_IMG;
      if(a == 3)
         return WHITE_KING_IMG;
      if(a == 4)
         return BLACK_KING_IMG;
      return null;
   }

}
