package ui;

import java.util.Map;
import java.util.Scanner;

import model.Basket;
import model.Client;
import model.ClientOrder;
import model.DataReader;
import model.Ofert;
import model.OfertDAO;
import model.OrderDAO;

public class ClientUI 
{
	private OfertDAO oferts;
	private OrderDAO orders;
	private Basket basket;
	private Client user;
	public ClientUI()
	{
		oferts= new OfertDAO();
		orders= new OrderDAO();
		 user= new Client();
		 basket = new Basket();
		Scanner input= new Scanner(System.in);
		DataReader<Client> clientList= new  DataReader<Client>("data/clients.dat");
		
		
		System.out.println("Witaj w panelu u�ytkownika. Co chcesz zrobi�");
		System.out.println("1.Zalogowa� si�\n 2.Za�o�y� konto");
		login(input, clientList);
		boolean tru=true;
		
		System.out.print("1.Przegl�da� oferty \n2.Stworzy� zam�wienie \n3.Z�o�y� zam�wienie\n4.Sprawdzi� stan zam�wie�\n5.Zap�aci� za zam�wienie\n6.Wyj�� b�dz dowolny klawisz");
		while(tru)
		{
			switch(input.nextInt())
			{
				case 1:
				{	
					System.out.println("id, nazwa, jednostka, cena");
					for(Ofert element: oferts.getAll().values())
					{
						System.out.println(element.toString());
					}
					break;
				}
				case 2:
				{
					if (!oferts.getAll().isEmpty())
					{
						System.out.print("Podaj id oferty, kt�re Ciebie interesuje");
						int ofertId=input.nextInt();
						System.out.println("ile sztuk/ jaki obszar Ciebie interesuje (bez jednostek)");
						input.nextLine();
						String number= input.nextLine();
						float ammount=Float.parseFloat(number);
						//dodawanie do koszyka
						basket.addToBasket(oferts.get(ofertId), ammount);
						//u�omny wydruk zawarto�ci koszyka 
						System.out.println(basket.getBasket());
					}
					else
					{
						System.out.print("Nie mo�na doda� oferty do koszyka, gdy� ofert nie ma");
					}
					break;
				}
				case 3:
				{
					
					if(!basket.isEmpty())
					{
						System.out.println("Tworz� zam�wienie z zawarto�ci koszyka");
						ClientOrder order = new ClientOrder(user.getId(), basket.getBasket(), basket.getCost());
						System.out.print(user.getId());
						System.out.println(order.getList());
						orders.save(order);
						System.out.println("z�o�ono zam�wienie");
						System.out.println(orders.getAll());
					}
					else
					{
						System.out.println("Nie uda�o si� utworzy� zam�wienia: Koszyk jest pusty");
					}
					break;
				}
				case 4:
				{
					for(ClientOrder element	:orders.getAll().values())
					{
						if(element.getId()==user.getId())
						{
							System.out.print(element.toString()+ " "+element.getOverallCost());
						}
					}
					break;
				}
				case 5:
				{
					for(ClientOrder element : orders.getAll().values())
					{
						System.out.println("Zam�wienia gotowe do zap�aty");
						if(element.getBill()!=null)
						{
							System.out.println(element.toString());
							System.out.println(element.getBill().toString());
						}
						System.out.println("Za kt�ry rachunek p�acisz?");
						int id=input.nextInt();
						ClientOrder temp = orders.get(id);
						temp.setBill();
						orders.update(temp, new String[] {});
					}
					break;
				}
				case 6: default:
				{
					System.out.print("Wychodz� z aplikacji");
					input.close();
					System.exit(0);
					break;
				}
					
			}
		}
		
	}
	
	
	private void login(Scanner input, DataReader<Client> clientList)
	{
		int n=input.nextInt();
		Map <Integer, Client> clients= clientList.readFile();
		switch(n)
		{
			case 1:
			{
				System.out.println("Wybierz na kt�rego u�ytkownika chcesz si� zalogowa�");
				System.out.println("id  | nazwa u�ytkownika");
				for(Integer element: clients.keySet() )
				{
					System.out.println(element+" | "+clients.get(element).toString());
				}
				while(true)
				{
					int id=input.nextInt();
					if(clients.containsKey(id))
					{
						user= clients.get(id);
						System.out.print(user.getId());
						break;
					}
					else
					{
						System.out.print("��dany u�ytkownik nie istnieje. Wybierz jeszcze raz");
					}
				}
				break;
			}
			case 2:
			{
				
				System.out.println("Podaj nazw� u�ytkownika");
				input.nextLine();
				user=new Client(clients.size()+1, input.next());
				System.out.println(user.getId());
				clients.put(clients.size()+1, user);
				System.out.println(clients);
				clientList.writeToFile(clients);
				
				break;
			}
			default:
			{
				System.out.println("Wyj�cie z programu");
				input.close();
				System.exit(0);
			}
		}
	}
	
}

