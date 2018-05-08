package co.edu.javeriana.midas.generator;

public class Util {

	public static int counter=0;
	public static int iterator=1;
	
	public String setCounter(String s){
		counter=Integer.parseInt(s);
		iterator=1;
		return "";
	}
	
	public String setIterator(String s){
		iterator++;
		return "";
	}
	
	public String getCounter(String s) {
		return Integer.toString(counter);
	}
	
	public String getIterator(String s) {
		return Integer.toString(iterator);
	}
	
}
