
import java.util.Random;
import java.util.Scanner;
//Main class
public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static int[][] board = new int[3][3];
    private static final Random random = new Random();
//Main method
    public static void main(String[] args) {
        initiateGame();
        Scanner input = new Scanner(System.in);
        System.out.print("Do you want to restart? (yes/no): ");
        String restart = input.nextLine();
        if (restart.equalsIgnoreCase("yes") || restart.equalsIgnoreCase("y")){
            System.out.println("\n\n");
            initiateGame();
        } else {
            System.out.println("\n\nThank you for playing the game.");
        }
    }

    /*
    This method initiates the game with the user choosing if he wants to start or the computer to start.
     */
    public static void initiateGame() {
        board = new int[3][3];
        printBoard();

        System.out.println("Who do you want to start the game?");
        System.out.println("1. User");
        System.out.println("2. Computer");
        int choice = scan.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("Incorrect input! Please enter:");
            System.out.println("1. User");
            System.out.println("2. Computer");
            choice = scan.nextInt();
        }

        startGame(choice);
    }
/*
This method prints the Gameboard
 */
    public static void printBoard() {
        char symbol;
        System.out.println("| --- | --- | --- |");
        for (int[] row : board) {
            for (int col : row) {
                if (col == 1) {
                    symbol = 'X';
                } else if (col == 2) {
                    symbol = 'O';
                } else {
                    symbol = ' ';
                }
                System.out.print("|  " + symbol + "  ");
            }
            System.out.println("|");
            System.out.println("| --- | --- | --- |");
        }
    }
    /*
    This method starts the game and all the moves

     */
    public static void startGame(int choice) {
        int check;
        if (choice == 1) {
            System.out.println("You're 'X'");
        } else {
            System.out.println("You're 'O'");
        }
        while (true) {
            if (choice == 1) {
                userTurn(choice);
                check = checkWin();
                printBoard();
                if (check != -1) {
                    break;
                }
                cpuTurn(choice);
            } else {
                cpuTurn(choice);
                check = checkWin();
                printBoard();
                if (check != -1) {
                    break;
                }
                userTurn(choice);
            }
            check = checkWin();
            System.out.println();
            printBoard();
            if (check != -1) {
                break;
            }
        }
        if (check == 0) {
            //draw
            System.out.println("\nIt's a draw");
        } else if (check == choice) {
            //user wins
            System.out.println("\nYou win");
        } else {
            // cpu wins
            System.out.println("\nCPU wins");
        }
    }
/*
This method shows the users turn when the users makes an input on where he wants to place the symbol.
 */
    public static void userTurn(int choice) {
        System.out.print("Enter your placement (1-9): ");
        int playerPos = scan.nextInt();


        while (playerPos < 1 || playerPos > 9){
            System.out.print("Invalid grid. Enter a correct placement (1-9): ");
            playerPos = scan.nextInt();
        }

        switch (playerPos) {
            case 1 -> {
                if (isOccupied(0, 0)) {
                    System.out.println("Invalid position");
                    userTurn(choice);
                } else {
                    board[0][0] = choice;
                }
            }
            case 2 -> {
                if (isOccupied(0, 1)) {
                    System.out.println("Invalid position");
                    userTurn(choice);
                } else {
                    board[0][1] = choice;
                }
            }
            case 3 -> {
                if (isOccupied(0, 2)) {
                    System.out.println("Invalid position");
                    userTurn(choice);
                } else {
                    board[0][2] = choice;
                }
            }
            case 4 -> {
                if (isOccupied(1, 0)) {
                    System.out.println("Invalid position");
                    userTurn(choice);
                } else {
                    board[1][0] = choice;
                }
            }
            case 5 -> {
                if (isOccupied(1, 1)) {
                    System.out.println("Invalid position");
                    userTurn(choice);
                } else {
                    board[1][1] = choice;
                }
            }
            case 6 -> {
                if (isOccupied(1, 2)) {
                    System.out.println("Invalid position");
                    userTurn(choice);
                } else {
                    board[1][2] = choice;
                }
            }
            case 7 -> {
                if (isOccupied(2, 0)) {
                    System.out.println("Invalid position");
                    userTurn(choice);
                } else {
                    board[2][0] = choice;
                }
            }
            case 8 -> {
                if (isOccupied(2, 1)) {
                    System.out.println("Invalid position");
                    userTurn(choice);
                } else {
                    board[2][1] = choice;
                }
            }
            default -> {
                if (isOccupied(2, 2)) {
                    System.out.println("Invalid position");
                    userTurn(choice);
                } else {
                    board[2][2] = choice;
                }
            }
        }
    }
