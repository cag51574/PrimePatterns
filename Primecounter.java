import java.io.*;
import java.time.Instant;
import java.time.Duration;
import java.util.*;
import java.text.*;
public class Primecounter 
{

    static int depth = 8;
    static int base = 8;
    static int numberOfFilesUsed = 100;
    static int choice = 0;
    static String outputType = "normal";
    static int bestPatternsSize = 10;

    static ArrayList<ArrayList<Integer>> megaList = new ArrayList<ArrayList<Integer>>();
    static String outputFile = "results.txt";
        
    public static void main(String[] args)throws Exception
    {
        gui();
    }


    public static void gui(){
        Boolean running = true;
        System.out.println("Welcome to the prime final digit analysis.");
        System.out.println();
        System.out.println("The goal of this program is to analyze the final digits of prime numbers in different bases and find patterns that would be otherwise difficult to notice.\n");
        System.out.println("This program only works for the first 2 billion primes and due to the nature of the program, the difficulty to compute will increase exponentially with larger depth and more primes. Thus, it is recommended to start with smaller numbers first.\n");

        Scanner s = new Scanner(System.in);
        while(running){
            System.out.println("Please select an option.");
            System.out.println("------------------------\n");
            System.out.println("1) Output all patterns up to a certian depth.");
            System.out.println("2) Output the most common 10 patterns at each power of 10 primes until a specific depth.");
            System.out.println("3) List the most common patterns at a specific depth.");
            System.out.println("4) List the most common patterns at all depths.");
            System.out.println("5) Exit program.");
            System.out.println();
            System.out.print("Select an option: ");
            choice = 0;
            try{
                if(s.hasNextInt()){
                    choice = s.nextInt();
                    if(choice == 5){
                        System.out.println("Goodbye!");
                        running = false;
                    }else if(choice == 1 ||choice == 2 ||choice == 3 || choice == 4){
                        System.out.println("Please select a depth (length of pattern).");
                        System.out.println("It is recommended to start with small numbers first as computation time will increase exponentially.");
                        System.out.print("\nDepth: ");
                        if(s.hasNextInt())
                            depth = s.nextInt() + 1;
                        System.out.println("How many primes would you like to look at?");
                        System.out.println("Choose a number between 1 and 200 where each one represents 10 million primes.");
                        if(s.hasNextInt())
                            numberOfFilesUsed = s.nextInt();
                        while(numberOfFilesUsed < 1 || numberOfFilesUsed > 200){
                            System.out.println("Please select a number between 1 and 200.");
                            if(s.hasNextInt())
                                numberOfFilesUsed = s.nextInt();

                        }
                        System.out.println("What base would you like to use?");

                        if(s.hasNextInt())
                            base = s.nextInt();
                        while(base < 3){
                            System.out.println("Please select a number greater than 2.");
                            if(s.hasNextInt())
                                base = s.nextInt();
                        }
                        System.out.println();
                        System.out.println("What format would you like the your results?");
                        System.out.println("1) Output to a file (Normal).");
                        System.out.println("2) Output to a file (Latex).");
                        System.out.println("3) Printed to screen");
                        int c = 0;
                        while(c != 1 && c != 2 && c != 3){
                            if(s.hasNextInt()) 
                                c = s.nextInt();

                            if(c != 1 && c != 2 && c != 3)
                                System.out.println("Not recognized. Please try again.\n");
                        }

                        if(c == 1 || c == 2){
                            System.out.println("The results will be located in a file called 'results.txt'");
                            if(c == 1){
                                outputType = "normal";
                            }else{
                                outputType = "latex";
                            }
                        }else{
                            outputType = "print";
                        }
                        runProgram();
                    }else{
                        System.out.println("Invalid Selection");
                    }
                }

            }catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Invalid choice. Please try again.\n");

                s.next();
            }
        }
        s.close();
    }
    
    public static void runProgram(){
        try (Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(outputFile), "utf-8"))) {
            writer.write("Prime Analysis Results\n\n");
        }catch(IOException ex){
            System.err.println("Caught error message: " + ex.getMessage());
        }

        initialize();
        String st;

        int when_to_print = 100;
        int number_printed = 0;
        ArrayList<Character> digitList = new ArrayList<Character>();
        for (int i = 0; i < depth; i++)
            digitList.add(' ');

        try{
            for(int i = 1; i <= numberOfFilesUsed; i++){
                String filename = "primes/2T_part" + i + ".txt";
                System.out.println(filename);

                File file = new File(filename);

                BufferedReader br = new BufferedReader(new FileReader(file));


                while ((st = br.readLine()) != null){
                    String[] list = st.split("\t");
                    
                    //fix this? 
                    for(String s : list){
                        if(base != 10){
                            int p = Integer.parseInt(s);
                            p = p%base;
                            //just make this char?
                            s = String.valueOf(p);
                        }
                        for (int j = depth - 1; j > 0; j--){
                            digitList.set(j, digitList.get(j - 1));
                        }
                        digitList.set(0, s.charAt(s.length() - 1));

                        count(digitList);
                    }
                    // Prints on powers of 10
                    if(choice == 1 || choice == 2){
                        if(when_to_print == megaList.get(0).get(0)){
                            //writeToFile(number_printed + 2);
                            if (choice == 1)
                                all();
                            if (choice == 2)
                                bestN();
                            when_to_print *= 10;
                            number_printed++;
                        }
                    }
                }
                if(choice == 3) bestN();
                if(choice == 4) max();

            }
            /* Timing function
            Instant before = Instant.now();
            Instant after = Instant.now();
            Duration duration = Duration.between(before, after);
            System.out.println(duration.toMillis());
            */
        }catch(Exception e){
            System.err.print(e.getMessage());
        }
    }

    public static void initialize(){
        int temp = depth;
        while(temp > -1){
            megaList.add(new ArrayList<Integer>());
            for(int i = 0; i < Math.pow(4, depth - temp); i++){
                megaList.get(depth - temp).add(0);
            }
            temp--;
        }
    }

    public static int cases(char i){
        switch(i){
            case '1': 
                return 0;
            case '3': 
                return 1;
            case '7': 
                return 2;
            case '9': 
                return 3;
        }
        return -1;
    }

    public static int revCases(int i){
        switch(i){
            case 0: 
                return 1;
            case 1: 
                return 3;
            case 2: 
                return 7;
            case 3: 
                return 9;
        }
        return -1;
    }

    public static int setIndex(int depth, int[] list){
        if(depth == 0) return 0;

        int index = 0;

        for(int i = 0; i < depth; i++){
            index += Math.pow(4, i) * list[i];
        }

        return index;
    }

    
    public static void count(ArrayList<Character> digitList){
        int[] list = new int[digitList.size()];
        for(int i = 0; i < list.length; i++){
            list[i] = cases(digitList.get(i));
        }
        int temp = depth;
        for(int i = 0; i < depth; i++){
            int index = setIndex(i,list);
            if( index > -1 )
                megaList.get(i).set(index, megaList.get(i).get(index) + 1);
        }
    }

    public static ArrayList<ArrayList<Double>> getPercent(){
        ArrayList<ArrayList<Double>> percents = new ArrayList<ArrayList<Double>>();
        int sum = 0;
        double d = 0.0;


        for(int i = 0; i < depth;i++){
            percents.add(new ArrayList<Double>());
        }
        percents.get(0).add(100.00);



        if(depth > 0){
            for(int i = 0; i < megaList.get(1).size();i++){
                sum += megaList.get(1).get(i);
            }
            for(int i = 0; i < megaList.get(1).size();i++){
                percents.get(1).add(((double)megaList.get(1).get(i)/sum) * 100.0);
            }
        }


        for (int m = 1; m < depth; m++){
            int size = megaList.get(m).size();
            d = 0.0;
            for(int i = 0; i < 4;i++){
                sum = 0;
                for(int j = 0; j < size;j++){
                    sum += megaList.get(m).get(j);
                }

                for(int j = 0; j < size;j++){
                    percents.get(m).add(((double)megaList.get(m).get(j)/sum) * 100.0);
                }
            }
        }

        return percents;

    }
    
    public static void max(){
        ArrayList<ArrayList<Double>> percents = getPercent();
        ArrayList<String> maxPatterns = new ArrayList<String>();
        if(outputType == "normal"){
            try (Writer writer = new BufferedWriter(
                        new FileWriter(outputFile, true))) {
                // Print total counted
                writer.append("\nDepth | Count      | Percent   | Pattern\n");
                writer.append("----------------------------------------\n");
                for(int i = 2; i < depth;i++){
                    for(int j = 0; j < Math.pow(4,i);j++){

                        int pow = (int) Math.pow(4, i);

                        double max = Collections.max(percents.get(i));
                        if (percents.get(i).get(j) == max){
                            String str = String.format("%5s | %10s | %8.4f%% | ", i, megaList.get(i).get(j), percents.get(i).get(j));
                            for (int k = i; k > 1; k--){
                                int kpow = (int) Math.pow(4,k);
                                str = str + String.valueOf(revCases(((j%kpow) - j%(kpow/4))/(kpow/4)) + " --> ");
                            }
                            str = str + revCases(j%4);

                            maxPatterns.add(str);
                            writer.append(str);
                        }
                    }
                    writer.append("\n");
                }
                writer.append("\n\n");
            }catch(IOException ex){
                System.err.println("Caught error message: " + ex.getMessage());
            }

        }
        if(outputType == "latex"){
            try (Writer writer = new BufferedWriter(
                        new FileWriter(outputFile, true))) {
                // Print total counted
                writer.append("\nbegin(tabular) { l l l r }");
                writer.append("\nDepth & Count      & Percent   & Pattern\n\\hline");
                for(int i = 0; i < depth;i++){
                    for(int j = 0; j < Math.pow(4,i);j++){
                        writer.append("\n");

                        int pow = (int) Math.pow(4, i);

                        double max = Collections.max(percents.get(i));
                        if (percents.get(i).get(j) == max){
                            String str = String.format("%5s & %10s & %8.4f\\%% & ", i, megaList.get(i).get(j), percents.get(i).get(j));
                            for (int k = i; k > 1; k--){
                                int kpow = (int) Math.pow(4,k);
                                str = str + String.valueOf(revCases(((j%kpow) - j%(kpow/4))/(kpow/4)) + " $\\rightarrow$ ");
                            }
                            str = str + revCases(j%4) + "\\\\\\\\";

                            writer.append(str);
                        }
                    }
                    if(i>1)
                        writer.append("\nend(tabular)\\\\\\\\\n");

                }
                writer.append("\n\n");
            }catch(IOException ex){
                System.err.println("Caught error message: " + ex.getMessage());
            }
        }
        if(outputType == "print"){
            System.out.print("\nDepth | Count      | Percent   | Pattern\n");
            System.out.print("----------------------------------------\n");
            for(int i = 2; i < depth;i++){
                double max = Collections.max(percents.get(i));
                for(int j = 0; j < Math.pow(4,i);j++){

                    int pow = (int) Math.pow(4, i);

                    if (percents.get(i).get(j) == max){
                        String str = String.format("%5s | %10s | %8.4f%% | ", i, megaList.get(i).get(j), percents.get(i).get(j));
                        for (int k = i; k > 1; k--){
                            int kpow = (int) Math.pow(4,k);
                            str = str + String.valueOf(revCases(((j%kpow) - j%(kpow/4))/(kpow/4)) + " --> ");
                        }
                        str = str + revCases(j%4);

                        maxPatterns.add(str);
                        System.out.print(str);
                    }
                }
                System.out.print("\n");
            }
            System.out.print("\n\n");
        }

    }

    public static void bestN(){ 
        ArrayList<String> maxPatterns = new ArrayList<String>();
        PriorityQueue<Pattern> bestPatterns = new PriorityQueue<Pattern>();
        ArrayList<ArrayList<Double>> percents = getPercent();
        if(outputType.equals("normal") || outputType.equals("print")){
            try (Writer writer = new BufferedWriter(
                        new FileWriter(outputFile, true))) {
                // Print total counted
                for(int i = 2; i < depth;i++){
                    for(int j = 0; j < Math.pow(4,i);j++){
                        int pow = (int) Math.pow(4, i);
                        double max = Collections.max(percents.get(i));
                        //hardcoded size
                        if(i == depth-1){
                            String str = String.format("%5s | %10s | %8.4f%% | ", i, megaList.get(i).get(j), percents.get(i).get(j));
                            for (int k = i; k > 1; k--){
                                int kpow = (int) Math.pow(4,k);
                                str = str + String.valueOf(revCases(((j%kpow) - j%(kpow/4))/(kpow/4)) + " --> ");
                            }
                            str = str + revCases(j%4);
                            double p = percents.get(i).get(j);
                            Pattern pattern = new Pattern();
                            pattern.setPattern(str, p);
                            bestPatterns.add(pattern);
                        }
                    }
                }

                if(outputType.equals("normal")){
                    writer.append("\nDepth | Count      | Percent   | Pattern\n");
                    writer.append("----------------------------------------\n");
                }else{
                    System.out.print("\nDepth | Count      | Percent   | Pattern\n");
                    System.out.print("----------------------------------------\n");
                }

                for(int i = 0; i < 10;i++){
                    String str = bestPatterns.poll().toString();

                    if(outputType.equals("normal")){
                        writer.append(str + "\n");
                    }else{
                        System.out.println(str);
                    }


                }

                writer.append("\n\n");
            }catch(IOException ex){
                System.err.println("Caught error message: " + ex.getMessage());
            }

        }
        if(outputType == "latex"){
            try (Writer writer = new BufferedWriter(
                        new FileWriter(outputFile, true))) {
                // Print total counted
                for(int i = 2; i < depth;i++){
                    writer.append("\nbegin(tabular) { l l l r }");
                    writer.append("\nDepth & Count      & Percent   & Pattern\n\\hline\n");
                    for(int j = 0; j < Math.pow(4,i);j++){
                        int pow = (int) Math.pow(4, i);
                        double max = Collections.max(percents.get(i));
                        //hardcoded size
                        if(i == depth-1){
                            String str = i + " & " + megaList.get(i).get(j) + " & " + String.format("%.4f\\%%", percents.get(i).get(j)) + " & ";
                            for (int k = i; k > 1; k--){
                                int kpow = (int) Math.pow(4,k);
                                str = str + String.valueOf(revCases(((j%kpow) - j%(kpow/4))/(kpow/4)) + " $\\rightarrow$ ");
                            }
                            str = str + revCases(j%4) + "\\\\";
                            double p = percents.get(i).get(j);
                            Pattern pattern = new Pattern();
                            pattern.setPattern(str, p);
                            bestPatterns.add(pattern);
                        }

                    }
                    writer.append("\nend(tabular)\\\\\\\\\n");
                }


                for(int i = 0; i < bestPatternsSize;i++){
                    writer.append(bestPatterns.poll().toString());
                }
                writer.append("\n\n");
            }catch(IOException ex){
                System.err.println("Caught error message: " + ex.getMessage());
            }

        }
        if(outputType == "print"){
        }
    }

    public static void all(){
        ArrayList<ArrayList<Double>> percents = getPercent();
        ArrayList<String> maxPatterns = new ArrayList<String>();
        if(outputType == "normal"){
            try (Writer writer = new BufferedWriter(
                        new FileWriter(outputFile, true))) {
                // Print total counted
                for(int i = 0; i < depth;i++){
                    if(i>1)
                        writer.append("Depth | Count      | Percent   | Pattern\n");
                        writer.append("----------------------------------------");
                    for(int j = 0; j < Math.pow(4,i);j++){
                        writer.append("\n");
                        if(i == 0)
                            writer.append("Total Counted: " + megaList.get(i).get(j) + "");
                        if(i == 1)
                            writer.append("Total " +  j%4 + "s: "  + megaList.get(i).get(j) 
                                    + "\tpercent: " + String.format("%.6f%%", percents.get(i).get(j)));

                        int pow = (int) Math.pow(4, i);

                        double max = Collections.max(percents.get(i));
                        if(i>1){
                            String str = String.format("%5s | %10s | %8.4f%% | ", i, megaList.get(i).get(j), percents.get(i).get(j));
                            for (int k = i; k > 1; k--){
                                int kpow = (int) Math.pow(4,k);
                                str = str + String.valueOf(revCases(((j%kpow) - j%(kpow/4))/(kpow/4)) + " --> ");
                            }
                            str = str + revCases(j%4);

                            writer.append(str);
                            if (percents.get(i).get(j) == max){
                                writer.append("  MAX  ");
                                maxPatterns.add(str);
                            }
                        }
                    }
                    writer.append("\n\n");
                }
                writer.append("\n\n");
            }catch(IOException ex){
                System.err.println("Caught error message: " + ex.getMessage());
            }

        }
        if(outputType == "latex"){
            try (Writer writer = new BufferedWriter(
                        new FileWriter(outputFile, true))) {
                // Print total counted
                for(int i = 0; i < depth;i++){
                    if(i>1)
                        writer.append("\nbegin(tabular) { l l l r }");
                        writer.append("\nDepth & Count      & Percent   & Pattern\n\\hline");
                    for(int j = 0; j < Math.pow(4,i);j++){
                        writer.append("\n");
                        if(i == 0)
                            writer.append("Total Counted: " + megaList.get(i).get(j) + "");
                        if(i == 1)
                            writer.append("Total " +  j%4 + "s: "  + megaList.get(i).get(j) 
                                    + "\tpercent: " + String.format("%.6f\\%%", percents.get(i).get(j)));

                        int pow = (int) Math.pow(4, i);

                        double max = Collections.max(percents.get(i));
                        if(i>1){
                            String str = String.format("%5s & %10s & %8.4f\\%% & ", i, megaList.get(i).get(j), percents.get(i).get(j));
                            for (int k = i; k > 1; k--){
                                int kpow = (int) Math.pow(4,k);
                                str = str + String.valueOf(revCases(((j%kpow) - j%(kpow/4))/(kpow/4)) + " $\\rightarrow$ ");
                            }
                            str = str + revCases(j%4) + "\\\\\\\\";

                            writer.append(str);
                            if (percents.get(i).get(j) == max){
                                writer.append("  MAX  ");
                                maxPatterns.add(str);
                            }
                        }
                    }
                    if(i>1)
                        writer.append("\nend(tabular)\\\\\\\\\n");

                }
                writer.append("\n\n");
            }catch(IOException ex){
                System.err.println("Caught error message: " + ex.getMessage());
            }
        }
        if(outputType == "print"){
            for(int i = 0; i < depth;i++){
                if(i>1)
                    System.out.print("Depth | Count      | Percent   | Pattern\n");
                    System.out.print("----------------------------------------");
                for(int j = 0; j < Math.pow(4,i);j++){
                    System.out.print("\n");
                    if(i == 0)
                        System.out.print("Total Counted: " + megaList.get(i).get(j) + "");
                    if(i == 1)
                        System.out.print("Total " +  j%4 + "s: "  + megaList.get(i).get(j) 
                                + "\tpercent: " + String.format("%.6f%%", percents.get(i).get(j)));

                    int pow = (int) Math.pow(4, i);

                    double max = Collections.max(percents.get(i));
                    if(i>1){
                        String str = String.format("%5s | %10s | %8.4f%% | ", i, megaList.get(i).get(j), percents.get(i).get(j));
                        for (int k = i; k > 1; k--){
                            int kpow = (int) Math.pow(4,k);
                            str = str + String.valueOf(revCases(((j%kpow) - j%(kpow/4))/(kpow/4)) + " --> ");
                        }
                        str = str + revCases(j%4);

                        System.out.print(str);
                        if (percents.get(i).get(j) == max){
                            System.out.print("  MAX  ");
                            maxPatterns.add(str);
                        }
                    }
                }
                System.out.print("\n\n");
            }

            System.out.print("\n\n");
        }
    }
}


