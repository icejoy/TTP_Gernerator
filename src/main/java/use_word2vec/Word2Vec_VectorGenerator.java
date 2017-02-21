package use_word2vec;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.medallia.word2vec.Searcher;
import com.medallia.word2vec.Word2VecModel;
import com.medallia.word2vec.Searcher.Match;
import com.medallia.word2vec.Searcher.UnknownWordException;
import com.medallia.word2vec.Word2VecTrainerBuilder.TrainingProgressListener;
import com.medallia.word2vec.neuralnetwork.NeuralNetworkType;
import com.medallia.word2vec.util.AutoLog;
import com.medallia.word2vec.util.Format;
import com.medallia.word2vec.util.ProfilingTimer;
import com.medallia.word2vec.util.ThriftUtils;

public class Word2Vec_VectorGenerator
{
	private static final Log LOG = AutoLog.getLog();
	Word2VecModel model = null;

	public ArrayList<ArrayList<Match>> Start(ArrayList<String> SimilarWords, String paragraph)
	{
		List<Match> temp;
		ArrayList<ArrayList<Match>> matches = new ArrayList<ArrayList<Match>>();

		RunWord2Vec(Clear_Paragraph(paragraph));
		for (String s : SimilarWords)
		{
			temp = interact(s, model.forSearch());
			if (temp != null)
			{
				matches.add(new ArrayList<Match>(temp));
			}
		}
		return matches;
	}

	@SuppressWarnings("finally")
	public List<Match> interact(String CompareWord, Searcher searcher)
	{
		List<Match> matches = null;
		try
		{
			matches = searcher.getMatches(CompareWord, 20);
		}
		catch (UnknownWordException a)
		{
			matches = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return matches;
		}
	}

	public String Clear_Paragraph(String paragraph)
	{
		paragraph = paragraph.replaceAll("[^a-zA-Z 0-9']", " ").toString();
		paragraph = paragraph.replaceAll("\\s\\s+", " ").toString();	
		return paragraph;
	}

	public void RunWord2Vec(String paragraph)
	{
		try
		{
			// 把要處理的內容丟入
			List<String> read = Arrays.asList(paragraph);
			// 將其依空格剪碎丟入list
			List<List<String>> partitioned = Lists.transform(read, new Function<String, List<String>>()
			{
				@Override
				public List<String> apply(String input)
				{
					return Arrays.asList(input.split(" "));
				}
			});

			// word2vec 宣告與設定
			model = Word2VecModel.trainer().setMinVocabFrequency(3).useNumThreads(20).setWindowSize(8)
					.type(NeuralNetworkType.CBOW).setLayerSize(200).useNegativeSamples(25).setDownSamplingRate(1e-4)
					.setNumIterations(5).setListener(new TrainingProgressListener()
					{
						@Override
						public void update(Stage stage, double progress)
						{
							System.out.println(
									String.format("%s is %.2f%% complete", Format.formatEnum(stage), progress * 100));
						}
					}).train(partitioned);

			// 把model寫進檔案裡 output
			try (ProfilingTimer timer = ProfilingTimer.create(LOG, "Writing output to file"))
			{
				FileUtils.writeStringToFile(new File("word2vec.model"), ThriftUtils.serializeJson(model.toThrift()));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
