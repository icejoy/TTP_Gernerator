package TTPGenerator;

import java.util.ArrayList;

import com.medallia.word2vec.Searcher.Match;

import Criteria.TTP;
import Input.Input_Factory;
import Input.Input_Interface;
import Output.Output_Factory;
import Output.Output_Interface;
import Parser.Parser_Factory;
import Parser.Parser_Interface;
import Tool.StanfordNLP_Lemma;
import Tool.StopWords_Clear;
import Tool.Word2Vec_VectorGenerator;

public class TTP_Test
{
	StopWords_Clear SWC;
	Word2Vec_VectorGenerator word2vec;
	StanfordNLP_Lemma lemma;

	Input_Interface input;
	ArrayList<Parser_Interface> parser;
	ArrayList<Output_Interface> output;

	ArrayList<TTP> ttps;
	ArrayList<ArrayList<String>> Lemma_Clear;
	ArrayList<ArrayList<String>> ParagraphList;
	ArrayList<ArrayList<String>> SimilarWords;
	ArrayList<ArrayList<ArrayList<ArrayList<Match>>>> W2V_Result;

	String FilePath = "";

	public TTP_Test(String Type, String filepath)
	{
		this.ttps = null;
		this.SimilarWords = null;
		this.ParagraphList = null;
		this.Lemma_Clear = null;
		this.W2V_Result = new ArrayList<ArrayList<ArrayList<ArrayList<Match>>>>();

		this.FilePath = filepath;
		this.SWC = new StopWords_Clear();
		this.word2vec = new Word2Vec_VectorGenerator();
		this.lemma = new StanfordNLP_Lemma();

		Input_Factory IF = new Input_Factory();
		this.input = IF.choose_Input(Type);
		Parser_Factory PF = new Parser_Factory();
		this.parser = PF.choose_parse(this.FilePath);
		Output_Factory OF = new Output_Factory();
		this.output = OF.get_Output();
	}

	public void Start()
	{
		// TTP
		this.input.input(this.FilePath);
		// parser[0] extract
		// parser[1] description clear
		// parser[2] similar words extract
		// parser[3] recall calculate
		this.parser.get(0).parse(null, this.input.get_input());
		this.parser.get(1).set_TTPs(this.parser.get(0).get_TTPresult());
		this.parser.get(1).parse(null, null);
		this.ttps = new ArrayList<TTP>(this.parser.get(1).get_TTPresult());
		this.parser.get(2).set_TTPs(ttps);
		// 由段落組成的ArrayList [0] stix [1] ttp
//		this.Lemma_Clear = new ArrayList<ArrayList<String>>(
//				this.lemma.Lemma_Paragraph_Clear(this.parser.get(2).get_Stringresult()));
//		this.ParagraphList = new ArrayList<ArrayList<String>>(this.SWC.ParagraphClear(this.Lemma_Clear));
		// 各類別的相似詞
		this.parser.get(2).parse(null, null);
		this.Lemma_Clear=null;
		this.Lemma_Clear = new ArrayList<ArrayList<String>>(
				this.lemma.Lemma_ArrayList_Clear(this.parser.get(2).get_Stringresult()));
		this.SimilarWords = new ArrayList<ArrayList<String>>(this.SWC.ArrayListClear(this.Lemma_Clear));
		this.output.get(3).start_ArrayList2String(true, "src/main/java/Input_Data/SimilarWords_List.txt",
				this.SimilarWords);

		// recall and precision
		this.parser.get(3).set_TTPs(ttps);
		this.parser.get(3).parse("TTP", "src/main/java/Input_Data/SimilarWords_List.txt");
		this.output.get(3).start_ArrayList2String(true, "./output/TTP_recall_50.txt",
				this.parser.get(3).get_Stringresult());

		// int Stix_TTP_count = -1;
		// // [0] stix [1] ttp
		// for (ArrayList<String> list : this.ParagraphList)
		// {
		// W2V_Result.add(new ArrayList<ArrayList<ArrayList<Match>>>());
		// Stix_TTP_count++;
		// // 根據每個stix 或 ttp 去跑
		// for (int i = 0; i < list.size(); i++)
		// {
		// if (list.get(i) != null)
		// {
		// // 對每個段落
		// W2V_Result.get(Stix_TTP_count).add(this.word2vec.Start(this.SimilarWords.get(0),
		// list.get(i)));
		// System.gc();
		// }
		// }
		// }
		// output.add(new Output_Excel());
		// output.add(new Output_Description());
		// output.add(new Output_W2V());
		// output.add(new Output_SaveArrayList2String());
		// this.output.get(2).start_Match_output("./output/TTP_W2V_output.csv",
		// W2V_Result);
	}

	public static void main(String[] args)
	{
		String filepath = "src/main/java/Input_data/Madiant_report.xml";
		TTP_Test test = new TTP_Test("ReadFile", filepath);
		test.Start();

		System.out.println("Done");
	}

}
