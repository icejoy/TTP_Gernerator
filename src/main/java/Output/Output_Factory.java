package Output;

import java.util.ArrayList;

public class Output_Factory
{
	public ArrayList<Output_Interface> get_Output()
	{
		ArrayList<Output_Interface> output = new ArrayList<Output_Interface>();
		output.add(new Output_Excel());
		output.add(new Output_Description());
		output.add(new Output_W2V());
		output.add(new Output_SaveArrayList2String());
		return output;
	}
}
