package org.stayat;

import java.util.ArrayList;

import lotus.domino.*;
import lotus.domino.NotesException;
import lotus.domino.Session;

/**
 * 
 * @author Marcel Bussien
 *
 */
public class Terminator {
	
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
	public static boolean terminateUser(Session session, ArrayList<String> alProfile,
			ArrayList<String> alUser) throws NotesException{
		
		System.out.println("Terminator.terminate alProfile 4 "+alProfile.get(4)+" 5 "+alProfile.get(5));
		
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
			bReturn = deleteNotesUser(session,alProfile, al );
		}
		return bReturn;
	}
	
	/**
	 * 
	 * @param session
	 * @param alProfile
	 * @param alParameter
	 * @return
	 * @throws NotesException
	 */
	public static boolean deleteNotesUser(Session session, ArrayList<String> alProfile, ArrayList<String> alParameter) throws NotesException{
		boolean bDelete = false;
		for(int i=0;i<=alProfile.size()-1;i++){
			System.out.println(i+". PPP Prof "+alProfile.get(i));
		}
		for(int k=0;k<=alParameter.size()-1;k++){
			System.out.println(k+". AAA Param "+alParameter.get(k));
		}

		System.out.println("deleteNotesUser MAILSERVER "+alProfile.get(5));
		System.out.println("deleteNotesUser CERTIDPATH "+alProfile.get(6));
		
		AdministrationProcess adminp = session.createAdministrationProcess(alProfile.get(4));
		adminp.setCertifierFile(alProfile.get(6));
		
		System.out.println("alProfile.get(4) "+alProfile.get(4)+" alProfile.get(6) "+alProfile.get(6));
		String strNotesUser = "CN="+alParameter.get(0)+" "+alParameter.get(1)+"/O="+alProfile.get(7);
		System.out.println("strNotesUser "+strNotesUser+" denygroup "+alProfile.get(8));

		adminp.setCertifierPassword(alProfile.get(3));
		
		adminp.deleteUser(strNotesUser, true, 1, alProfile.get(8));
		
		bDelete = true;
		
		return bDelete;
		
	}
	
}
