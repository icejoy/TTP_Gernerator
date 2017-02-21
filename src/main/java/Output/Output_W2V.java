package Output;

import java.io.FileWriter;
import java.util.ArrayList;

import com.medallia.word2vec.Searcher.Match;

import Criteria.TTP;

public class Output_W2V implements Output_Interface
{


	public void start_Match_output(String FileName, ArrayList<ArrayList<ArrayList<ArrayList<Match>>>> Word2Vec)
	{
		try
		{
			String words="";
			String numbers="";
			FileWriter writer = new FileWriter(FileName);
			String[] description={"stix\n","ttp\n"};
			for(int i=0;i<Word2Vec.size();i++)
			{
				writer.write(description[i]+"\n");
				for(int j=0;j<Word2Vec.get(i).size();j++)
				{
					writer.write("paragraph "+(j+1)+" :"+"\n");
					for(int k=0;k<Word2Vec.get(i).get(j).size();k++)
					{
						// 哪個字
						writer.write(Word2Vec.get(i).get(j).get(k).get(0).match()+" :\n");
						words="";
						numbers="";
						for(int l=0;l<Word2Vec.get(i).get(j).get(k).size();l++)
						{
							words+=Word2Vec.get(i).get(j).get(k).get(l).match().toString()+",";
							numbers+=Word2Vec.get(i).get(j).get(k).get(l).distance()+",";
						}

						writer.write(words+"\n"+numbers+"\n");
					}
				}
			}
			writer.flush();
			writer.close();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start_WordNum_output(String FileName, ArrayList<ArrayList<ArrayList<String>>> Words,
			ArrayList<ArrayList<ArrayList<Double>>> Numbers)
	{	
	}
	
	public void start_TTP_output(String FileName, ArrayList<TTP> TTPs)
	{		
	}

	@Override
	public void start_ArrayList2String(String FileName, ArrayList<ArrayList<String>> ArrayList2Strings)
	{
		// TODO Auto-generated method stub
		
	}


}
