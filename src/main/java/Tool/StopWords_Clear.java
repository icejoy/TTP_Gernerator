package Tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.medallia.word2vec.util.Common;

public class StopWords_Clear
{
	List<String> read;
	String StopWords_FilePath = "src/main/java/Input_Data/stop-word-list.txt";

	public ArrayList<ArrayList<String>> ParagraphClear(ArrayList<ArrayList<String>> Target)
	{
		Read_StopWordsFile();
		for (String s : read)
		{
			for (int i = 0; i < Target.size(); i++)
			{
				for (int j = 0; j < Target.get(i).size(); j++)
				{
					if (Target.get(i).get(j) != null)
					{
						Target.get(i).set(j, Target.get(i).get(j).replaceAll("[^a-zA-Z 0-9']", " "));
						Target.get(i).set(j,Target.get(i).get(j).replaceAll("\\s\\s+", " "));
						if (Target.get(i).get(j).contains(s))
						{
							Target.get(i).set(j,Target.get(i).get(j).replaceAll(" " + s + " ", " "));
						}
					}
				}
				// while (Target.get(i).indexOf(s) > -1)
				// {
				// Target.get(i).remove(Target.get(i).indexOf(s));
				// }
			}
		}
		return Target;
	}

	public ArrayList<ArrayList<String>> ArrayListClear(ArrayList<ArrayList<String>> Target)
	{
		Read_StopWordsFile();
		for (String s : read)
		{
			for (int i = 0; i < Target.size(); i++)
			{
				while (Target.get(i).indexOf(s) > -1)
				{
					Target.get(i).remove(Target.get(i).indexOf(s));

				}
			}
		}
		for(int i=0;i<Target.size();i++)
		{
			for(int j=0;j<Target.get(i).size();j++)
			{
				if(Target.get(i).get(j).matches("[a-zA-Z]"))
				{
					Target.get(i).remove(j);
				}
				else if(Target.get(i).get(j).matches("[0-9]"))
				{
					Target.get(i).remove(j);
				}
			}
		}
		return Target;
	}

	public void Read_StopWordsFile()
	{
		try
		{
			File f = new File(StopWords_FilePath);
			read = Common.readToList(f);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
