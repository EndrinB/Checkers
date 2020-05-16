package CheckersGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/* CheckersTable class draws the board and the side info ,it shows players turn,movement,piecestaken etc
 *
 * @author Endrin */
 
public class CheckersTable extends JPanel implements PieceValues{
   int turn,whiteTaken,blackTaken;
   CheckersMove lastMove;
   JFrame frame;
   Color pJumpedColor,pMoveColor;
   
   /* The constructor creates a jframe and an instance of the checkers play buttons
    * @param d the data wich will be given to the buttons */
   CheckersTable(CheckersData d){      
      frame = new JFrame("Checkers");
      frame.setSize(966,780);
      frame.getContentPane().add(this);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.setResizable(false);    
      frame.setLayout(null);
      frame.setLocationRelativeTo(null);
      CheckersPlayButtons button = new CheckersPlayButtons(this,d);
      turn = button.getPlayerTurn();
      
   }
   /* paintComponent draws the board and the side info
    * @param g the pen wich draws */
   public void paintComponent(Graphics g){
      TABLE.paintIcon(this,g,0,0);      //this draws the board and side
      SIDE.paintIcon(this,g,752,0);
      
      Font font1 = new Font("Times New Roman", Font.BOLD, 31);
      g.setFont(font1);
      g.setColor(Color.white);
      if(blackTaken < 10)
         g.drawString(blackTaken+"",805,330);
      else
         g.drawString(blackTaken+"",797,330);
      g.setColor(Color.black);                     // this draws the piecesTaken for white and black
      if(whiteTaken <10)
         g.drawString(whiteTaken+"",883,330);
      else
         g.drawString(whiteTaken+"",87,330);
      
      font1 = new Font("Times New Roman", Font.BOLD, 20);
      g.setFont(font1);
      if(lastMove != null){
         if(!lastMove.isJump()){
            MOVED_TO.paintIcon(this,g,768,186);
            g.setColor(pMoveColor);
            g.drawString((char)(65+lastMove.yFrom) + "" + (1+lastMove.xFrom),778,225);
            g.drawString((char)(65+lastMove.yTo) + "" + (1+lastMove.xTo),902,225);
            repaint();
         }else{                                                                                //this draws the movement in the movement table
            JUMPED_TO.paintIcon(this,g,768,186);
            g.setColor(pMoveColor);
            g.drawString((char)(65+lastMove.yFrom) + "" + (1+lastMove.xFrom),778,225);
            g.drawString((char)(65+lastMove.yTo) + "" + (1+lastMove.xTo),902,225);
            g.setColor(pJumpedColor);
            g.fillOval(837,220,30,20);
         }
      }
      if(turn == WHITE){
         g.setColor(new Color(211,173,152));
         g.fillOval(901,80,35,35);
         g.setColor(Color.white);
         g.fillOval(901,80,30,30);
      }
      else{                                //this draws the circle telling the turn
         g.setColor(Color.black);
         g.fillOval(901,80,35,35);
         g.setColor(new Color(50,50,50));
         g.fillOval(903,82,25,25);   
      }
   }
}
