package Parser;

import java.util.ArrayList;

import Criteria.TTP;

public class Parser_Paragraph_CommonWords_Compare implements Parser_Interface
{
	ArrayList<String> CommonWords;
	ArrayList<String> Paragraph;
	ArrayList<ArrayList<String>> Compare_Paragraph;

	public void parse(String reg, String content)
	{
		if (content == "single")
		{
			Single_CommonWord_Compare();
		}
		else if (content == "mix")
		{
			Multiple_CommonWord_Compare(Integer.valueOf(reg));
		}
	}

	public void Single_CommonWord_Compare()
	{
		int count = 1;
		Compare_Paragraph = null;
		Compare_Paragraph = new ArrayList<ArrayList<String>>();
		Compare_Paragraph.add(new ArrayList<String>());
		for (String s : this.CommonWords)
		{
			count = 1;
			this.Compare_Paragraph.get(0).add("Common Word " + s + " :<br>");
			for (int i = 0; i < this.Paragraph.size(); i++)
			{
				if (this.Paragraph.get(i).contains(s))
				{
					this.Compare_Paragraph.get(0).add("paragraph " + count + " is :<br>");
					this.Compare_Paragraph.get(0).add(this.Paragraph.get(i) + "<br>");
					count++;
				}
			}
		}
	}

	public void Multiple_CommonWord_Compare(int reg)
	{
		int count = 0;
		String CommWord = "";
		Compare_Paragraph = null;
		Compare_Paragraph = new ArrayList<ArrayList<String>>();

		Compare_Paragraph.add(new ArrayList<String>());
		for (int i = 0; i < this.Paragraph.size(); i++)
		{
			count = 0;
			for (String common : this.CommonWords)
			{
				if (this.Paragraph.get(i).indexOf(common)>-1)
				{
					count++;
					CommWord += common + ",";
				}
			}
			if (count >= reg)
			{
				Compare_Paragraph.get(0).add("CommWords :" + CommWord + "<br>Paragraph "+(Compare_Paragraph.get(0).size()+1)+" is :<br>" + this.Paragraph.get(i)+"<p>");
				this.Paragraph.remove(i);
				CommWord="";
				i--;
			}
		}
	}

	public ArrayList<TTP> get_TTPresult()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ArrayList<String>> get_Stringresult()
	{
		return this.Compare_Paragraph;
	}

	@Override
	public void set_TTPs(ArrayList<TTP> TTPs)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void set_ArrayList2String(ArrayList<ArrayList<String>> list)
	{
		// [0] common words
		// [1] paragraph
		this.CommonWords = null;
		this.Paragraph = null;
		this.CommonWords = new ArrayList<String>(list.get(0));
		this.Paragraph = new ArrayList<String>(list.get(1));
	}

}
