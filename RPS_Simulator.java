import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class RPS_Simulator extends JFrame {

    private static final int FRAME_WIDTH = Constants.MAIN_FRAME_WIDTH;
    private static final int FRAME_HEIGHT = Constants.MAIN_FRAME_HEIGHT;
    private static final int TIMER_DELAY = Constants.TIMER_DELAY;
    private static final int TEAM_SIZE = Constants.TEAM_SIZE;

    private Betting bettingWindow;

    private final String[] pieceTypes = Constants.pieceTypes;

    private Timer timer;

    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    private BufferedImage offScreenImage;

    private InteractionManager interactionManager;

    public RPS_Simulator() throws IOException {
        for (int players = 0; players < TEAM_SIZE; players++) {
            for (String currentPieceType : pieceTypes) {
                double[] speeds = Constants.getSpeed();
                pieces.add(
                        new Piece(
                                Constants.getX(currentPieceType),
                                Constants.getY(currentPieceType),
                                speeds[0],
                                speeds[1],
                                currentPieceType
                        )
                );
            }
        }

        interactionManager = new InteractionManager(pieces);
        bettingWindow = new Betting(); 
        bettingWindow.setVisible(true);

        setTitle("Rock Paper Scissors Simulator");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        offScreenImage = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        timer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    moveObjects();
                    if(interactionManager.getWinnerString()==null){
                        double[] newOdds = interactionManager.getOdds();
                        bettingWindow.updateOdds(newOdds);
                    }
                    else{
                        bettingWindow.gameOver(interactionManager.getWinnerString());
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                repaint();
            }
        });
        timer.start();
    }

    private void moveObjects() throws IOException {
        for (Piece piece : pieces) {
            piece.move();
        }
        interactionManager.checkCollisions();
    }

    @Override
    public void paint(Graphics g) {
        Graphics offScreenGraphics = offScreenImage.getGraphics();
        offScreenGraphics.setColor(getBackground());
        offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());
        for (Piece piece : pieces) {
            piece.draw(offScreenGraphics, this);
        }
        g.drawImage(offScreenImage, 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new RPS_Simulator().setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
