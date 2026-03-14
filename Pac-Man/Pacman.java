import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pacman {
    private int pacman_x; 
    private int pacman_y;
    private int pacmand_x;
    private int pacmand_y;
    private int blockSize = 20;

    private Image up;
    private Image down;
    private Image left;
    private Image right;

    private int[][] maze;
    public int score = 0;

    public Pacman(int[][] maze, int score) {
        this.maze = maze;
        this.score = score;
        pacman_x = blockSize;
        pacman_y = 2 * blockSize;
        pacmand_x = 0;
        pacmand_y = 0;
    }

    public void loadPacman() {
        try {
            right = ImageIO.read(new File("images/right.gif"));
            down = ImageIO.read(new File("images/down.gif"));
            up = ImageIO.read(new File("images/up.gif"));
            left = ImageIO.read(new File("images/left.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawPacman(Graphics2D g2d) {
        if (pacmand_x == -1) {
            g2d.drawImage(left, pacman_x, pacman_y, 18, 18, null);
        } else if (pacmand_x == 1) {
            g2d.drawImage(right, pacman_x, pacman_y, 18, 18, null);
        } else if (pacmand_y == -1) {
            g2d.drawImage(up, pacman_x, pacman_y, 18, 18, null);
        } else {
            g2d.drawImage(down, pacman_x, pacman_y, 18, 18, null);
        }
    }

    public void resetPosition() {
        pacman_x = blockSize;
        pacman_y = 2 * blockSize;
        pacmand_x = 0;
        pacmand_y = 0;
    }

    public void changeDirection(int x, int y) {
        pacmand_x = x;
        pacmand_y = y;
    }

    public int getX() {
        return pacman_x;
    }

    public int getY() {
        return pacman_y;
    }

    public void movePacMan() {
        int nextX = pacman_x + pacmand_x * blockSize;
        int nextY = pacman_y + pacmand_y * blockSize;

        int gridX = nextX / blockSize;
        int gridY = nextY / blockSize;
            
        if (maze[gridY][gridX] != 2 && maze[gridY][gridX] != 3) {
                
            if (maze[gridY][gridX] == 1) {
                score++;
            }
            maze[gridY][gridX] = 0;
            pacman_x = nextX;
            pacman_y = nextY;
        }
    }
}
