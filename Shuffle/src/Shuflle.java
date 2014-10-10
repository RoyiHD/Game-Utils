
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;


public class Shuflle {
	Random rand = new Random();
	public static MyObject [][] objects = new MyObject[10][10];
	public static int COLS = 10;
	public static int ROWS = 10;
	
	
	public  MyObject placeActorsWhileShuffling(int col, int row, ArrayList<MyObject> list){
		return selectActorForShuffling(col, row, list);
	}
	
	public  MyObject selectActorForShuffling(int col, int row, ArrayList<MyObject> list){
		MyObject object = null;
		boolean colTest = false, rowTest = false;
		int listSize = list.size();
	
		do{
			object = list.get(rand.nextInt(listSize));
			
			if(listSize < 0)
				return null;

			listSize--;
			colTest = placeOnBoardTestColCheck(col, row, object.id);
			rowTest = placeOnBoardTestRowCheck(col, row, object.id);
			
		}while(rowTest || colTest);
	
		return object;
	}
	
	public  boolean placeOnBoardTestColCheck(int col, int row, int id){
		int counter = 0;
	
		for(int i = row-1; i > row - 3; i-- ){
			if(i >= 0 && i <= ROWS)
				if(objects[col][i] != null){
					
					if(objects[col][i].id == id){
						counter++;
						if(counter >= 2 )
							return true;
					}else
						counter = 0;
				}else
					counter = 0;	
		}
		
		if(counter >= 2)
			return true;
		
		return false;
	}
	
	public  boolean placeOnBoardTestRowCheck(int col, int row, int id){
		int counter = 0;
		
		for(int i = col-1; i > col - 3; i-- ){
			if(i >= 0 && i <= COLS){
				if(objects[i][row] != null){
					if(objects[i][row].id == id){
						counter++;
						if(counter >= 2)
							return true;
					}else
						counter = 0;	
				}else
					counter = 0;
			}
		}
		
		if(counter >= 2)
			return true;
		
		return false;
	}

	
	public ArrayList<MyObject> makeShuffleList(){
		ArrayList<MyObject> list = new ArrayList<MyObject>();
	
		for(int i = 0; i< COLS; i++){
			for(int j = 0; j <ROWS; j++){
				if(objects[i][j] != null)
					list.add(objects[i][j]);
			}
		}
		return list;
	}
	
	public boolean shuffle(ArrayList<MyObject> list, LinkedHashMap<Integer, int []> map){
		
		ArrayList<MyObject> tempList = new ArrayList<MyObject>(list);
		LinkedHashMap<Integer, int []> tempMap = new LinkedHashMap<Integer, int []>(map);
		
		 Iterator<Entry<Integer, int[]>> it = tempMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<Integer, int[]> pairs = (Entry<Integer, int[]>)it.next();
		        int col = pairs.getValue()[0];
		        int row = pairs.getValue()[1];
		        
		        MyObject object = placeActorsWhileShuffling(col, row, tempList);
		        
		        if(object == null)
		        	return false;
		       
				objects[col][row] = object;
				tempList.remove(object);
		        
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		    
		return true;
	}
	
	public static LinkedHashMap<Integer, int []> makeShuffleMapLocations(ArrayList<MyObject> shuffleList){
		
		LinkedHashMap<Integer, int []> map = new LinkedHashMap<Integer, int []>();
		int counter = 0;
		int col = 0;
		int row = 0;
		
		for(MyObject object : shuffleList){
			
			col = object.col;
			row = object.row;
			
			int [] location = new int [2];
			
			location[0] = col;
			location[1] = row;
			
			map.put(counter++, location);
			objects[col][row] = null;
		}
		return map;
	}
}


