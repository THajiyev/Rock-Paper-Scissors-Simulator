import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Piece {

    String piece_type;
    int x;
    int y;
    double xSpeed;
    double ySpeed;
    BufferedImage image;

    final BufferedImage rockImage = ImageIO.read(getClass().getResource("rock.png"));
    final BufferedImage paperImage = ImageIO.read(getClass().getResource("paper.png"));
    final BufferedImage scissorsImage = ImageIO.read(getClass().getResource("scissors.png"));

    private final String rock_str = "Rock";
    private final String paper_str = "Paper";
    private final String scissors_str = "Scissors";

    public Piece(int x, int y, double xSpeed, double ySpeed, String piece_type) throws IOException {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.piece_type = piece_type;
        setImage();
    }

    public String getPieceType(){
        return piece_type;
    }

    private void setImage(){
        if(this.piece_type.equals("Rock")){
            this.image = rockImage;
        }
        else if(this.piece_type.equals("Paper")){
            this.image = paperImage;
        }
        else if(this.piece_type.equals("Scissors")){
            this.image = scissorsImage;
        }
    }

    public void updatePieceType(String new_piece_type) throws IOException{
        this.piece_type = new_piece_type;
        setImage();
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
        if (x < 0|| x > Constants.MAIN_FRAME_WIDTH - Constants.OBJECT_SIZE) {
            xSpeed = -xSpeed;
        }
        if (y  < Constants.OBJECT_SIZE*Constants.UPPER_LIMIT_MULTIPLIER || y > Constants.MAIN_FRAME_HEIGHT - Constants.OBJECT_SIZE) {
            ySpeed = -ySpeed;
            if(y  < Constants.OBJECT_SIZE*Constants.UPPER_LIMIT_MULTIPLIER){
                y = (int)(Constants.OBJECT_SIZE*Constants.UPPER_LIMIT_MULTIPLIER);
            }
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, x, y, Constants.OBJECT_SIZE, Constants.OBJECT_SIZE, observer);
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, (int)(Constants.OBJECT_SIZE/2), Constants.OBJECT_SIZE);
    }

    public boolean beats(Piece anotherPiece){
        String anotherPieceType = anotherPiece.getPieceType();
        if(piece_type.equals(rock_str)){
            return anotherPieceType.equals(scissors_str);
        }
        else if(piece_type.equals(paper_str)){
            return anotherPieceType.equals(rock_str);
        }
        else if(piece_type.equals(scissors_str)){
            return anotherPieceType.equals(paper_str);
        }
        else{
            return false;
        }
    }
}
