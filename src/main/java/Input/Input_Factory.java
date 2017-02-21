package Input;

public class Input_Factory
{
	public Input_Interface choose_Input(String Type)
	{
		if (Type.equals("ReadFile"))
		{
			return new Input_FileReader();
		}
		else
		{
			return null;
		}
	}
}
