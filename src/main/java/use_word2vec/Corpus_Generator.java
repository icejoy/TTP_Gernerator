package use_word2vec;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.medallia.word2vec.util.Common;

public class Corpus_Generator
{
	String Corpus_FileName = "src/main/java/Data_Clear/Corpus.txt";

	public static void main(String[] args)
	{
		Corpus_Generator TM = new Corpus_Generator();
		TM.demoW2V();

	}

	public void demoW2V()
	{
		InputCorpus_Clear("src/main/java/Data/Mandiant_APT1_Report.txt");
	}

	public void InputCorpus_Clear(String FileName)
	{
		try
		{
			Boolean open_corpus = false;
			File f = new File(FileName);
			List<String> read = Common.readToList(f);
			FileName = FileName.replace("/Data/", "/Data_Clear/");
			FileWriter writer1 = new FileWriter(FileName);
			FileWriter writer2 = null;
			if (!new File(FileName).exists())
			{
				open_corpus = true;
				writer2 = new FileWriter(Corpus_FileName, true);
			}
			for (String s : read)
			{
				s = REPLACE(s);
				writer1.write(s);
				if (open_corpus)
				{
					writer2.write(s);
				}
			}
			writer1.flush();
			writer1.close();
			if (open_corpus)
			{
				writer2.flush();
				writer2.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String REPLACE(String input)
	{
		input = input.replaceAll("[^a-zA-Z 0-9]", " ").toString();
		input=input.replaceAll("\\s\\s+", " ").toString();
		return input;
	}
}
