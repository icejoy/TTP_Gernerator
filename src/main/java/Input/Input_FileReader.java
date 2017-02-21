package Input;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Input_FileReader implements Input_Interface
{
	String input;

	public void input(String Full_FileName)
	{
		try
		{
			input="";
			BufferedReader read = new BufferedReader(
					new InputStreamReader(new FileInputStream(Full_FileName)));

			while (read.ready())
			{
				input += read.readLine() + "\r\n";
			}
			read.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String get_input()
	{
		return input;
	}
}
