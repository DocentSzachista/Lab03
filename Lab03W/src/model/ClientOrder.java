package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ClientOrder implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id;
	private String date;
	private String deliveryDate;
	private int clientId;
	//Kolejno bêdzie zawieraæ id z oferty, i jego zawartoœæ
	private Map<Integer, Ofert> order;
	private State status;
	private float overallCost;
	private boolean isPaid;
	private Bill bill;
	public ClientOrder()
	{
		
	}
	public ClientOrder( int clientId, Map<Integer, Ofert> order, float cost  )
	{
		this.setStatus(State.PENDING);
		this.order=order;
		this.clientId=clientId;
		Locale locale = new Locale("en", "US");
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
		this.date = dateFormat.format(new Date());
		this.setPaid(false);
		this.overallCost=cost;
		this.deliveryDate="";
		this.bill=null;
	}
	public int getClientId() 
	{
		return clientId;
	}
	public void setClientId(int clientId) 
	{
		this.clientId = clientId;
	}
	public String getDate() 
	{
		return date;
	}
	public void setDate(String date) 
	{
		this.date = date;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;

	}
	public Map<Integer, Ofert> getList() {
		return order;
	}
	public void setList(Map<Integer, Ofert> list) {
		this.order = list;
	}
	public String toString()
	{
		String state;
		switch(status)
		{
			case CONFIRMED:
				state="Zatwierdzone do realizacji";
				break;
			case PENDING:
				state="Oczekuje na zatwierdzenie";
				break;
			case COMPLETED:
				state="Zosta³o zrealizowane";
				break;
			case EXECUTING:
				state="Jest realizowane";
				break;
			default:
				state="";
				break;
		}
		return "id zamówienia "+id+"\n id klienta "+clientId+"\n Data z³o¿enia zamówienia "+date+ "\nStan zamówienia: "
				+ ""+state+"\n Data dorêczenia "+deliveryDate+"\n"+printOrderDetails();
	
	}
	
	private String printOrderDetails()
	{
		StringBuilder temp=new StringBuilder("");
		for(Ofert element:order.values())
		{
			temp.append(element.toString()+"\n");
		}
		return temp.toString();
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public State getStatus() {
		return status;
	}
	public void setStatus(State status) {
		this.status = status;
	}
	public float getOverallCost() {
		return overallCost;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill() {
		this.bill = new Bill(id, date, clientId, overallCost, order );
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate() 
	{
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 2);
		dt = c.getTime();
		Locale locale = new Locale("en", "US");
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
		this.deliveryDate = dateFormat.format(dt);
	}

}
