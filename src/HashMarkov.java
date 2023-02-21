import java.util.*;
public class HashMarkov implements MarkovInterface{
	protected int myOrder;
    protected Random RandomNumber;
	protected String[] myWords;		
    protected Map<WordGram,ArrayList<String>> myHashMap;

    public HashMarkov(int order){
		myOrder = order;
        myHashMap = new HashMap<>();
        RandomNumber = new Random();
	}

	public void setTraining(String text){
        myHashMap.clear();
        myWords = text.split("\\s+");
        int i = 0;
        while (i < myWords.length - myOrder){
            WordGram wordgram = new WordGram(myWords, i, myOrder);
            if(myHashMap.containsKey(wordgram)==false){
               myHashMap.put(wordgram, new ArrayList<String>());
            }
            myHashMap.get(wordgram).add(myWords[i + myOrder]);
            i = i+1;
        }
	}

    @Override
	public List<String> getFollows(WordGram wordgram1){
        if(myHashMap.containsKey(wordgram1) == false){
			return new ArrayList<>();
		}
		ArrayList<String> final1 = myHashMap.get(wordgram1);
        return final1;
    }

    private String getNext(WordGram wordgram2) {
		List<String> follows = getFollows(wordgram2);
		if ((follows.size() == 0)== true) {
			int randomIndex = RandomNumber.nextInt(myWords.length);
			return myWords[randomIndex];
		}
		else {
			int size = follows.size();
			int randomIndex = RandomNumber.nextInt(size);
			return follows.get(randomIndex);
		}
	}

    @Override
	public String getRandomText(int length){
		int index = RandomNumber.nextInt(myWords.length - myOrder + 1);
		WordGram current = new WordGram(myWords,index,myOrder);
		ArrayList<String> newList = new ArrayList<>(length);
		newList.add(current.toString());
		int i = 0;
		while (i<length-myOrder){
			String nextWord = getNext(current);
			newList.add(nextWord);
			current = current.shiftAdd(nextWord);
			i = i + 1;
		}
		return String.join(" ", newList);
	}

    @Override
	public int getOrder() {
		return myOrder;
	}

    @Override
	public void setSeed(long seed) {
		RandomNumber.setSeed(seed);
	}
}
