package Parser;

import java.util.ArrayList;

import Criteria.TTP;

public class Parser_TTPExtract implements Parser_Interface
{
	ArrayList<TTP> TTPs = new ArrayList<TTP>();

	public void parse(String reg,String content)
	{
		TTPs.clear();
		extract(content);
	}

	@SuppressWarnings("unused")
	public void extract(String content)
	{
		String[] str;
		String temp = "";
		int count_ttp = 0;
		boolean ttp_start = false;
		
		str = content.split("\r\n");
		for (int i = 0; i < str.length; i++)
		{
			if (!ttp_start && str[i].contains("<stix:TTP timestamp"))
			{
				ttp_start = true;
				count_ttp++;
				TTPs.add(new TTP());
				/*----timestamp----*/
				temp = str[i].split("timestamptimestamp=")[0];
				temp = temp.split("\"")[1];
				TTPs.get(TTPs.size() - 1).set_timestamp(temp);
			}
			else
			{
				if (ttp_start && str[i].contains("</stix:TTP>"))
				{
					ttp_start = false;
				}
				else
				{
					/*----title----*/
					if (str[i].contains("<ttp:Title>"))
					{
						temp = str[i].split("<ttp:Title>")[1];
						temp = temp.split("</ttp:Title>")[0];
						TTPs.get(TTPs.size() - 1).set_title(temp);
					}
					/*----description----*/
					else if (str[i].contains("Description"))
					{
						if (str[i].contains("<stixCommon:Description"))
						{
							temp = str[i].split(">", 2)[1];
							for (; !str[i].contains("</stixCommon:Description>"); i++)
							{
								temp += str[i];
							}
							temp += str[i].split("</stixCommon:Description>")[0];
							TTPs.get(TTPs.size() - 1).set_stixCommon_Description(temp);
						}
						else if (str[i].contains("<ttp:Description"))
						{
							temp = str[i].split(">", 2)[1];
							for (; !str[i].contains("</ttp:Description>"); i++)
							{
								temp += str[i];
							}
							temp += str[i].split("</ttp:Description>")[0];
							TTPs.get(TTPs.size() - 1).set_ttp_Description(temp);
						}
						else if (str[i].contains("<cyboxCommon:Description"))
						{
							temp = str[i].split(">", 2)[1];
							for (; !str[i].contains("</cyboxCommon:Description>"); i++)
							{
								temp += str[i];
							}
							temp += str[i].split("</cyboxCommon:Description>")[0];
							TTPs.get(TTPs.size() - 1).set_cyboxCommon_Description(temp);
						}
						else
						{
							if(str[i].contains(":Description "))
							{
								temp = str[i].split(">", 2)[1];
								for (; !str[i].contains("Description>"); i++)
								{
									temp += str[i];
								}
								temp += str[i].split("Description>")[0];
								TTPs.get(TTPs.size() - 1).set_other_Description(temp);
							}
						}
					}
				}
			}
		}
	}

	public ArrayList<TTP> get_TTPresult()
	{
		return TTPs;
	}

	public void set_TTPs(ArrayList<TTP> TTPs)
	{
		// TODO Auto-generated method stub
		
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
