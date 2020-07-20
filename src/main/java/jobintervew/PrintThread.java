package jobintervew;

import java.util.ArrayList;
import jobintervew.Record;

public class PrintThread extends Thread{
	private RecordCache recordCache;
	private ArrayList<Record> list;

	
	public PrintThread(RecordCache recordCache)
	{
		this.recordCache = recordCache;
	}
	
	@Override
	public void run()
	{
		
		while(!this.recordCache.getStopSignal().equals(Variables.stopSingal))
		{
			if(this.recordCache.isFileNameGiven()) 
			{
				System.out.println("Your Account info:");
				list = recordCache.read();						
				for(Record record : list)
				{
					if(record.getAmount() != 0 )
						System.out.println(record);
				}
				
			}
		
			try
			{
				Thread.sleep(Variables.printPeriod);
			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		
	}
	
}
