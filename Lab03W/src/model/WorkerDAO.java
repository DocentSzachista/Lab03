package model;

import java.util.Map;
import java.util.Optional;

public class WorkerDAO implements DAO<Worker>{
	
	private Map<Integer, Worker> workers;
	private DataReader<Worker> file;
	private String filePath="data/work.dat";
	public WorkerDAO()
	{
		file=new DataReader<Worker>(filePath);
		this.workers=file.readFile();
	}
	@Override
	public Worker get(int id) {
		try
		{
			Worker item=workers.get(id);
			return item;
		}
		catch(NullPointerException e)
		{
			throw new NullPointerException("Próbujesz wybraæ element, który nie istnieje");
			
		}
	}

	@Override
	public Map<Integer, Worker> getAll() {
		// TODO Auto-generated method stub
		return workers;
	}
	@Override
	public void save(Worker t) {
		t.setId(workers.size()+1);
		workers.put(t.getId(), t);
		file.writeToFile(workers);
		
	}

	@Override
	public void update(Worker t, String[] params) {
		t.setName(params[0]);
		workers.replace(t.getId(), t );
		file.writeToFile(workers);
		
	}
	@Override
	public void delete(Worker t) {
		try 
		{			
			workers.remove(t.getId());
		
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new IndexOutOfBoundsException("próbujesz wyrzuciæ element który nie istnieje");
		}			
		file.writeToFile(workers);
	}

}
