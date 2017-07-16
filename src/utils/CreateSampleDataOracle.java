package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import model.Creditcard;
import model.Person;
import model.Product;
import model.Shopuser;

public class CreateSampleDataOracle {
	
	
	
	private ArrayList<Creditcard> creditcardlist = new ArrayList<Creditcard>();
	private ArrayList<Person> personlist = new ArrayList<Person>();
	private ArrayList<Product> productlist = new ArrayList<Product>();
	private ArrayList<Shopuser> shopuserlist = new ArrayList<Shopuser>();

	
	public static void main(String[] argv) {
		CreateSampleDataOracle csdo = new CreateSampleDataOracle();
		
		csdo.fillCreditCards();
		csdo.fillPersons();
		csdo.fillProducts();
		csdo.fillShopusers();
		
		
		
		try {
			//csdo.writeCreditcardsToOracle();
			//csdo.writePersonsToOracle();
			//csdo.writeProductsToOracle();
			csdo.writeShopusersToOracle();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	
	/**
	 * Fill the ArrayList with 10000 sample Creditcard Objects
	 */
	private void fillCreditCards() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Creditcard c1;
		for (int i = 1; i <= 10000; i++) {
			String cardname = "Visa";
			if(i%3==0) {
				cardname = "Mastercard";
			}
			else if (i%8 == 0) {
				cardname = "Amex";
			}
			
			c1 = new Creditcard(i, cardname, "1234"+i, DateHelper.getCurrentTimeStampAsDate());
			creditcardlist.add(c1);
		}
		endtime = System.nanoTime();
		System.out.println("Duration of fillCreditCards (ms): "+(endtime-starttime)/1000000);
		
	}
	
	
	/**
	 * Fill the ArrayList with 10000 sample Person Objects
	 */
	private void fillPersons() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Person p1;
		for (int i = 1; i <= 10000; i++) {
			String country = "Austria";
			if(i%4==0) {
				country = "Germany";
			}
			else if (i%13 == 0) {
				country = "Italy";
			} 
			else if (i%7 == 0) {
				country = "France";
			}
			p1 = new Person(i, "Firstname"+i, "Lastname"+i, new java.util.Date(), "0664"+i, country, "Testcity", (i+1000)+"", "Teststreet "+i, creditcardlist.get(i-1));
			personlist.add(p1);
		}
		endtime = System.nanoTime();
		System.out.println("Duration of fillPersons (ms): "+(endtime-starttime)/1000000);
		
	}

	/**
	 * Fill the ArrayList with 100000 sample Product Objects
	 */
	private void fillProducts() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Random randy = new Random();
		
