package jobintervew;

public class Accounter
{
	
	
	public static void main(String[] args)
	{
		RecordCache recordCache = new RecordCache();
		InputThread inputThread = new InputThread(recordCache);
		PrintThread printThread = new PrintThread(recordCache);
		
		inputThread.start();
		printThread.start();
	}


	

	
	
}
