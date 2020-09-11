import java.util.Random;
import java.util.Scanner;

public class GameHandler {

    char[][] board = new char[3][3];
    char X ='X';
    char O = 'O';
    char EMPTY = '-';
    Scanner sc = new Scanner(System.in);
    int emptySpots;


    public GameHandler(){
        for(int i = 0; i<board.length; i++){
            for(int j=0; j<board.length;j++){
                board[i][j] = EMPTY;
            }
        }
        emptySpots = 9;
        Game();
    }

    public char Game(){
        char winner = X;
        boolean over = false;
        boolean turnX = true;
        while(!over){
            if(turnX) {
                System.out.println("It's your turn, where do you want to place X");
                System.out.println(toString());

                int inputX = sc.nextInt();
                int inputY = sc.nextInt();

                while (inputX>2 || inputY>2 || board[inputY][inputX] != EMPTY) {
                    if(inputX>2 || inputY>2)
                        System.out.println("ERROR! there is no place to put X in ( " + inputX + " , " + inputY + " )");
                    else
                        System.out.println("ERROR! The place is already filled with " + board[inputY][inputX] + "please insert again");

                    inputX = sc.nextInt();
                    inputY = sc.nextInt();
                }
                board[inputY][inputX] = X;
                turnX = false;
                emptySpots--;
                System.out.println("YOUR PLACEMENT: ");
                System.out.println(toString());
                System.out.println("-------------------------------------------------");
            }else{
                boolean placed = false;
                int[] posPlace = huristic();
                int y = posPlace[0],x=posPlace[1];
                if(y==-1) {
                    while (!placed) {
                        x = (int) (Math.random() * 3);
                        y = (int) (Math.random() * 3);
                        if (board[y][x] == EMPTY) {
                            board[y][x] = O;
                            placed = true;
                        }
                    }
                }else{
                    board[y][x] = O;
                    System.out.println("used huristeic");
                }
                emptySpots--;
                System.out.println();
                turnX = true;
                System.out.println("O player placed is O in (" + x + " , " + y + " )" + "\n");
                System.out.println(toString());
                System.out.println("-------------------------------------------------" + "\n");
            }

            char state = gameOver();
            if(state == 'f'){
                over = true;
                System.out.println("GAME OVER! Board is full");
            }else if(state != EMPTY){
                over = true;
                System.out.println(toString());
                System.out.println("GAME OVER! " + state + " is the winner!");
            }
        }


        return winner;
    }

    public char gameOver(){
        char ans = EMPTY;
        if(emptySpots<4) {
            if (board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != EMPTY || board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != EMPTY || board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTY) //Checks top row and left column and left to right slant
                ans = board[0][0];
            else if (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][1] != EMPTY || board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[1][1] != EMPTY) //Check middle row and middle column
                ans = board[1][1];
            else if (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != EMPTY || board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[2][0] != EMPTY || board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != EMPTY) //Check bottom row and right column and right to left slant
                ans = board[2][0];
            else if (emptySpots == 0)
                ans = 'f';
        }
        return ans;
    }

    public String toString(){
        String ans = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j]==EMPTY)
                    ans += "   ";
                else
                    ans += " " + board[i][j] + " ";
                if(j!=2)
                    ans += " |";
            }
            if(i != board.length-1)
                ans += "\n" + " ____________" + "\n";
        }
        ans+="\n";
        return ans;
    }

    public int[] huristic(){
        int[] ans = {-1,-1};
        char checking = O;
        boolean found = false;
        if(emptySpots<=6) {
            for (int i = 0; i < 2 && !found; i++) {
                if (!found && (board[0][1] == checking && board[0][1] == board[0][2] || board[1][0] == checking && board[1][0] == board[2][0] || board[1][1] == checking && board[1][1] == board[2][2])) {
                    ans[0] = 0;
                    ans[1] = 0;
                    if(board[ans[0]][ans[1]] ==EMPTY)
                        found = true;
                } if (!found && (board[1][1] == checking && board[1][1] == board[2][1])) {
                    ans[0] = 0;
                    ans[1] = 1;
                    if(board[ans[0]][ans[1]] ==EMPTY)
                        found = true;
                } if (!found && (board[0][1] == checking && board[0][0] == board[0][1] || board[1][1] == checking && board[2][0] == board[1][1] || board[1][2] == checking && board[1][2] == board[2][2])) {
                    ans[0] = 0;
                    ans[1] = 2;
                    if(board[ans[0]][ans[1]] ==EMPTY)
                        found = true;
                } if (!found && (board[1][1] == checking && board[1][1] == board[1][2])) {
                    ans[0] = 1;
                    ans[1] = 0;
                    if(board[ans[0]][ans[1]] ==EMPTY)
                        found = true;
                } if (!found && (board[1][1] == checking && board[1][0] == board[1][1])) {
                    ans[0] = 1;
                    ans[1] = 2;
                    if(board[ans[0]][ans[1]] ==EMPTY)
                        found = true;
                } if (!found && (board[1][0] == checking && board[0][0] == board[1][0] || board[1][1] == checking && board[1][1] == board[0][2] || board[2][1] == checking && board[2][1] == board[2][2])) {
                    ans[0] = 2;
                    ans[1] = 0;
                    if(board[ans[0]][ans[1]] ==EMPTY)
                        found = true;
                } if (!found && (board[1][1] == checking && board[0][1] == board[1][1])) {
                    ans[0] = 2;
                    ans[1] = 1;
                    if(board[ans[0]][ans[1]] ==EMPTY)
                        found = true;
                } if (!found && (board[2][1] == checking && board[2][0] == board[2][1] || board[1][1] == checking && board[0][0] == board[1][1] || board[1][2] == checking && board[0][2] == board[1][2])) {
                    ans[0] = 2;
                    ans[1] = 2;
                    if(board[ans[0]][ans[1]] ==EMPTY)
                        found = true;
                }

                checking = X;
            }
        }
        return ans;
    }
}
