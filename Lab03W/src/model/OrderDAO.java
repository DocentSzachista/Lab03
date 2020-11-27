package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderDAO implements DAO<ClientOrder>{
	private Map<Integer, ClientOrder> orders= new HashMap<Integer, ClientOrder>();
	private DataReader<ClientOrder> file;
	private String filePath="data/orders.dat";
	public OrderDAO()
	{
		file = new DataReader<ClientOrder>(filePath);
		orders= file.readFile();
	}
	
	
	@Override
	public ClientOrder get(int id) 
	{
		try
		{
			ClientOrder item=orders.get(id);
			return item;
		}
		catch(NullPointerException e)
		{
			System.out.println("Próbujesz wybraæ element, który nie istnieje");
			return null;
		}
	}

	@Override
	public Map<Integer, ClientOrder> getAll() 
	{
		return orders;
	}

	@Override
	public void save(ClientOrder t) 
	{
		t.setId(orders.size()+1);
		orders.put(t.getId(), t);
		file.writeToFile(orders);
		
	}

	@Override
	public void update(ClientOrder t, String[] params) 
	{
		orders.replace(t.getId(), t);
		file.writeToFile(orders);
		
	}
	public void updateStatus(ClientOrder t, State state)
	{
		t.setStatus(state);
		t.setDeliveryDate();
		orders.replace(t.getId(), t );
		file.writeToFile(orders);
	}

	@Override
	public void delete(ClientOrder t) 
	{
		try 
		{			
			orders.remove(t.getId());
		
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("próbujesz wyrzuciæ element który nie istnieje");
		}			
		file.writeToFile(orders);
	}
	
}
