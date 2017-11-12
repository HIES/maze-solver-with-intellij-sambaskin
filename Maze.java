import sun.plugin2.util.ColorUtil;

import java.awt.*;

public class Maze {
    private Cell[][] board;
    private final int DELAY = 200;


    private int prevCol = 0;    //Previous column modified for reverting color
    private int prevRow = 0;    //Previous row modified for reverting color

    Color cellMarker = new Color(250, 80, 80);  //New color to mark cells

    public Maze(int rows, int cols, int[][] map) {
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows);
        board = new Cell[rows][cols];
        //grab number of rows to invert grid system with StdDraw (lower-left, instead of top-left)
        int height = board.length - 1;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                board[r][c] = map[r][c] == 1 ? new Cell(c, height - r, 0.5, false) : new Cell(c, height - r, 0.5, true);
            }
    }


    public void draw() {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++) {
                Cell cell = board[r][c];
                StdDraw.setPenColor(cell.getColor());
                StdDraw.filledSquare(cell.getX(), cell.getY(), cell.getRadius());
            }
        StdDraw.show();
    }

    public boolean findPath(int row, int col) {
        boolean isFinished = false;
        if (isValid(row, col)) {
            board[row][col].visitCell();
            board[prevRow][prevCol].setColor(Color.RED);
            board[row][col].setColor(cellMarker);
            prevRow = row;
            prevCol = col;
            this.draw();
            StdDraw.pause(DELAY);

            if (isExit(row, col)) {

                board[row][col].becomePath();
                isFinished = true;

            }

            if(!isFinished) {


                if (!isFinished) {

                    isFinished = findPath(row, col + 1);
                    System.out.println("3");
                }
                if (!isFinished) {

                    isFinished = findPath(row, col - 1);
                    System.out.println("4");
                }
                if (!isFinished) {

                    isFinished = findPath(row + 1, col);
                    System.out.println("1");
                }

            if (!isFinished) {

                isFinished = findPath(row - 1, col);
                System.out.println("2");
            }





            if(isFinished) {
                board[row][col].becomePath();
            }



                }

        }

        return isFinished;
    }


    private boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < board.length && col < board[0].length && board[row][col].isWall() == false
                && !board[row][col].isVisited())
            return true;

        return false;
    }

    private boolean isExit(int row, int col) {
        if (row == board.length - 1 && col == board[0].length - 1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        int[][] maze = {{1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 0, 1, 1, 0, 0},
                {0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 0, 0, 0, 0, 0, 1, 1, 1},
                {0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
                {0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1}};
        Maze geerid = new Maze(maze.length, maze[0].length, maze);
        geerid.draw();
        geerid.findPath(0, 0);
        geerid.draw();
    }
}
