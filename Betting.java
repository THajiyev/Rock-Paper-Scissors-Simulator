import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Betting extends JFrame {

    private double budget = Constants.STARTING_BUDGET;
    private JLabel budgetLabel;
    private JTextField inputField;
    private JButton rock_button;
    private JButton paper_button;
    private JButton scissors_button;
    private JButton submit_button;
    private ArrayList<Bet> bets;
    private String selectedTeam = null;
    private double[] odds;

    public Betting() {

        setTitle("Betting Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridLayout(6, 1));

        bets = new ArrayList<Bet>();
        String originalOdd = Constants.American? "+200":"3.00";
        odds = Constants.American? Constants.defaultAmericanOdds : Constants.defaultEuropeanOdds;
        
        budgetLabel = new JLabel("$"+budget);
        inputField = new JTextField();
        rock_button = new JButton("Rock("+originalOdd+")");
        paper_button = new JButton("Paper("+originalOdd+")");
        scissors_button = new JButton("Scissors("+originalOdd+")");
        submit_button = new JButton("Submit");

        add(budgetLabel);
        add(inputField);
        add(rock_button);
        add(paper_button);
        add(scissors_button);
        add(submit_button);

        rock_button.addActionListener(new ButtonListener());
        paper_button.addActionListener(new ButtonListener());
        scissors_button.addActionListener(new ButtonListener());
        submit_button.addActionListener(new ButtonListener());
    }

    private String formatOdd(int index){
        double odd = odds[index];
        if(Constants.American){
            if(odd>0){
                return "+"+(int)odd;
            }
            else{
                return ""+(int)odd;
            }
        }
        else{
            return MathMethods.round(odd, 2);
        }
    }

    public void updateOdds(double[] newOdds){
        odds = newOdds;
        updateButtons();
    }

    public void updateButtons(){
        rock_button.setText("Rock("+formatOdd(0)+")");
        paper_button.setText("Paper("+formatOdd(1)+")");
        scissors_button.setText("Scissors("+formatOdd(2)+")");
    }

    private double payOut(String winner){
        double revenue = 0;
        for(Bet bet: bets){
            revenue += bet.payOut(winner);
        }
        return revenue+budget;
    }

    public void gameOver(String winner){
        double revenue = payOut(winner);
        budgetLabel.setText("$"+MathMethods.round(revenue, 2));
        rock_button.setEnabled(false);
        paper_button.setEnabled(false);
        scissors_button.setEnabled(false);
        submit_button.setEnabled(false);
        inputField.setText("Game Over. "+winner+" won.");
    }

    public void selectButton(JButton button) {
        button.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); 
    }

    public void unselectButton(JButton button) {
        button.setBorder(BorderFactory.createEmptyBorder()); 
    }

    public void unselectButtons(){
        unselectButton(rock_button);
        unselectButton(paper_button);
        unselectButton(scissors_button);
    }

    public boolean processBet(){
        try {
            double money = Double.parseDouble(inputField.getText());
            if(selectedTeam!=null && money<=budget){
                budget-=money;
                budgetLabel.setText("$"+MathMethods.round(budget, 2));
                int index = selectedTeam.equals("Rock")?0:selectedTeam.equals("Paper")?1:0;
                double thisOdd = odds[index];
                Bet new_bet = new Bet(selectedTeam, money, thisOdd);
                bets.add(new_bet);
                inputField.setText("");
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void disableButton(JButton button) {
        button.setEnabled(false);
    } 

    public void updateBudgetLabel(double newBudget){
        budgetLabel.setText("$"+MathMethods.round(newBudget, 2));
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit_button) {
                boolean successfulBet = processBet();
                if(successfulBet){
                    selectedTeam = null;
                    unselectButtons();
                }
            } else if (e.getSource() == rock_button) {
                unselectButtons();
                selectButton(rock_button);
                selectedTeam = "Rock";
            } else if (e.getSource() == paper_button) {
                unselectButtons();
                selectButton(paper_button);
                selectedTeam = "Paper";
            } else if (e.getSource() == scissors_button) {
                unselectButtons();
                selectButton(scissors_button);
                selectedTeam = "Scissors";
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Betting betting = new Betting();
            betting.setVisible(true);
        });
    }

    public class Bet {

        private String bet_on;
        private double bet_amount;
        private double odds;

        public Bet(String bet_on, double bet_amount, double odds){
            this.bet_on = bet_on;
            this.bet_amount = bet_amount;
            this.odds = odds;
        }

        public double payOut(String winner){
            if(winner.equals(bet_on)){
                if(Constants.American){
                    if(odds>100){
                        return bet_amount*(1+odds/100);
                    }
                    else{
                        return -bet_amount*(100/odds-1);
                    }
                }
                else{
                    return bet_amount*odds;
                }
            }
            else{
                return 0;
            }
        }
    }
}