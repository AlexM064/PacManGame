import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {
    private final int blockSize = 20;
    private final int blockX = 33;
    private final int blockY = 21;

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private StartPanel startPanel;
    private GamePanel gamePanel;

    private Image icon;
    
    private int lives = 3;
    private int score;
    private int counter;

    private int[][] maze = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 0, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 2,
                    2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1,
                    2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1,
                    2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 1, 3, 1, 1, 3,
                    1, 2, 2, 2, 2, 1, 1, 2, 1, 2, 2, 2, 2, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 3,
                    1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 2, 1, 1, 1, 2, 1, 3, 1, 2, 1, 3, 1, 1, 3,
                    1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2},
            {2, 1, 1, 1, 2, 1, 2, 1, 3, 1, 2, 1, 3, 1, 1, 3,
                    1, 2, 1, 1, 2, 1, 1, 1, 2, 0, 2, 1, 2, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 1, 1, 1, 3, 1, 2, 1, 3, 1, 1, 3,
                    1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 1, 3, 1, 1, 3,
                    1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 2, 2, 1, 2, 2, 1, 3, 1, 2, 1, 3, 3, 3, 3,
                    1, 2, 1, 1, 2, 2, 1, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2},
            {2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2},
            {2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2,
                    2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1,
                    1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 2},
            {2, 1, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 2, 2, 2,
                    2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
    };

    private int[][] backupMaze = new int[maze.length][maze[0].length]; {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                backupMaze[i][j] = maze[i][j];
            }
        }
    }

    Timer timer;
    Pacman pacman;
    Ghosts ghost;
    Ghosts ghost1; 
    Ghosts ghost2;

    public Game() {
        pacman = new Pacman(maze, score);
        ghost = new Ghosts(maze);
        ghost1 = new Ghosts(maze);
        ghost2 = new Ghosts(maze);
        timer = new Timer(150, this); 

        frame = new JFrame("Pac-Man Game");
        icon = new ImageIcon("gameicon.png").getImage();  
        frame.setIconImage(icon);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(blockX * blockSize + 16, blockY * blockSize + 38);    

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        gamePanel = new GamePanel(frame, cardLayout, cardPanel, timer, maze, backupMaze, 
            blockSize, blockX, blockY, lives, counter, pacman, ghost, ghost1, ghost2);
        startPanel = new StartPanel(cardLayout, cardPanel, timer);

        frame.add(cardPanel);

        cardLayout.show(cardPanel, "start");
        frame.setVisible(true);
        frame.addKeyListener(new TAdapter());
        loadImages();

        gamePanel.setLayout(new BorderLayout());

        frame.setFocusable(true);
        frame.requestFocus();
    }

    private void loadImages() {
        pacman.loadPacman();
        ghost.loadGhosts();
        ghost1.loadGhosts();
        ghost2.loadGhosts();
    }
    
    private boolean ghostCollision() {
        int pacman_x = pacman.getX();
        int pacman_y = pacman.getY();
        int ghost_x = ghost.getX();
        int ghost_y = ghost.getY();
        int ghost1_x = ghost1.getX();
        int ghost1_y = ghost1.getY();
        int ghost2_x = ghost2.getX();
        int ghost2_y = ghost2.getY();

        return ((pacman_x >= ghost_x && pacman_x <= ghost_x + 20)
                && (pacman_y >= ghost_y && pacman_y <= ghost_y + 20)) 
                || ((pacman_x >= ghost1_x && pacman_x <= ghost1_x + 20)
                && (pacman_y >= ghost1_y && pacman_y <= ghost1_y + 20)) 
                || ((pacman_x >= ghost2_x && pacman_x <= ghost2_x + 20)
                && (pacman_y >= ghost2_y && pacman_y <= ghost2_y + 20));
    }

    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                pacman.changeDirection(-1, 0);
            } else if (key == KeyEvent.VK_RIGHT) {
                pacman.changeDirection(1, 0);
            } else if (key == KeyEvent.VK_UP) {
                pacman.changeDirection(0, -1);
            } else if (key == KeyEvent.VK_DOWN) {
                pacman.changeDirection(0, 1);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.movePacMan();
        if (ghostCollision()) {
            gamePanel.minusLives();
            pacman.resetPosition();
        }
        ghost.moveGhosts();
        ghost1.moveGhosts();
        ghost2.moveGhosts();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Game();
        });
    }
}