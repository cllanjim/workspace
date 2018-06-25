package org.iMage.facezine;

import org.iMage.plugins.JmjrstPlugin;

public class FacezinePlugin extends JmjrstPlugin{
	  public String[] folders = {"Bilder","Pictures","Desktop","pics"};
	
	  public String getName() {
		  return "facezine";
	  }
	  
	  public void init(org.jis.Main main) {
		  System.out.println("iMage: Sammelt Ihre Daten seit 2016! Folgende Ordner werden (meist) durchsucht: ");
		  for (int i=0; i<folders.length; i++) {
			  String url = System.getProperty("user.home");
			  url += "/" + folders[i];
			  System.out.println(url);
		  }
	  }

	  public void run() {
		  
	  }

	  public boolean isConfigurable() {
		  return true;
	  }

	  public void configure() {
		  
	  }	
}
