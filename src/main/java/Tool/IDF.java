package Tool;

import java.util.ArrayList;

public class IDF
{
	ArrayList<ArrayList<ArrayList<String>>> All_TTP_Words;
	ArrayList<ArrayList<String>> All_Words = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<Double>> All_Count = new ArrayList<ArrayList<Double>>();

	public void Start_IDF(ArrayList<ArrayList<ArrayList<String>>> TTP_Words)
	{
		this.All_TTP_Words = null;
		this.All_TTP_Words = new ArrayList<ArrayList<ArrayList<String>>>(TTP_Words);
		if (All_Words.size() < 1)
		{
			Scan_All_Words();
		}
		Scan_All_IDF();
	}

	public void Scan_All_IDF()
	{
		ArrayList<Integer> result;
		for (int i = 0; i < this.All_Words.size(); i++)
		{
			this.All_Count.add(new ArrayList<Double>());
			for (int j = 0; j < this.All_Words.get(i).size(); j++)
			{
				result = null;
				result = new ArrayList<Integer>(Scan_TTP(i, this.All_Words.get(i).get(j)));
				this.All_Count.get(i).add(new Double(result.get(0)) / new Double(result.get(1)));
			}
		}
	}

	public ArrayList<Integer> Scan_TTP(int index, String str)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		int count = 0, total_description = 0;
		for (int i = 0; i < this.All_TTP_Words.size(); i++)
		{
			if (this.All_TTP_Words.get(i).get(index)!=null&&this.All_TTP_Words.get(i).get(index).size() > 0)
			{
				total_description++;
				if (this.All_TTP_Words.get(i).get(index).contains(str))
				{
					count++;
				}
			}
		}
		result.add(total_description);
		result.add(count);
		return result;
	}

	public void Scan_All_Words()
	{
		// 每個Description
		for (int i = 0; i < this.All_TTP_Words.get(0).size(); i++)
		{
			this.All_Words.add(new ArrayList<String>());
			// 每個TTP
			for (int j = 0; j < this.All_TTP_Words.size(); j++)
			{
				// 如果裡面有東西
				if (All_TTP_Words.get(j).get(i) != null && this.All_TTP_Words.get(j).get(i).size() > 0)
				{
					for (int k = 0; k < this.All_TTP_Words.get(j).get(i).size(); k++)
					{
						if (!this.All_Words.get(i).contains(this.All_TTP_Words.get(j).get(i).get(k)))
						{
							this.All_Words.get(i).add(this.All_TTP_Words.get(j).get(i).get(k));
						}
					}
				}
			}
		}
	}

	public ArrayList<ArrayList<String>> get_All_Words()
	{
		return this.All_Words;
	}

	public ArrayList<ArrayList<Double>> get_All_Count()
	{
		return this.All_Count;
	}
}
