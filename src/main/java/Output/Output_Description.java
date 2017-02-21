package Output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.medallia.word2vec.Searcher.Match;

import Criteria.TTP;

public class Output_Description implements Output_Interface
{

	public void start_WordNum_output(String FileName, ArrayList<ArrayList<ArrayList<String>>> Words,
			ArrayList<ArrayList<ArrayList<Double>>> Numbers)
	{
	}

	public void start_TTP_output(String FileName, ArrayList<TTP> TTPs)
	{
		try
		{
			FileWriter writer = new FileWriter(FileName);
			String[] description =
			{ "stix", "ttp", "cybox", "other" };
			for (int i = 0; i < description.length; i++)
			{
				writer.write(description[i]+"<br>");
				for(int j=0;j<TTPs.size();j++)
				{
					switch (i)
					{
						case 0:
							writer.write(TTPs.get(j).get_stixCommon_Description()+"<br>");
							break;
						case 1:
							writer.write(TTPs.get(j).get_ttp_Description()+"<br>");
							break;
						case 2:
							writer.write(TTPs.get(j).get_cyboxCommon_Description()+"<br>");
							break;
						case 3:
							writer.write(TTPs.get(j).get_other_Description()+"<br>");
							break;
						default:
							break;
					}
				}
				writer.write("<br>");
			}
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void start_Match_output(String FileName, ArrayList<ArrayList<ArrayList<ArrayList<Match>>>> Word2Vec)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start_ArrayList2String(String FileName, ArrayList<ArrayList<String>> ArrayList2Strings)
	{
		// TODO Auto-generated method stub
		
	}

}
