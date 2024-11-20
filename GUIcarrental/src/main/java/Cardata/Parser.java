package Cardata;

public class Parser {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getloclist("Windsor");
	}
	
	public static void getloclist(String ctyname) {
		String loclsturl="https://www.hertz.com/rentacar/location?search="+ctyname+"&isAutoFill=&searchFrom=Homepage&triggerSearch=true&locType=pUp#results";
	}

}
