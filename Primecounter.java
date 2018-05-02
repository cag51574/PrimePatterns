import java.io.*;
import java.util.*;
import java.text.*;
public class Primecounter 
{
    static int totalCounted = 0;
    static int total[] = {0,0,0,0};
    static int next1[][] = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    static double next1percent[][] = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    static int next2[][][] = {{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
        {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
        {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
        {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}}};
    static int skip1[][] = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};

    
    // Prevent hardcoding depth
    static int depth = 3;
    static ArrayList<Integer> d1 = new ArrayList<Integer>();
    static ArrayList<ArrayList<Integer>> d2 = new ArrayList<ArrayList<Integer>>();
    static ArrayList<ArrayList<ArrayList<Integer>>> d3 = new ArrayList<ArrayList<ArrayList<Integer>>>();
    static ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> d4 = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>();

    static int temp = 0;
        
    public static void main(String[] args)throws Exception
    {
        // We need to provide file path as the parameter:
        // double backquote is to avoid compiler interpret words
        // like \test as \t (ie. as a escape sequence)
        //File file = new File("primes1.txt");
        //File file = new File("primes/2T_part1.txt");

        //BufferedReader br = new BufferedReader(new FileReader(file));

        try (Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream("results.txt"), "utf-8"))) {
            writer.write("Prime Analysis Results\n\n");
        }catch(IOException ex){
            System.err.println("Caught error message: " + ex.getMessage());
        }

        initialize();
        String st;

        char back2   = ' ';
        char back1   = ' ';
        char current = ' '; 

        int when_to_print = 100;
        int number_printed = 0;
        ArrayList<Character> digitList = new ArrayList<Character>();
        for (int i = 0; i < depth; i++)
            digitList.add(' ');

