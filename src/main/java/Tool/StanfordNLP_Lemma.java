package Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import edu.stanford.nlp.util.CoreMap;

public class StanfordNLP_Lemma
{
	Annotation document;
	List<CoreMap> sentences;
	Properties props;
	StanfordCoreNLP pipeline;

	public StanfordNLP_Lemma()
	{
		this.props = null;
		this.props = new Properties();
		this.props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

		this.pipeline = null;
		this.pipeline = new StanfordCoreNLP(props);
	}

	public ArrayList<ArrayList<String>> Lemma_Paragraph_Clear(ArrayList<ArrayList<String>> content)
	{
		String outputStr = "";
		for (int a = 0; a < content.size(); a++)
		{
			for (int i = 0; i < content.get(a).size(); i++)
			{
				if (content.get(a).get(i) != null)
				{
					outputStr = "";
					document = null;
					document = new Annotation(content.get(a).get(i));
					pipeline.annotate(document);
					sentences = document.get(SentencesAnnotation.class);
					for (CoreMap sentence : sentences)
					{
						// traversing the words in the current sentence
						// a CoreLabel is a CoreMap with additional
						// token-specific
						// methods
						for (CoreLabel token : sentence.get(TokensAnnotation.class))
						{
							String lema = token.get(LemmaAnnotation.class);
							outputStr += lema + " ";
						}

					}
					content.get(a).set(i, outputStr);
				}
			}
		}
		return content;
	}
	
	public ArrayList<ArrayList<String>> Lemma_ArrayList_Clear(ArrayList<ArrayList<String>> content)
	{
		String outputStr = "";
		for (int a = 0; a < content.size(); a++)
		{
			for (int i = 0; i < content.get(a).size(); i++)
			{
				if (content.get(a).get(i) != null)
				{
					outputStr = "";
					document = null;
					document = new Annotation(content.get(a).get(i));
					pipeline.annotate(document);
					sentences = document.get(SentencesAnnotation.class);
					for (CoreMap sentence : sentences)
					{
						// traversing the words in the current sentence
						// a CoreLabel is a CoreMap with additional
						// token-specific
						// methods
						for (CoreLabel token : sentence.get(TokensAnnotation.class))
						{
							String lema = token.get(LemmaAnnotation.class);
							outputStr = lema ;
						}

					}
					content.get(a).set(i, outputStr);
				}
			}
		}
		return content;
	}
	// public String stemmed(String inputStr)
	// {
	// Annotation document = new Annotation(inputStr);
	// pipeline.annotate(document);
	// List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	//
	// String outputStr = "";
	// for (CoreMap sentence : sentences)
	// {
	// // traversing the words in the current sentence
	// // a CoreLabel is a CoreMap with additional token-specific methods
	// for (CoreLabel token : sentence.get(TokensAnnotation.class))
	// {
	// String lema = token.get(LemmaAnnotation.class);
	// outputStr += lema + " ";
	// }
	//
	// }
	// return outputStr;
	// }

	// public static void main(String[] args)
	// {
	//
	// StanfordNLP_Core lemma = new StanfordNLP_Core();
	// String input = "jack had been to china there months ago. he likes china
	// very much,and he is falling love with this country";
	// String output = lemma.stemmed(input);
	// System.out.print("原句 ：");
	// System.out.println(input);
	// System.out.print("词干化：");
	// System.out.println(output);
	//
	// }

}