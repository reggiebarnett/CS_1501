//Reggie Barnett
//rdb26@pitt.edu
//CS 1501
//Assignment 4: Burrows-Wheeler Data Compression Algorithm

public class CircularSuffixArray{
    
    public Suffix[] suffixes;
    
    public CircularSuffixArray(String s){ // circular suffix array of s   - 10 points
        suffixes = new Suffix[s.length()];
        //rotates through and adds substrings
        for (int i = 0; i < s.length(); i++){
            String sub = s.substring(i); 
            //rotates and adds letters from beginning to end of substrings
            for(int j=0; j < i; j++) 
                sub += s.charAt(j);
            suffixes[i] = new Suffix(sub,i); //add each suffix to array
        }
        sort(0,s.length()-1); //sort suffixes using a method of quicksort
    }
    //Using Quicksort
    public void swap(int i, int j){ //swap both text and indexes
        int temp = suffixes[i].index;
        String temp2 = suffixes[i].text;
        suffixes[i].index = suffixes[j].index;
        suffixes[i].text = suffixes[j].text;
        suffixes[j].index = temp;
        suffixes[j].text = temp2;
    }
    public int partition(int i, int j){ //finding the middle 
        String piv = suffixes[i].text;
        while(i<j){
            //comparing 
            while(suffixes[i].text.compareTo(piv)<0) i++; 
            while(suffixes[j].text.compareTo(piv)>0) j--;
            swap(i,j); 
        }
        return i;
    }
    public void sort(int i, int j){ //sorting recursively 
        if(i>=j) return;
        int pivot = partition(i,j);
        sort(i,pivot);
        sort(pivot+1,j);
    }
    public int length(){  //length of s
	return suffixes.length;
    }//End of quicksort
    
    public int index(int i){  // returns index of ith sorted suffix - 10 points
	return suffixes[i].index;
    }
    private static class Suffix{ //suffix class
        private String text; //has text values and index values
        private int index;

        private Suffix(String text, int index) {
            this.text  = text;
            this.index = index;
        }
    }
}

