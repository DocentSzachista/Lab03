package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OfertDAO implements DAO<Ofert> 
{
	private DataReader<Ofert> file;
	//lista ofert
	private Map<Integer,Ofert> list= new HashMap<Integer,Ofert>();
	private String filePath="data/oferty.dat";
	public OfertDAO()
	{
		file= new DataReader<Ofert>(filePath);
		list= file.readFile();
	}
	@Override
	public Ofert get(int id)  
	{
		try 
		{
			Ofert item=list.get(id);
			return item;
		}
		catch(NullPointerException e)
		{
			throw new NullPointerException("Brak rz¹danego elementu w liœcie.");
			
		}
	}

	@Override
	public Map<Integer, Ofert> getAll() {
		return list;
	}

	@Override
	public void save(Ofert t) {
		t.setId(list.size()+1);
		list.put(t.getId(), t);
		file.writeToFile(list);
		
	}

	@Override
	public void update(Ofert t, String[] params) 
	{
		if(t.getName()!=params[0])
		{
			t.setName(params[0]);
		}
		if(t.getUnit()!=params[1])
		{
			t.setUnit(params[1]);
		}
		if(t.getUnit()!=params[2])
		{
			t.setCost(params[2]);
		}
		list.replace(t.getId(), t );
		file.writeToFile(list);
	}

	@Override
	public void delete(Ofert t) {
		try {
		list.remove(t.getId());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new IndexOutOfBoundsException("próbujesz wyrzuciæ element który nie istnieje");
		}
		file.writeToFile(list);
	}
}
