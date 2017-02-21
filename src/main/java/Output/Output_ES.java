package Output;

import java.net.InetAddress;
import java.util.ArrayList;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.medallia.word2vec.Searcher.Match;

import Criteria.TTP;

public class Output_ES implements Output_Interface
{
	TransportClient client;
	
	public void Open_ES(ArrayList<String> set)
	{
		// [0] ClusterName
		// [1] host
		// [2] port
		try
		{
			Settings settings = Settings.settingsBuilder().put("cluster.name", set.get(0)).build();
			client = TransportClient.builder().settings(settings).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(set.get(1)), Integer.valueOf(set.get(2))));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

	}
	
	public void Start_Save_ES()
	{
	}

	public void Close_ES()
	{
		client.close();
	}

	public void start_ArrayList2String(Boolean Cover,String FileName, ArrayList<ArrayList<String>> ArrayList2Strings)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void start_WordNum_output(Boolean Cover,String FileName, ArrayList<ArrayList<ArrayList<String>>> Words,
			ArrayList<ArrayList<ArrayList<Double>>> Numbers)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void start_TTP_output(Boolean Cover,String FileName, ArrayList<TTP> TTPs)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void start_Match_output(Boolean Cover,String FileName, ArrayList<ArrayList<ArrayList<ArrayList<Match>>>> Word2Vec)
	{
		// TODO Auto-generated method stub

	}

}
