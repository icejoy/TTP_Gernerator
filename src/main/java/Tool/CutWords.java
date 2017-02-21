package Tool;

import java.util.ArrayList;

import Criteria.TTP;

public class CutWords
{
	// 每個TTP的每個Description的斷字
	public ArrayList<ArrayList<ArrayList<String>>> ArrayListTTP_CutWords(ArrayList<TTP> TTPs)
	{
		ArrayList<ArrayList<ArrayList<String>>> TTP_CutWords = new ArrayList<ArrayList<ArrayList<String>>>();
		for (TTP T : TTPs)
		{
			// 第n個TTP
			TTP_CutWords.add(new ArrayList<ArrayList<String>>());
			// stix
			if (T.get_stixCommon_Description() != null)
			{
				TTP_CutWords.get(TTP_CutWords.size() - 1).add(String_Split(T.get_stixCommon_Description()));
			}
			else
			{
				TTP_CutWords.get(TTP_CutWords.size() - 1).add(null);
			}
			// ttp
			if (T.get_ttp_Description() != null)
			{
				TTP_CutWords.get(TTP_CutWords.size() - 1).add(String_Split(T.get_ttp_Description()));
			}
			else
			{
				TTP_CutWords.get(TTP_CutWords.size() - 1).add(null);
			}
			// cybox
			if (T.get_cyboxCommon_Description() != null)
			{
				TTP_CutWords.get(TTP_CutWords.size() - 1).add(String_Split(T.get_cyboxCommon_Description()));
			}
			else
			{
				TTP_CutWords.get(TTP_CutWords.size() - 1).add(null);
			}
			// other
			if (T.get_other_Description() != null)
			{
				TTP_CutWords.get(TTP_CutWords.size() - 1).add(String_Split(T.get_other_Description()));
			}
			else
			{
				TTP_CutWords.get(TTP_CutWords.size() - 1).add(null);
			}
		}

		return TTP_CutWords;
	}

	public ArrayList<String> String_Split(String content)
	{
		ArrayList<String> temp = new ArrayList<String>();
		if (content != null)
		{
			content = content.replaceAll("[^a-zA-Z 0-9']", " ");
			// 不管多少個空白
			content = content.replaceAll("\\s\\s+", " ");
			// content = content.replace("]", " ");
			// content = content.replace("?", " ");
			// content = content.replace(".", " ");
			// content = content.replace("!", " ");
			// content = content.replace(":", " ");
			// content = content.replace("\"", " ");

			// 不管多少個空白
			// String[] str = content.split(" +");
			String[] str = content.split(" ");
			for (String s : str)
			{
				temp.add(s);
			}
		}
		return temp;
	}
}
