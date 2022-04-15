package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    private static final String[] SIGNS = { "X", "O" };
    private final Scanner input;
    private final int[][] board;

    private int turn = 0;
    private int winner = 0;

    public TicTacToe() {
        input = new Scanner(System.in);
        board = new int[3][3];
        clear();
    }

    public void clear() {
        turn = 0;
        winner = 0;
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = 0;
        }
    }

    public void play() {
        clear();
        int col;
        int row;
        while (winner == 0 && turn < 9) {
            drawBoard();
            System.out.print("\n" + SIGNS[turn % 2] + " turn: ");
            try {
                row = input.nextInt();
                col = input.nextInt();
                row--;
                col--;
            } catch (InputMismatchException e) {
                row = -1;
                col = -1;
                input.next();
                input.reset();
            }

            if (placeTile(row, col)) {
                turn++;
                winner = checkWinner();
            } else
                System.out.println("\nInvalid position!\n");
        }
        drawBoard();
        if (winner > 0)
            System.out.println("\nPlayer " + SIGNS[winner - 1] + " won!\n");
        else
            System.out.println("\nDraw!\n");
    }

    private int checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return board[i][0];
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return board[i][0];
        }

        if (board[1][1] != 0 && (board[0][0] == board[1][1] && board[1][1] == board[2][2])
                || (board[0][2] == board[1][1] && board[1][1] == board[2][0]))
            return board[1][1];

        return 0;
    }

    private void drawBoard() {
        System.out.println("\n      1     2     3   \n   |-----|-----|-----|");
        for (int i = 0; i < 3; i++) {
            System.out.println(
                    " " + (i + 1) + " |  " + getTile(i, 0) + "  |  " + getTile(i, 1) + "  |  " + getTile(i, 2) + "  |");
            System.out.println("   |-----|-----|-----|");
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row > -1 && row < 3 && col > -1 && col < 3;
    }

    private boolean placeTile(int row, int col) {
        if (!isValidPosition(row, col) || board[row][col] != 0)
            return false;
        board[row][col] = turn % 2 + 1;
        return true;
    }

    private String getTile(int row, int col) {
        switch (board[row][col]) {
            case 1:
                return SIGNS[0];
            case 2:
                return SIGNS[1];
            default:
                return " ";
        }
    }
}