/*
this method checks if the cell is occupied.
 */
    public static boolean isOccupied(int i, int j) {
        return board[i][j] == 1 || board[i][j] == 2;
    }
    /*
    This method shows the CPus turn when the Cpu makes an input on where it wants to place the symbol.
     */
    public static void cpuTurn(int choice) {
        // 5 minute pause
        second5Gap();

        if (choice == 1) {
            choice = 2;
        } else {
            choice = 1;
        }

        int row, cols;
        if (isFirstTurn(choice)){
            if (isOccupied(1,1)) {
                row = random.nextInt(2) * 2;
                cols = random.nextInt(2) * 2;
                board[row][cols] = choice;
            } else {
                board[1][1] = choice;
            }
        } else if (findWinningMove(choice)[0] != -1) {
            row = findWinningMove(choice)[0];
            cols = findWinningMove(choice)[1];
            board[row][cols] = choice;
        } else if (findBlockingMove(choice)[0] != -1) {
            row = findBlockingMove(choice)[0];
            cols = findBlockingMove(choice)[1];
            board[row][cols] = choice;
        } else {
            while (true) {
                row = random.nextInt(3);
                cols = random.nextInt(3);
                if (!isOccupied(row, cols)) {
                    board[row][cols] = choice;
                    return;
                }
            }
        }
    }
//Show the board and wait for 5 seconds fot the CPu to make a turn to make ot seem like the CPi is thinking
    public static void second5Gap() {
        System.out.print("Loading");
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000); // 1000 milliseconds = 1 second
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            System.out.println("Thread sleep interrupted.");
            e.printStackTrace();
        }
        System.out.println("\nDone loading.\n");
    }

    public static boolean isFirstTurn(int choice) {
        for (int[] rows : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (rows[j] == choice) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] findWinningMove(int choice) {
        // checking rows and columns
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == choice && board[i][1] == choice && !isOccupied(i, 2)) {
                return new int[]{i, 2};
            }
            if (board[i][0] == choice && board[i][2] == choice && !isOccupied(i, 1)) {
                return new int[]{i, 1};
            }
            if (board[i][1] == choice && board[i][2] == choice && !isOccupied(i, 0)) {
                return new int[]{i, 0};
            }
            if (board[0][i] == choice && board[1][i] == choice && !isOccupied(2, i)) {
                return new int[]{2, i};
            }
            if (board[0][i] == choice && board[2][i] == choice && !isOccupied(1, i)) {
                return new int[]{1, i};
            }
            if (board[1][i] == choice && board[2][i] == choice && !isOccupied(0, i)) {
                return new int[]{0, i};
            }
        }

        // Check diagonals
        if (board[0][0] == choice && board[1][1] == choice && !isOccupied(2, 2)) {
            return new int[]{2, 2};
        }
        if (board[0][0] == choice && board[2][2] == choice && !isOccupied(1, 1)) {
            return new int[]{1, 1};
        }
        if (board[1][1] == choice && board[2][2] == choice && !isOccupied(0, 0)) {
            return new int[]{0, 0};
        }
        if (board[0][2] == choice && board[1][1] == choice && !isOccupied(2, 0)) {
            return new int[]{2, 0};
        }
        if (board[0][2] == choice && board[2][0] == choice && !isOccupied(1, 1)) {
            return new int[]{1, 1};
        }
        if (board[1][1] == choice && board[2][0] == choice && !isOccupied(0, 2)) {
            return new int[]{0, 2};
        }

        return new int[]{-1, -1};
    }

    public static int[] findBlockingMove(int choice) {
        if (choice == 1) {
            choice = 2;
        } else {
            choice = 1;
        }

        return findWinningMove(choice);
    }

    public static int checkWin() {
        boolean checkDraw = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!isOccupied(i, j)) {
                    checkDraw = false;
                    break;
                }
            }
        }
        if (checkDraw) {
            return 0;
        }

        if (checkWin(1)){
            return 1;
        }

        if (checkWin(2)) {
            return 2;
        }

        return -1;
    }
//This method checkwin by rows and columns
    public static boolean checkWin(int choice) {
        // check rows
        for (int[] rows : board) {
            if (rows[0] == choice && rows[1] == choice && rows[2] == choice) {
                return true;
            }
        }

        // check columns
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i] == choice && board[1][i] == choice && board[2][i] == choice) {
                return true;
            }
        }

        // check diagonals
        if (board[1][1] == choice) {
            if (board[0][0] == choice && board[2][2] == choice) {
                return true;
            }
            return board[0][2] == choice && board[2][0] == choice;
        }

        // no win
        return false;
    }
}
