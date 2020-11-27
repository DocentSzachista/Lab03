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
		
		System.out.println("Witaj w interfejsie menadøera.");
		
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
					System.out.println("Wybierz zamÛwienie do akceptacji");
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
					System.out.println("WychodzÍ z programu");
					System.exit(0);
					break;
					default:
					System.out.print("Wybrano niepoprawnie opcjÍ, sprÛbuj jeszcze raz");
					break;
			}
		}
		
	}
	private void printMenu()
	{
		System.out.println(" Co chcesz zrobiÊ");
		System.out.println("1. RedagowaÊ ofertÍ");//done
		System.out.println("2. ZarzπdzaÊ zamÛwieniami");//done
		System.out.println("3. StworzyÊ zlecenie z dostÍpnych zamÛwieÒ");//done
		System.out.println("4. Wystawienie rachunku za zamÛwienie");//notdone
		System.out.println("5  StworzyÊ pracownika");//done
		System.out.println("6. Wyjúcie");
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
		System.out.println("Co chcesz zrobiÊ?");
		System.out.print("1.DodaÊ ofertÍ \n2.ZaaktualizowaÊ ofertÍ \n3.usunπÊ ofertÍ");
		
		switch(input.nextInt())
		{
			case 1:
			{
				System.out.println("Podaj kolejno oddzielony przecinkami: nazwÍ oferty,jednostkÍ(np: 1 sztuka;1 m^2),koszt oferty(z automatu bÍdzie przypisywana z≥otÛwka");
				input.nextLine();
				String[] singleofert=input.nextLine().split(",");
				if(singleofert.length!=3)
				{
					throw new ArrayIndexOutOfBoundsException("Nie zosta≥a podana wystarczajπca liczba argumentÛw");
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
				System.out.println("Podaj id, ktÛre chcesz zmodyfikowaÊ");
				id= input.nextInt();
				input.nextLine();
				System.out.println("Podaj nowπ nazwÍ oferty");
				String updateName=input.nextLine();
				System.out.println("Podaj jej  nowπ jednostkÍ");
				String updateUnit=input.nextLine();
				System.out.println("Podaj jej  nowπ cenÍ");
				String updateCost=input.nextLine();
				ofert.update(ofert.get(id), new String[] {updateName, updateUnit, updateCost});
				break;
			}	
			case 3:
			{
				System.out.print("Podaj id oferty, ktÛrπ chcesz ususnπÊ");
				id= input.nextInt();
				ofert.delete(ofert.get(id));
				break;
			}
			default: 
				System.out.println("èle wybra≥eú opcjÍ ");
				break;
				
		};
	
	}
	
	private void orderStatus(Scanner input)
	{
		System.out.println("ZamÛwienia");
		for(ClientOrder element: order.getAll().values())
		{
			
			System.out.println(element.toString());
		}
		System.out.println("Podaj id ktÛre chcesz wybraÊ");
		int id=input.nextInt();
		System.out.print("Co chcesz zrobiÊ? \n1.ZaakceptowaÊ\n2odrzuciÊ");
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
					System.out.println("Nie moøna wykonaÊ operacji");
				}
				break;
			case 2:
			{
				order.delete(order.get(id));
				System.out.println("usuniÍto");
				break;
			}	
			default:
				System.out.println("Z≥y wybÛr");
		}
	}
	
	private  void createBill(Scanner input)
	{
		System.out.println("Lista wykonanych zleceÒ");
		for(ClientOrder element: order.getAll().values())
		{
			if(element.getStatus().equals(State.COMPLETED))
			{
				System.out.println(element.toString());
			}
		}
		System.out.println("Wybierz id zlecenia z ktÛrego chcesz wystawiÊ rachunek");
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
				System.out.println("B≥πd: zlecenie jest jeszcze w toku");
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
		System.out.println("wybierz id zamÛwienia do utworzenia zlecenia");
		int id= input.nextInt();
		System.out.println("Wybierz id pracownika ktÛry ma wykonaÊ zlecenie");
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
		System.out.println("Podaj nazwÍ pracownika");
		String name= input.next();
		workers.save(new Worker(workers.getAll().size()+1, name));
	}
}
