package task1;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Currencyconverter {
	
public static void main(String[] args) throws IOException {
	Boolean running= true;
	do {
	HashMap<Integer,String > currencyCodes=new HashMap<Integer, String>();
	
	//Add currency codes
	currencyCodes.put(1, "USD");
	currencyCodes.put(2, "AUD");
	currencyCodes.put(3, "CAD");
	currencyCodes.put(4, "PLN");
	currencyCodes.put(5, "MXN");
	
	Integer from,to;
	
	String fromCode, toCode = null ;
	double amount;
	Scanner sc=new Scanner(System.in);
	
	System.out.println("Welcome to currency converter!");
	
	System.out.println("Currency converting FROM? ");	
	System.out.println("1:USD(US Doller)\t 2:AUD (Australian Dollar)\t 3:CAD (Canadian Dollar)\t 4:PLN (Polish Zloty)\t 5:MXN (Mexian Peso)");
	from = sc.nextInt();
	while(from<1 || from > 5) {
		System.out.println("Please enter a vaild currency(1-5");
		System.out.println("1:USD(US Doller)\t 2:AUD (Australian Dollar)\t 3:CAD (Canadian Dollar)\t 4:PLN (Polish Zloty)\t 5:MXN (Mexian Peso)");
		from = sc.nextInt();
		}
	fromCode = currencyCodes.get(from);
	
	System.out.println("Currency converting TO? ");	
	System.out.println("1:USD(US Doller)\t 2:AUD (Australian Dollar)\t 3:CAD (Canadian Dollar)\t 4:PLN (Polish Zloty)\t 5:MXN (Mexian Peso)");
	to = sc.nextInt();
	while(to<1 || to > 5) {
		System.out.println("Please enter a vaild currency(1-5");
		System.out.println("1:USD(US Doller)\t 2:AUD (Australian Dollar)\t 3:CAD (Canadian Dollar)\t 4:PLN (Polish Zloty)\t 5:MXN (Mexian Peso)");
		to = sc.nextInt();
		}
	toCode = currencyCodes.get(to);
	
	System.out.println("Amount you wish to convert?");
	amount = sc.nextFloat();
	
	sendHttpGETRequest(fromCode,toCode,amount);
	
	System.out.println("would you like to make another conversion");
	System.out.println("1: Yes \t Any other Integer : No");
	if(sc.nextInt()!=1) {
		running = false;
	}
	}while(running);
	System.out.println("Thank you for using the currency converter!");
}

private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
	// TODO Auto-generated method stub
	DecimalFormat f = new DecimalFormat( "00.00");
	
	
	String GET_URL ="http://api.exchangeratesapi.io/v1/latest?base="+ toCode + "&symbols=" + fromCode;
	URL url =new URL(GET_URL);
	HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	httpURLConnection.setRequestMethod("GET");
	int responseCode = httpURLConnection.getResponseCode();
	
	if(responseCode==HttpURLConnection.HTTP_OK) {//success
		BufferedReader in= new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null){
			response.append(inputLine);
		}in.close();
		
		JSONObject obj =new JSONObject(response.toString());
		Double exchangeRate = obj.getJSONObject("rates").getDouble(fromCode);
		System.out.println(obj.getJSONObject("rates"));
		System.out.println(exchangeRate);//keep for debugging
		System.out.println();
		System.out.println(f.format(amount) + fromCode + " = " +f.format(amount/exchangeRate) + toCode);
		}
	else {
		System.out.println("GET request failed!");
	}
	}
	
   }

