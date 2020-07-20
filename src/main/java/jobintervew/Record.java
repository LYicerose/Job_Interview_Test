package jobintervew;

import java.io.Serializable;

public class Record implements Serializable
{

	private static final long serialVersionUID = 6110633101498657841L;
	private String currencyName;
	private long amount;
	private float rate = 0.0f;
	
	
	public Record(String currencyName, long amount, float rate)
	{
		this.currencyName = currencyName;
		this.amount = amount;
		this.rate = rate;
		
	}
	
	public Record()
	{
		
	}

	public String getCurrencyName()
	{
		return currencyName;
	}
	public void setCurrencyName(String currencyName)
	{
		this.currencyName = currencyName;
	}
	public long getAmount()
	{
		return amount;
	}


	public void setAmount(long amount)
	{
		this.amount = amount;
	}
	public float getRate()
	{
		return rate;
	}
	public void setRate(float rate)
	{
		this.rate = rate;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyName == null) ? 0 : currencyName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if(currencyName == null)
		{
			if(other.currencyName != null)
				return false;
		}else if(!currencyName.equals(other.currencyName))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		if(this.rate == 0.0f)
			return this.currencyName + " " + this.amount;
		else	
			return this.currencyName + " " + this.amount + " (USD " + this.amount*this.rate + ")";
	}

}
