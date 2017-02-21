package Output;

import java.io.FileWriter;
import java.util.ArrayList;

import com.medallia.word2vec.Searcher.Match;

import Criteria.TTP;

public class Output_SaveArrayList2String implements Output_Interface
{
	public void start_ArrayList2String(String FileName, ArrayList<ArrayList<String>> ArrayList2Strings)
	{
		try
		{
			FileWriter writer = new FileWriter(FileName);
			for(ArrayList<String>list : ArrayList2Strings)
			{
				for(String s:list)
				{
					writer.write(s+"\n");
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


	@Override
	public void start_WordNum_output(String FileName, ArrayList<ArrayList<ArrayList<String>>> Words,
			ArrayList<ArrayList<ArrayList<Double>>> Numbers)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start_TTP_output(String FileName, ArrayList<TTP> TTPs)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start_Match_output(String FileName, ArrayList<ArrayList<ArrayList<ArrayList<Match>>>> Word2Vec)
	{
		// TODO Auto-generated method stub
		
	}

}
