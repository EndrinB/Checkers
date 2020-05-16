package CheckersGame;

import java.awt.*;
import javax.swing.*;

/* this interface hold all the values of the images and the  pieces
 * @author Endrin */
public interface PieceValues{
   public static final ImageIcon WHITE_IMG = new ImageIcon("images/img/White.png");
   public static final ImageIcon WHITE_KING_IMG = new ImageIcon("images/img/White_King.png");
   public static final ImageIcon WHITE_HL_IMG = new ImageIcon("images/img/White_HighLight.png");
   public static final ImageIcon WHITE_KING_HL_IMG = new ImageIcon("images/img/White_King_Highlight.png");
   public static final ImageIcon BLACK_IMG = new ImageIcon("images/img/Black.png");
   public static final ImageIcon BLACK_KING_IMG = new ImageIcon("images/img/Black_King.png");
   public static final ImageIcon BLACK_HL_IMG = new ImageIcon("images/img/Black_HighLight.png");
   public static final ImageIcon BLACK_KING_HL_IMG = new ImageIcon("images/img/Black_King_Highlight.png");
   public static final ImageIcon EMPTY_IMG = new ImageIcon("images/img/Empty.png");
   public static final ImageIcon EMPTY_HL_IMG = new ImageIcon("images/img/Highlight_Empty.png");
   public static final ImageIcon TABLE = new ImageIcon("images/img/Checkers_Table.png");
   public static final ImageIcon SIDE = new ImageIcon("images/img/Side.png");
   public static final ImageIcon MOVED_TO = new ImageIcon("images/img/Moved_To.png");
   public static final ImageIcon JUMPED_TO = new ImageIcon("images/img/Jumped_To.png");
   public static final ImageIcon WHITE_PICKED = new ImageIcon("images/img/White_Picked.png");
   public static final ImageIcon BLACK_PICKED = new ImageIcon("images/img/Black_Picked.png");
   public static final ImageIcon WHITE_KING_PICKED = new ImageIcon("images/img/WHITE_King_Picked.png");
   public static final ImageIcon BLACK_KING_PICKED = new ImageIcon("images/img/Black_King_Picked.png");
  
   public static final int EMPTY = 0;
   public static final int WHITE = 1;
   public static final int BLACK = 2;
   public static final int WHITE_KING = 3;
   public static final int BLACK_KING = 4;
  
}
