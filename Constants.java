import java.util.Random;

public class Constants {

    public static final String[] pieceTypes = {"Rock", "Paper", "Scissors"};

    public static final int MAIN_FRAME_WIDTH = 1080;
    public static final int MAIN_FRAME_HEIGHT = 720;
    public static final int OBJECT_SIZE = 40;
    public static final int TIMER_DELAY = 60;
    public static final int TEAM_SIZE = 7;
    public static final int TOTAL_SPEED = 8;
    public static final double UPPER_LIMIT_MULTIPLIER = .75;

    public static final int BETTING_FRAME_WIDTH = 300;
    public static final int BETTING_FRAME_HEIGHT = 150;
    public static final double STARTING_BUDGET = 100;
    public static final boolean American = false;
    public static final double[] defaultAmericanOdds = {200.0, 200.0, 200.0};
    public static final double[] defaultEuropeanOdds = {3.0, 3.0, 3.0};


    private static int randomNumberGenerator(int min, int max, int increment){
        Random random = new Random();
        int numPossibleValues = (max - min) / increment + 1;
        int randomIndex = random.nextInt(numPossibleValues);
        int randomNumber = min + randomIndex * increment;
        return randomNumber;
    }

    //Rock gets the bottom left corner
    //Paper gets the upper third 
    //Scissors get the bottom right corner
    public static int getX(String pieceType){
        if(pieceType.equals("Rock")){
            return randomNumberGenerator(OBJECT_SIZE, MAIN_FRAME_WIDTH/2-OBJECT_SIZE*2, OBJECT_SIZE);
        }
        else if(pieceType.equals("Paper")){
            return randomNumberGenerator(OBJECT_SIZE, MAIN_FRAME_WIDTH-OBJECT_SIZE, OBJECT_SIZE);
        }
        else{
            return randomNumberGenerator(MAIN_FRAME_WIDTH/2+OBJECT_SIZE*2, MAIN_FRAME_WIDTH-OBJECT_SIZE, OBJECT_SIZE);
        }
    }

    public static int getY(String pieceType){
        //Upper third
        if(pieceType.equals("Paper")){
            return randomNumberGenerator(OBJECT_SIZE, MAIN_FRAME_HEIGHT/3-OBJECT_SIZE, OBJECT_SIZE);
        }
        //Lower 2/3s
        else{
            return randomNumberGenerator(MAIN_FRAME_HEIGHT/3+OBJECT_SIZE*2, MAIN_FRAME_HEIGHT-OBJECT_SIZE,OBJECT_SIZE);
        }
    }

    public static double[] getSpeed(){
        Random random = new Random();
        double x_speed = (TOTAL_SPEED/2) + 2 * random.nextDouble();
        double y_speed = Math.sqrt(Math.pow(TOTAL_SPEED, 2)-Math.pow(x_speed, 2));
        double[] speeds = {x_speed, y_speed};
        return speeds;
    }
}
