package jobintervew;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import jobintervew.Record;

class InputThread extends Thread
{
	private Scanner scanner = new Scanner(System.in);
	private String inputValue = "";
	private RecordCache recordCache;

	public InputThread(RecordCache recordCache)
	{
		this.recordCache = recordCache;
	}

	@Override
	public void run()
	{
		System.out.println("Please set a name for your account which store your info(optional):");
		String fileName = scanner.nextLine();
		
		if(!fileName.equals(Variables.stopSingal) && !fileName.isEmpty())
		{
			this.recordCache.setFileNameGiven(true);
			this.recordCache.saveFileName(fileName);
		}
		else
			System.exit(MAX_PRIORITY);
		System.out.println("Now you can type in your info:");
		do{
			inputValue = scanner.nextLine();
			
			if(!inputValue.equals(Variables.stopSingal))
			{
				ArrayList<String> list = separateInput(inputValue);
				if(list.size() > 1 )
				{
					String currency = list.get(0);
					if(!currency.matches("[A-Z]{3,}"))
					{
						System.out.println("Invalid CurrencyName,please retry:");
						continue;
					}
					try {
						long amount = Long.parseLong(list.get(1));
						if(!list.get(1).matches("[1-9]\\d*") && !list.get(1).matches("^-[1-9]\\d*"))
						{
							System.out.println("Invalid Amount,please retry:");
							continue;
						}
						
						float rate = 0.0f;
						if(list.size() > 2)
						{
							rate = Float.parseFloat(list.get(2));
							if(!list.get(2).matches("([1-9]\\d*\\.?\\d+)|(0\\.\\d*[1-9])|(\\d+)"))
							{
								System.out.println("Invalid Rate,please retry:");
								continue;
							}	
						}
						Record record = new Record(currency, amount, rate);
						recordCache.write(record);
					}catch(NumberFormatException e) {
						System.out.println("Invalid NumberFormat,please retry:");
					}
					
				}
				
			}else {
				this.recordCache.setStopSignal(inputValue);
				this.recordCache.flush();
				System.exit(MAX_PRIORITY);
			}
			
		}while(!inputValue.equals(Variables.stopSingal));
	}

	private ArrayList<String> separateInput(String input)
	{
		ArrayList<String> list = new ArrayList<String>();
		input = input.trim();
		String[] strings = input.split("\\s+");
		for(int i = 0; i < strings.length; i++)
		{
			list.add(strings[i]);
		}
		return list;
	}
	
	
}