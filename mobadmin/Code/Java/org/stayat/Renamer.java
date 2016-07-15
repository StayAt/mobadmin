package org.stayat;

import java.util.ArrayList;
import lotus.domino.*;
import lotus.domino.NotesException;
import lotus.domino.Session;

public class Renamer {
	
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
	public static boolean renameALLUser(Session session, ArrayList<String> alProfile,
			ArrayList<String> alUser) throws NotesException{
		
		System.out.println("Renamer.renameALLUser alProfile 4 "+alProfile.get(4)+" 5 "+alProfile.get(5));
		
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
			ArrayList<String> alLine = new ArrayList<String>();
			alLine.clear();
			while (sep >= 1) {
				strEntry = strLine.substring(0, sep);
				alLine.add(strEntry);
				strLine = strLine.substring(strLine.substring(0, sep).length()+1,strLine.length());
			    sep = strLine.indexOf(strSep);
			 }
			System.out.println("strLine "+strLine+" alLine "+alLine);
			bReturn = renameNotesUser(session,alProfile, alLine );
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
	public static boolean renameNotesUser(Session session, ArrayList<String> alProfile, ArrayList<String> alUserEntry) throws NotesException{
		boolean bRename = false;
		for(int i=0;i<=alProfile.size()-1;i++){
			System.out.println(i+". PPP Prof "+alProfile.get(i));
		}
		for(int k=0;k<=alUserEntry.size()-1;k++){
			System.out.println(k+". alUserEntry "+alUserEntry.get(k));
		}
		
		System.out.println("MAILSERVER "+alProfile.get(5));
		System.out.println("CERTIDPATH "+alProfile.get(6));
		
		AdministrationProcess adminp = session.createAdministrationProcess(alProfile.get(4));
		adminp.setCertifierFile(alProfile.get(6));
		
		System.out.println("alProfile.get(4) "+alProfile.get(4)+" alProfile.get(6) "+alProfile.get(6));
		System.out.println("alUserEntry.get(2) "+alUserEntry.get(2)+" alUserEntry.get(3) "+alUserEntry.get(3));
		System.out.println("alUserEntry.get(0) "+alUserEntry.get(0)+" alUserEntry.get(1) "+alUserEntry.get(1)+" alProfile.get(7) "+alProfile.get(7));
		String strNotesUser = "CN="+alUserEntry.get(0)+" "+alUserEntry.get(1)+"/O="+alProfile.get(7);
		System.out.println("strNotesUser "+strNotesUser);

		adminp.setCertifierPassword(alProfile.get(3));
		
		String noteid = adminp.renameNotesUser(strNotesUser, alUserEntry.get(3), alUserEntry.get(2),"","");

	if(noteid.length()>=1){
	bRename= true;
	}
	return bRename;
	}	

}
