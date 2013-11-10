package com.github.winneonsword.MM;

import java.util.List;

import org.apache.commons.lang.WordUtils;

import com.github.winneonsword.MM.exceptions.InvalidClassException;
import com.github.winneonsword.MM.utils.UtilsMM;

public class ClassInfo {
	
	private String name;
	private String description;
	private String alpha;
	private String omega;
	
	public ClassInfo(String name) throws InvalidClassException {
		
		List<String> c = UtilsMM.getClassList();
		
		if (c.contains(name)){
			
			this.name = name;
			this.description = UtilsMM.pl.datacore.getString("Classes." + this.name + ".description");
			this.alpha = UtilsMM.pl.datacore.getString("Classes." + this.name + ".alpha");
			this.omega = UtilsMM.pl.datacore.getString("Classes." + this.name + ".omega");
			
		} else {
			
			throw new InvalidClassException(name);
			
		}
		
	}
	
	public String getClassName(){
		
		return WordUtils.capitalize(this.name);
		
	}
	
	public String getClassDescription(){
		
		return this.description;
		
	}
	
	public String getClassAlpha(){
		
		return this.alpha;
		
	}
	
	public String getClassOmega(){
		
		return this.omega;
		
	}
	
}
