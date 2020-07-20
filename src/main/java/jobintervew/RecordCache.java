package jobintervew;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RecordCache
{
	private final int MaxSize = 20;
	private ArrayList<Record> records = new ArrayList<Record>();
	private String fileName = null;
	private boolean isFileNameGiven = false;
	private String stopSignal = "";
	
	public RecordCache()
	{
		try
		{
			FileReader fr = new FileReader(RecordCache.class.getClassLoader().getResource(Variables.defaultfileNameLocation).getFile());
			BufferedReader br = new BufferedReader(fr);
			this.fileName = br.readLine();
			br.close();
			fr.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void write(Record record) 
	{

		this.records.add(record);
		if(this.records.size() > this.MaxSize)
				this.writeToFile();
		
	}
	
	public synchronized ArrayList<Record> read()
	{

			if(records.size() > 0) 
			{
				this.writeToFile();		
			}
			return this.readFromFile();
		
	}
	
	public void flush()
	{
		if(this.records.size() > 0)
			this.writeToFile();
	}
	
	private void writeToFile()
	{
		try
		{
			File file = new File(RecordCache.class.getClassLoader().getResource(this.fileName).getFile());
			FileOutputStream fos = new FileOutputStream(file,true);
			ObjectOutputStream oos;
			if(file.length() > 0)
				oos = new AppendObjectOutputStream(fos);
			else
				oos = new ObjectOutputStream(fos);
			for(Record record : records)
			{
				oos.writeObject(record);
				
				
			}
			this.records.clear();
			oos.flush();
			oos.close();
			fos.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private ArrayList<Record> readFromFile()
	{
		ArrayList<Record> list = new ArrayList<Record>();
		try
		{
			FileInputStream fis = new FileInputStream(RecordCache.class.getClassLoader().getResource(this.fileName).getFile());
			if(fis.available() <= 0)
			{
				fis.close();
				return list;
			}
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			while (fis.available() > 0) 
			{
				list.add((Record) ois.readObject());

			}
			ois.close();
			fis.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	
		if(list.size() > 0)
			list = this.calculateTheNet(list);

		return list;

	}
	
	private ArrayList<Record> calculateTheNet(ArrayList<Record> list)
	{
		
		ArrayList<Record> calculatedList = new ArrayList<Record>();
		for(int i = 0; i < list.size();)
		{

			Record r1 = list.remove(i);

			for(int j = 0; j < list.size(); )
			{
				Record r2 = null;
				if(r1.equals(list.get(j)))
				{
					r2= list.remove(j);

					r1.setCurrencyName(r1.getCurrencyName());
					r1.setAmount(r1.getAmount() + r2.getAmount());
					r1.setRate(r2.getRate() == 0.0f ? r1.getRate() : r2.getRate()); 
					j = 0;
				}else {
					j++;
				}
				
			}
			calculatedList.add(r1);
			if(list.size() == 1 && list.get(0).equals(calculatedList.get(calculatedList.size()-1)))
			{
				Record first = list.remove(0);
				Record last = calculatedList.remove(calculatedList.size()-1);
				Record newRecord = new Record();
				newRecord.setCurrencyName(first.getCurrencyName());
				newRecord.setAmount(first.getAmount() + last.getAmount());
				newRecord.setRate(first.getRate());
				calculatedList.add(newRecord);
			}
		}

		return calculatedList;
		
	}
	
	public void saveFileName(String fileName)
	{
		try
		{
			String fileLocation = InputThread.class.getClassLoader().getResource(Variables.defaultfileNameLocation).getFile();
			FileWriter fw = new FileWriter(fileLocation);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fileName);
			bw.close();
			fw.close();
			File file = new File(new File(fileLocation).getParent() + File.separator + fileName);
			
			this.fileName = fileName;
			file.createNewFile();

		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getStopSignal()
	{
		return stopSignal;
	}

	public void setStopSignal(String stopSignal)
	{
		this.stopSignal = stopSignal;
	}
	
	public String getFileName()
	{
		return fileName;
	}

	public boolean isFileNameGiven()
	{
		return isFileNameGiven;
	}

	public void setFileNameGiven(boolean isFileNameGiven)
	{
		this.isFileNameGiven = isFileNameGiven;
	}
	
}
