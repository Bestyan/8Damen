/**
  * 
  * 8 Damen Problem
  *
  * @version 1.0 vom 16.01.2015
  * @author Bastian Schattenberg
  */

public class DamenProblem {
  public static final int FREE = 0;
  public static final int CHECK = 1;
  public static final int QUEEN = 4;
  public static final int WIDTH = 8;  
  
  private int[][] queens;
  private int[][] startingSpots;
  private int[][] board;
  
  /**
  * 1 - nicht besetzbar
  * 8 - Dame
  */
  public DamenProblem(){
    board = new int[WIDTH][WIDTH]; // y x
    startingSpots = new int[WIDTH][2]; //[dame][y, x]
    queens = new int[WIDTH][2]; //[dame][y, x]
    for(int i = 0; i < WIDTH; i++){
      deleteQueen(i);
    }
    reset();
  }
  
  public void reset(){
    for(int i = 0; i < WIDTH; i++){
      for(int j = 0; j < WIDTH; j++){
        board[i][j] = FREE;
      }
    }
  }
  
  public boolean isFree(int y, int x){
    return board[y][x] == FREE;
  }
  
  public boolean addQueen(){ 
    int indexOfNewQueen = getNumberOf(QUEEN); 
    if(indexOfNewQueen >= WIDTH){
      return false;
    } 
    
    for(int y = startingSpots[indexOfNewQueen][0]; y < WIDTH; y++){
      for(int x = startingSpots[indexOfNewQueen][1]; x < WIDTH; x++){
        if(board[y][x] == QUEEN){
          break;
        }
        if(isFree(y, x)){
          board[y][x] = QUEEN;
          queens[indexOfNewQueen] = new int[]{y, x};
          updateRows(y, x);
          return true;
        }
      }
    }
    return false;
  }
  
  public void updateRows(int y, int x){
    for(int i = 0; i < WIDTH; i++){
      //Horizontal  
      if(isFree(y, i)){
        board[y][i] = CHECK;
      }
      //Vertikal
      if(isFree(i, x)){
        board[i][x] = CHECK;
      }
      //Diagonal \
      if(x - y + i >= 0 && x - y + i < WIDTH){
        if(isFree(i, x-y+i)){
          board[i][x-y+i] = CHECK;
        }
      }
      //Diagonal /
      if(x + y - i < WIDTH && x + y - i >= 0){
        if(isFree(i, x+y-i)){
          board[i][x+y-i] = CHECK;
        }
      }
    }
  }
  
  public void printBoard(int[][] board){
    for(int x = 0; x < WIDTH; x++){
      System.out.print("+-");
    }  
    System.out.print("+\n");
    for(int y = 0; y < WIDTH; y++){
      for(int x = 0; x < WIDTH; x++){
        System.out.print("|" + (board[y][x] == 1 ? " " : board[y][x]));
      }
      System.out.print("|\n");
      for(int x = 0; x < WIDTH; x++){
        System.out.print("+-");
      } 
      System.out.print("+\n");
    }
  }
  
  public void updateBoard(){
    int number = getNumberOf(QUEEN);  
    int[] queenX = new int[number];
    int[] queenY = new int[number];
    int counter = 0;
    for(int y = 0; y < WIDTH; y++){
      for(int x = 0; x < WIDTH; x++){
        if(board[y][x] == QUEEN){
          queenX[counter] = x;
          queenY[counter] = y;
          counter++;
          break;
        }
      }
      
      if(counter == number){
        break;
      }
    }
    
    reset();
    for(int i = 0; i < number; i++){
      int y = queenY[i];
      int x = queenX[i];  
      board[y][x] = QUEEN;
      updateRows(y, x);
    }
  }
  
  public void set(int y, int x, int value){
    board[y][x] = value;
  }
  
  public void set(int[] c, int value){
    board[c[0]][c[1]] = value;
  }
  
