import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.google.common.collect.Table;


public class proben extends JFrame implements ItemListener,ActionListener{
	
	String synAnalis [] = new String [1000]; 
	int addTrue = -1;
	int labNum = 1;
	int check = 0;
	int globCount = 1;
	int prisCheck = 0;
	int arrClear = 0;
	Exception myException = new ArithmeticException("You can't divide by zero!");
	int Excep = 0;
	JTextArea display = new JTextArea ();
	JButton b1 = new JButton("RUN");
	JButton bDebug = new JButton("Debug");
	static Test obj = new Test();
	int lexErr = 0;
	int SynErr = 0; 
	JScrollPane scroll = new JScrollPane ( display );
	JFrame frame = new JFrame ("Menu");
	//boolean dotCheck = false;
	int dotCheck = 0;
	
	//za semanti4en 
	SynAnalis mf[] = new SynAnalis[1000];
	int incMf = 0;
	String R ="";//nov kod
	int inc = 0;//nov kod
	
	//tablica za promenlivi
	float rowNum = 0;
	String[] columnNames = {"Varible name",
            "Value",
            };
	
	Object[][] data ={};
	
	 JTable wachTable = new JTable(new DefaultTableModel(data,columnNames));
	 JScrollPane tScroll = new JScrollPane ( wachTable );
	 DefaultTableModel model = (DefaultTableModel) wachTable.getModel();
	 
	//tablica za Mezdinna forma
		String[] colNames = {"¹",
				"Op",
	            "Op1",
	            "Op2",
	            "Rez"
	            };
		
		
		
