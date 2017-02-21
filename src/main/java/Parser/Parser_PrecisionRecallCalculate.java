package Parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.cli.CliParser.incrementValue_return;

import com.medallia.word2vec.util.Common;

import Criteria.TTP;
import Tool.StopWords_Clear;

public class Parser_PrecisionRecallCalculate implements Parser_Interface
{
	Double math = 0.0;
	int SizeCount = 0;
	int a = 0;

	List<String> read;
	ArrayList<ArrayList<String>> list;
	ArrayList<TTP> TTPs;
	ArrayList<ArrayList<String>> Result = new ArrayList<ArrayList<String>>();

	StopWords_Clear SWC = new StopWords_Clear();

	public void Read_SimilarWordsFile(String filepath)
	{
		try
		{
			File f = new File(filepath);
			read = Common.readToList(f);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void parse(String reg, String content)
	{
		Read_SimilarWordsFile(content);

		if (reg.equals("TTP"))
		{
			TTP_CountRecall();
		}
		else if (reg.equals("paragraph"))
		{
			Paragraph_CountRecall();
		}
		else if (reg.equals("Precision"))
		{
			Calculate_Precision();
		}
	}

	public void Calculate_Precision()
	{
		int document_count = 0;
		int ttp_count = 0;
		this.Result.clear();
		this.Result.add(new ArrayList<String>());
		this.Result.get(0).add("Precision is : ");
		for (String s : read)
		{
			a = 0;
			for (TTP ttp : TTPs)
			{
				if (ttp.get_stixCommon_Description() != null
						&& ttp.get_stixCommon_Description().toLowerCase().contains(s))
				{
					a++;
				}
				else if (ttp.get_ttp_Description() != null && ttp.get_ttp_Description().toLowerCase().contains(s))
				{
					a++;
				}
			}
			ttp_count = a;
			a = 0;
			for (ArrayList<String> l : list)
			{
				for (String s1 : l)
				{
					if (s1.toLowerCase().contains(" " + s + " "))
					{
						a++;
					}
				}
			}
			document_count = a;
			if (ttp_count < 2)
			{
				System.out.println(s);
			}
			math = ((double) (ttp_count) / (double) (document_count + ttp_count)) * 100;
			this.Result.get(0).add(s + ": " + math.toString());
		}
	}

	public void Paragraph_CountRecall()
	{
		this.Result.clear();
		SizeCount = 0;
		for (ArrayList<String> s : list)
		{
			SizeCount += s.size();
		}
		this.Result.add(new ArrayList<String>());
		this.Result.get(0).add("recall is : ");
		for (String s : read)
		{
			a = 0;
			for (ArrayList<String> l : list)
			{
				for (String s1 : l)
				{
					if (s1.toLowerCase().contains(" " + s + " "))
					{
						a++;
					}
				}
			}
			math = ((double) (a) / (double) (SizeCount)) * 100;
			if (math > 50)
			{
				this.Result.get(0).add(s + ": " + math.toString());
			}
		}
	}

	public void TTP_CountRecall()
	{
		this.Result.clear();
		SizeCount = TTPs.size();
		// for (TTP ttp : TTPs)
		// {
		// if (ttp.get_stixCommon_Description() != null)
		// {
		// count_stix++;
		// }
		// if (ttp.get_ttp_Description() != null)
		// {
		// count_ttp++;
		// }
		// }
		// System.out.println("stix:" + count_stix);
		// System.out.println("ttp:" + count_ttp);
		this.Result.add(new ArrayList<String>());
		this.Result.get(0).add("recall is : ");
		for (String s : read)
		{
			a = 0;
			for (TTP ttp : TTPs)
			{
				if (ttp.get_stixCommon_Description() != null
						&& ttp.get_stixCommon_Description().toLowerCase().contains(s))
				{
					a++;
				}
				else if (ttp.get_ttp_Description() != null && ttp.get_ttp_Description().toLowerCase().contains(s))
				{
					a++;
				}
			}
			math = ((double) (a) / (double) (SizeCount)) * 100;
			if (math > 50)
			{
				this.Result.get(0).add(s + ": " + math.toString());
			}
		}
	}

	public ArrayList<TTP> get_TTPresult()
	{
		return null;
	}

	public ArrayList<ArrayList<String>> get_Stringresult()
	{
		return this.Result;
	}

	public void set_TTPs(ArrayList<TTP> TTPs)
	{
		this.TTPs = null;
		this.TTPs = new ArrayList<TTP>(TTPs);
	}

	public void set_ArrayList2String(ArrayList<ArrayList<String>> list)
	{
		this.list = null;
		this.list = new ArrayList<ArrayList<String>>(list);
	}

}
