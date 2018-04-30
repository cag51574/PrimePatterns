import java.io.*;
import java.util.*;
import java.text.*;
public class Primecounter 
{
    static int totalCounted = 0;
    static int total[] = {0,0,0,0};
    static int next1[][] = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    static int next2[][][] = {{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
                              {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
                              {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
                              {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}}};
    static int skip1[][] = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};

    public static void main(String[] args)throws Exception
    {
        // We need to provide file path as the parameter:
        // double backquote is to avoid compiler interpret words
        // like \test as \t (ie. as a escape sequence)
        File file = new File("primes1.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;

        char back2   = ' ';
        char back1   = ' ';
        char current = ' '; 
        while ((st = br.readLine()) != null){
            List<String> list = getWords(st);
            for(String s : list){
                back2   = back1;
                back1   = current;
                current = s.charAt(s.length() - 1);
                count(current, back1, back2);
                totalCounted++;
            }
        }
        printStuff();
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

    public static void printStuff(){

        System.out.println();
        System.out.println();
        System.out.println("Total Counted: " + totalCounted);

        for(int i = 0; i < total.length; i++){
            System.out.println("Total " + revCases(i) + "s: " + total[i]);
        }
        System.out.println();
        System.out.println();
        for(int i = 0; i < total.length; i++){
            for(int j = 0; j < total.length; j++){
                System.out.println("Total " + revCases(i) + "s to " 
                        + revCases(j) + "s: " + next1[i][j]);
            }
            System.out.println();
        }
        for(int i = 0; i < total.length; i++){
            for(int j = 0; j < total.length; j++){
                for(int k = 0; k < total.length; k++){
                    System.out.println("Total " + revCases(i) + "s to " 
                            + revCases(j) + "s: " + "s to " 
                            + revCases(k) + "s: " + next2[i][j][k]);
                }
            }
            System.out.println();
        }
        for(int i = 0; i < total.length; i++){
            for(int k = 0; k < total.length; k++){
                System.out.println("Total " + revCases(i) + "s skip one, then " 
                        + revCases(k) + "s: " + skip1[i][k]);
            }
            System.out.println();
        }


    }

    public static List<String> getWords(String text) {
        List<String> words = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(text);
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
                words.add(text.substring(firstIndex, lastIndex));
            }
        }

        return words;
    }
}




/*

import java.io.*;
import java.util.*;
import java.text.*;
public class Primecounter 
{
    static int totalCounted = 0;
    static int total[] = {0,0,0,0};
    static int next1[][] = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    static int next2[][][] = {{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
                              {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
                              {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
                              {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}}};
    static int skip1[][] = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    static char lastIntRecord[] = {' ',' ',' '};

    public static void main(String[] args)throws Exception
    {
        // We need to provide file path as the parameter:
        // double backquote is to avoid compiler interpret words
        // like \test as \t (ie. as a escape sequence)
        File file = new File("primes1.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;

        char back2   = ' ';
        char back1   = ' ';
        char current = ' '; 
        int sex = 0;
        while ((st = br.readLine()) != null ){
            sex++;
            List<String> list = getWords(st);
            char[] lastIntRecord = {' ',' ',' '};
            for(String s : list){
                //System.out.println(lastIntRecord[0]);
                //System.out.println(lastIntRecord[1]);
                //System.out.println(lastIntRecord[2]);
                for (int i = lastIntRecord.length - 1; i > 0; i--){
                    lastIntRecord[i] = lastIntRecord[i-1];
                }
                //System.out.println(lastIntRecord[0]);
                //System.out.println(lastIntRecord[1]);
                //System.out.println(lastIntRecord[2]);

                lastIntRecord[0] = s.charAt(s.length() - 1);
                count(lastIntRecord);
                totalCounted++;
                //System.out.println(lastIntRecord[0]);
                //System.out.println(lastIntRecord[1]);
                //System.out.println(lastIntRecord[2]);
            }
        }
        printStuff();
        
        //System.out.println(lastIntRecord.length);
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

    public static void count(char[] lastIntRecord){
        int i = cases(lastIntRecord[0]);
        int j = cases(lastIntRecord[1]); 
        int k = cases(lastIntRecord[2]); 
        if(i != -1) {
            total[i]++;
            if(j != -1) {
                next1[i][j]++;
                if(k != -1) {
                    next2[i][j][k]++;
                    skip1[i][k]++;
                }
            }
        }

    }

    public static void printStuff(){

        System.out.println();
        System.out.println();
        System.out.println("Total Counted: " + totalCounted);

        for(int i = 0; i < total.length; i++){
            System.out.println("Total " + revCases(i) + "s: " + total[i]);
        }
        System.out.println();
        System.out.println();
        for(int i = 0; i < total.length; i++){
            for(int j = 0; j < total.length; j++){
                System.out.println("Total " + revCases(i) + "s to " 
                        + revCases(j) + "s: " + next1[i][j]);
            }
            System.out.println();
        }
        for(int i = 0; i < total.length; i++){
            for(int j = 0; j < total.length; j++){
                for(int k = 0; k < total.length; k++){
                    System.out.println("Total " + revCases(i) + "s to " 
                            + revCases(j) + "s: " + "s to " 
                            + revCases(k) + "s: " + next2[i][j][k]);
                }
            }
            System.out.println();
        }
        for(int i = 0; i < total.length; i++){
            for(int k = 0; k < total.length; k++){
                System.out.println("Total " + revCases(i) + "s skip one, then " 
                        + revCases(k) + "s: " + skip1[i][k]);
            }
            System.out.println();
        }


    }

    public static List<String> getWords(String text) {
        List<String> words = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(text);
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
                words.add(text.substring(firstIndex, lastIndex));
            }
        }

        return words;
    }
}
*/
