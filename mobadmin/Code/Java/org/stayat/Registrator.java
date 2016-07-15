package org.stayat;

import java.util.ArrayList;
import lotus.domino.NotesException;
import lotus.domino.Registration;
import lotus.domino.Session;

/**
 * 
 * @author Marcel Bussien
 *
 */
public class Registrator {
	
	static String strLine="";
	static String strField="";
	
	/**
	 * 
	 * @param session
	 * @param alProfile
	 * @param alUser
	 * @return
	 * @throws NotesException
	 */
	public static boolean registerALLUser(Session session, ArrayList<String> alProfile,
			ArrayList<String> alUser) throws NotesException{
		
		System.out.println("Registrator.registerALLUser alProfile 4 "+alProfile.get(4)+" 5 "+alProfile.get(5));
		
		boolean bReturn = false;
		
		for(int k=0;k<=alUser.size()-1;k++){
			strLine = alUser.get(k).toString();
			strLine = strLine.replace("¬¬", "¬#¬");
			strLine = strLine.replace("¬¬", "¬#¬");
			strLine = strLine.replace("¬¬", "¬#¬");
			System.out.println(k+". strLine "+strLine);

			String strSep = "¬";
			int sep = strLine.indexOf("¬");
			String strEntry=null; 
			ArrayList<String> al = new ArrayList<String>();
			al.clear();
			while (sep >= 1) {
				strEntry = strLine.substring(0, sep);
				al.add(strEntry);
				strLine = strLine.substring(strLine.substring(0, sep).length()+1,strLine.length());
			    sep = strLine.indexOf(strSep);
			 }
			System.out.println("strLine "+strLine+" al "+al);
			bReturn = registerNewUser(session,alProfile, al );
		}
		return bReturn;
	}
	
	/**
	 * 
	 * @param session
	 * @param alProfile
	 * @param alUserEntry
	 * @return
	 * @throws NotesException
	 */
	public static boolean registerNewUser(Session session, ArrayList<String> alProfile, ArrayList<String> alUserEntry) throws NotesException{
		for(int i=0;i<=alProfile.size()-1;i++){
			System.out.println(i+". alProfile "+alProfile.get(i));
		}
		for(int k=0;k<=alUserEntry.size()-1;k++){
			System.out.println(k+". alUserEntry "+alUserEntry.get(k));
		}
		
		boolean bRegister = false;
		
		String strId = createID(alUserEntry.get(0).toString(),alUserEntry.get(1).toString());
		System.out.println("strId "+strId);
		System.out.println("DATAPATH "+alProfile.get(2)+"\\"+strId+".ID");
		System.out.println("MAILSERVER "+alProfile.get(5));
		System.out.println("CERTIDPATH "+alProfile.get(6));
	try{
	Registration reg = session.createRegistration();
      reg.setRegistrationServer(alProfile.get(0));
      reg.setCreateMailDb(false);
      reg.setCertifierIDFile(alProfile.get(6));
      reg.setIDType(Registration.ID_HIERARCHICAL);
      reg.setMinPasswordLength(5); // password strength
      reg.setNorthAmerican(true);
      reg.setRegistrationLog("log.nsf");
      reg.setUpdateAddressBook(true);
      reg.setStoreIDInAddressBook(true);

      if (reg.registerNewUser(alUserEntry.get(1), // last name
    		  alProfile.get(2)+"\\"+strId+".ID", // file to be created
    		  alProfile.get(5), // mail server
    		  alUserEntry.get(0), // first name
    		  "", // middle initial
        alProfile.get(3), // certifier password
        "", // location field
        "", // comment field
        "mail\\"+strId+".nsf", // mail file
        "", // forwarding domain
        alUserEntry.get(8))) //user password
        { 
    	  System.out.println("Registration succeeded"); 
    	  bRegister = true;
        }else { 
        	System.out.println("Registration failed"); 
        }
  	} catch (NotesException e) {
		e.printStackTrace();
	}

		return bRegister;
	}
	
	/**
	 * check existance of Name Shortname IDName ... here
	 * @param FirstName
	 * @param LastName
	 * @return
	 */
	public static String createID(String FirstName, String LastName){
		String strId;
		int iLast = LastName.length();
		System.out.println("Registartor.createID "+FirstName+" "+LastName+" LastName "+LastName.length());
		int iAdd = 0;
		if(iLast<=5){
			iAdd = 6-iLast;
			System.out.println("iAdd "+iAdd);
			for(int i= 0;i <=iAdd;i++){
				LastName = LastName+"0";
			}
		}
		System.out.println("LastName "+LastName);
		strId = FirstName.substring(0, 1).toLowerCase()+LastName.substring(0, 5).toLowerCase();
		
		return strId;
	}
}
