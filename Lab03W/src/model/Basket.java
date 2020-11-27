package model;

import java.util.HashMap;
import java.util.Map;
/**
 * Koszyk przetrzymuj¹cy na czas trwania programu wybrane oferty przez u¿ytkownika
 * dopóki nie stworzy z nich zamówienia (wtedy zostaje opró¿niony)
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
			System.out.println("Odwo³ujesz siê do oferty, która nie istnieje");
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
	 * Wyliczenie kosztów
	 */
	private float parseToFloat(Ofert t, float amount)
	{
		float value=Float.parseFloat(t.getCost())*t.getUnitAsNumber();
		
		return value;
	}
}
