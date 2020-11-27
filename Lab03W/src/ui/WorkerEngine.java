package ui;

import java.util.Map;

import model.ClientOrder;
import model.OrderDAO;
import model.State;
import model.Worker;
import model.WorkerDAO;
/**
 * Klasa maj¹ca za zadanie obs³u¿yæ logikê pracownika
 */
public class WorkerEngine {
	private  WorkerDAO workers;
	private  OrderDAO order; 
	public WorkerEngine()
	{
		workers=new WorkerDAO();
		order= new OrderDAO();
	}
	public Map<Integer, ClientOrder> getOrder(int idUser)
	{
		return workers.get(idUser).getOrdersTodo();
	}
	public void completeWork(int orderId)
	{
		order.updateStatus(workers.get(orderId).getOrdersTodo().get(orderId), State.COMPLETED);
	}
	public Map<Integer, Worker> getWorkers()
	{
		return workers.getAll();
	}
}
