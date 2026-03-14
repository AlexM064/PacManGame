import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel{

    private int blockSize = 20;
    private int mazeLength;
    private int mazeWidth;
    private Pacman pacman;
    private Ghosts ghost;
    private Ghosts ghost1;
    private Ghosts ghost2;
    private JFrame frame;
    private Timer timer;
    private int lives;
    private int[][] maze;
    private int[][] backupMaze;
    public int counter = 0;

    private Image live;
    private Image gameOver;
    private Image win;
    
    public GamePanel (JFrame frame, CardLayout cardLayout, JPanel cardPanel,Timer timer,
        int[][] maze, int [][] backupMaze, int blockSize, int mazeLength, int mazeWidth, int live,
        int counter, Pacman pacman, Ghosts ghost, Ghosts ghost1, Ghosts ghost2) {
        this.maze = maze;
        this.mazeLength = maze.length;
        this.mazeWidth = maze[0].length;
        this.blockSize = blockSize;
        this.pacman = pacman;
        this.ghost = ghost;
        this.ghost1 = ghost1;
        this.ghost2 = ghost2;
        this.counter = counter;
        this.frame = frame;
        this.timer = timer;
        this.lives = live;
        this.backupMaze = new int[mazeLength][mazeWidth];
        Images();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                updateScore(g2d);
                updateLives(g2d, lives);
                drawMaze(g2d);

                pacman.drawPacman(g2d);
                ghost.drawGhost(g2d);
                ghost1.drawGhost1(g2d);
                ghost2.drawGhost2(g2d);

                checkCounter(g2d);
                checkLives(g2d);
            }
        };

        panel.setLayout(null);
        timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.repaint();
                }
            });

        Icon pause = new ImageIcon("images/pause.png");
        Icon resume = new ImageIcon("images/play.png");
        Icon restart = new ImageIcon("images/restart.png");

        JButton pauseButton = new JButton(pause);
        pauseButton.setPreferredSize(new Dimension(78, 18));
        pauseButton.setBounds(470, 1, 78, 18);
        pauseButton.setBorder(new LineBorder(Color.BLACK));
        
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                frame.requestFocus();
                panel.repaint();
            } 
        });

        JButton resumeButton = new JButton(resume);
        resumeButton.setPreferredSize(new Dimension(78, 18));
        resumeButton.setBounds(550, 1, 78, 18);
        resumeButton.setBorder(new LineBorder(Color.BLACK));

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
                frame.requestFocus();
                panel.repaint(); 
                
            }
        });
        
        JButton restartButton = new JButton(restart);
        restartButton.setPreferredSize(new Dimension(75, 18));
        restartButton.setBounds(330, 400, 75, 18);
        restartButton.setBorder(new LineBorder(Color.BLACK));

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
                frame.requestFocus();
                panel.repaint();
                ghost.resetPosition();
                ghost1.resetPosition();
                ghost2.resetPosition();
                pacman.resetPosition();
                pacman.score = 0;
                resetLives();
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[0].length; j++) {
                        maze[i][j] = backupMaze[i][j];
                    }
                }
            }
        });
        panel.add(pauseButton);
        panel.add(resumeButton);
        panel.add(restartButton);
        
        cardPanel.add(panel, "game");    
    }

    private void checkLives(Graphics2D g2d) {
        if (lives == 0) {
            timer.stop();
            loseGame(g2d);
            frame.requestFocus();
        }
    }

    private void resetLives() {
        lives = 3;
    }

    public void minusLives() {
        lives--;
    }

    private void drawMaze(Graphics2D g2d){
        for (int i = 0; i < mazeLength * blockSize; i += blockSize) {
            for (int j = 0; j < mazeWidth * blockSize; j +=  blockSize) {
                if (maze[i / blockSize][j / blockSize] == 2) {
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect(j, i, 20, 20);
                } else if (maze[i / blockSize][j / blockSize] == 3) {
                    g2d.setColor(Color.RED);
                    g2d.fillRect(j, i, 20, 20);
                } else if (maze[i / blockSize][j / blockSize] == 0) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(j, i, blockSize, blockSize);
                } else {
                    g2d.setColor(Color.YELLOW);
                    g2d.fillOval(j + 7, i + 7, 5, 5);
                }
                if (maze[i / blockSize][j / blockSize] == 1) {
                    counter++;
                } 
            }
        }
    }

    private void checkCounter(Graphics g2d) {
        if (counter == 0) {
            timer.stop();
            winGame(g2d);
        }
        counter = 0;
    }

    private void Images(){
        try {
            live = ImageIO.read(new File("images/lives.jpg"));
            gameOver = ImageIO.read(new File("images/gameover.png"));
            win = ImageIO.read(new File("images/win.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateScore(Graphics2D g2d) {
        String s;
        g2d.setColor(new Color(96, 128, 255));
        s = "Score: " + pacman.score;
        g2d.drawString(s, 10, 413);
    }

    private void updateLives(Graphics2D g2d, int lives) {
        String l = "Lives:";
        g2d.setColor(new Color(96, 128, 255));
        g2d.drawString(l, 500, 413);

        for (int i = 0; i < lives; i++) {
            g2d.drawImage(live, (i + 2) * 20 + 500, 400, null);
        }
    }

    private void winGame(Graphics g2d) {
        g2d.setColor(new Color(96, 128, 255));
        g2d.fillRect(150, 100, 350, 200);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(155, 105, 340, 190);
        g2d.setColor(Color.WHITE);
        g2d.drawImage(win, 265, 150, 120, 71, null);
        String s;
        s = "Score: " + pacman.score;
        g2d.setFont(new Font(s, Font.BOLD, 20));
        g2d.drawString(s, 275, 260);
    }

    private void loseGame(Graphics g2d) {
        g2d.setColor(new Color(102, 102, 102));
        g2d.fillRect(150, 100, 350, 200);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(155, 105, 340, 190);
        g2d.setColor(Color.BLACK);
        g2d.drawImage(gameOver, 210, 170, 236, 45, null);
        String s;
        s = "Score: " + pacman.score;
        g2d.setFont(new Font(s, Font.BOLD, 20));
        g2d.drawString(s, 275, 260);
    }
}