  public boolean removeLastQueen(){
    if(getNumberOf(QUEEN) == 0){
      System.out.println("Nothing left to remove");  
      return false;
    }  
    int deletedQueen = getNumberOf(QUEEN) - 1;
    
    for(int i = deletedQueen + 1; i < WIDTH; i++){
      startingSpots[i] = new int[]{0, 0};
    }
    
    int y = queens[deletedQueen][0], x = queens[deletedQueen][1];
    set(y, x, FREE);
    deleteQueen(deletedQueen);
    if(x == WIDTH - 1){
      if(y + 1 >= WIDTH){
        startingSpots[deletedQueen] = new int[]{y + 1, 0};
      }else{
        removeLastQueen();
      }  
    } else{
      startingSpots[deletedQueen] = new int[]{y, x + 1};
    }
    
    updateBoard();
    return true;
  }
  
  public void deleteQueen(int index){
    queens[index] = new int[]{-1, -1};
  }
  
  public boolean is(int y, int x, int val){
    return get(y, x) == val;
  }
  
  public int getNumberOf(int value){
    int counter = 0;  
    for(int i = 0; i < WIDTH; i++){
      for(int j = 0; j < WIDTH; j++){
        if(board[i][j] == value){
          counter++;
        }
      }
    }
    return counter; 
  }
  
  public int get(int y, int x){
    return board[y][x];
  }
  
  public int get(int[] c){
    return board[c[0]][c[1]];
  }
  
  /**
  * Custom starting point for the first Queen
  */
  public int[][] fillBoardStarting(int y, int x){
    clearArrays();  
    startingSpots[0] = new int[]{y, x};
    return fillBoard();
  }
  
  public void clearArrays(){
    for(int i = 0; i < WIDTH; i++){
      deleteQueen(i);
      startingSpots[i] = new int[]{0, 0};
    } 
    reset();
  }
  
  public int[][] fillBoard(){
    boolean successfulTry = true;  
    while(getNumberOf(QUEEN) < WIDTH){
      boolean successful = addQueen();
      if(!successful){
        if(!removeLastQueen()){
          successfulTry = false;  
          break; //no possible solution here
        }
      }
    }
    if(successfulTry){
      return board;
    }
    return null;
  }
  
  public static int[][] rotateBoardBy(int[][] b, int times){
    int[][] newB = new int[WIDTH][WIDTH];
    for(int y = 0; y < WIDTH; y++){
      for(int x = 0; x < WIDTH; x++){
        int newX = y, newY = WIDTH - x - 1;
        newB[newY][newX] = b[y][x];
      }
    }
    if(times % 4 > 1){
      return rotateBoardBy(newB, times - 1);
    }
    return newB;
  }
  
  /**
  * type 1 = horizontal achsen-gespiegelt
  *      2 = vertikal achsen-gespiegelt
  *      andere = Punktspiegelung
  */
  public static int[][] mirrorBoard(int[][] b, int type){
    if(type != 1 && type != 2){ //doppelte Achsenspiegelung = Punktspiegelung
      return mirrorBoard(mirrorBoard(b, 1), 2);
    }  
    int[][] newB = new int[WIDTH][WIDTH];  
    for(int y = 0; y < WIDTH; y++){
      for(int x = 0; x < WIDTH; x++){
        int newX = 0, newY = 0;  
        if(type == 1){
          newX = WIDTH - x - 1;
          newY = y;
        } else if(type == 2){
          newX = x;
          newY = WIDTH - y - 1;
        }
        newB[newY][newX] = b[y][x];
      }
    }
    return newB;
  }
  
  public static boolean isSame(int[][] b1, int[][] b2){ //TODO anscheinend teilweise fälschlicherweise true
    if(equals(b1, b2)){ //direkte Gleichheit
      return true;
    }
    
    for(int i = 1; i < 4; i++){ 
      if(equals(b1, rotateBoardBy(b2, i)) || equals(b1, mirrorBoard(b2, i))){ //Rotationen + Spiegelungen
        return true;
      }   
    }
    return false;
  }
  
  
  public static boolean equals(int[][] b1, int[][] b2){
    for(int y = 0; y < WIDTH; y++){
      for(int x = 0; x < WIDTH; x++){
        if(b1[y][x] != b2[y][x]){
          return false;
        }
      }
    }
    return true;
  }  
}