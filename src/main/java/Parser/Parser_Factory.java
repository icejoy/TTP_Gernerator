package Parser;

import java.util.ArrayList;

public class Parser_Factory
{
	public ArrayList<Parser_Interface> choose_parse(String Full_FileName)
	{
		ArrayList<Parser_Interface> PI = new ArrayList<Parser_Interface>();
		if (Full_FileName.contains(".xml"))
		{
			PI.add(new Parser_TTPExtract());
			PI.add(new Parser_DescriptionClear());
			PI.add(new Parser_SimilarWordsExtract());
			PI.add(new Parser_PrecisionRecallCalculate());
			return PI;
		}
		else if (Full_FileName.contains(".txt"))
		{
			PI.add(new Parser_TTPExtract());
			PI.add(new Parser_DescriptionClear());
			PI.add(new Parser_DocumentSplit());
			PI.add(new Parser_PrecisionRecallCalculate());
			PI.add(new Parser_Paragraph_CommonWords_Compare());
			return PI;
		}
		else
		{
			return null;
		}
	}
}
