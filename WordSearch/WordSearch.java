//package wordsearch;

//Reggie Barnett
//rdb26@pitt.edu
//CS 1501
//Assignment 1: Word Search

public class WordSearch {

    public static void main(String[] args) {
        StdOut.println("\nWelcome to Word Search!  Run with \"-help\" for cmd-line options.");
       
        int i;
        int cols = 6;
        int wordtotal = 0;
        Bag<String> WordBag = new Bag();
        TST<String> WordTST = new TST();
        In getDict= new In("dict10.txt");
        String cmdBoard = "";
        //Reading in from command line==========================================
        for(i=0;i<args.length;i++){
            if(args[i].equals("-board")){
                cmdBoard = args[i+1];
            }
            if(args[i].equals("-dict")){
                getDict = new In(args[i+1]);
            }
            if(args[i].equals("-cols")){
                cols = Integer.parseInt(args[i+1]);
            }
            if(args[i].equals("-help")){
                StdOut.println("Options:\n\"-board FILENAME\": Specifies game board file.\n"+
                " \"-dict FILENAME\":  Specifies dictionary file.\n"+
                " \"-cols NUMCOLS\": Specifies the number of columns for printing words.");
                return;
            }
        }
        String play = "Y";
        while(play.equalsIgnoreCase("Y")){
            //Putting dictionary into bag=======================================
            Stopwatch BagTime = new Stopwatch();
            while(getDict.hasNextLine()){
                        WordBag.add(getDict.readLine().toLowerCase());
                        wordtotal++;
            }
            StdOut.println("Reading dictionary ("+wordtotal+" words): "+BagTime.elapsedTime()+" seconds.");
            //Moving dictionary from bag into TST===============================
            Stopwatch TSTTime = new Stopwatch();
            for(String NextWord : WordBag){
                WordTST.put(NextWord, NextWord);
            }
            StdOut.println("Putting dictionary into ternary-search-trie: "+TSTTime.elapsedTime()+" seconds.");
            //Building the board================================================
            int boardlen;
            String[][] board;
            if(cmdBoard.equals("")){//No -board input from command line
                StdOut.print("===Creating your own board===\nPlease enter the size of the board [Ex: Entering 5 creates a 5x5]: ");
                boardlen = StdIn.readInt();
                board = new String[boardlen][boardlen];
                StdOut.println("Enter "+boardlen+" letters per row, "+boardlen+" rows total.");
                for(int m=0;m<boardlen;m++){
                    StdOut.print("Row "+(m+1)+": ");
                    for(int n=0;n<boardlen;n++){
                        board[m][n] = StdIn.readString();
                    }StdIn.readLine();
                }
            }
            else{
                In getboard = new In(cmdBoard);
                boardlen = getboard.readInt();
                board = new String[boardlen][boardlen];
                for(int r = 0; r < boardlen ; r++){
                    for(int c = 0; c < boardlen; c++){
                        board[r][c] = getboard.readString();
                        if(board[r][c].equals(" "))
                            board[r][c] = getboard.readString();
                    }
                }
            }
            //Finding words in the board========================================
            Stopwatch searchTime = new Stopwatch();
            Bag<String> foundWords = new Bag();
            String currWord = "";
            String[] wildcard = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
            for(int r=0;r<boardlen;r++){
                for(int c=0;c<boardlen;c++){
                    checkBoard(board,foundWords,currWord,r,c,boardlen,0,wildcard);//Look right
                    checkBoard(board,foundWords,currWord,r,c,boardlen,1,wildcard);//Look down, right
                    checkBoard(board,foundWords,currWord,r,c,boardlen,2,wildcard);//Look down
                    checkBoard(board,foundWords,currWord,r,c,boardlen,3,wildcard);//Look down, left
                    checkBoard(board,foundWords,currWord,r,c,boardlen,4,wildcard);//Look left
                    checkBoard(board,foundWords,currWord,r,c,boardlen,5,wildcard);//Look up, left
                    checkBoard(board,foundWords,currWord,r,c,boardlen,6,wildcard);//Look up
                    checkBoard(board,foundWords,currWord,r,c,boardlen,7,wildcard);//Look up, right
                }
            }
            TST<String> boardWordsFound = new TST();
            Bag<String> BWFBag = new Bag();
            for(String nextWord : foundWords){
                if(WordTST.contains(nextWord) && !boardWordsFound.contains(nextWord) && nextWord.length()>3){
                    boardWordsFound.put(nextWord, nextWord);
                    BWFBag.add(nextWord);
                }
            }
            StdOut.println("Time to find all words in gameboard: "+searchTime.elapsedTime()+" seconds.");
            //Printing the board================================================
            StdOut.println("\nGame Board:");
            for(int row = 0; row < boardlen ; row++){
                for(int col = 0; col < boardlen; col++){
                    StdOut.print(board[row][col]+" ");
                }StdOut.println();
            }
            //User can now enter words==========================================
            StdOut.println("\nYou can now type in words.  Type a non-alphabetic symbol and ENTER to quit "+
                    "\nplaying and list all the words on the game board.");
            boolean done = false;
            Bag<String> userBag = new Bag();
            while(!done){
                String guess = StdIn.readLine();
                char[] guesschk = guess.toCharArray();
                for(int a=0;a<guesschk.length;a++){
                    if(guesschk[a]<65||(guesschk[a]>90&&guesschk[a]<97)||guesschk[a]>122) //ASCII values
                        done = true;
                }  
                if(!done){
                    if(boardWordsFound.contains(guess)){ //Correct guess
                        StdOut.println("Yes, \""+guess.toUpperCase()+"\" is in the dictionary and on the game board.");
                        userBag.add(guess);
                    }
                    else
                        StdOut.println("No, \""+guess.toUpperCase()+"\" is not in the dictionary or on the game board.");
                }
            }
            //Listing all possible words========================================
            StdOut.println("\nList of all words on the game board:");
            int total = 0;
            for(String WordsOnBoard : BWFBag){
                StdOut.print("\t"+WordsOnBoard);
                total++;
                if(total%cols==0)
                    StdOut.println();
            } 
            //Listing guess words===============================================
            StdOut.println("\n\nList of words that you found:");
            int user = 0;
            for(String WordsGuessed : userBag){
                StdOut.print("\t"+WordsGuessed);
                user++;
                if(user%cols==0)
                    StdOut.println();
            }
            //Percentage of words found=========================================
            double percent = ((double)user/(double)total)*100;
            StdOut.println("\n\nYou found "+user+" out of "+total+" words, or "+percent+" percent.");
            StdOut.print("Play again? [Y/N]: ");
            play = StdIn.readString();
            StdIn.readLine();
        }
    }//End of main
    //Checking for words on the board===========================================
    public static void checkBoard(String[][] board,Bag foundWords,String currWord,int r,int c,int boardlen,int check,String[] wildcard){
        if(r>=boardlen || r<0 || c<0 || c>=boardlen){
            currWord = "";
            return;
        }
        String wildHolder = currWord;
        if(board[r][c].equals("*")){
           for(int i=0;i<wildcard.length;i++){
               currWord += wildcard[i];
               foundWords.add(currWord.toLowerCase());
               if(check==0)
                    checkBoard(board,foundWords,currWord,r,c+1,boardlen,0,wildcard);//right
               if(check==1)
                    checkBoard(board,foundWords,currWord,r+1,c+1,boardlen,1,wildcard);//down, right
               if(check==2)
                    checkBoard(board,foundWords,currWord,r+1,c,boardlen,2,wildcard);//down
               if(check==3)
                    checkBoard(board,foundWords,currWord,r+1,c-1,boardlen,3,wildcard);//down left
               if(check==4)
                    checkBoard(board,foundWords,currWord,r,c-1,boardlen,4,wildcard);//left
               if(check==5)
                    checkBoard(board,foundWords,currWord,r-1,c-1,boardlen,5,wildcard);//up,left
               if(check==6)
                    checkBoard(board,foundWords,currWord,r-1,c,boardlen,6,wildcard);//up
               if(check==7)
                    checkBoard(board,foundWords,currWord,r-1,c+1,boardlen,7,wildcard);//up, right
               currWord = wildHolder;
           }   
        }
        else{
            currWord += board[r][c];
            foundWords.add(currWord.toLowerCase());
            if(check==0)
                checkBoard(board,foundWords,currWord,r,c+1,boardlen,0,wildcard);//right
            if(check==1)
                checkBoard(board,foundWords,currWord,r+1,c+1,boardlen,1,wildcard);//down, right
            if(check==2)
                checkBoard(board,foundWords,currWord,r+1,c,boardlen,2,wildcard);//down
            if(check==3)
                checkBoard(board,foundWords,currWord,r+1,c-1,boardlen,3,wildcard);//down left
            if(check==4)
                checkBoard(board,foundWords,currWord,r,c-1,boardlen,4,wildcard);//left
            if(check==5)
                checkBoard(board,foundWords,currWord,r-1,c-1,boardlen,5,wildcard);//up,left
            if(check==6)
                checkBoard(board,foundWords,currWord,r-1,c,boardlen,6,wildcard);//up
            if(check==7)
                checkBoard(board,foundWords,currWord,r-1,c+1,boardlen,7,wildcard);//up, right
        }
    }//End of checkBoard
}//End of WordSearch