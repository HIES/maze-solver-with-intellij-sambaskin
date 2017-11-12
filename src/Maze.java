import sun.plugin2.util.ColorUtil;
//In main Args: Enter maze, mazeGen, or mazeGenNatural
import java.awt.*;

public class Maze {
    private Cell[][] board;
    private final int DELAY = 2;


    private int prevCol = 0;    //Previous column modified for reverting color
    private int prevRow = 0;    //Previous row modified for reverting color

    Color cellMarker = new Color(150, 0, 90);  //New color to mark cells

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

    public static int[][] generateMaze(int squareLength, double p) {
        int[][] newMaze = new int[squareLength][squareLength];

        for (int r = 0; r < newMaze.length; r++) {
            for (int c = 0; c < newMaze.length; c++) {
                double rand = Math.random();
                if (r == newMaze.length - 1 && c == newMaze.length - 1 || r == newMaze.length - 1 && c == newMaze.length - 2
                        || r == 0 && c == 0 || r == 0 && c == 1) //Checks if cell is start or exit
                {
                    newMaze[r][c] = 1;
                } else if (r == 0 || c == 0 || r == newMaze.length - 1 || c == newMaze.length - 1) //Check if cell is border
                {
                    newMaze[r][c] = 0;
                } else {
                    if (rand > p)
                        newMaze[r][c] = 1;
                    else
                        newMaze[r][c] = 0;
                }
            }
        }
        return newMaze;
    }


    public static int[][] generateMazeNatural(int squareLength, double p, int strands) {
        int[][] newMaze = new int[squareLength][squareLength];
        {
            int direction = 0; //0-left 1-up 2-right 3-down
            for (int r = 0; r < newMaze.length; r++) {      //Loop fills in borders with walls and the rest with open
                for (int c = 0; c < newMaze.length; c++) {
                    if (r == 0 || c == 0 || r == newMaze.length - 1 || c == newMaze.length - 1) {
                        newMaze[r][c] = 0;
                    } else {
                        newMaze[r][c] = 1;
                    }
                    if (r == newMaze.length - 1 && c == newMaze.length - 1 || r == newMaze.length - 1 && c == newMaze.length - 2
                            || r == 0 && c == 0 || r == 0 && c == 1) //Checks if cell is start or exit
                    {
                        newMaze[r][c] = 1;
                    }

                }
            }

            while (strands > 0) {
                strands--;

                int timer = (int) (Math.random() * squareLength*p);
                int r = (int) (Math.random() * squareLength);
                int c = (int) (Math.random() * squareLength);
                while (timer > 0) {
                    double changeDir = Math.random();
                    if (changeDir > .75) {
                        direction = (int) (4 * Math.random());
                    }
                    //random start. Moves in direction and changes. random length
                    double rand = Math.random();
                    if (r > 0 && c > 0 && r < squareLength - 1 && c < squareLength - 1) {
                        newMaze[r][c] = 0;
                        if (direction == 0)
                            r++;
                        if (direction == 1)
                            r--;
                        if (direction == 2)
                            c++;
                        if (direction == 3)
                            c--;

                    }
                    timer--;
                }
            }

        }

        return newMaze;
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

            if (!isFinished) {


                if (!isFinished) {

                    isFinished = findPath(row, col + 1);
                    System.out.println("3");
                }
                if (!isFinished) {

                    isFinished = findPath(row + 1, col);
                    System.out.println("1");
                }
                if (!isFinished) {

                    isFinished = findPath(row, col - 1);
                    System.out.println("4");
                }


                if (!isFinished) {

                    isFinished = findPath(row - 1, col);
                    System.out.println("2");
                }


                if (isFinished) {
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
//        int[][] maze = {{1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 1, 0, 1, 1, 1, 0},
//                {0, 1, 1, 1, 1, 0, 1, 1, 0, 0},
//                {0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
//                {0, 1, 0, 0, 0, 0, 0, 1, 1, 1},
//                {0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
//                {0, 0, 1, 0, 0, 1, 0, 1, 0, 0},
//                {0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
//                {0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1}};

        //int[][] maze = generateMaze(30, .23);           //RANDOMLY GENERATE MAZE (comment out to run hardcoded)
        int[][] maze = generateMazeNatural(500, 0.3,4000); //MAZE LOOKS MORE NATURAL (comment out to run pure random)
        //P changes how long each strand is, strands determines how many strands there are
        Maze geerid = new Maze(maze.length, maze[0].length, maze); //Switch
        geerid.draw();
        geerid.findPath(0, 0);
        geerid.draw();
    }
}
