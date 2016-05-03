package com.gmail.ichglauben.exifreader.core.concretes;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.gmail.ichglauben.exifreader.core.abstracts.AbstractExifReader;

public class ExifReader extends AbstractExifReader {
	public void read(Path file) {
		search(file);
	}
	
	public void read(File file) {
		search(file);
	}
	
	public void read(String file) {
		search(file);
	}
	
	public ExifReader() {
		super();
	}
	
	public List<String> getList(String file) {
		search(file);
		return getTagsList();
	}
	
	public List<String> getList(File file) {
		search(file);
		return getTagsList();
	}
	
	public List<String> getList(Path file) {
		search(file);
		return getTagsList();
	}
	
	public Map<String,String> getMap(String file) {
		search(file);
		return getTagsMap();
	}
	
	public Map<String,String> getMap(File file) {
		search(file);
		return getTagsMap();
	}
	
	public Map<String,String> getMap(Path file) {
		search(file);
		return getTagsMap();
	}
	
	public String toString() { return "Exif Reader"; }
}
