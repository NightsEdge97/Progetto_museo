package database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Manager {
	
	//Returns ArrayList with Opera objects
	public static List<Opera> getArtList(){
		ArrayList<Opera> lista = new ArrayList<>();
		int i = 1;
		Creator getter = new OperaCreator();
		Opera opera = (Opera) getter.createComponent(i);
		while(opera != null){
			lista.add(opera);
			i++;
			opera = (Opera) getter.createComponent(i);
		}
		return lista;
	}
	
	//Returns ArrayList with Room objects
	public static List<Room> getRoomList(){
		ArrayList<Room> lista = new ArrayList<>();
		int i = 1;
		Creator getter = new RoomCreator();
		Room room = (Room) getter.createComponent(i);
		while(room != null){
			lista.add(room);
			i++;
			room = (Room) getter.createComponent(i);
		}
		return lista;
	}

	//Returns String with room info
	public static String getRoomString(Room room){
		String roomInfo = "";
		String termalStatus ="";
		if (room.getTermal())
			termalStatus ="Acceso";
			else
				termalStatus ="Spento";
		
		String socialDistance = "";
		if(room.isDistanceRespected())
			socialDistance ="Rispettato";
			else
				socialDistance ="Non rispettato!";
		
		
		roomInfo = "Temperatura: "+room.getTemp()+
				"\nStato Impianto termico: "+termalStatus+
				"\nNumero Persone: "+room.getPeople()+
				"\nDistanziamento sociale "+socialDistance+
				"\nStato luci: "+room.getLights();
		
		return roomInfo;
	}

	//Returns Art pieces available in a room
	public static String isAvilableOpera(Opera art, int room){
		if((art.getStatus().equals("Disponibile"))&&(art.getRoom()==room)){
			return "\n>"+art.getTitle();
		} else
			return "";
	}
	
	//Returns a String with all Art piece titles in a room
	public static String getAvilableOperaString(ArrayList<Opera> listaOpere, int room){
		String listAvailable = "";
		for(int i = 0; i<listaOpere.size(); i++)
			listAvailable += isAvilableOpera(listaOpere.get(i), room);
		return listAvailable;
	}

	//Return a String with ID, Title, Artist and availability
	public static String getStringOpera(Opera opera){
		String string="";
		string ="ID: "+opera.getID()+" - "+ opera.getTitle()+" - "+opera.getArtist()+" - "+opera.getStatus();
		return string;
	}
	
	//Return true if an Artwork ID exists
	public static boolean operaExists(int ID) {
		List<Opera> listaOpere = Manager.getArtList();
		boolean exists = false;
		for (Opera op : listaOpere) {
			if (op.getID() == ID) {
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	//Return an Opera object by id
	public static Opera getOperaByID(int ID) {
		List<Opera> listaOpere = Manager.getArtList();
		for (Opera op : listaOpere) {
			if (op.getID() == ID)
				return op;
		}
		return null;
	}
	
	//Return the incoming artwork list
	public static List<Opera> getIncomingOpera() {
		List<Opera> listaOpere = Manager.getArtList();
		List<Opera> incomingArtworks = new ArrayList<>();
		for (Opera op : listaOpere) {
			if (op.getStatus().equals("In arrivo"))
				incomingArtworks.add(op);
		}
		return incomingArtworks;
	}
	
	//Returns the Artwork List but in alphabetic order, positive values means ascending order, negative values means descending order, 0 means default order: ascending order
	public static List<Opera> getSortedArtList(int order) 
	{	
		List<Opera> list = Manager.getArtList(); //Creating art list
        
		Comparator<Opera> compareByTitle = (Opera operaX, Opera operaY) -> operaX.getTitle().compareTo( operaY.getTitle() ); //Creating Custom Comparator
		
		if(order >= 1)
		{
			Collections.sort(list, compareByTitle); //Ascending order
		}
		else if(order <= -1)
		{
			Collections.sort(list, compareByTitle.reversed() ); //Descending order
		}
		else
		{
			Collections.sort(list, compareByTitle); //Default order: Ascending order
		}
		
		return list;
	}
	
	//Returns the amount of people in the whole museum
	public int getNumOfVisitors() 
	{
		int np = 0; //count people number
		Manager manager = new Manager();
		List<Room> rooms = manager.getRoomList();
		for(int i = 0; i < rooms.size(); i++)	np = np + rooms.get(i).getPeople();
		return np;
	}
}
