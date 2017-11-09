public class Maze
{
    private Cell[][] board;
    private final int DELAY = 200;

    public Maze(int rows, int cols, int[][] map){
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows);
        board = new Cell[rows][cols];
        //grab number of rows to invert grid system with StdDraw (lower-left, instead of top-left)
        int height = board.length - 1;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                board[r][c] = map[r][c] == 1 ? new Cell(c , height - r, 0.5, false) : new Cell(c, height - r, 0.5, true);
            }
    }

    public void draw()
    {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++){
                Cell cell = board[r][c];
                StdDraw.setPenColor(cell.getColor());
                StdDraw.filledSquare(cell.getX(), cell.getY(), cell.getRadius());
            }
            StdDraw.show();
    }

    public boolean findPath(int row, int col)
    {
        boolean isFinished = false;

        return isFinished;
    }

    private boolean isValid(int row, int col)
    {
        if(row>=0 && col>= 0 && board[row][col].isWall()==false && row < board.length && col < board[0].length)
            return true;

        return false;
    }

    private boolean isExit(int row, int col)
    {
        if(row==board.length-1 && col == board[0].length-1)
        {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        int[][] maze = {{1,1,0,0,0,0,0,0,0,0},
                        {0,1,1,1,1,0,1,1,1,0},
                        {0,1,1,1,1,0,1,1,0,0},
                        {0,1,0,1,1,1,1,1,1,0},
                        {0,1,0,0,0,0,0,1,1,0},
                        {0,1,1,0,1,1,1,1,1,0},
                        {0,0,1,0,0,1,0,1,0,0},
                        {0,1,1,0,1,1,0,1,1,0},
                        {0,1,1,0,1,1,0,1,1,0},
                        {0,0,0,0,0,0,0,0,1,1}};
        Maze geerid = new Maze(maze.length, maze[0].length, maze);
        geerid.draw();
        geerid.findPath(0, 0);
        geerid.draw();
    }
}
