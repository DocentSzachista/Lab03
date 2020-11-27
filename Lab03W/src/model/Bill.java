package model;

import java.io.Serializable;
import java.util.Map;

public class Bill implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String date;
	private int clientName;
	private float cost;
	private Map<Integer, Ofert> list;
	public Bill(int id, String date, int clientName, float cost, Map<Integer,Ofert> list)
	{
		this.id=id;
		this.date=date;
		this.clientName=clientName;
		this.cost=cost;
		this.list=list;
	}
	private String convertToString()
	{
		StringBuilder mapAsString=new StringBuilder("");
		for(Integer key: list.keySet())
		{
			mapAsString.append(list.get(key).toString()+"\n");
		}
		return mapAsString.toString();
	}
	public String toString()
	{
		return "Rachunek dla: "+clientName+"\nData: "+date+"\nZamówienie:\n"+convertToString()+"Koszt: "+cost;
	}
}
