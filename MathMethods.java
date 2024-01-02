public class MathMethods {
    
    public static double[][] multiplyMatrix(double[][] matrix1, double[][] matrix2){
        double[][] outputMatrix = new double[matrix1.length][matrix2[0].length];
        for(int x = 0; x < matrix1.length; x++){
            for(int y = 0; y < matrix2[0].length; y++){
                outputMatrix[x][y] = multiplicationHelper(matrix1[x], matrix2, y);
            }
        }
        return outputMatrix;
    }
    
    private static double multiplicationHelper(double[] vector, double[][] matrix, int pos){
        double output = 0;
        for(int i = 0; i < vector.length; i++){
            output += vector[i] * matrix[i][pos];
        }
        return output;
    }

    private static double[] convertToRatios(double[] counts){
        double total = 0;
        for(double value: counts){
            total+=value;
        }
        double[] percentages = new double[counts.length];
        for(int index=0; index<counts.length; index++){
            percentages[index] = counts[index]/total;
        }
        return percentages;
    }

    private static double[] getEuropeanOdds(double[] counts){
        double[] ratios = convertToRatios(counts);
        for(int index=0; index<counts.length; index++){
            ratios[index] = 1/ratios[index];
        }
        return ratios;
    }

    private static double[] getAmericanOdds(double[] counts){
        double[] europeanOdds = getEuropeanOdds(counts);
        double[] americanOdds = new double[counts.length];
        for(int index=0; index<counts.length; index++){
            double ratio = europeanOdds[index];
            if(ratio>=2){
                americanOdds[index] = (ratio-1)*100;
            }
            else{
                americanOdds[index] = -100/(ratio-1);
            }
        }
        return americanOdds;
    }

    public static double[] getOdds(double[] count){
        if(Constants.American){
            return getAmericanOdds(count);
        }
        else{
            return getEuropeanOdds(count);
        }
    }

    public static String round(double value, int places){
        double multiplier = Math.pow(10, places);
        return ""+Math.round(value * multiplier) / multiplier;
    }
}
