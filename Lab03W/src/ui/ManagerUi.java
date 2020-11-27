package ui;

import java.util.Map;
import java.util.Scanner;

import model.ClientOrder;
import model.Ofert;
import model.OfertDAO;
import model.OrderDAO;
import model.State;
import model.Worker;
import model.WorkerDAO;

public class ManagerUi {
	private  OfertDAO ofert; 
	private  OrderDAO order;
	private  WorkerDAO workers;
	public ManagerUi()
	{
		ofert=new OfertDAO();
		order=new OrderDAO();
		workers=new WorkerDAO();
		
		System.out.println("Witaj w interfejsie menad�era.");
		
		while(true) 
		{
			printMenu();
			Scanner input= new Scanner(System.in);
			int choice= input.nextInt();
			
			switch(choice)
			{
				case 1:
					offerts(input);
					break;
				case 2:
					System.out.println("Wybierz zam�wienie do akceptacji");
					orderStatus(input);
					break;
				case 3:
				{
					createWork(input);
					break;
				}
				case 4:
					createBill(input);
					break;
				case 5:
				{
					addWorker(input);
					break;
				}
				case 6:
					System.out.println("Wychodz� z programu");
					System.exit(0);
					break;
					default:
					System.out.print("Wybrano niepoprawnie opcj�, spr�buj jeszcze raz");
					break;
			}
		}
		
	}
	private void printMenu()
	{
		System.out.println(" Co chcesz zrobi�");
		System.out.println("1. Redagowa� ofert�");//done
		System.out.println("2. Zarz�dza� zam�wieniami");//done
		System.out.println("3. Stworzy� zlecenie z dost�pnych zam�wie�");//done
		System.out.println("4. Wystawienie rachunku za zam�wienie");//notdone
		System.out.println("5  Stworzy� pracownika");//done
		System.out.println("6. Wyj�cie");
	}
	private void printList()
	{
		
		
		for(Ofert element : ofert.getAll().values() )
		{
			System.out.println(element.toString());
		}
	}
	
	private void offerts(Scanner input)
	{
		System.out.println("id nazwa, jednostka, cena");
		printList();
		int id;
		System.out.println("Co chcesz zrobi�?");
		System.out.print("1.Doda� ofert� \n2.Zaaktualizowa� ofert� \n3.usun�� ofert�");
		
		switch(input.nextInt())
		{
			case 1:
			{
				System.out.println("Podaj kolejno oddzielony przecinkami: nazw� oferty,jednostk�(np: 1 sztuka;1 m^2),koszt oferty(z automatu b�dzie przypisywana z�ot�wka");
				input.nextLine();
				String[] singleofert=input.nextLine().split(",");
				if(singleofert.length!=3)
				{
					throw new ArrayIndexOutOfBoundsException("Nie zosta�a podana wystarczaj�ca liczba argument�w");
				}
				else
				{
					ofert.save(new Ofert(singleofert[0], singleofert[1], singleofert[2]));
					System.out.println("Sukcess!");
				}
				break;
			}
			case 2:
			{
				System.out.println("Podaj id, kt�re chcesz zmodyfikowa�");
				id= input.nextInt();
				input.nextLine();
				System.out.println("Podaj now� nazw� oferty");
				String updateName=input.nextLine();
				System.out.println("Podaj jej  now� jednostk�");
				String updateUnit=input.nextLine();
				System.out.println("Podaj jej  now� cen�");
				String updateCost=input.nextLine();
				ofert.update(ofert.get(id), new String[] {updateName, updateUnit, updateCost});
				break;
			}	
			case 3:
			{
				System.out.print("Podaj id oferty, kt�r� chcesz ususn��");
				id= input.nextInt();
				ofert.delete(ofert.get(id));
				break;
			}
			default: 
				System.out.println("�le wybra�e� opcj� ");
				break;
				
		};
	
	}
	
	private void orderStatus(Scanner input)
	{
		System.out.println("Zam�wienia");
		for(ClientOrder element: order.getAll().values())
		{
			
			System.out.println(element.toString());
		}
		System.out.println("Podaj id kt�re chcesz wybra�");
		int id=input.nextInt();
		System.out.print("Co chcesz zrobi�? \n1.Zaakceptowa�\n2odrzuci�");
		int n=input.nextInt();
		switch(n)
		{
			case 1:
				if(order.get(id).getStatus().equals(State.PENDING))
				{
					order.updateStatus(order.get(id), State.CONFIRMED);
					System.out.println("Zaakceptowano do realizacji");
				}
				else
				{
					System.out.println("Nie mo�na wykona� operacji");
				}
				break;
			case 2:
			{
				order.delete(order.get(id));
				System.out.println("usuni�to");
				break;
			}	
			default:
				System.out.println("Z�y wyb�r");
		}
	}
	
	private  void createBill(Scanner input)
	{
		System.out.println("Lista wykonanych zlece�");
		for(ClientOrder element: order.getAll().values())
		{
			if(element.getStatus().equals(State.COMPLETED))
			{
				System.out.println(element.toString());
			}
		}
		System.out.println("Wybierz id zlecenia z kt�rego chcesz wystawi� rachunek");
		int id=input.nextInt();
		
		switch(order.get(id).getStatus())
		{
			case COMPLETED:
				ClientOrder ord=order.get(id);
				ord.setBill();
				System.out.println(ord.getBill().toString());
				order.update(ord, new String[] {});
				break;
			default:
				System.out.println("B��d: zlecenie jest jeszcze w toku");
		}
	}
	
	
	
	private void createWork(Scanner input)
	{
		for(ClientOrder element: order.getAll().values())
		{
			if(element.getStatus().equals(State.CONFIRMED))
			{
			System.out.println(element.toString());
			}
		}
		System.out.println("wybierz id zam�wienia do utworzenia zlecenia");
		int id= input.nextInt();
		System.out.println("Wybierz id pracownika kt�ry ma wykona� zlecenie");
		for(Worker element: workers.getAll().values())
		{
			System.out.println(element.toString());
		}
		int id2=input.nextInt();
		Worker worker=  workers.get(id2);
		System.out.println(worker.toString());
		worker.getOrdersTodo().put(id, order.get(id));
		workers.update(worker, new String[] {worker.getName()});		
	}
	private  void addWorker(Scanner input)
	{
		System.out.println("Podaj nazw� pracownika");
		String name= input.next();
		workers.save(new Worker(workers.getAll().size()+1, name));
	}
}
