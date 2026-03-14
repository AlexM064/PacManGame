import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class StartPanel extends JPanel {
    private ImageIcon start;
    private Image startBackground;

    public StartPanel (CardLayout cardLayout, JPanel cardPanel, Timer timer) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Images();
                g2d.drawImage(startBackground, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        start = new ImageIcon("images/start.png");
        JButton startButton = new JButton(start);

        startButton.setBounds(260, 165, 150, 59);
        startButton.setPreferredSize(new Dimension(150, 59));
        startButton.setBorder(new LineBorder(Color.BLACK));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "game");
                timer.start();
                requestFocus();
            } 
        });
        panel.add(startButton);
        cardPanel.add(panel, "start");
    }


    private void Images(){
        try {
            startBackground = ImageIO.read(new File("images/startBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}