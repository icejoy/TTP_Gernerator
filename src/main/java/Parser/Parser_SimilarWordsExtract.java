package Parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Criteria.TTP;
import Tool.CutWords;

public class Parser_SimilarWordsExtract implements Parser_Interface
{
	CutWords CW = new CutWords();
	ArrayList<TTP> TTPs = null;
	// [0] stix [1] ttp
	ArrayList<ArrayList<String>> Similar_Stix_Ttp = new ArrayList<ArrayList<String>>();

	public void parse(String reg, String content)
	{
		String str = "";
		Similar_Stix_Ttp.clear();
		for (TTP ttp : TTPs)
		{
			// 只針對stix 跟 ttp的description
			// 因為兩者是最大宗且最為重要的
			Similar_Stix_Ttp.add(new ArrayList<String>());
			if (ttp.get_stixCommon_Description() != null && ttp.get_ttp_Description() != null)
			{
				str = ttp.get_stixCommon_Description() + " " + ttp.get_ttp_Description();
				ArrayListStack(Similar_Stix_Ttp.size() - 1, this.CW.String_Split(str));
			}
			else if (ttp.get_stixCommon_Description() == null && ttp.get_ttp_Description() != null)
			{
				str = ttp.get_ttp_Description();
				ArrayListStack(Similar_Stix_Ttp.size() - 1, this.CW.String_Split(str));
			}
			else if (ttp.get_stixCommon_Description() != null && ttp.get_ttp_Description() == null)
			{
				str = ttp.get_stixCommon_Description();
				ArrayListStack(Similar_Stix_Ttp.size() - 1, this.CW.String_Split(str));
			}
			// ArrayListStack(0,
			// this.CW.String_Split(ttp.get_stixCommon_Description()));
			// ArrayListStack(1,
			// this.CW.String_Split(ttp.get_ttp_Description()));
		}
		SimilarExtract();
	}

	public void ArrayListStack(int index, ArrayList<String> temp)
	{
		if (temp.size() > 0)
		{
			for (String s : temp)
			{
				if (Similar_Stix_Ttp.get(index).size() > 0 && !Similar_Stix_Ttp.get(index).contains(s))
				{
					Similar_Stix_Ttp.get(index).add(s);
				}
				else if (Similar_Stix_Ttp.get(index).size() == 0)
				{
					Similar_Stix_Ttp.get(index).add(s);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void SimilarExtract()
	{
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>(Similar_Stix_Ttp);
		Similar_Stix_Ttp.clear();
		Set set=new HashSet();

		String word = "";
		Boolean have = false;
		for (int i = 0; i < temp.size(); i++)
		{
			for (int j = 0; j < temp.get(i).size(); j++)
			{
				word = temp.get(i).get(j);
				for (int k = 0; k < temp.size(); k++)
				{
					if (temp.get(k).contains(word) && k != i&&!word.equals("")&&!word.equals(" ")&& !word.matches("[0-9]+"))
					{
						set.add(word);
//						if (!Similar_Stix_Ttp.get(0).contains(word))
//						{
//							Similar_Stix_Ttp.get(0).add(word);
//						}
						have = true;
						Del_RepeatWord(word, temp.get(k));
					}
				}
				if (have)
				{
					have = false;
					Del_RepeatWord(word, temp.get(i));
					j--;
				}
			}
		}

		Similar_Stix_Ttp.add(new ArrayList<String>(set));
	}

	public void Del_RepeatWord(String word, ArrayList<String> temp)
	{
		// int count = 0;
		while (temp.contains(word))
		{
			// count++;
			temp.remove(temp.indexOf(word));
		}
		// System.out.println(count);
	}

	public ArrayList<ArrayList<String>> get_Stringresult()
	{
		return Similar_Stix_Ttp;
	}

	public void set_TTPs(ArrayList<TTP> TTPs)
	{
		this.TTPs = null;
		this.TTPs = new ArrayList<TTP>(TTPs);

		Similar_Stix_Ttp.clear();
		// [0] stix
		Similar_Stix_Ttp.add(new ArrayList<String>());
		// [1]ttp
		Similar_Stix_Ttp.add(new ArrayList<String>());
		for (TTP ttp : TTPs)
		{
			// 只針對stix 跟 ttp的description
			// 因為兩者是最大宗且最為重要的
			Similar_Stix_Ttp.get(0).add(ttp.get_stixCommon_Description());
			Similar_Stix_Ttp.get(1).add(ttp.get_ttp_Description());
		}
	}

	public ArrayList<TTP> get_TTPresult()
	{
		return null;
	}

	@Override
	public void set_ArrayList2String(ArrayList<ArrayList<String>> list)
	{
		// TODO Auto-generated method stub

	}
}
