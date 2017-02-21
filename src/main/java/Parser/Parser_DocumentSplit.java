package Parser;

import java.util.ArrayList;

import Criteria.TTP;

public class Parser_DocumentSplit implements Parser_Interface
{
	ArrayList<ArrayList<String>> document = new ArrayList<ArrayList<String>>();

	public void parse(String reg, String content)
	{
		if (reg.equals("SimilarWords"))
		{
			SimilarWords_Split(content);
		}
		else
		{
			Document_Split(reg, content);
		}
	}

	public void SimilarWords_Split(String content)
	{
		document.clear();
		document.add(new ArrayList<String>());
		String[] str = content.split("\r\n");
		for (String s : str)
		{
			if (!s.equals("") && !s.equals(" "))
			{
				document.get(0).add(s);
			}
		}
	}

	public void Document_Split(String reg, String content)
	{
		document.clear();
		document.add(new ArrayList<String>());
		String[] temp = content.split(reg);
		for (String s : temp)
		{
			if (s.toCharArray().length > 0)
			{
				s = s.replaceAll("\\s\\s+", " ");
				if (s.toCharArray().length > 50)
				{
					document.get(0).add(s);
				}
			}
		}
		System.out.println("document size:" + document.get(0).size());
	}

	public ArrayList<TTP> get_TTPresult()
	{
		return null;
	}

	public ArrayList<ArrayList<String>> get_Stringresult()
	{
		return document;
	}

	public void set_TTPs(ArrayList<TTP> TTPs)
	{

	}

	@Override
	public void set_ArrayList2String(ArrayList<ArrayList<String>> list)
	{
		// TODO Auto-generated method stub

	}

}
