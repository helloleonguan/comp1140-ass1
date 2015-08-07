package comp1110.ass1;

import java.util.Arrays;

/**
 * Created by steveb on 23/07/2015.
 */
public class MMGuesser {

    public static String toColor(int num) {
        /*BWRGAY*/
        String colors = "";
        int spot[] = new int[4];
        spot[0] = num / 1000;
        num = num % 1000;
        spot[1] = num / 100;
        num = num % 100;
        spot[2] = num / 10;
        num = num % 10;
        spot[3] = num / 1;

        for (int i = 0; i < 4; i++ ) {
        switch (spot[i])
            {
                case 0: colors = colors + "B"; break;
                case 1: colors = colors + "W"; break;
                case 2: colors = colors + "R"; break;
                case 3: colors = colors + "G"; break;
                case 4: colors = colors + "A"; break;
                case 5: colors = colors + "Y"; break;
            }
        }

        return (colors);
    }

    public static int eliminationCounter(MMScore origin, String candidate, String[] possibilities) {

        int counter = 0;
        MMRow guess = MMRow.create(candidate);

        for (String s: possibilities) {
            if (s == null) {
                return 1300;
            }

            MMScore score = guess.getScore(MMRow.create(s));

            if (!origin.equals(score))
                counter++;
        }

        return counter;
    }

     /**
     * Return an automatically generated guess.
     *
     * @return A new guess.
     */
    public static MMRow guess() {
       
       /*Generating all possible cases*/
        String[] all;
        all = new String[1296];
        int index = 0;

        for (int peg1 = 0; peg1 < 6 ; peg1 ++) {
            for (int peg2 = 0; peg2 < 6 ; peg2 ++) {
                for (int peg3 = 0; peg3 < 6 ; peg3 ++ ) {
                    for (int peg4 = 0; peg4 < 6 ; peg4 ++) {
                        all[index] = toColor(peg1*1000 + peg2*100 + peg3*10 + peg4);
                        index++;
                    }  
                }
            }
        }

        /*Reserve promising guesses and deleting the rest*/
    	int turn = MM.getCurrentTurn();

        if (turn == 0) {
            return MMRow.create(toColor(11)); //initial guess.
        } else {
            
            /*Access last score*/
            
            MMRow[] rowHistory = MM.getGuesses();
            String[] candidates = new String[1296];

            for (int i = 0; i < 1296 ; i ++) {
                candidates[i] = all[i];
            }

            for (int i = 0; i < 1296 ; i++ ) {
                        
                    MMRow currentRow = MMRow.create(candidates[i]);
                    MMScore score = currentRow.getScore(rowHistory[turn-1]);
                    
                        if (! score.equals(MM.getScore(turn-1))) {
                             candidates[i] = null;
                        }
            }
        

        /*Using minimax to search for the best candidate*/
        
        int[] minValue = new int[1296];

        for (int j = 0; j < 1296; j++ ) {
            int[] goodness = new int[10];
            for (int k = 0; k < 10 ; k ++ ) {
                goodness[k] = 1300; // bigger than 1296.
            }

            for (int i = 0; i < turn; i ++) {
            goodness[i] = eliminationCounter(MM.getScore(i), all[j], candidates);
            }
            Arrays.sort(goodness);
            minValue[j] = goodness[0];
        }

        
        for (int i = 0; i < 1296 ; i++ ) {
            if (candidates[i] == null)
                minValue[i] = -1; 
        }

        /*Generating the targeting guess for current turn*/
        int position = 0;
        for (int i = 0; i < (1296 - 1) ; i++ ) {
            if (minValue[position] < minValue[i+1])
                position = i+1;
            }

     
        return MMRow.create(candidates[position]);
        }
    }
}

// I implemented the basic ideas of what I learn from the website but somehow it did not work as it intended to be.
// If you could just point out what is wrong with my code as a feedback, I would appreciate it.
