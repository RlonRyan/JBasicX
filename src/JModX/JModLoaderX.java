/*
 * JBasicX
 * By RlonRyan
 * 
 * All rights reserved. No warrenty on use. User accepts all risks and damages.
 * Class: JTemplateX
 * Description: JTemplateX
 */
package JModX;

import JSpriteX.JVectorSpriteX;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ryan
 */
public class JModLoaderX {
    
    final File folder;
    final Gson gson;
    
    public final List<JModPackX> mods;
    public final List<Object> objects;
    
    public JModLoaderX(File dir) {
	this.folder = dir;
	this.gson = new GsonBuilder().setPrettyPrinting().create();
	
	this.mods = new ArrayList<>();
	this.objects = new ArrayList<>();
    }
    
    public void loadMods() {
	File[] packs = this.folder.listFiles();
	
	for (File pack : packs) {
	    File manifest = new File(pack, "JModPackX.jmx");
	    if (manifest.isFile()) {
		try {
		    FileReader reader = new FileReader(manifest);
		    JModPackX mod = this.gson.fromJson(reader, JModPackX.class);
		    reader.close();
		    List modobjects = new ArrayList();
		    
		    for (String filepath : mod.files.keySet()) {
			File file = new File(pack, filepath);
			reader = new FileReader(file);
			modobjects.add(this.gson.fromJson(reader, Class.forName(mod.files.get(filepath))));
			reader.close();
		    }
		    
		    this.mods.add(mod);
		    this.objects.add(modobjects);
		} catch (FileNotFoundException e) {
		    Logger.getLogger("JModX").log(Level.WARNING, "Failed to load mod: {0}", pack.getName());
		} catch (ClassNotFoundException e) {
		    Logger.getLogger("JModX").log(Level.WARNING, "Failed to load mod: {0}\n\tClass {1} not found.", new String[] {pack.getName(), e.getLocalizedMessage()});
		} catch (IOException e) {
		    Logger.getLogger("JModX").log(Level.WARNING, "Failed to load mod: {0}\n\tReason: {1}.", new String[] {pack.getName(), e.getLocalizedMessage()});
		}
	    }
	}
    }
    
    public void saveExample () {
	try {
	    File savefile = new File(this.folder, "RlonMod/JModPackX.jmx");
	    savefile.getParentFile().mkdirs();
	    
	    JModPackX p = new JModPackX(null, null, null, null, null);
	    p.files.put("RlonMod/sprite.jmx", JVectorSpriteX.class.getName());
	    String temp = this.gson.toJson(p);
	    
	    try (FileWriter writer = new FileWriter(savefile)) {
		writer.write(temp);
		writer.flush();
	    }
	    
	    savefile = new File(this.folder, "RlonMod/sprite.jmx");
	    temp = this.gson.toJson(new JVectorSpriteX());
	    
	    try (FileWriter writer = new FileWriter(savefile)) {
		writer.write(temp);
		writer.flush();
	    }
	}
	catch (IOException e) {
	    Logger.getLogger("JModX").log(Level.WARNING, "Failed to write example mod.");
	}
    }
}
