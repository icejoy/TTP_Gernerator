package Tool;

import java.util.ArrayList;

public class TFIDFMatrix
{
	TF tf = new TF();
	IDF idf = new IDF();

	ArrayList<ArrayList<ArrayList<String>>> Words = new ArrayList<ArrayList<ArrayList<String>>>();
	ArrayList<ArrayList<ArrayList<Double>>> TFIDF = new ArrayList<ArrayList<ArrayList<Double>>>();

	public void Start_TFIDF(ArrayList<ArrayList<ArrayList<String>>> All_TTP_Words)
	{
		this.idf.Start_IDF(All_TTP_Words);

		// 每個TTP
		for (int i = 0; i < All_TTP_Words.size(); i++)
		{
			Words.add(new ArrayList<ArrayList<String>>());
			TFIDF.add(new ArrayList<ArrayList<Double>>());
			// 每個Description
			for (int j = 0; j < All_TTP_Words.get(i).size(); j++)
			{
				// 算TF
				this.tf.Start_TF(All_TTP_Words.get(i).get(j));
				Words.get(i).add(new ArrayList<String>(this.tf.get_Words()));
				TFIDF.get(i).add(new ArrayList<Double>(Calculate_TFIDF(j, this.tf.get_Words())));
			}
		}
	}

	public ArrayList<ArrayList<ArrayList<String>>> get_TTP_Words()
	{
		return Words;
	}

	public ArrayList<ArrayList<ArrayList<Double>>> get_TFIDF()
	{
		return TFIDF;
	}

	public ArrayList<Double> find_Word_in_IDF(int which_Description, ArrayList<String> words)
	{
		int index = 0;
		ArrayList<Double> IDF_list = new ArrayList<Double>();
		ArrayList<ArrayList<String>> IDF_Words = new ArrayList<ArrayList<String>>(this.idf.get_All_Words());
		ArrayList<ArrayList<Double>> IDF_Counts = new ArrayList<ArrayList<Double>>(this.idf.get_All_Count());
		for (int i = 0; i < words.size(); i++)
		{
			if (IDF_Words.get(which_Description).contains(words.get(i)))
			{
				// 取得在IDF的數值
				index = IDF_Words.get(which_Description).indexOf(words.get(i));
				IDF_list.add(IDF_Counts.get(which_Description).get(index));
			}
			else
			{
				IDF_list.add(Double.MAX_VALUE);
			}
		}
		return IDF_list;
	}

	public ArrayList<Double> Calculate_TFIDF(int which_Description, ArrayList<String> words)
	{
		Double TFIDF = 0.0;
		ArrayList<Double> tfidf = new ArrayList<Double>();
		ArrayList<Double> IDF_list = new ArrayList<Double>(find_Word_in_IDF(which_Description, words));
		for (int i = 0; i < words.size(); i++)
		{
			TFIDF = this.tf.get_TF().get(i) * IDF_list.get(i);
			tfidf.add(TFIDF);
		}
		return tfidf;
	}
}
