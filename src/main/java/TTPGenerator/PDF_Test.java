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
import Parser.Parser_PrecisionRecallCalculate;
import Tool.StopWords_Clear;
import Tool.Word2Vec_VectorGenerator;

public class PDF_Test
{
	StopWords_Clear SWC;
	Word2Vec_VectorGenerator word2vec;

	Input_Interface input;
	ArrayList<Parser_Interface> parser;
	ArrayList<Output_Interface> output;

	ArrayList<ArrayList<String>> ParagraphList;
	ArrayList<ArrayList<String>> SimilarWords;
	ArrayList<ArrayList<ArrayList<ArrayList<Match>>>> W2V_Result;

	String DocumentFilePath = "";
	String SimilarFilePath = "";

	public PDF_Test(String Type, String filepath1, String filepath2)
	{
		this.SimilarWords = null;
		this.ParagraphList = null;
		this.W2V_Result = new ArrayList<ArrayList<ArrayList<ArrayList<Match>>>>();

		this.DocumentFilePath = filepath1;
		this.SimilarFilePath = filepath2;
		this.SWC = new StopWords_Clear();
		this.word2vec = new Word2Vec_VectorGenerator();

		Input_Factory IF = new Input_Factory();
		this.input = IF.choose_Input(Type);
		Parser_Factory PF = new Parser_Factory();
		this.parser = PF.choose_parse(this.DocumentFilePath);
		Output_Factory OF = new Output_Factory();
		this.output = OF.get_Output();
	}

	public void Start()
	{
		// TTP
		this.input.input("src/main/java/Input_data/Madiant_report.xml");
		this.parser.get(0).parse(null, this.input.get_input());
		this.parser.get(1).set_TTPs(this.parser.get(0).get_TTPresult());
		this.parser.get(1).parse(null, null);

		ArrayList<TTP> ttps = null;
		ttps = new ArrayList<TTP>(this.parser.get(1).get_TTPresult());
		this.parser.get(3).set_TTPs(ttps);
		// get document
		this.input.input(this.DocumentFilePath);
		this.parser.get(2).parse("\r\n\r\n\r\n", this.input.get_input());
		this.ParagraphList = new ArrayList<ArrayList<String>>(
				this.SWC.ParagraphClear(this.parser.get(2).get_Stringresult()));
		this.parser.get(3).set_ArrayList2String(this.ParagraphList);
		this.parser.get(3).parse("Precision", "src/main/java/Input_Data/SimilarWords_List.txt");
		this.output.get(3).start_ArrayList2String(true,"./output/Precision.txt", this.parser.get(3).get_Stringresult());

		// PI.add(new Parser_DocumentSplit());
		// PI.add(new Parser_RecallCalculate());

		// get similar list
		// this.input.input(this.SimilarFilePath);
		// this.parser.get(0).parse("\r\n", this.input.get_input());
		// this.SimilarWords = new ArrayList<ArrayList<String>>(
		// this.SWC.ParagraphClear(this.parser.get(0).get_Stringresult()));

		// recall and precision
		this.parser.get(3).set_ArrayList2String(this.ParagraphList);
		this.parser.get(3).parse("paragraph", "src/main/java/Input_Data/SimilarWords_List.txt");
		this.output.get(3).start_ArrayList2String(true,"./output/Document_recall.txt",
				this.parser.get(3).get_Stringresult());

		int Stix_TTP_count = -1;
		// [0] stix [1] ttp
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
		// W2V_Result.get(Stix_TTP_count)
		// .add(this.word2vec.Start(this.SimilarWords.get(Stix_TTP_count),
		// list.get(i)));
		// System.gc();
		// }
		// }
		// }
		// output.add(new Output_Excel());
		// output.add(new Output_Description());
		// output.add(new Output_W2V());
		// output.add(new Output_SaveArrayList2String());
		// this.output.get(2).start_Match_output("./Document_output.csv",
		// W2V_Result);
		// this.output.get(3).start_ArrayList2String("src/main/java/Input_Data/SimilarWords_List.txt",
		// this.SimilarWords);
	}

	public static void main(String[] args)
	{
		String filepath1 = "src/main/java/Data/Mandiant_APT1_Report_Clear.txt";
		String filepath2 = "src/main/java/Input_Data/SimilarWords_List.txt";
		PDF_Test test = new PDF_Test("ReadFile", filepath1, filepath2);
		test.Start();
		System.out.println("Done");
	}
}
