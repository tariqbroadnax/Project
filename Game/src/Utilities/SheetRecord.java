package Utilities;

public class SheetRecord
{
	public final int startID;
	
	public final String srcFileName;
	
	public final int cols, rows;
	
	public SheetRecord(
			int startID, String srcFileName,
			int cols, int rows)
	{
		this.startID = startID;
		this.srcFileName = srcFileName;
		this.cols = cols;
		this.rows = rows;
	}
	
	public String serialString()
	{
		return "sheet " + startID + " " 
						+ srcFileName + " "
						+ cols + " " 
						+ rows;
	}
	
	public String toString()
	{
		return "startID: " + startID + " " +
			    "srcFileName: " + srcFileName + "\n" +
				"cols: " + cols + " rows: " + rows;
	}
}
