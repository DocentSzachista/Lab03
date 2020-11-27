package users;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import model.OrderDAO;
import model.State;
import model.Worker;
import model.WorkerDAO;
import ui.WorkerEngine;

public class WorkerUI 
{
	private static WorkerDAO workers=new WorkerDAO();
	private static OrderDAO order= new OrderDAO();
	static WorkerEngine engine= new WorkerEngine();
	public static void main(String[] args)
	{
		Scanner input=new Scanner(System.in);
		System.out.println("Panel pracownika kt�rym pracownikiem jeste�? Wybierz id");
		printOrders(engine.getWorkers());
		int idUser=input.nextInt();
		while(true)
		{
			System.out.print("1.Przegl�daj zlecenia\n2.Wykonajzlecenie\n3Wyj�cie");
			int n=input.nextInt();
			switch(n)
			{
				case 1:
					printOrders(engine.getOrder(idUser));
					break;
				case 2:
					printOrders(engine.getOrder(idUser));
					System.out.print("Wybierz id zlecenia, kt�re wykona�e�");
					int idItem=input.nextInt();
					engine.completeWork(idItem);
					break;
				case 3:
					System.out.print("Aplikacja si� wy��cza");
					input.close();
					System.exit(0);
					break;
					
				default:
					System.out.print("wybrano z�� opcj�, wybierz ponownie");
					break;
			}
		}
	}
	private static<T> void printOrders(Map<Integer, T> list)
	{
		for(T element: list.values())
		{
			System.out.println(element.toString());
		}
	}

}
