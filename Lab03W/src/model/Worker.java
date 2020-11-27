package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
//wsadzamy zlecenie do listy i pracownik zmienia jego stan na completed i tyle
public class Worker implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private final State completion = State.COMPLETED;
	private Map<Integer, ClientOrder> ordersToDo;
	
	public Worker(int id, String name)
	{
		this.id=id;
		this.name=name;
		ordersToDo=new HashMap<Integer, ClientOrder>();
	}
	public Worker()
	{
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public State getCompletion() {
		return completion;
	}
	public Map<Integer, ClientOrder> getOrdersTodo() {
		return ordersToDo;
	}
	public void setOrdersTodo(Map<Integer, ClientOrder> ordersTodo) {
		this.ordersToDo = ordersTodo;
	}
	public String toString()
	{
		return this.id +" | "+ this.name;
	}
	
}
