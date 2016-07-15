function getProfileData() {
	
	var profile = {};
	// variant = notesRegistration.RegisterNewUser( lastname$, idfile$, 
	// mailserver$ [, firstname$ [, middle$ [, certpw$ [, location$ [, comment$ [, maildbpath$ [, fwddomain$ [,userpw$ [, usertype% [, altname [, altnamelang ]]]]]]]]]]] ) 
	try {
		var profileView:NotesView = database.getView("ProfileView");
		var profileDoc:NotesDocument = profileView.getFirstDocument();
		if ( profileDoc != null ) {
			
			
			profile.SERVER = profileDoc.getItemValueString("SERVER");
			profile.FILEPATH = profileDoc.getItemValueString("FILEPATH");
			
			
		}
		
	} catch(e) {
		print( e );
	}
	
	return profile;
}