        for(int i = 1; i < 3; i++){
            String filename = "primes/2T_part" + i + ".txt";
            System.out.println(filename);

            File file = new File(filename);

            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((st = br.readLine()) != null){
                String[] list = st.split("\t");
                
                for(String s : list){
                    for (int j = depth - 1; j > 0; j--){
                        digitList.set(j, digitList.get(j - 1));
                    }
                    digitList.set(0, s.charAt(s.length() - 1));

                    back2   = back1;
                    back1   = current;
                    current = s.charAt(s.length() - 1);
                    //count(current, back1, back2);
                    count2(digitList);
                    totalCounted++;
                }
                // Prints on powers of 10
                /*
                if(when_to_print == totalCounted){
                    writeToFile(number_printed + 2);
                    when_to_print *= 10;
                    number_printed++;
                }
                */
            }
            writeToFile(7);
            //printStuff();
        }
    }


    public static void initialize(){
        for(int i = 0; i < 4;i++){
            d1.add(0);
            d2.add(new ArrayList<Integer>());
            d3.add(new ArrayList<ArrayList<Integer>>());
            d4.add(new ArrayList<ArrayList<ArrayList<Integer>>>());
            for(int j = 0; j < 4;j++){
                d2.get(i).add(0);
                d3.get(i).add(new ArrayList<Integer>());
                d4.get(i).add(new ArrayList<ArrayList<Integer>>());
                for(int k = 0; k < 4;k++){
                    d3.get(i).get(j).add(0);
                    d4.get(i).get(j).add(new ArrayList<Integer>());
                    for(int m = 0; m < 4;m++){
                        d4.get(i).get(j).get(k).add(0);
                    }
                }
            }
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
    
    public static void count2(ArrayList<Character> digitList){
        int[] list = new int[digitList.size()];
        for(int i = 0; i < list.length; i++){
            list[i] = cases(digitList.get(i));
        }
        if(list[0] != -1) {
            d1.set(list[0], d1.get(list[0]) + 1);
            if( depth > 1)
                depth2(list);
        }
    }


    public static void depth2(int[] list){
        if(list[1] != -1) {
            d2.get(list[0]).set(list[1], d2.get(list[0]).get(list[1]) + 1);
            if( depth > 2)
                depth3(list);
        }
    }
    public static void depth3(int[] list){
    }
    public static void depth4(int[] list){
    }

    public static void count(char current, char back1, char back2){
        int i = cases(current);
        int j = cases(back1); 
        int k = cases(back2); 
        if(i != -1) {
            total[i]++;
            if(j != -1) {
                next1[j][i]++;
                if(k != -1) {
                    next2[k][j][i]++;
                    skip1[k][i]++;
                }
            }
        }

    }

    public static List<String> getPercent(int[] stuff){
        List<String> percent = new ArrayList<String>();
        int stuff_total = 0;
        for(int i : stuff){
            stuff_total += i;
        }
        double d = 0.0;
        for(int i : stuff){
            d = ((double)i/stuff_total) * 100.0;
            percent.add(String.format("%.2f%%", d));
        }

        return percent;
    }

    public static List<ArrayList<String>> getPercent(int[][] stuff){
        List<ArrayList<String>> percent = new ArrayList<ArrayList<String>>();
        int stuff_total = 0;
        for(int[] i : stuff){
            for(int j : i){
                stuff_total += j;
            }
        }
        double d = 0.0;
        for(int i = 0; i < stuff.length; i++){
            percent.add(new ArrayList<String>());
            for(int j : stuff[i]){
                d = ((double)j/stuff_total) * 100.0;
                percent.get(i).add(String.format("%.2f%%", d));
            }
        }

        return percent;
    }

    /*
    public static void writeToFile(int power){

        List<String> percent = new ArrayList<String>();
        List<ArrayList<String>> percent2 = new ArrayList<ArrayList<String>>();
        try (Writer writer = new BufferedWriter(
                    new FileWriter("results.txt", true))) {
            // Print total counted
            writer.append("Total counted: " + "10^" +  power + " = "  + totalCounted + "\n");

            writer.append("\n");

            // Print the number of each final digit counted
            percent = getPercent(total);
            for(int i = 0; i < total.length; i++){
                writer.append("Total " + revCases(i) + "s: " + 
                        total[i] + "\t" + percent.get(i) + "\n");
            }
            
            writer.append("\n");

            // Print the next digit counts and percents
            for(int i = 0; i < total.length; i++){
                percent = getPercent(next1[i]);
                for(int j = 0; j < total.length; j++){
                    writer.append(revCases(i) + " --> " 
                            + revCases(j) + ": " + next1[i][j] + "\tpercent: " 
                            + percent.get(j) + "\n");
                }
                writer.append("\n");
            }

            // Print the next two digit counts and percents
            for(int i = 0; i < total.length; i++){
                percent2 = getPercent(next2[i]);
                for(int j = 0; j < total.length; j++){
                    percent = getPercent(next2[i][j]);
                    for(int k = 0; k < total.length; k++){
                        writer.append(revCases(i) + " --> " 
                                + revCases(j) + " --> " 
                                + revCases(k) + ": " + next2[i][j][k]
                                + "\tpercent: " + percent2.get(j).get(k) + "\n");
                    }
                }
                writer.append("\n");
            }

            writer.append("\n");

            //Prints data on the digit two after the prime
            for(int i = 0; i < total.length; i++){
                percent = getPercent(skip1[i]);
                for(int k = 0; k < total.length; k++){
                    writer.append(revCases(i) + " --> __ --> " 
                            + revCases(k) + ": " + skip1[i][k] + 
                            "\tpercent: " + percent.get(k) + "\n");
                }
                writer.append("\n");
            }

            writer.append("\n\n");
        }catch(IOException ex){
            System.err.println("Caught error message: " + ex.getMessage());
        }
    }
    */
    public static void writeToFile(int power){

        List<String> percent = new ArrayList<String>();
        List<ArrayList<String>> percent2 = new ArrayList<ArrayList<String>>();
        try (Writer writer = new BufferedWriter(
                    new FileWriter("results.txt", true))) {
            // Print total counted
            writer.append("Total counted: " + "10^" +  power + " = "  + totalCounted + "\n");

            writer.append("\n");

            // Print the number of each final digit counted
            percent = getPercent(total);
            for(int i = 0; i < d1.size(); i++){
                writer.append("Total " + revCases(i) + "s: " + 
                        d1.get(i) + "\t" + percent.get(i) + "\n");
            }
            
            writer.append("\n");

            // Print the next digit counts and percents
            for(int i = 0; i < total.length; i++){
                percent = getPercent(next1[i]);
                for(int j = 0; j < total.length; j++){
                    writer.append(revCases(i) + " --> " 
                            + revCases(j) + ": " + d2.get(i).get(j) + "\tpercent: " 
                            + percent.get(j) + "\n");
                }
                writer.append("\n");
            }

            writer.append("\n\n");
        }catch(IOException ex){
            System.err.println("Caught error message: " + ex.getMessage());
        }
    }
}


