package Parser;

import java.util.ArrayList;

import Criteria.TTP;

public interface Parser_Interface
{
	public void parse(String reg,String content);

	public ArrayList<TTP> get_TTPresult();
	
	public ArrayList<ArrayList<String>> get_Stringresult();

	public void set_TTPs(ArrayList<TTP> TTPs);
	
	public void set_ArrayList2String(ArrayList<ArrayList<String>> list);
}
