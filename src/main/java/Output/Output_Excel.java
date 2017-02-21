package Output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.medallia.word2vec.Searcher.Match;

import Criteria.TTP;
import Input.Input_Factory;
import Input.Input_Interface;

public class Output_Excel implements Output_Interface
{
	Input_Interface II;

	ArrayList<ArrayList<ArrayList<String>>> Description = new ArrayList<ArrayList<ArrayList<String>>>();
	ArrayList<ArrayList<ArrayList<Double>>> TFIDF = new ArrayList<ArrayList<ArrayList<Double>>>();

	public void input()
	{
		Input_Factory IF = new Input_Factory();
		this.II = IF.choose_Input("ReadFile");
		this.II.input("./Input_Data/stop-word-list.txt");
	}

	public void start_WordNum_output(Boolean Cover,String FileName, ArrayList<ArrayList<ArrayList<String>>> Words,
			ArrayList<ArrayList<ArrayList<Double>>> Numbers)
	{
		input();

		@SuppressWarnings("unused")
		int word_count = 0, number_count = 0;
		Description.add(new ArrayList<ArrayList<String>>());
		TFIDF.add(new ArrayList<ArrayList<Double>>());
		Description.add(new ArrayList<ArrayList<String>>());
		TFIDF.add(new ArrayList<ArrayList<Double>>());
		Description.add(new ArrayList<ArrayList<String>>());
		TFIDF.add(new ArrayList<ArrayList<Double>>());
		Description.add(new ArrayList<ArrayList<String>>());
		TFIDF.add(new ArrayList<ArrayList<Double>>());
		try
		{
			String word_str, number_str;
			FileWriter writer = new FileWriter(FileName + ".csv",!Cover);
			String[] description =
			{ "stix", "ttp", "cybox", "other" };
			// 每個TTP
			for (int i = 0; i < Words.size(); i++)
			{
				// 每個Description
				for (int j = 0; j < Words.get(i).size(); j++)
				{
					word_str = "";
					number_str = "";
					word_count = 0;
					number_count = 0;
					// 每個字
					if (Words.get(i).get(j).size() != Numbers.get(i).get(j).size())
					{
						System.out.println("error");
					}
					for (int k = 0; k < Words.get(i).get(j).size(); k++)
					{
						word_str += Words.get(i).get(j).get(k);
						word_count++;
						number_str += Numbers.get(i).get(j).get(k).toString();
						number_count++;
						if (k++ < Words.get(i).get(j).size())
						{
							word_str += ",";
							number_str += ",";
						}
						k--;
					}
					Description.get(j).add(new ArrayList<String>(Words.get(i).get(j)));
					TFIDF.get(j).add(new ArrayList<Double>(Numbers.get(i).get(j)));
					writer.write(description[j] + "\n" + word_str + "\n");
					writer.write(number_str + "\n");
					writer.flush();
				}
			}
			writer.close();
			Data_Del_StopWords_output(true,FileName);
			Data_Filter_output(true,FileName);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void Data_Filter_output(Boolean Cover,String FileName)
	{
		int count_word = 0, count_double = 0;
		ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<Double>> tfidf = new ArrayList<ArrayList<Double>>();

		try
		{
			String word_str, number_str;
			FileWriter writer = new FileWriter(FileName + "_Filter_Del_Stop_Words.csv",!Cover);
			String[] description =
			{ "stix", "ttp", "cybox", "other" };
			// 4個Description
			for (int i = 0; i < Description.size(); i++)
			{
				words.add(new ArrayList<String>());
				tfidf.add(new ArrayList<Double>());
				// 同個description不同TTP
				for (int j = 0; j < Description.get(i).size(); j++)
				{
					// 同個description不同TTP的字
					for (int k = 0; k < Description.get(i).get(j).size(); k++)
					{
						words.get(i).add(Description.get(i).get(j).get(k));
						tfidf.get(i).add(TFIDF.get(i).get(j).get(k));
					}
				}
			}

			int index = 0;
			Double Subtract = 0.0;
			// 4個description
			for (int i = 0; i < words.size(); i++)
			{
				// 字
				for (int j = 0; j < words.get(i).size(); j++)
				{
					// 其他的description
					for (int k = 0; k < words.size(); k++)
					{
						if (k != i)
						{
							if (words.get(k).contains(words.get(i).get(j)))
							{
								index = words.get(k).indexOf(words.get(i).get(j));
								Subtract = tfidf.get(k).get(index) - tfidf.get(i).get(j);
								if (Subtract > 0)
								{
									words.get(i).remove(j);
									tfidf.get(i).remove(j);
									j--;
									break;
								}
								else if (Subtract == 0)
								{
									words.get(k).remove(index);
									tfidf.get(k).remove(index);
									words.get(i).remove(j);
									tfidf.get(i).remove(j);
									j--;
									break;
								}
								else
								{
									words.get(k).remove(index);
									tfidf.get(k).remove(index);
								}
							}
						}
					}
				}
			}
			
			// 刪除stop words
			String[] stop_words_list = this.II.get_input().split("\r\n");
			for (int i = 0; i < words.size(); i++)
			{
				for (String s : stop_words_list)
				{
					if (words.get(i).contains(s))
					{
						index = words.get(i).indexOf(s);
						words.get(i).remove(index);
						tfidf.get(i).remove(index);
					}
				}
			}

			// 4個description
			for (int i = 0; i < words.size(); i++)
			{
				word_str = "";
				count_word = 0;
				number_str = "";
				count_double = 0;
				if (words.get(i).size() != tfidf.get(i).size())
				{
					System.out.println("error");
				}
				for (int j = 0; j < words.get(i).size(); j++)
				{
					word_str += words.get(i).get(j).toString();
					count_word++;
					number_str += tfidf.get(i).get(j).toString();
					count_double++;
					if (j++ < words.get(i).size())
					{
						word_str += ",";
						number_str += ",";
					}
					j--;
				}
				if (count_double != count_word)
				{
					System.out.println("error");
				}
				writer.write(description[i] + "\n" + word_str + "\n");
				writer.write(number_str + "\n");
				writer.flush();
			}
			writer.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Data_Del_StopWords_output(Boolean Cover,String FileName)
	{
		int count_word = 0, count_double = 0;
		ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<Double>> tfidf = new ArrayList<ArrayList<Double>>();

		try
		{
			String word_str, number_str;
			FileWriter writer = new FileWriter(FileName + "_Del_Stop_Words.csv",!Cover);
			String[] description =
			{ "stix", "ttp", "cybox", "other" };
			// 4個Description
			for (int i = 0; i < Description.size(); i++)
			{
				words.add(new ArrayList<String>());
				tfidf.add(new ArrayList<Double>());
				// 同個description不同TTP
				for (int j = 0; j < Description.get(i).size(); j++)
				{
					// 同個description不同TTP的字
					for (int k = 0; k < Description.get(i).get(j).size(); k++)
					{
						words.get(i).add(Description.get(i).get(j).get(k));
						tfidf.get(i).add(TFIDF.get(i).get(j).get(k));
					}
				}
			}
			int index = 0;
			// 刪除stop words
			String[] stop_words_list = this.II.get_input().split("\r\n");
			for (int i = 0; i < words.size(); i++)
			{
				for (String s : stop_words_list)
				{
					if (words.get(i).contains(s))
					{
						index = words.get(i).indexOf(s);
						words.get(i).remove(index);
						tfidf.get(i).remove(index);
					}
				}
			}

			// 4個description
			for (int i = 0; i < words.size(); i++)
			{
				word_str = "";
				count_word = 0;
				number_str = "";
				count_double = 0;
				if (words.get(i).size() != tfidf.get(i).size())
				{
					System.out.println("error");
				}
				for (int j = 0; j < words.get(i).size(); j++)
				{
					word_str += words.get(i).get(j).toString();
					count_word++;
					number_str += tfidf.get(i).get(j).toString();
					count_double++;
					if (j++ < words.get(i).size())
					{
						word_str += ",";
						number_str += ",";
					}
					j--;
				}
				if (count_double != count_word)
				{
					System.out.println("error");
				}
				writer.write(description[i] + "\n" + word_str + "\n");
				writer.write(number_str + "\n");
				writer.flush();
			}
			writer.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start_TTP_output(Boolean Cover,String FileName,ArrayList<TTP> TTPs)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start_Match_output(Boolean Cover,String FileName, ArrayList<ArrayList<ArrayList<ArrayList<Match>>>> Word2Vec)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start_ArrayList2String(Boolean Cover,String FileName, ArrayList<ArrayList<String>> ArrayList2Strings)
	{
		// TODO Auto-generated method stub
		
	}

}
