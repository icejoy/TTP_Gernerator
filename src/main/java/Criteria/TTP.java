package Criteria;

public class TTP
{
	String timestamp;
	String title;
	String stixCommon_Description;//<stixCommon:Description
	String ttp_Description;//<ttp:Description
	String cyboxCommon_Description;//<cyboxCommon:Description
	String other_Description;

	public void set_timestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String get_timestamp()
	{
		return this.timestamp;
	}

	public void set_title(String title)
	{
		this.title = title;
	}

	public String get_title()
	{
		return this.title;
	}

	public void set_stixCommon_Description(String stixCommon_Description)
	{
		this.stixCommon_Description = stixCommon_Description;
	}

	public String get_stixCommon_Description()
	{
		return this.stixCommon_Description;
	}
	
	public void set_ttp_Description(String ttp_Description)
	{
		this.ttp_Description = ttp_Description;
	}

	public String get_ttp_Description()
	{
		return this.ttp_Description;
	}
	
	public void set_cyboxCommon_Description(String cyboxCommon_Description)
	{
		this.cyboxCommon_Description = cyboxCommon_Description;
	}

	public String get_cyboxCommon_Description()
	{
		return this.cyboxCommon_Description;
	}

	public void set_other_Description(String other_Description)
	{
		this.other_Description = other_Description;
	}

	public String get_other_Description()
	{
		return this.other_Description;
	}
	
}
