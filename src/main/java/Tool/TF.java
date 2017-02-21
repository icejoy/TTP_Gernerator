package Tool;

import java.util.ArrayList;

public class TF
{
	ArrayList<String> Words = new ArrayList<String>();
	ArrayList<Double> Words_TF = new ArrayList<Double>();
	ArrayList<Integer> Words_Count = new ArrayList<Integer>();

	public void Start_TF(ArrayList<String> words)
	{
		Words.clear();
		Words_TF.clear();
		Words_Count.clear();
		Word_Count(words);
		Calculate_TF();
	}

	public void Calculate_TF()
	{
		int all_word = Words.size();
		for (Integer i : Words_Count)
		{
			Words_TF.add((double) i / all_word);
		}
	}

	public void Word_Count(ArrayList<String> words)
	{
		int index = 0;
		if (words != null)
		{
			for (String s : words)
			{
				if (Words.contains(s))
				{
					index = Words.indexOf(s);
					Words_Count.set(index, Words_Count.get(index) + 1);
				}
				else
				{
					Words.add(s);
					Words_Count.add(1);
				}
			}
		}
	}

	public ArrayList<String> get_Words()
	{
		return Words;
	}

	public ArrayList<Double> get_TF()
	{
		return Words_TF;
	}
}
