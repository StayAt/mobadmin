package org.stayat;

import java.util.ArrayList;
import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.Session;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;

/**
 * 
 * @author Marcel Bussien
 *
 */
public class NotesAdmin {
	static String SERVER;
	static String FILEPATH;
	static String CERTIFIERIDFILE;
	static String CERTPW;
	
	static String REGISTRATIONSERVER;
	static String MAILSERVER;
	static String ACTION;
	
	
	static ArrayList<String> alProfile = new ArrayList<String>();
	static ArrayList<String> alUser = new ArrayList<String>();
	static Document DOC;
	
	/**
	 * 
	 * @param Server
	 * @param FilePath
	 * @param action
	 */
	public NotesAdmin(String Server, String FilePath, String action) {
		SERVER = Server;
		FILEPATH = FilePath;
		ACTION = action;
		System.out.println("NotesAdmin constructor SERVER "+SERVER+" FILEPATH "+FILEPATH+" ACTION "+ACTION);
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 * @throws NotesException
	 */
	public static String registerAll(Session session) throws NotesException {		// ***** registerAll
		boolean bRegistered = false;
	
		try { // ***** accessNSFInventar
			Database db = session.getDatabase(SERVER, FILEPATH);
			if (!db.isOpen()) {
				System.out.println("registerAll DOES NOT EXIST on "+SERVER);
			} else {
				View viewProfile = db.getView("ProfileView");
				ViewEntryCollection vecProfile = viewProfile.getAllEntries();
				ViewEntry entryProfile = vecProfile.getFirstEntry();
				
				DOC = entryProfile.getDocument();
				alProfile.add(DOC.getItemValueString("SERVER"));
				alProfile.add(DOC.getItemValueString("FILEPATH"));
				alProfile.add(DOC.getItemValueString("DATAPATH"));
				alProfile.add(DOC.getItemValueString("CERTPW"));
				alProfile.add(DOC.getItemValueString("REGISTRATIONSERVER"));
				alProfile.add(DOC.getItemValueString("MAILSERVER"));
				alProfile.add(DOC.getItemValueString("CERTIDPATH"));
				alProfile.add(DOC.getItemValueString("DOMAIN"));
				
				View viewUser = db.getView("register");
				System.out.println("NotesAdmin.registerAll SERVER " +SERVER+" 1 " + FILEPATH+" ACTION "+ACTION+" db "+db.getTitle()+" viewUser "+viewUser.getName());
				
				ViewEntryCollection vec = viewUser.getAllEntries();
			    ViewEntry tmpentry;
			    ViewEntry entry = vec.getFirstEntry();
			    alUser.clear();
			    int count = 0;
			    int countAll = 0;
			      while (entry != null) {
			    	  DOC = entry.getDocument();
			    	  countAll++;
			    	  if(!DOC.getItemValueString("fld09").equals("registered")){
			    		  count++;
			    		  alUser.add(DOC.getItemValueString("fld01")+"¬"+DOC.getItemValueString("fld02")
			        		  +"¬"+DOC.getItemValueString("fld03")+"¬"+DOC.getItemValueString("fld04")
			        		  +"¬"+DOC.getItemValueString("fld05")+"¬"+DOC.getItemValueString("fld06")
			        		  +"¬"+DOC.getItemValueString("fld07")+"¬"+DOC.getItemValueString("fld08")
			        		  +"¬"+DOC.getItemValueString("fld9")+"¬"+DOC.getItemValueString("fld10"));
			      }
			        tmpentry = vec.getNextEntry();
			        entry.recycle();
			        entry = tmpentry;
			      }
			      System.out.println(" count "+count+" countAll "+countAll);
			bRegistered = Registrator.registerALLUser(session, alProfile, alUser);
		      ViewEntry entryStatus = vec.getFirstEntry();
		      if(bRegistered){
		    	  while (entryStatus != null) {
			    	  DOC = entryStatus.getDocument();
			    	  DOC.replaceItemValue("fld09", "registered");
			    	  if (DOC.save()) {
						} else {
							System.out.println("Something went wrong");
						}

			        tmpentry = vec.getNextEntry();
			        entryStatus.recycle();
			        entryStatus = tmpentry;
			      }  
		      }    
			}
		} catch (NotesException e) {
			e.printStackTrace();
		}
		return "strReturn bRegistered "+bRegistered;
	}		// ***** END registerAll

	/**
	 * 
	 * @param session
	 * @return
	 * @throws NotesException
	 */
	public static String renameAll(Session session) throws NotesException {
		boolean bRenamed = false;
	
		try { // ***** accessNSFInventar
			Database db = session.getDatabase(SERVER, FILEPATH);
			if (!db.isOpen()) {
				System.out.println("renameAll DOES NOT EXIST on "+SERVER);
			} else {
				View viewProfile = db.getView("ProfileView");
				ViewEntryCollection vecProfile = viewProfile.getAllEntries();
				ViewEntry entryProfile = vecProfile.getFirstEntry();
				
				DOC = entryProfile.getDocument();
				alProfile.add(DOC.getItemValueString("SERVER"));
				alProfile.add(DOC.getItemValueString("FILEPATH"));
				alProfile.add(DOC.getItemValueString("DATAPATH"));
				alProfile.add(DOC.getItemValueString("CERTPW"));
				alProfile.add(DOC.getItemValueString("REGISTRATIONSERVER"));
				alProfile.add(DOC.getItemValueString("MAILSERVER"));
				alProfile.add(DOC.getItemValueString("CERTIDPATH"));
				alProfile.add(DOC.getItemValueString("DOMAIN"));
				
				View viewUser = db.getView("rename");
				System.out.println("NotesAdmin.renameAll SERVER " +SERVER+" FILEPATH " +FILEPATH+" db "+db.getTitle()+" viewUser "+viewUser.getName());
				
				ViewEntryCollection vec = viewUser.getAllEntries();
			    ViewEntry tmpentry;
			    ViewEntry entry = vec.getFirstEntry();
			    alUser.clear();
			      while (entry != null) {
			    	  DOC = entry.getDocument();
			    	  if(DOC.getItemValueString("fld04").length()+DOC.getItemValueString("fld04").length()>=2) {
			    		  System.out.println("fld04+05 = "+DOC.getItemValueString("fld04").length()+DOC.getItemValueString("fld04").length());
			    		  	alUser.add(DOC.getItemValueString("fld01")+"¬"+DOC.getItemValueString("fld02")
			        		  +"¬"+DOC.getItemValueString("fld04")+"¬"+DOC.getItemValueString("fld05")+"¬NOTHING");
			    	  }else{
			    		  System.out.println("NOTHING TO RENAME");
			    	  }
			        tmpentry = vec.getNextEntry();
			        entry.recycle();
			        entry = tmpentry;
			      }
			      bRenamed = org.stayat.Renamer.renameALLUser(session, alProfile, alUser);
			      if(bRenamed){
			    	  System.out.println("bRenamed "+bRenamed);
			    	  ViewEntry entryStatus = vec.getFirstEntry();
			    	  while (entryStatus != null) {
			    		  System.out.println("bRenamed "+bRenamed);
				    	  DOC = entryStatus.getDocument();
				    	  System.out.println("DOC "+DOC.getItemValueString("FORM"));
				    	  DOC.replaceItemValue("fld09", "renamed");
				    	  if (DOC.save()) {
							} else {
								System.out.println("Something went wrong");
							}
	
				        tmpentry = vec.getNextEntry();
				        entryStatus.recycle();
				        entryStatus = tmpentry;
				      }  
			      }
			}
		} catch (NotesException e) {
			e.printStackTrace();
		}
		System.out.println("alUser "+alUser+" bRenamed "+bRenamed);
		
		return "strReturn bRenamed "+bRenamed;
	}  // ***** END renameAll

	/**
	 * 
	 * @param session
	 * @return
	 * @throws NotesException
	 */
	public static String deleteALL(Session session) throws NotesException {		// ***** deleteALL
		System.out.println("NotesAdmin.deleteALL ACTION "+ACTION);
		boolean bDeleted = false;
	
		try { // ***** accessNSFInventar
			Database db = session.getDatabase(SERVER, FILEPATH);
			if (!db.isOpen()) {
				System.out.println("deleteALL DOES NOT EXIST on "+SERVER);
			} else {
				
				View viewProfile = db.getView("ProfileView");
				
				ViewEntryCollection vecProfile = viewProfile.getAllEntries();
				ViewEntry entryProfile = vecProfile.getFirstEntry();
				
				alProfile.clear();
				DOC = entryProfile.getDocument();
				alProfile.add(DOC.getItemValueString("SERVER"));
				alProfile.add(DOC.getItemValueString("FILEPATH"));
				alProfile.add(DOC.getItemValueString("DATAPATH"));
				alProfile.add(DOC.getItemValueString("CERTPW"));
				alProfile.add(DOC.getItemValueString("REGISTRATIONSERVER"));
				alProfile.add(DOC.getItemValueString("MAILSERVER"));
				alProfile.add(DOC.getItemValueString("CERTIDPATH"));
				alProfile.add(DOC.getItemValueString("DOMAIN"));
				alProfile.add(DOC.getItemValueString("DenyAccess"));
				
				System.out.println("NotesAdmin.deleteALL SERVER " +SERVER+" 1 " +
						FILEPATH);
				
				View viewUser = null;
				if(ACTION.equals("register")){
					viewUser = db.getView("register");

					ViewEntryCollection vec = viewUser.getAllEntries();
				    ViewEntry tmpentry;
				    ViewEntry entry = vec.getFirstEntry();
				    alUser.clear();
				      while (entry != null) {
				    	  DOC = entry.getDocument();
				          alUser.add(DOC.getItemValueString("fld01")+"¬"+DOC.getItemValueString("fld02")
				        		  +"¬"+DOC.getItemValueString("fld9"));
				        tmpentry = vec.getNextEntry();
				        entry.recycle();
				        entry = tmpentry;
				      }
				      System.out.println("NotesAdmin.deleteALL db "+db.getTitle()+" viewUser "+viewUser.getName());
				      bDeleted = Terminator.terminateUser(session, alProfile, alUser);
				}
				if(ACTION.equals("rename")){
					viewUser = db.getView("rename");
				}
				if(ACTION.equals("delete")){
					viewUser = db.getView("delete");
					ViewEntryCollection vec = viewUser.getAllEntries();
				    ViewEntry tmpentry;
				    ViewEntry entry = vec.getFirstEntry();
				    alUser.clear();
				      while (entry != null) {
				    	  DOC = entry.getDocument();
				          alUser.add(DOC.getItemValueString("fld01")+"¬"+DOC.getItemValueString("fld02")
				        		  +"¬"+DOC.getItemValueString("fld9"));
				        tmpentry = vec.getNextEntry();
				        entry.recycle();
				        entry = tmpentry;
				      }
				      System.out.println("NotesAdmin.deleteALL db "+db.getTitle()+" viewUser "+viewUser.getName());
				      bDeleted = Terminator.terminateUser(session, alProfile, alUser);

				      ViewEntry entryStatus = vec.getFirstEntry();
				      if(bDeleted){
				    	  while (entryStatus != null) {
					    	  DOC = entryStatus.getDocument();
					    	  System.out.println("DOC "+DOC.getItemValueString("FORM"));
					    	  DOC.replaceItemValue("fld09", "deleted");
					    	  if (DOC.save()) {
								} else {
									System.out.println("Something went wrong");
								}

					        tmpentry = vec.getNextEntry();
					        entryStatus.recycle();
					        entryStatus = tmpentry;
					      }  
				      } 
				}   
			}
		} catch (NotesException e) {
			e.printStackTrace();
		}
		return "strReturn bDeleted "+bDeleted;
	}		// ***** END deleteAll	
}