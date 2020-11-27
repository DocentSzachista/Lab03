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
		
		
		System.out.println("Witaj w panelu u¿ytkownika. Co chcesz zrobiæ");
		System.out.println("1.Zalogowaæ siê\n 2.Za³o¿yæ konto");
		login(input, clientList);
		boolean tru=true;
		
		System.out.print("1.Przegl¹daæ oferty \n2.Stworzyæ zamówienie \n3.Z³o¿yæ zamówienie\n4.Sprawdziæ stan zamówieñ\n5.Zap³aciæ za zamówienie\n6.Wyjœæ b¹dz dowolny klawisz");
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
						System.out.print("Podaj id oferty, które Ciebie interesuje");
						int ofertId=input.nextInt();
						System.out.println("ile sztuk/ jaki obszar Ciebie interesuje (bez jednostek)");
						input.nextLine();
						String number= input.nextLine();
						float ammount=Float.parseFloat(number);
						//dodawanie do koszyka
						basket.addToBasket(oferts.get(ofertId), ammount);
						//u³omny wydruk zawartoœci koszyka 
						System.out.println(basket.getBasket());
					}
					else
					{
						System.out.print("Nie mo¿na dodaæ oferty do koszyka, gdy¿ ofert nie ma");
					}
					break;
				}
				case 3:
				{
					
					if(!basket.isEmpty())
					{
						System.out.println("Tworzê zamówienie z zawartoœci koszyka");
						ClientOrder order = new ClientOrder(user.getId(), basket.getBasket(), basket.getCost());
						System.out.print(user.getId());
						System.out.println(order.getList());
						orders.save(order);
						System.out.println("z³o¿ono zamówienie");
						System.out.println(orders.getAll());
					}
					else
					{
						System.out.println("Nie uda³o siê utworzyæ zamówienia: Koszyk jest pusty");
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
						System.out.println("Zamówienia gotowe do zap³aty");
						if(element.getBill()!=null)
						{
							System.out.println(element.toString());
							System.out.println(element.getBill().toString());
						}
						System.out.println("Za który rachunek p³acisz?");
						int id=input.nextInt();
						ClientOrder temp = orders.get(id);
						temp.setBill();
						orders.update(temp, new String[] {});
					}
					break;
				}
				case 6: default:
				{
					System.out.print("Wychodzê z aplikacji");
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
				System.out.println("Wybierz na którego u¿ytkownika chcesz siê zalogowaæ");
				System.out.println("id  | nazwa u¿ytkownika");
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
						System.out.print("¯¹dany u¿ytkownik nie istnieje. Wybierz jeszcze raz");
					}
				}
				break;
			}
			case 2:
			{
				
				System.out.println("Podaj nazwê u¿ytkownika");
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
				System.out.println("Wyjœcie z programu");
				input.close();
				System.exit(0);
			}
		}
	}
	
}

