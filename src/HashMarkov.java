import java.util.*;

public class HashMarkov implements MarkovInterface {
    protected String[] myWords; // Training text split into array of words
    protected Random myRandom; // Random number generator
    protected int myOrder;
    protected HashMap<Object, List<String>> myHashMap;

    public HashMarkov(int order) {
        myOrder = order;
        myRandom = new Random();
    }

    public void setTraining(String text) {
        myHashMap.clear();
        myWords = text.split("\\s+");
        for (int i = 0; i < myWords.length; i++) {
            WordGram newgram = new WordGram(myWords, i, myOrder);
            if (myHashMap.containsKey(newgram) == false) {
                myHashMap.put(newgram, new ArrayList<>());
            } else {
                myHashMap.get(newgram).add(myWords[i]);
            }
        }
    }

    public List<String> getFollows(WordGram wgram) {
        List<String> terms = myHashMap.get(wgram);
        if (terms != wgram) {
            return terms;
        } else {
            return new ArrayList<String>();
        }
    }

    

    public int getOrder() {
        return myOrder;
    }

    public void setSeed(long seed) {
        myRandom.setSeed(seed);
    }
}
