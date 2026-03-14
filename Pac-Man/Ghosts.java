import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class Ghosts {
    private int ghost_dx; 
    private int ghost_dy;
    private int ghost_x;
    private int ghost_y;
    private int blockSize = 20;

    private Image ghost, ghost1, ghost2;

    private int[][] maze;

    public Ghosts(int[][] maze) {
        this.maze = maze;      
        ghost_x = 260;
        ghost_y = 200;
        ghost_dx = 1;
        ghost_dy = 0;
    }

    public void loadGhosts() {
        try {
            ghost = ImageIO.read(new File("images/ghost.png"));
            ghost1 = ImageIO.read(new File("images/ghost1.png"));
            ghost2 = ImageIO.read(new File("images/ghost2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawGhost(Graphics2D g2d) {
        g2d.drawImage(ghost, ghost_x, ghost_y, 18, 18, null);
    }
    public void drawGhost1(Graphics2D g2d) {
        g2d.drawImage(ghost1, ghost_x, ghost_y, 18, 18, null);
    }
    public void drawGhost2(Graphics2D g2d) {
        g2d.drawImage(ghost2, ghost_x, ghost_y, 18, 18, null);
    }

    public void resetPosition() {
        ghost_x = 260;
        ghost_y = 200;
        ghost_dx = 1;
        ghost_dy = 0;
    }

    public int getX() {
        return ghost_x;
    }

    public int getY() {
        return ghost_y;
    }

    public void moveGhosts() {

        int nextGhostX = ghost_x + ghost_dx * blockSize;
        int nextGhostY = ghost_y + ghost_dy * blockSize;
        
        int gridGhostX = nextGhostX / blockSize;
        int gridGhostY = nextGhostY / blockSize;

        int nextGhostX2 = ghost_x + 2 * ghost_dx * blockSize;
        int nextGhostY2 = ghost_y + 2 * ghost_dy * blockSize;

        int gridGhostX2 = nextGhostX2 / blockSize;
        int gridGhostY2 = nextGhostY2 / blockSize;
        
        ArrayList<Integer> availableDirections = new ArrayList<>();

        if (maze[gridGhostY - 1][gridGhostX] != 2 && maze[gridGhostY - 1][gridGhostX] != 3) {
            availableDirections.add(0);
        }
        
        if (maze[gridGhostY + 1][gridGhostX] != 2 && maze[gridGhostY + 1][gridGhostX] != 3) {
            availableDirections.add(1);
        }
             
        if (maze[gridGhostY][gridGhostX - 1] != 2 && maze[gridGhostY][gridGhostX - 1] != 3) {
            availableDirections.add(2);
        }
        
        if (maze[gridGhostY][gridGhostX + 1] != 2 && maze[gridGhostY][gridGhostX + 1] != 3) {
            availableDirections.add(3);
        }
            
        if (maze[gridGhostY2][gridGhostX2] == 2 || maze[gridGhostY2][gridGhostX2] == 3) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(availableDirections.size());
            int direction = availableDirections.get(randomIndex);
        
            switch (direction) {
                case 0:
                    ghost_dx = 0;
                    ghost_dy = -1;
                    break;
                case 1:
                    ghost_dx = 0;
                    ghost_dy = 1;
                    break;
                case 2:
                    ghost_dx = -1;
                    ghost_dy = 0;
                    break;
                case 3:
                    ghost_dx = 1;
                    ghost_dy = 0;
                    break;
                default:
                    break;
            }
            
            availableDirections.clear();
        }
        ghost_x = nextGhostX;
        ghost_y = nextGhostY;

    }
}
