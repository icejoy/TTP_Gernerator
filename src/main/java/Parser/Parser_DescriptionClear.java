package Parser;

import java.util.ArrayList;

import Criteria.TTP;

public class Parser_DescriptionClear implements Parser_Interface
{
	ArrayList<TTP> TTPs = null;

	public void parse(String reg,String content)
	{
		Clear_HTMLTag();
	}

	public void Clear_HTMLTag()
	{
		String temp = "";
		for (int i = 0; i < TTPs.size(); i++)
		{
			if (TTPs.get(i).get_stixCommon_Description() != null)
			{
				temp = REPLACE(TTPs.get(i).get_stixCommon_Description());
				TTPs.get(i).set_stixCommon_Description(temp);
			}
			if (TTPs.get(i).get_ttp_Description() != null)
			{
				temp = REPLACE(TTPs.get(i).get_ttp_Description());
				TTPs.get(i).set_ttp_Description(temp);
			}
			if (TTPs.get(i).get_cyboxCommon_Description() != null)
			{
				temp = REPLACE(TTPs.get(i).get_cyboxCommon_Description());
				TTPs.get(i).set_cyboxCommon_Description(temp);
			}
			if (TTPs.get(i).get_other_Description() != null)
			{
				temp = REPLACE(TTPs.get(i).get_other_Description());
				TTPs.get(i).set_other_Description(temp);
			}

		}
	}

	public String REPLACE(String Words)
	{
		Words = Words.toLowerCase();
		Words = Words.replace("\t", " ");
		Words = Words.replace(",", " ");
		Words = Words.replace("<p>", " ");
		Words = Words.replace("<html>", " ");
		Words = Words.replace("</html>", " ");
		Words = Words.replace("<body>", " ");
		Words = Words.replace("</body>", " ");
		Words = Words.replace("<![cdata[<!doctype html>"," ");
		
		if (Words.contains("<code>"))
		{
			String[] str = Words.split("<code>");
			Words="";
			for(String s: str)
			{
				if(s.contains("</code>"))
				{
					for(String s1:s.split("</code>"))
					{
						Words+=s1+" ";
					}
				}
				else
				{
					Words+=s+" ";
				}
			}
		}
		
		Words=Words.replaceAll("\\s\\s+", " ");

		char[] word_array = Words.toCharArray();
		Words = "";
		int Can_Save = 0;
		for (char c : word_array)
		{
			if (c == '<')
			{
				Can_Save++;
			}
			else if (c == '>')
			{
				Can_Save--;
			}
			else if (Can_Save == 0)
			{
				Words += c;
			}
		}

		return Words;
	}

	public ArrayList<TTP> get_TTPresult()
	{
		return TTPs;
	}

	public void set_TTPs(ArrayList<TTP> TTPs)
	{
		this.TTPs = null;
		this.TTPs = new ArrayList<TTP>(TTPs);

	}

	@Override
	public ArrayList<ArrayList<String>> get_Stringresult()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set_ArrayList2String(ArrayList<ArrayList<String>> list)
	{
		// TODO Auto-generated method stub
		
	}

}
