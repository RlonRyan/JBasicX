/*
 * JBasicX
 * By RlonRyan
 * 
 * All rights reserved. No warrenty on use. User accepts all risks and damages.
 * Class: JModPackX
 * Description: JModPackX
 */

package JModX;

import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class JModPackX {
    
    public String name;
    public String author;
    public int[] version;
    public String description;
    
    public HashMap<String, String> files;
    
    /*public JModPackX() {
	this.name = "JModX";
	this.author = "RlonRyan";
	this.version = new int[]{0,0,0};
	this.description = "An incomplete mod.";
	this.files = new HashMap<>();
    }*/

    public JModPackX(String name, String author, int[] version, String description, HashMap<String, String> files) {
	this.name = name == null ? "JModX" : name;
	this.author = author == null ? "RlonRyan" : author;
	this.version = version == null ? new int[]{0,0,0} : version;
	this.description = description == null ? "An incomplete mod." : description;
	this.files = files == null ?  new HashMap<>() : files;
    }
    
}
