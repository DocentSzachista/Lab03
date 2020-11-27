package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
//Odczytywanie Plików 
public class DataReader<T> {
	
	private String filePath;
	public DataReader(String filePath)
	{
		this.filePath=filePath;
	}
	public Map<Integer, T> readFile()
	{
		Map<Integer, T> list= new HashMap<Integer, T>();
		if(new File(filePath).length()>0)
		{
			try 
			{
			    ObjectInputStream o = new ObjectInputStream( new FileInputStream(filePath));
			    list =(HashMap<Integer, T>) o.readObject(); // odczyt obiektu ze strumienia
			    o.close(); 
			 
			 
			} catch (Exception ex) 
			{
			    ex.printStackTrace();
			}
		}
		return list;
	}
	public void writeToFile(Map<Integer, T> list)
	{
		try 
        {
            // tworzy strumieñ do zapisu
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(filePath)); 
 
        o.writeObject(list); // zapisanie obiektu
        o.close(); // zamkniêcie strumienia
 
        } catch (Exception ex) // zg³asza wyj¹tki
        {
            ex.printStackTrace();
        }
	}
	
}
