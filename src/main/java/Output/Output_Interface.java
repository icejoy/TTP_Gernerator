package Output;

import java.util.ArrayList;

import com.medallia.word2vec.Searcher.Match;

import Criteria.TTP;

public interface Output_Interface
{
	public void start_ArrayList2String(Boolean Cover,String FileName,ArrayList<ArrayList<String>> ArrayList2Strings);
	
	public void start_WordNum_output(Boolean Cover,String FileName, ArrayList<ArrayList<ArrayList<String>>> Words,
			ArrayList<ArrayList<ArrayList<Double>>> Numbers);
	
	public void start_TTP_output(Boolean Cover,String FileName,ArrayList<TTP> TTPs);
	
	public void start_Match_output(Boolean Cover,String FileName,ArrayList<ArrayList<ArrayList<ArrayList<Match>>>> Word2Vec);
}
