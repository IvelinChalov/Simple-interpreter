import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*; 

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;


public class Test{

	public Multimap<String, Float> mymap= ArrayListMultimap.create();
	int pos = 5;
	public static void main(String[] args) {
		
		Test obj = new Test();
		
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		Scanner in=new Scanner(System.in);
		  String s = "";
		  String ex  ="y";
		  int i = 0;
		  
		
	while(ex.equals("y")){
		
	
		  System.out.print("write key: ");
		  try {
			s = buf.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.print("write code: ");
		  i=in.nextInt();
		  
		  try {
		//	obj.Find(s, i,2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println();
		  System.out.print("continue? y/n ");
		  try {
			ex = buf.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	
	public void Load(){
		
		  mymap.clear();
		  pos = 10;
		  mymap.put("go",(float) 100);//key+code
		  mymap.put("go",(float) 1);//key+position
		  mymap.put("finish",(float)101);//key+code
		  mymap.put("finish",(float)2);//key+position
		  mymap.put(".",(float)102);//key+code
		  mymap.put(".",(float)3);//key+position
		  mymap.put("+",(float)103);
		  mymap.put("+",(float)4);
		  mymap.put("-",(float)104);
		  mymap.put("-",(float)5);
		  mymap.put("/",(float)105);
		  mymap.put("/",(float)6);
		  mymap.put("*",106f);
		  mymap.put("*",(float)7);
		  mymap.put(":=",(float)107);
		  mymap.put(":=",(float)8);
		  mymap.put("=",(float)108);
		  mymap.put("=",(float)9);
		  mymap.put("(",(float)109);
		  mymap.put("(",(float)10);
		  mymap.put(")",(float)110);
		  mymap.put(")",(float)11);
		  mymap.put("#",(float)111);
		  mymap.put("#",(float)12);
		  mymap.put(">",(float)112);
		  mymap.put(">",(float)13);
		  mymap.put("<",(float)113);
		  mymap.put("<",(float)13);
		  mymap.put("]",(float)114);
		  mymap.put("]",(float)14);
		  mymap.put("[",(float)115);
		  mymap.put("[",(float)15);
		  mymap.put("ako",(float)116);
		  mymap.put("ako",(float)16);
		  mymap.put("dokato",(float)117);
		  mymap.put("dokato",(float)17);
		  mymap.put("inache",(float)118);
		  mymap.put("inache",(float)18);
	}
	
	
	public float Find(String value, Float num,Float row)throws Exception {
		  
		Object val = new Object();
		Collection  valSet;
		Iterator valIterator;
	    char ch = value.charAt(0);	  
		  if(!mymap.containsKey(value)){
			  
			  mymap.put(value,num);//key+code
			 if(Character.isDigit(ch)){
				 mymap.put(value, Float.parseFloat(value));// defalt value
			 }else{
				 mymap.put(value, row);
				 mymap.put(value, (float) 0);// defalt value 
			 }
			  pos++;
			  System.out.println(mymap.keySet());
				return 0;	
			  //vra6ta vsi4ki klycove			  
		  }else{
			valSet = mymap.get(value);
			valIterator = valSet.iterator();
			val = valIterator.next( ); 
			return (float)val;
		  }	
	}
	
	public float getValue(String name){
		Object val = new Object();
		Collection  valSet;
		Iterator valIterator;
		if(!mymap.containsKey(name)){
			System.out.println("Unexpected err");
			return 111;
		}else{
			valSet = mymap.get(name);
		    valIterator = valSet.iterator();
		    while( valIterator.hasNext( ) ) {  
		         val = valIterator.next( ); 
		        
		    }  
		    return (float)val;
			
		}
				
	}
	
	public void addValue(String name,Float oldValue, Float newValue){
		Object val = new Object();
		Collection  valSet;
		Iterator valIterator;
		if(mymap.containsKey(name)){
			mymap.remove(name, oldValue);
			mymap.put(name, newValue);
		}
		
		
		
	}
	
	public float getCord(String name){
		int count = 0;
		Object val = new Object();
		Collection  valSet;
		Iterator valIterator;
		if(mymap.containsKey(name)){
			valSet = mymap.get(name);
		    valIterator = valSet.iterator();
		    while( valIterator.hasNext( ) ) {
		    	if(count == 1)break;
		    	val = valIterator.next( );
		    	count++;
		         
		    }
		    return (float)val;
		}
		return (float)val;
		
	}
	
	public void removeCode(){
		Set g = mymap.keySet();
		 Object []keyArr = g.toArray();
		 for(int i = 0; i < keyArr.length; i++){
			 mymap.remove(keyArr[i], -3f);
		 }		 	 
			System.out.println("--");

	}

}