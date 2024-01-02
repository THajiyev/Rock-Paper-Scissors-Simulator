import java.io.IOException;
import java.util.ArrayList;

public class InteractionManager{

    private final String rock_str = "Rock";
    private final String paper_str = "Paper";
    private final String scissors_str = "Scissors";

    private int rocks = Constants.TEAM_SIZE;
    private int papers = Constants.TEAM_SIZE;
    private int scissors = Constants.TEAM_SIZE;

    private ArrayList<Piece> pieces;
    
    public InteractionManager(ArrayList<Piece> pieces){
        this.pieces = pieces;
    }

    public void checkCollisions() throws IOException{
        for(int first_piece_index=0; first_piece_index<pieces.size()-1; first_piece_index++){
            for(int second_piece_index=first_piece_index+1; second_piece_index<pieces.size(); second_piece_index++){
                Piece piece1 = pieces.get(first_piece_index);
                Piece piece2 = pieces.get(second_piece_index);
                if(piece1.getBoundingBox().intersects(piece2.getBoundingBox())){
                    handleCollision(piece1, piece2);
                }
            }
        }
        updateCount();
    }

    private void handleCollision(Piece piece1, Piece piece2) throws IOException{
        if(piece1.beats(piece2)){
            piece2.updatePieceType(piece1.getPieceType());
        }
        else if(piece2.beats(piece1)){
            piece1.updatePieceType(piece2.getPieceType());
        }
    }

    private double[][] getProbabilityMatrix(){
        double total = rocks+papers+scissors;
        double[] rock_probabilities = {0, -papers/total, scissors/total};
        double[] paper_probabilities = {rocks/total, 0 , - scissors/total};
        double[] scissors_probabilities = {-rocks/total, papers/total, 0};
        double[][] probabilityMatrix = {
            rock_probabilities, 
            paper_probabilities, 
            scissors_probabilities
        };
        return probabilityMatrix;
    }

    private double[] countEstimator(){
        double[][] piecesMatrix = {{rocks, papers, scissors}};
        double[] changes = MathMethods.multiplyMatrix(piecesMatrix, getProbabilityMatrix())[0];
        for(int index=0; index<piecesMatrix[0].length; index++){
            piecesMatrix[0][index] = piecesMatrix[0][index]+changes[index];
        }
        return piecesMatrix[0];
    }

    public double[] getOdds(){
        if(rocks==0 || papers==0 || scissors==0){
            double[] zeros = new double[3];
            return zeros;
        }
        else{
            double[] odds = MathMethods.getOdds(countEstimator());
            return odds;
        }
    }

    public void updateCount(){
        int rocks_count = 0;
        int papers_count = 0;
        int scissors_count = 0;
        for(Piece piece: pieces){
            if(piece.getPieceType().equals(rock_str)){
                rocks_count++;
            }
            else if(piece.getPieceType().equals(paper_str)){
                papers_count++;
            }
            else if(piece.getPieceType().equals(scissors_str)){
                scissors_count++;
            }
        }
        rocks = rocks_count;
        papers = papers_count;
        scissors = scissors_count;
    }

    public String getWinnerString(){
        if(rocks==Constants.TEAM_SIZE*3){
            return rock_str;
        }
        else if(papers==Constants.TEAM_SIZE*3){
            return paper_str;
        }
        else if(scissors==Constants.TEAM_SIZE*3){
            return scissors_str;
        }
        else{
            return null;
        }
    }
}