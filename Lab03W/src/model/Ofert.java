package model;

import java.io.Serializable;

public class Ofert implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String unit;
	private String cost;
	private float unitAmmount;
	public Ofert()
	{
	}
	public Ofert( String name, String unit, String cost) 
	{
		String[] temp=unit.split(" ",2);
		this.name=name;
		this.unit=temp[1];
		this.cost=cost;
		this.unitAmmount=Float.parseFloat(temp[0]);
	}
	public String getCost() 
	{
		return cost;
	}
	public void setCost(String cost) 
	{
		this.cost = cost;
	}
	public String getUnit() 
	{
		return this.unitAmmount+" "+this.unit;
	}
	public float getUnitAsNumber()
	{
		return this.unitAmmount;
	}
	public void setUnit(String unit) 
	{
		String[] temp=unit.split(" ", 2);
		this.unit = temp[1];
		this.unitAmmount=Float.parseFloat(temp[0]);
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getId() 
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	@Override
	public String toString()
	{
		return id+" | "+name+" | "+unitAmmount+" "+unit+" | "+cost+" PLN";
	}
	
}