		 JTable mfTable = new JTable(new DefaultTableModel(data,colNames));
		 JScrollPane mfTabScroll = new JScrollPane ( mfTable );
		 DefaultTableModel mfTabModel = (DefaultTableModel) mfTable.getModel();
	 

	
	proben(){
		
	    frame.getContentPane().setBackground(Color.LIGHT_GRAY);
	    frame.setSize(1100,375);
	    frame.setLayout(null);
	   
	    frame.setVisible ( true );
	    frame.setResizable(false);//trqbva da e false
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
	    scroll.setBounds(10, 10, 500, 200);
	  
	    
	    frame.getContentPane().add(scroll);
	    display.setEditable ( true ); // set textArea non-editable
	    display.setBackground(Color.white);
	    
	
	       

	    mfTabScroll.setBounds(750, 10, 300, 300);
	    frame.add(mfTabScroll);
	    b1.setBounds(50, 250, 150,32);
	    frame.add(b1);
	    b1.addActionListener(this);
	    
	   
	    bDebug.setBounds(230, 250, 150,32);
	    bDebug.setEnabled(false);
	    frame.add(bDebug);
	    bDebug.addActionListener(this);
	    
	    //table
	    tScroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    tScroll.setBounds(525, 10, 200, 300);
	    
	    frame.getContentPane().add(tScroll);
	    
	 
		
	    
	}
	
	
	public static void main(String[] args) {
		
		proben obj1 = new proben();
		
		
		
		
	

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("RUN")){
			i =0;//broqch na debugera
			incMf = 0;
			obj.Load();
			for(int i=0;i<1000;i++){
				synAnalis[i] = "";
			}
			clearTable();
			String str = display.getText();
			
			str = str + " ";
			str.toLowerCase();
			int ascii = 0;
			int count = 0;
			String buff = "";
			char ch;
			
			for(int i=0;i<str.length();i++){
				
				ascii = (int)str.charAt(i);
				ch = str.charAt(i);
				if (lexErr == 1){
					System.out.println("Lex Err");
					break;
				}
				if(ascii<=32){ 								//za nov red i etc.
					continue;
				}else if(Character.isLetter(ch)){			// proverka za bykvi vsi4kite
					while((Character.isLetter(ch))||(Character.isDigit(ch))){
						buff = buff+str.charAt(i);
						i++;
						ch = str.charAt(i);
					}
					System.out.println(buff);
					try {
						if(obj.Find(buff,-3f, rowNum) == 0f){
							model.addRow(new Object[]{buff, "0"});
							rowNum ++;
						}
						synAnalis[count] = buff;
						
						arrClear++;
						
						count++;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					buff = "";	
					i--;
				}else if(Character.isDigit(ch)){//proverki za chisla
					while((Character.isDigit(ch))||(ch=='.')){
						buff = buff+str.charAt(i);
						for( int j=0; j<buff.length(); j++ ) {
						    if( buff.charAt(j) == '.' ) {
						        dotCheck++;
						    } 
						}
						if(dotCheck>1){
							lexErr = 1;
						}
						i++;
						ch = str.charAt(i);
						dotCheck = 0;
					}
					if ((Character.isLetter(ch))||(buff.charAt(buff.length()-1)=='.')) {  //proverka za bykva sled chislo i za 12. primerno
						lexErr = 1;
					}else{							//vliza kogato e samo 4islo
						try {
							obj.Find(buff, -2f,-1f);
							synAnalis[count] = buff;	
							arrClear++;
							count++;
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					buff = "";
					i--;
				}else if((ascii==35)||(ascii==40)||(ascii==41)||(ascii==42)||(ascii==43)||(ascii==45)||(ascii==47)||(ascii==58)||(ascii==60)||(ascii==61)||(ascii==62)||(ascii==91)||(ascii==93)){//proverka za +,- ...
					if((ch==':')&&(str.charAt(i+1)=='=')){
						buff = ":=";
						i++;
					}else if((ch!='#')&&(ch!='=')&&(ch!=']')&&(ch!='[')&&(ch!='>')&&(ch!='<')&&(ch!=' ')&&(ch!='+')&&(ch!='-')&&(ch!='*')&&(ch!='/')&&(ch!='(')&&(ch!=')')&&(str.charAt(i+1)!='=')&&(str.charAt(i+1)!=']')&&(str.charAt(i+1)!='[')&&(str.charAt(i+1)!='>')&&(str.charAt(i+1)!='<')&&(str.charAt(i+1)!=' ')&&(str.charAt(i+1)!='#')&&(str.charAt(i+1)!='+')&&(str.charAt(i+1)!='-')&&(str.charAt(i+1)!='*')&&(str.charAt(i+1)!='/')&&(str.charAt(i+1)!='(')&&(str.charAt(i+1)!=')')){
							lexErr = 1;	
					}else{
						buff =buff + str.charAt(i);
					}
					try {
					if(buff!=""){
						obj.Find(buff, 2f, 0f);
						synAnalis[count] = buff;
						arrClear++;
						count++;
					}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						buff = "";
				}else { 									//err
					System.out.println("err");
					lexErr = 1;
					break;
					
				}								
			}
		
		
		if(lexErr == 0){
			try {
				SynAnalis();
				
				createAssembly();
				bDebug.setEnabled(true);
				
				for(int i=0; i<mf.length;i++){
					if(mf[i].op.equals("STOP")){
						mfTabModel.addRow(new Object[]{i, mf[i].op, mf[i].o1, mf[i].o2, mf[i].rez});
						break;
					}
					mfTabModel.addRow(new Object[]{i, mf[i].op, mf[i].o1, mf[i].o2, mf[i].rez});	
				}
				
				obj.removeCode();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				 switch (Excep) {
				 	case 1: System.out.println("\"var\", \"const\" or \")\" expected");
				 			break;
				 	case 2: System.out.println(" \")\" expected ");
		 					break;	
				 	case 3: System.out.println(" \"go\" expected or writing before go ");
 							break;
				 	case 4: System.out.println(" \"finish\" expected ");
							break;
				 	case 5: System.out.println(" \"#\" expected or remove this token ");
				 			break;
				 	case 6: System.out.println(" writing after finish ");
				 			break;
				 	case 7: System.out.println(" \":=\" expected ");
		 					break;
				 	case 8: System.out.println(" operator expected ");
 							break;
				 	case 9: System.out.println(" \")\" or \"<\" expected ");
							break;
				 	case 10: System.out.println(" \"#\" or \">\" expected ");
				 			break;
				 	case 11: System.out.println(" add \"<\" or remove the operator ");
		 					break;
				 	case 12: System.out.println(" add operator or remove \"<\"  ");
		 					break;
				 	case 13: System.out.println(" logical expresin expected ");
 							break;
				 	case 14: System.out.println(" \"(\" expected ");
							break;		
				 }
			}
			globCount = 1;
			SynErr = 0;
			Excep = 0;
			R = "";
			inc = 0;
			rowNum =0;
		}else{
			lexErr = 0;
		}
		
	}else if(e.getActionCommand().equals("Debug")){
			debuger();
		}
}
	

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void SynAnalis()throws Exception{
		int lastErr = 0; //za gre6ka pri posleden simvol
		if(!synAnalis[0].equals("go")){
			Excep = 3;
			lastErr = 1;
			 throw myException;
		} else{
			Operators();
			while(synAnalis[globCount].equals("#")){
				if(SynErr<3){
					SynErr = 0;
					prisCheck=0;
					globCount++;
					Operators();
					
				}else{
					System.out.println("Syn Err");
					lastErr = 1;
					break;
				}			
			}
			
			if((synAnalis[globCount].equals("finish"))&&(!synAnalis[globCount + 1].equals(""))){
				Excep = 6;
				throw myException;
			}else{
				if(!synAnalis[globCount + 1].equals("")){
					Excep = 5;
					throw myException;
				}else if(synAnalis[globCount].equals("finish")){
					if(addTrue != -1){
						addTetr("STOP", "-", "-","-", true);
						addTrue = -1;
					}else{
						addTetr("STOP", "-", "-","-", false);
					}
					System.out.println("No Err");
				}else{
					Excep = 4;
					throw myException;
				}
				//throw myException;
			}		
		}		
	}
	
	public void Operators() throws Exception{
		int code = (int)obj.Find(synAnalis[globCount], 123f, 0f);
		if(code==-3){
			prisvoqvane(synAnalis[globCount]);
		}else if(synAnalis[globCount].equals("ako")){
			ako(synAnalis[globCount]);
		}else if(synAnalis[globCount].equals("dokato")){
			dokato(synAnalis[globCount]);
		}else if((synAnalis[globCount].equals("finish"))||(synAnalis[globCount].equals(">"))){
			
		}
		else{
			Excep = 8;
			throw myException;
			
		}
		
		
	}
	
	public void prisvoqvane(String op1) throws Exception{
		String op2 = "";
			if((synAnalis[globCount+1].equals(":="))&&(synAnalis[globCount+2]!=null)){
				Excep = 0;
				globCount = globCount + 2;
				op2 = izraz(op2);
				if(addTrue != -1) {
					addTetr(":=", op2, "-",op1, true);
					addTrue = -1;
				}else{
					addTetr(":=", op2, "-",op1, false);
				}
				System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
			}else {
				Excep = 7;
				throw myException;
				
			}
		
			
		
		
	}
	
	public void ako(String op1) throws Exception{
		int jmpBRZ = 0, jmpJmp = 0;
		String op2 = "";
		if(synAnalis[globCount+1].equals("(")){
			Excep = 0;
			globCount = globCount+2;
			op2 = logIzraz(op1);
			addTetr("BRZ", "-",op2 ,"-", false);
			jmpBRZ =incMf-1;
	
			System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
			
			if((synAnalis[globCount].equals(")"))&&(synAnalis[globCount+1].equals("<"))){
				globCount = globCount+2;
				Operators();
				
				while(synAnalis[globCount].equals("#")){
					globCount++;
					Operators();
				}
				if(synAnalis[globCount].equals(">")){
					System.out.println("ako done");
					prisCheck = 1;
					SynErr = 0;
					globCount++;//?
					System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
				
					//elseaaaaaaaaaa
					if((synAnalis[globCount].equals("inache"))&&(synAnalis[globCount+1].equals("<"))){
						mf[jmpBRZ].o1 = Integer.toString(incMf+1);//<----
						if(addTrue != -1){
							addTetr("jmp", "-","-" ,"-", true);
							addTrue = -1;
						}else{
							addTetr("jmp", "-","-" ,"-", false);
						}
						jmpJmp =incMf-1;
						addTrue = incMf;// kod za asemblera  dali da e predi ili sled addTetr?
						globCount = globCount+2;
						Operators();
						
						while(synAnalis[globCount].equals("#")){
							globCount++;
							Operators();
							
						}
						if(synAnalis[globCount].equals(">")){
							System.out.println("inache done");
							prisCheck = 1;
							SynErr = 0;
							globCount++;//?
							mf[jmpJmp].o1 = Integer.toString(incMf);
							addTrue = incMf;
						}else{
							Excep = 10;
							throw myException;
						}
					}else if((synAnalis[globCount].equals("inache"))&&(!synAnalis[globCount+1].equals("<"))){
						Excep = 11;
						throw myException;
					}else if(synAnalis[globCount].equals("<")){
						Excep = 12;
						throw myException;
					}else{
						mf[jmpBRZ].o1 = Integer.toString(incMf);//ako nqma else se prisvoqva edno po-malko
						addTrue = incMf;
					}
					
				}else{
					Excep = 10;
					throw myException;
				}
				
			}else{
				Excep = 9;
				throw myException;
			}
			
		}else if((synAnalis[globCount].equals("ako"))&&(!synAnalis[globCount+1].equals("("))){
			Excep = 14;
			throw myException;
		}else if(synAnalis[globCount].equals("(")){
			Excep = 8;
			throw myException;
		}
	}
public void dokato(String op1) throws Exception{
	int up = 0, down = 0;
	String op2 = "";
	if(synAnalis[globCount+1].equals("(")){
		Excep = 0;
		globCount = globCount+2;
		up = incMf;
		addTrue = incMf;
		op2 = logIzraz(op1);
		addTetr("BRZ", "-",op2 ,"-", false);
		down = incMf-1;
		System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
		if((synAnalis[globCount].equals(")"))&&(synAnalis[globCount+1].equals("<"))){
			globCount = globCount+2;
			Operators();
			
			while(synAnalis[globCount].equals("#")){
				globCount++;
				Operators();
				
			}
			if(synAnalis[globCount].equals(">")){
				if(addTrue != -1){
					addTetr("jmp", Integer.toString(up),"-" ,"-", true);
					addTrue = -1;
				}else{
					addTetr("jmp", Integer.toString(up),"-" ,"-", false);
				}
				 
				mf[down].o1 = Integer.toString(incMf);
				addTrue = incMf;
				System.out.println("dokato done");
				prisCheck = 1;
				SynErr = 0;
				globCount++;//?
			}else{
				Excep = 10;
				throw myException;
			}
			
		}else{
			Excep = 9;
			throw myException;
		}
	}else if((synAnalis[globCount].equals("dokato"))&&(!synAnalis[globCount+1].equals("("))){
		Excep = 14;
		throw myException;
	}else if(synAnalis[globCount].equals("(")){
		Excep = 8;
		throw myException;
	}
}
	 String logIzraz(String op1) throws Exception{
		 String op2 = "";
		op1 = izraz(op1);
		if(synAnalis[globCount].equals("=")){
			System.out.println("LogIzraz =");
			globCount++;
			op2 = izraz(op2);
			R = varGenerator(R);
			obj.Find(R,1f, -1f);
			if(addTrue != -1){
				addTetr("=", op1, op2, R, true);
				addTrue = -1;
			}else{
				addTetr("=", op1, op2, R, false);
			}
			System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
			op1 = R;
			return op1;/////
		}else if(synAnalis[globCount].equals("[")){
			System.out.println("LogIzraz [");
			globCount++;
			op2 = izraz(op2);
			R = varGenerator(R);
			obj.Find(R,1f, -1f);
			if(addTrue != -1){
				addTetr("[", op1, op2, R, true);
				addTrue = -1;
			}else{
				addTetr("[", op1, op2, R, false);
			}
			System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
			op1 = R;
			return op1;/////
		}else if(synAnalis[globCount].equals("]")){
			System.out.println("LogIzraz ]");
			globCount++;
			op2 = izraz(op2);
			R = varGenerator(R);
			obj.Find(R,1f, -1f);
			if(addTrue != -1){
				addTetr("]", op1, op2, R, true);
				addTrue = -1;
			}else{
				addTetr("]", op1, op2, R, false);
			}
			System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
			op1 = R;
			return op1;/////
		}else{
			Excep = 13;
			throw myException;
			
			
		}
		
	}
	
	public String izraz(String op1) throws Exception{
		String op2 = "";
		op1 = term(op1);
		while((synAnalis[globCount].equals("+"))||(synAnalis[globCount].equals("-"))){
			if(synAnalis[globCount].equals("+")){
			System.out.println("Izraz +");
			globCount++;
			op2 = term(op2);
			R = varGenerator(R);
			obj.Find(R,1f, -1f);
			if(addTrue != -1){
				addTetr("+", op1, op2, R, true);
				addTrue = -1;
			}else{
				addTetr("+", op1, op2, R, false);
			}
			System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
			op1 = R;
			}else{
				System.out.println("Izraz -");
				globCount++;
				op2 = term(op2);	
				R = varGenerator(R);
				obj.Find(R,1f, -1f);
				if(addTrue != -1){
					addTetr("-", op1, op2, R, true);
					addTrue = -1;
				}else{
					addTetr("-", op1, op2, R, false);
				}
				System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
				op1 = R;
			}
		}
		return op1;
	}
			
	public String term(String op1) throws Exception{
		String op2 = "";//nov kod
		op1 = factor(op1);
		while((synAnalis[globCount].equals("*"))||(synAnalis[globCount].equals("/"))){
			if(synAnalis[globCount].equals("*")){
			System.out.println("Izraz *");
			globCount++;
			op2 =factor(op2);
			R = varGenerator(R);
			obj.Find(R,1f, -1f);
			if(addTrue != -1){
				addTetr("*", op1, op2, R, true);
				addTrue = -1;
			}else{
				addTetr("*", op1, op2, R, false);
			}
			System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
			op1 = R;
			}else{
				System.out.println("Izraz /");
				globCount++;
				op2 = factor(op2);	
				R = varGenerator(R);
				obj.Find(R,1f, -1f);
				if(addTrue != -1){
					addTetr("/", op1, op2, R, true);
					addTrue = -1;
				}else{
					addTetr("/", op1, op2, R, false);
				}
				System.out.println(mf[incMf-1].op + " " + mf[incMf-1].o1 + " " +mf[incMf-1].o2 + " " + mf[incMf-1].rez);
				op1 = R;
			}
		}
		return op1;
	}
	
	public String factor(String op) throws Exception {
		int code = 0;
		try {
			code = (int)obj.Find(synAnalis[globCount], 123f, 0f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(code==-2){
			System.out.println("Prisvoqvane const");
			op = synAnalis[globCount];//nov kod
			globCount++;
			return op;
		}else if(code==-3){
			System.out.println("Prisvoqvane var");
			op = synAnalis[globCount];//nov kod
			globCount++;
			return op;
			
		}else if(code==109){
			globCount++;
			op = izraz(op);
			try {
				code = (int)obj.Find(synAnalis[globCount], 123f, 0f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(code==110){
				System.out.println("Prisvoqvane ()");
				globCount++;
				return op;
				
			}else{
				SynErr=3;
				Excep = 2;
				throw myException;
			}
			
		}else{
			SynErr = 3;
			Excep = 1;
		    throw myException;
		}
	}
	
	public String varGenerator(String s){
		
		
		s = "$" + Integer.toString(inc);
		inc++;
		return s;
	}
	
public void addTetr(String op, String op1, String op2, String rez, Boolean skok ){
	
	mf[incMf] = new SynAnalis(op, op1, op2, rez, skok);
	incMf++;
		
		
	}
public void createAssembly(){
	String name = "";
	
	FileWriter write;
	try {
		String lab = "";
		String lab1 = "";
		write = new FileWriter(".\\Asembly.txt");
		BufferedWriter bw = new BufferedWriter(write);
		bw.write("	Hello world");
		bw.newLine();
		for(int i=0; i<incMf;i++){
			name = mf[i].op;
			
			switch (name){
				case "+": if(mf[i].jmp){
							bw.write("ET" + i + "	mov " + "eax, " + mf[i].o1);
						}else{
							bw.write("	mov " + "eax, " + mf[i].o1);
						}
						bw.newLine();
						bw.write("	mov " + "ebx, " + mf[i].o2);
						bw.newLine();
						bw.write("	add " + "eax, " +  "ebx");
						bw.newLine();
						bw.write("	mov " + mf[i].rez + ", " + "eax");
						bw.newLine();
						bw.newLine();
						break;
				case "-":if(mf[i].jmp){
							bw.write("ET" + i + "	mov " + "eax, " + mf[i].o1);
						}else{
							bw.write("	mov " + "eax, " + mf[i].o1);
						}
						bw.newLine();
						bw.write("	mov " + "ebx, " + mf[i].o2);
						bw.newLine();
						bw.write("	sub " + "eax, " +  "ebx");
						bw.newLine();
						bw.write("	mov " + mf[i].rez + ", " + "eax");
						bw.newLine();
						bw.newLine();
						break;
				case "*": if(mf[i].jmp){
							bw.write("ET" + i + "	mov " + "eax, " + mf[i].o1);
						}else{
							bw.write("	mov " + "eax, " + mf[i].o1);
						}
						bw.newLine();
						bw.write("	imul " + "eax, " + mf[i].o2);
						bw.newLine();
						bw.write("	mov " + mf[i].rez + ", " + "eax");
						bw.newLine();
						bw.newLine();
						break;
				case "/": if(mf[i].jmp){
							bw.write("ET" + i + "	mov " + "edx, " + mf[i].o1);
						}else{
							bw.write("	mov " + "edx, " + mf[i].o1);
						}
						bw.newLine();
						bw.write("	idiv " +  mf[i].o2 );
						bw.newLine();
						bw.write("	mov " + mf[i].rez + ", " + "eax");
						bw.newLine();
						bw.newLine();
						break;
				case "=": if(mf[i].jmp){
							bw.write("ET" + i + "	mov " + "eax, " + mf[i].o1);
						}else{
							bw.write("	mov " + "eax, " + mf[i].o1);
						}
						bw.newLine();
						bw.write("	mov " + "ebx, " + mf[i].o2);
						bw.newLine();
						bw.write("	cmp " + "eax, " + "ebx" );
						bw.newLine();
						lab = labGen();
						bw.write("	jne " +  lab);
						bw.newLine();
						bw.write("	mov " + "eax, " + "1");
						bw.newLine();
						lab1 = labGen();
						bw.write("	jmp " +  lab1);
						bw.newLine();
						bw.write(lab + "    " + "mov " + "eax, " + "0");
						bw.newLine();
						bw.write(lab1 + "    " + "mov " + "ebx, " + mf[i].rez);
						bw.newLine();
						bw.write("	mov " + "[" + "ebx" + "], " + "eax");
						bw.newLine();
						bw.newLine();
						break;
				case "[":if(mf[i].jmp){
							bw.write("ET" + i + "	mov " + "eax, " + mf[i].o1);// po malko
						}else{
							bw.write("	mov " + "eax, " + mf[i].o1);
						}
						bw.newLine();
						bw.write("	mov " + "ebx, " + mf[i].o2);
						bw.newLine();
						bw.write("	cmp " + "eax, " + "ebx" );
						bw.newLine();
						lab = labGen();
						bw.write("	jg " +  lab);
						bw.newLine();
						bw.write(	"	mov " + "eax, " + "1");
						bw.newLine();
						lab1 = labGen();
						bw.write("	jmp " +  lab1);
						bw.newLine();
						bw.write(lab + "    " + "mov " + "eax, " + "0");
						bw.newLine();
						bw.write(lab1 + "    " + "mov " + "ebx, " + mf[i].rez);
						bw.newLine();
						bw.write("	mov " + "[" + "ebx" + "], " + "eax");
						bw.newLine();
						bw.newLine();
						break;
				case "]":if(mf[i].jmp){
							bw.write("ET" + i + "	mov " + "eax, " + mf[i].o1);
						}else{
							bw.write("	mov " + "eax, " + mf[i].o1);
						}
						bw.newLine();
						bw.write("	mov " + "ebx, " + mf[i].o2);
						bw.newLine();
						bw.write("	cmp " + "eax, " + "ebx" );
						bw.newLine();
						lab = labGen();
						bw.write("	jl " +  lab);
						bw.newLine();
						bw.write("	mov " + "eax, " + "1");
						bw.newLine();
						lab1 = labGen();
						bw.write("	jmp " +  lab1);
						bw.newLine();
						bw.write(lab + "    " + "mov " + "eax, " + "0");
						bw.newLine();
						bw.write(lab1 + "    " + "mov " + "ebx, " + mf[i].rez);
						bw.newLine();
						bw.write("	mov " + "[" + "ebx" + "], " + "eax");
						bw.newLine();
						bw.newLine();
						break;
				case ":=": if(mf[i].jmp){
							bw.write("ET" + i + "	mov " + "eax, " + mf[i].rez);
						}else{
							bw.write("	mov " + "eax, " + mf[i].rez);
						}
						bw.newLine();
						bw.write("	mov " + "[" + "eax" + "], " + mf[i].o1);
						bw.newLine();
						bw.newLine();
						break;
				case "BRZ": bw.write("	mov " + "eax, " + "1");
						bw.newLine();
						bw.write("	mov " + "ebx, " + mf[i].o2);
						bw.newLine();
						bw.write("	cmp " + "eax, " + "ebx" );
						bw.newLine();
						bw.write("	jne " +  "ET" + mf[i].o1);
						bw.newLine();
						bw.newLine();
						break;
				case "jmp":if(mf[i].jmp){
							bw.write("ET" + i + "	jmp " +  "ET" + mf[i].o1);
						}else{
							bw.write("	jmp " +  "ET" + mf[i].o1);
						}
						bw.newLine();
						bw.newLine();
						break;
				case "STOP": if(mf[i].jmp){
								bw.write("ET" + i + "	int 20 ");
								bw.newLine();
							}else{
								bw.write("	int 20 ");
							}
			}	
			
		}
		
		
		
		bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}

public String labGen(){
	String lab = "";
	lab = "lab" + Integer.toString(labNum);
	labNum++;
	return lab;
	
	
}
 int i =0;
public void debuger(){
	
	String operatin = "";
	float iOp1 = 0, iOp2 = 0, iRez = 0, oldRez = 0;
	int tabRow = 0;
		try {
			operatin = mf[i].op;
		} catch (Exception e) {
			System.out.print("Ne ste vaveli kod!");
		}
		
		switch (operatin){
			case "+": iOp1 = obj.getValue(mf[i].o1);
					iOp2 = obj.getValue(mf[i].o2);
					oldRez = obj.getValue(mf[i].rez);
					iRez = iOp1 + iOp2;
					obj.addValue(mf[i].rez,oldRez, iRez);
					mfTable.changeSelection(i,1,false,false);
					iRez = obj.getValue(mf[i].rez);
					System.out.println( "+ " + iOp1 + " " + iOp2 + " " + iRez );
					break;
			case "-": iOp1 = obj.getValue(mf[i].o1);
					iOp2 = obj.getValue(mf[i].o2);
					iRez = iOp1 - iOp2;
					oldRez = obj.getValue(mf[i].rez);
					obj.addValue(mf[i].rez, oldRez, iRez);
					System.out.println( "- " + iOp1 + " " + iOp2 + " " + iRez );
					mfTable.changeSelection(i,1,false,false);
					break;
			case "*": iOp1 = obj.getValue(mf[i].o1);
					iOp2 = obj.getValue(mf[i].o2);
					iRez = iOp1 * iOp2;
					oldRez = obj.getValue(mf[i].rez);
					obj.addValue(mf[i].rez, oldRez, iRez);
					System.out.println( "* " + iOp1 + " " + iOp2 + " " + iRez );
					mfTable.changeSelection(i,1,false,false);
					break;
			case "/": iOp1 = obj.getValue(mf[i].o1);
					iOp2 = obj.getValue(mf[i].o2);
					iRez = iOp1 / iOp2;
					oldRez = obj.getValue(mf[i].rez);
					obj.addValue(mf[i].rez, oldRez, iRez);
					System.out.println( "/ " + iOp1 + " " + iOp2 + " " + iRez );
					mfTable.changeSelection(i,1,false,false);
					break;
			case "=": iOp1 = obj.getValue(mf[i].o1);
					iOp2 = obj.getValue(mf[i].o2);
					if(iOp1 == iOp2){
						oldRez = obj.getValue(mf[i].rez);
						obj.addValue(mf[i].rez, oldRez, 1f);
					}else{
						oldRez = obj.getValue(mf[i].rez);
						obj.addValue(mf[i].rez, oldRez, 0f);
					}
					iRez = obj.getValue(mf[i].rez);
					System.out.println( "= " + iOp1 + " " + iOp2 + " " + iRez );
					mfTable.changeSelection(i,1,false,false);
					break;
			case "[": iOp1 = obj.getValue(mf[i].o1);
					iOp2 = obj.getValue(mf[i].o2);
					if(iOp1 < iOp2){
						oldRez = obj.getValue(mf[i].rez);
						obj.addValue(mf[i].rez, oldRez, 1f);
					}else{
						oldRez = obj.getValue(mf[i].rez);
						obj.addValue(mf[i].rez, oldRez, 0f);
					}
					iRez = obj.getValue(mf[i].rez);
					System.out.println( "< " + iOp1 + " " + iOp2 + " " + iRez );
					mfTable.changeSelection(i,1,false,false);
					break;
			case "]": iOp1 = obj.getValue(mf[i].o1);
					iOp2 = obj.getValue(mf[i].o2);
					if(iOp1 > iOp2){
						oldRez = obj.getValue(mf[i].rez);
						obj.addValue(mf[i].rez, oldRez, 1f);
					}else{
						oldRez = obj.getValue(mf[i].rez);
						obj.addValue(mf[i].rez, oldRez, 0f);
					}
					iRez = obj.getValue(mf[i].rez);
					System.out.println( "> " + iOp1 + " " + iOp2 + " " + iRez );
					mfTable.changeSelection(i,1,false,false);
					break;
			case ":=": mfTable.changeSelection(i,1,false,false);
					iOp1 = obj.getValue(mf[i].o1);
					oldRez = obj.getValue(mf[i].rez);
					obj.addValue(mf[i].rez, oldRez, iOp1);
					tabRow = (int)obj.getCord(mf[i].rez);
					model.setValueAt(iOp1, tabRow, 1);// obnovqva stoinosta na promenlivata
					iRez = obj.getValue(mf[i].rez);
					System.out.println( ":= " + iOp1 + " " + mf[i].rez);
					break;
			case "BRZ": iOp2 = obj.getValue(mf[i].o2);
					if(iOp2 == 0){
						System.out.println( "BRZ " + mf[i].o1 + " " + iOp2);
						i = Integer.parseInt(mf[i].o1) - 1;
					}
					mfTable.changeSelection(i,1,false,false);
					break;
			case "jmp": 
					mfTable.changeSelection(i,1,false,false);
					i = Integer.parseInt(mf[i].o1) - 1;
					break;
			case "STOP": System.out.println("End of program");
					mfTable.changeSelection(i,1,false,false);
					bDebug.setEnabled(false);
						i = -1;
					break;
		}
		i++;
}
int f = 0;
	public void clearTable(){
		
		f = model.getRowCount();
		System.out.println("ROW COWNT =" + model.getRowCount());
		for(int i = 0; i < f; i++ ){			
			model.removeRow(0);//avtomati4no si obnovqva poziciqta !!!!
		}
		f = mfTabModel.getRowCount();
		for(int i = 0; i < f; i++ ){
			mfTabModel.removeRow(0);
		}
	}
	
}