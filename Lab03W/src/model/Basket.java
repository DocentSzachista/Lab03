package model;

import java.util.HashMap;
import java.util.Map;
/**
 * Koszyk przetrzymuj�cy na czas trwania programu wybrane oferty przez u�ytkownika
 * dop�ki nie stworzy z nich zam�wienia (wtedy zostaje opr�niony)
 *
 */
public class Basket {
	private Map<Integer, Ofert> basket;
	private float cost;
	public Basket()
	{
		basket=new HashMap<Integer, Ofert>();
		cost=0;
	}
	public void addToBasket(Ofert t, float amount)
	{
		try {
			
		basket.put(t.getId(), t);
		this.cost+=amount*parseToFloat(t, amount);
		}
		catch(NullPointerException e)
		{
			System.out.println("Odwo�ujesz si� do oferty, kt�ra nie istnieje");
		}
	}
	public Map<Integer,Ofert> getBasket()
	{
		return basket;
	}
	public boolean isEmpty()
	{
		return basket.isEmpty();
	}
	public float getCost()
	{
		return cost;
	}
	/*
	 * Wyliczenie koszt�w
	 */
	private float parseToFloat(Ofert t, float amount)
	{
		float value=Float.parseFloat(t.getCost())*t.getUnitAsNumber();
		
		return value;
	}
}
