package TTPGenerator;

import java.util.ArrayList;

import com.medallia.word2vec.Searcher.Match;

import Input.Input_Factory;
import Input.Input_Interface;
import Output.Output_Factory;
import Output.Output_Interface;
import Parser.Parser_Factory;
import Parser.Parser_Interface;
import Parser.Parser_Paragraph_CommonWords_Compare;
import Tool.StopWords_Clear;
import Tool.Word2Vec_VectorGenerator;

public class Training
{
	StopWords_Clear SWC;
	Word2Vec_VectorGenerator word2vec;

	Input_Interface input;
	ArrayList<Parser_Interface> parse;
	ArrayList<Output_Interface> output;

	String CommonWord_FilePath = "";
	ArrayList<String> CommonWords;
	String Document_FilePath = "";
	ArrayList<String> Document_Paragraph;
	ArrayList<ArrayList<ArrayList<ArrayList<Match>>>> w2v_result;

	public void init()
	{
		// ArrayList
		this.CommonWords = null;
		this.Document_Paragraph = null;
		this.w2v_result = null;
		// String
		this.Document_FilePath = "";
		this.CommonWord_FilePath = "";
		// class
		this.SWC = null;
		this.SWC = new StopWords_Clear();
	}

	public Training(String Type, String common, String document)
	{
		init();

		this.Document_FilePath = document;
		this.CommonWord_FilePath = common;
		this.word2vec = new Word2Vec_VectorGenerator();
		this.w2v_result = new ArrayList<ArrayList<ArrayList<ArrayList<Match>>>>();
		// new Input_FileReader();
		Input_Factory IF = new Input_Factory();
		this.input = IF.choose_Input(Type);

		// [0] PI.add(new Parser_TTPExtract());
		// [1] PI.add(new Parser_DescriptionClear());
		// [2] PI.add(new Parser_DocumentSplit());
		// [3] PI.add(new Parser_PrecisionRecallCalculate());
		// [4] PI.add(new Parser_Paragraph_CommonWords_Compare());
		Parser_Factory PF = new Parser_Factory();
		this.parse = PF.choose_parse(document);

		// output.add(new Output_Excel());
		// output.add(new Output_Description());
		// output.add(new Output_W2V());
		// output.add(new Output_SaveArrayList2String());
		Output_Factory OF = new Output_Factory();
		this.output = OF.get_Output();

	}

	public void Start()
	{
		// Common Word List
		this.input.input(this.CommonWord_FilePath);
		this.parse.get(2).parse("SimilarWords", this.input.get_input());
		this.CommonWords = new ArrayList<String>(this.parse.get(2).get_Stringresult().get(0));

		// Document
		this.input.input(this.Document_FilePath);
		this.parse.get(2).parse("\r\n\r\n", this.input.get_input());
		this.Document_Paragraph = new ArrayList<String>(this.parse.get(2).get_Stringresult().get(0));
		// this.Document_Paragraph = new ArrayList<String>(
		// this.SWC.ParagraphClear(this.parse.get(2).get_Stringresult()).get(0));

		// get common paragrap
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		temp.add(this.CommonWords);
		temp.add(this.Document_Paragraph);
		this.parse.get(4).set_ArrayList2String(temp);
		this.parse.get(4).parse("3", "mix");

		this.output.get(3).start_ArrayList2String(true,"./output/Document_CommonWords_mix_output.html",
				this.parse.get(4).get_Stringresult());

		// run w2v
		// this.w2v_result.add(new ArrayList<ArrayList<ArrayList<Match>>>());
		// for (String s : this.Document_Paragraph)
		// {
		// System.gc();
		// this.w2v_result.get(0).add(word2vec.Start(this.CommonWords, s));
		// System.gc();
		// }
		// this.output.get(2).start_Match_output("./output/Document_W2V_output.csv",
		// this.w2v_result);
	}

	public static void main(String[] args)
	{
//		System.gc();
		// String XmlFilePath= "src/main/java/Input_data/Madiant_report.xml";
		String CommonWordListFilePath = "src/main/java/Input_Data/SimilarWords_List.txt";
		String DocumentFilePath = "src/main/java/Input_Data/mcafee_2016.txt";

		Training t = new Training("ReadFile", CommonWordListFilePath, DocumentFilePath);
		t.Start();
		System.out.println("Done");
	}
}
