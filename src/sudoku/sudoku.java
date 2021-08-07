package sudoku;

import java.util.*;
import java.io.*;

/**
 * @Author  Rhydor [Juan Ruiz]
 * @Date    31/07/2021
 * @Version 0.1.0
 * <p>Read Sudoku of txt and validate if this is correct or not</p>
 */
public class sudoku {
    //Put colors in console
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_ORANGE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";
    //
    private static final int[][] sudoku     = new int [9][9];
    private static final int[][] sudoku_inv = new int [9][9];
    private static int           choose     = 0;
    private static final Hashtable<Integer, String> files = new Hashtable<Integer, String>();

    public static void main(String[] args) {
        //Create Dictionary for save sudoku files
        files.put(0, "exit");
        files.put(1, "basic");
        files.put(2, "another_basic");
        files.put(3, "row_error");
        files.put(4, "column_error");
        files.put(5, "subgrid_error");
        files.put(6, "rubbish_error");
        files.put(7, "count_error");
        files.put(8, "Own sudoku");

        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            System.out.println(ANSI_WHITE+"Select a sudoku to validate or validate yours"+ANSI_ORANGE);
            for (int i = 0; i < files.size(); i++) {
                System.out.println(i + ": "+ files.get(i));
            }
            System.out.print(ANSI_RESET);
            choose = sc.nextInt();
            if (choose == 0){
                flag = false;
                sc.close();
            }else{
                if (choose > 0 && choose <= 7){
                    uploadSudoku(choose);
                }else{
                    ownSudoku();
                }
                // Print sudoku
                System.out.println(ANSI_PURPLE+choose +" "+ files.get(choose)+ANSI_RESET);
                for (int i = 0; i < 9; i++) {
                    System.out.println(ANSI_YELLOW+Arrays.toString(sudoku[i])+ANSI_RESET);
                }
                // Print result of sudoku
                boolean res = validator(sudoku, sudoku_inv);
                if (res) {
                    System.out.println("Sudoku correct? "+ANSI_GREEN+res+ANSI_RESET);
                }else{
                    System.out.println("Sudoku correct? "+ANSI_RED+res+ANSI_RESET);
                }
                System.out.println(ANSI_RED+"Pres enter to continue"+ANSI_RESET);
                sc.nextLine();
                sc.nextLine();

            }
        }
    }

    /**
     * <h3> function in charge of loading file in array for later validation</h3>
     * @param choose sudoku number to extract from txt
     */
    public static void uploadSudoku(int choose){
        String vali = files.get(choose);
        try{
            File    txt  = new File("testcases//"+vali+".txt");
            Scanner read = new Scanner(txt);
            while (read.hasNextInt()){
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int data = read.nextInt();
                        sudoku[i][j]     = data;
                        sudoku_inv[j][i] = data;
                    }
                }
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.err.println("File: "+vali+" doesn't exists." );
        }
    }

    /**
     * <h3> function in charge of requesting data to put in an array for later validation</h3>
     */
    public static void ownSudoku(){
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int n = in.nextInt();
                sudoku[i][j] = n;
                sudoku_inv[j][i] = n;
            }
        }
    }

    /**
     * <h3>function in charge of validating if the sudoku is complete or not</h3>
     * @param sudoku     sudoku in array type [9] [9]
     * @param sudoku_inv reverse sudoku in array type [9] [9]
     * @return           Return true or false if the sudoku is correct
     */
    public static boolean validator(int[][] sudoku, int[][] sudoku_inv){
        int t = 0;
        for (int i = 0; i < sudoku.length; i++) {
            int ax = Arrays.stream(sudoku[0]).sum();
            int ay = Arrays.stream(sudoku_inv[0]).sum();
            //System.err.println(ax);
            //System.err.println(ay);
            if (ax != 45 || ay != 45) {
                break;
            }
            if (Arrays.stream(sudoku[0]).distinct().count() != sudoku[0].length){
                break;
            }
            t += ax + ay;
        }

        if (t == 810){
            boolean v = true;
            for (int i=0; i <9; i+=3) {
                for (int j=0; j <9; j+=3) {
                    if (!v){
                        break;
                    }
                    int a = 0;
                    for (int k=j; k<j+3; k++) {
                        for (int l=0; l<i+3; l++){
                            a += sudoku[l][k];
                        }
                    }
                    if (!(a == 45 || a == 90 || a == 135)){
                        v = false;
                        break;
                    }
                }
            }
            return v;
        }else{
            return false;
        }
    }
}
