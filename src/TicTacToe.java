import java.util.Random;
import java.util.Scanner;
/**
 * Tic-Tac-Toe: Two-player or with ai console, non-graphics, non-OO version.
 * All variables/methods are declared as static (belong to the class)
 *  in the non-OO version.
 */

class TTTConsoleNonOO2P {
   // Name-constants to represent the seeds and cell contents
   public static final int EMPTY = 0;
   public static final int CROSS = 1;
   public static final int NOUGHT = 2;
 
   // Name-constants to represent the various states of the game
   public static final int PLAYING = 0;
   public static final int DRAW = 1;
   public static final int CROSS_WON = 2;
   public static final int NOUGHT_WON = 3;
   public static Scanner in = new Scanner(System.in); // the input Scanner
   public static Random r = new Random();
   
    // The game board and the game status
   
   public static int boardSize= size();
   public static int friend = company();
   
   public static  int ROWS = boardSize, COLS = boardSize; // number of rows and columns
   public static int[][] board = new int[ROWS][COLS]; // game board in 2D array
                                                      //  containing (EMPTY, CROSS, NOUGHT)
   public static int currentState;  // the current state of the game
                                    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
   public static int currentPlayer; // the current player (CROSS or NOUGHT)
   public static int currntRow, currentCol; // current seed's row and column
 
   
 
   /** The entry main method (the program starts here) */
   public static void main(String[] args) {
      // Initialize the game-board and current status
      initGame();
      
      boolean completed=false;
      int again;
      
      // Play the game once
      do {
         playerMove(currentPlayer); // update currentRow and currentCol
         updateGame(currentPlayer, currntRow, currentCol); // update currentState
         printBoard();
         
         // Print message if game-over
         if (currentState == CROSS_WON) {
            System.out.println("'X' won!");
            completed = true;
         } else if (currentState == NOUGHT_WON) {
            System.out.println("'O' won!  ");
            completed = true;
         } else if (currentState == DRAW) {
            System.out.println("It's a Draw! ");
            completed = true;
         }
         //asiking to Play again 
         if(completed == true) {
        	 do {
        		 System.out.println("Wanna play again ? (0 = no, 1 = yes)");
            	 again = in.nextInt();
        		 if(again == 1) {
            		 friend = company();
            		 main(args);
            	 }else if(again == 0) {
            		 System.out.println("Thanks for playing ! ");
            		 System.exit(0);
            	 }
        	 }while(again != 1 && again != 0);
        	 
         }
         // Switch player
         currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
      } while (currentState == PLAYING); // repeat if not game-over
   }
   /** The Dynamic of Game board **/
   public static int size() {
	   System.out.print("Game Board Size (X*X) : ");
	   return in.nextInt();
	   
   }
   public static int company() {
	   int x;
	   do {
		   System.out.print("With a friend(1) or Computer(2): ");
		   x = in.nextInt();		   
	   }while(x!=1 && x!=2);
	return x;
   }
   
   /** Initialize the game-board contents and the current states */
   public static void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = EMPTY;  // all cells empty
         }
      }
      currentState = PLAYING; // ready to play
      currentPlayer = CROSS;  // cross plays first
   }
 
   /** Player with the "theSeed" makes one move, with input validation.
       Update global variables "currentRow" and "currentCol". */
   public static void playerMove(int theSeed) {
	   int row, col;
      boolean validInput = false;  // for input validation
      do {
         if (theSeed == CROSS) {
            System.out.print("Player 'X', enter your move (row[1-"+ROWS+"] column[1-"+COLS+"]): ");
             row = in.nextInt() - 1;  // array index starts at 0 instead of 1
             col = in.nextInt() - 1;
         } else if(theSeed != CROSS && friend == 2){
        	 do {
        		 row = r.nextInt(ROWS) ;  // array index starts at 0 instead of 1
                 col = r.nextInt(COLS) ;
        	 }while(!(row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY));
             System.out.println("Computer 'O' Enter move (row[1-"+ROWS+"] column[1-"+COLS+"]): "+row+" "+col);             
         }else {
        	 System.out.print("Player 'O', enter your move (row[1-"+ROWS+"] column[1-"+COLS+"]): ");
             row = in.nextInt() - 1;  // array index starts at 0 instead of 1
             col = in.nextInt() - 1;
         }

         if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY) {
            currntRow = row;
            currentCol = col;
            board[currntRow][currentCol] = theSeed;  // update game-board content
            validInput = true;  // input okay, exit loop
         } else {
            System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                  + ") is not valid. Try again...");
         }
      } while (!validInput);  // repeat until input is valid
   }
 
   /** Update the "currentState" after the player with "theSeed" has placed on
       (currentRow, currentCol). */
   public static void updateGame(int theSeed, int currentRow, int currentCol) {
      if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
         currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
      } else if (isDraw()) {  // check for draw
         currentState = DRAW;
      }
      // Otherwise, no change to currentState (still PLAYING).
   }
 
   /** Return true if it is a draw (no more empty cell) */
   // TODO: Shall declare draw if no player can "possibly" win
   public static boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (board[row][col] == EMPTY) {
               return false;  // an empty cell found, not draw, exit
            }
         }
      }
      return true;  // no empty cell, it's a draw
   }
 
   /** Return true if the player with "theSeed" has won after placing at
       (currentRow, currentCol) */
   public static boolean hasWon(int theSeed, int currentRow, int currentCol) {
	  // ROW Winning
	   int count =0;
	   for(int i = 0; i<COLS;i++) {
		   if(board[currentRow][i] == theSeed) {
			   count++;
		   }
	   }
	   if(count == ROWS) {
		   return true;
	   }
	   // COLS winning
	   count = 0;
	   for(int i = 0; i<COLS;i++) {
		   if(board[i][currentCol] == theSeed) {
			   count++;
		   }
	   }
	   if(count == ROWS) {
		   return true;
	   }
	   // Diag winning
	   count = 0;
	   
	   for(int i = 0; i<ROWS;i++) {
		   if(board[i][i] == theSeed) {
			   count++;
		   }
	   }
	   if(count == ROWS) {
		   return true;
	   }
	   count = 0;
	   for(int i = 0; i<ROWS;i++) {
		   if(board[i][COLS - i -1 ] == theSeed) {
			   count++;
		   }
	   }
	   return(count == ROWS);
 
   }
 
   /** Print the game board */
   public static void printBoard() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            printCell(board[row][col]); // print each of the cells
            if (col != COLS - 1) {
               System.out.print("|");   // print vertical partition
            }
         }
         System.out.println();
         if (row != ROWS - 1) {
        	 for(int l =0; l < ROWS ;l++) {
        		 System.out.print("----"); // print horizontal partition
        	 }
        	 System.out.println();
         }
      }
      System.out.println();
   }
 
   /** Print a cell with the specified "content" */
   public static void printCell(int content) {
      switch (content) {
         case EMPTY:  System.out.print(" - "); break;
         case NOUGHT: System.out.print(" O "); break;
         case CROSS:  System.out.print(" X "); break;
      }
   }
 
}