		Product p1;
		for (int i = 1; i <= 1000000; i++) {
			p1 = new Product(i, "Product"+i, "The best Product!", randy.nextFloat()*randy.nextInt(1000), randy.nextFloat()*randy.nextInt(500));
			productlist.add(p1);
		}
		endtime = System.nanoTime();
		System.out.println("Duration of fillProducts (ms): "+(endtime-starttime)/1000000);
		
	}
	
	/**
	 * Fill the ArrayList with 10000 sample Shopuser Objects
	 */
	private void fillShopusers() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		

		Shopuser s1;
		for (int i = 1; i <= 10000; i++) {
			
			java.util.Date d = null;
			if(i%27 == 0) {
				d = new java.util.Date();
			}
			
			//create MD5 Hash of Password
			String pwd = "pwd"+i;
			byte[] bytesOfMessage = null;
			try {
				bytesOfMessage = pwd.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] thedigest = md.digest(bytesOfMessage);
			StringBuffer sb = new StringBuffer();
			for (byte b : thedigest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			pwd = sb.toString();

			s1 = new Shopuser("user"+i, "user"+i+"@testmail.at", pwd, new java.util.Date(), d, personlist.get(i-1));
			shopuserlist.add(s1);
		}
		endtime = System.nanoTime();
		System.out.println("Duration of fillShopusers (ms): "+(endtime-starttime)/1000000);
		
	}
	
	
	
	// ---------------------------------------------------------------------------
	
	/**
	 * Insert the Creditcards from the ArrayList into the Oracle DB
	 */
	private void writeCreditcardsToOracle() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO creditcard (creditcard_id, cardname, cardnumber, expiry_date) "
								+ "VALUES (?,?,?,?)";
		try {
			dbConnection = ConnectionHelperOracle.getDBConnection();
			
			for(Creditcard c : creditcardlist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setInt(1, c.getCreditcard_id()); // the 1. ? from the PreparedStatement
				statement.setString(2, c.getCardname()); // the 2. ? from the PreparedStatement
				statement.setString(3, c.getCardnumber()); // the 3. ? from the PreparedStatement
				statement.setDate(4, (new java.sql.Date(c.getExpiry_date().getTime())) ); // the 4. ? from the PreparedStatement
				
				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into CREDITCARD table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		endtime = System.nanoTime();
		System.out.println("Duration of writeCreditcardsToOracle (ms): "+(endtime-starttime)/1000000);
		
	}
	

	/**
	 * Insert the Shopusers from the ArrayList into the Oracle DB
	 */
	private void writeShopusersToOracle() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO shopuser (username, email, pwd, created_date, locked_date, person_id) "
								+ "VALUES (?,?,?,?,?,?)";
		try {
			dbConnection = ConnectionHelperOracle.getDBConnection();
			
			for(Shopuser s : shopuserlist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setString(1, s.getUsername()); // the 1. ? from the PreparedStatement
				statement.setString(2, s.getEmail()); // the 2. ? from the PreparedStatement
				statement.setString(3, s.getPwd()); // the 3. ? from the PreparedStatement
				statement.setDate(4, (new java.sql.Date(s.getCreated_date().getTime())) ); // the 4. ? from the PreparedStatement
				
				if (s.getLocked_date() != null) {
					statement.setDate(5, (new java.sql.Date(s.getLocked_date().getTime())) ); // the 5. ? from the PreparedStatement
				}else {
					statement.setDate(5, null); // the 5. ? from the PreparedStatement
				}
				statement.setInt(6, s.getPerson().getPerson_id()); // the 6. ? from the PreparedStatement

				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into SHOPUSER table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		endtime = System.nanoTime();
		System.out.println("Duration of writeShopusersToOracle (ms): "+(endtime-starttime)/1000000);
		
	}
	
	

	/**
	 * Insert the Person from the ArrayList into the Oracle DB
	 */
	private void writePersonsToOracle() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO person (person_id, firstname, lastname, birthdate, phone, country, city, zip, street, creditcard_id) "
								+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {
			dbConnection = ConnectionHelperOracle.getDBConnection();
			
			for(Person p : personlist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setInt(1, p.getPerson_id()); // the 1. ? from the PreparedStatement
				statement.setString(2, p.getFirstname()); // the 2. ? from the PreparedStatement
				statement.setString(3, p.getLastname()); // the 3. ? from the PreparedStatement
				statement.setDate(4, (new java.sql.Date(p.getBirthdate().getTime())) ); // the 4. ? from the PreparedStatement
				statement.setString(5, p.getPhone()); // the 5. ? from the PreparedStatement
				statement.setString(6, p.getCountry()); // the 6. ? from the PreparedStatement
				statement.setString(7, p.getCity()); // the 7. ? from the PreparedStatement
				statement.setString(8, p.getZip()); // the 8. ? from the PreparedStatement
				statement.setString(9, p.getStreet()); // the 9. ? from the PreparedStatement
				statement.setInt(10, p.getCreditcard().getCreditcard_id()); // the 10. ? from the PreparedStatement
				
				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into PERSON table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		endtime = System.nanoTime();
		System.out.println("Duration of writePersonsToOracle (ms): "+(endtime-starttime)/1000000);
		
	}
	

	
	

	/**
	 * Insert the Products from the ArrayList into the Oracle DB
	 */
	private void writeProductsToOracle() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO product (product_id, product_name, product_description, price, in_stock) "
								+ "VALUES (?,?,?,?,?)";
		try {
			dbConnection = ConnectionHelperOracle.getDBConnection();
			
			for(Product p : productlist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setInt(1, p.getProduct_id()); // the 1. ? from the PreparedStatement
				statement.setString(2, p.getProduct_Name()); // the 2. ? from the PreparedStatement
				statement.setString(3, p.getProduct_Description()); // the 3. ? from the PreparedStatement
				statement.setFloat(4, p.getPrice()); // the 4. ? from the PreparedStatement
				statement.setFloat(5, p.getIn_stock()); // the 5. ? from the PreparedStatement

				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into PRODUCT table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		endtime = System.nanoTime();
		System.out.println("Duration of writeProductsToOracle (ms): "+(endtime-starttime)/1000000);
		
	}
	

}
