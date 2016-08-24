package com.gmail.ichglauben.exifreader.concretes;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.junit.Test;

import com.gmail.ichglauben.exifreader.core.concretes.ExifReader;
import com.gmail.ichglauben.exifreader.core.utils.abstracts.CustomClass;

public class ExifReaderTests extends CustomClass {
	ClassLoader loader = getClass().getClassLoader();
	ExifReader er;
	
	@Test
	public void testExifReaderGetTagsMapGetTagsListMethods() {
		Path pathLynx = new File(loader.getResource("lynx.jpg").getFile()).toPath();
		er = new ExifReader();
		
		er.search(pathLynx);
		Map<String,String> tagsMap = null;
		
		tagsMap = er.getTagsMap();
		assertTrue("Map is null", null != tagsMap);
		
		List<String> tagsList = null;
		
		tagsList = er.getTagsList();
		assertTrue("List is null", null != tagsList);
		assertTrue("Sizes not equal", tagsMap.size() == tagsList.size());
		
		println("\t\tTag List");
		for (String l:tagsList) {
			println(l);
		}
		println("\n");
		
		println("\t\tTag Map");
		for (Map.Entry<String,String> me:tagsMap.entrySet()) {
			println(me.getKey() + ": " + me.getValue());
		}
	}
	
	@Test
	public void testExifReaderGetSpecifiedTags() {
		Path pathWildcat = new File(loader.getResource("wildcat.jpg").getFile()).toPath();
		er = new ExifReader();
		
		er.search(pathWildcat.toAbsolutePath().toString(),new TagInfo[] {TiffConstants.EXIF_TAG_GPSINFO});
		
		List<String> tags = er.getTagsList();
		
		for(String tag:tags) {
			println(tag);
		}
	}
	
	@Test
	public void testExifReaderInstantiated() {
		assertTrue("ExifReader not null", null == er);		

		er = new ExifReader();
		assertTrue("ExifReader null", null != er);
		
		er = null;
		assertTrue("ExifReader not null", null == er);
	}
	
	@Test
	public void testExifReaderSearchFilePathStringMethods() throws URISyntaxException {
		er = new ExifReader();
		
		File fileBobcat = new File(loader.getResource("bobcat.jpg").getFile());
		File fileFishingcat = new File(loader.getResource("fishingcat.jpg").getFile());
		File fileOcelot = new File(loader.getResource("ocelot.jpg").getFile());
		
		Path pathBobcat = fileBobcat.toPath();
		Path pathFishingcat = fileFishingcat.toPath();
		Path pathOcelot = fileOcelot.toPath();
		
		String stringBobcat = pathBobcat.toAbsolutePath().toString();
		String stringFishingcat = pathFishingcat.toAbsolutePath().toString();
		String stringOcelot = pathOcelot.toAbsolutePath().toString();
		
		er.search(pathBobcat);	
		print(pathBobcat.getFileName() + " File:\t");
		Map<String,String> bobcatTags = er.getTagsMap();
		assertTrue("Map is null",null != bobcatTags);
		
		for (Map.Entry<String,String> me:bobcatTags.entrySet()) {
			println(me.getKey() + "\t" + me.getValue());
		}
		
		er.search(fileBobcat);	
		print(pathBobcat.getFileName() + " Path:\t");
		bobcatTags = er.getTagsMap();
		assertTrue("Map is null",null != bobcatTags);
		
		for (Map.Entry<String,String> me:bobcatTags.entrySet()) {
			println(me.getKey() + "\t" + me.getValue());
		}
		
		er.search(stringBobcat);	
		print(pathBobcat.getFileName() + " String:\t");
		bobcatTags = er.getTagsMap();
		assertTrue("Map is null",null != bobcatTags);
		
		for (Map.Entry<String,String> me:bobcatTags.entrySet()) {
			println(me.getKey() + "\t" + me.getValue());
		}
		
		println("---------------------------------------------------------------------\n");
		
		er.search(pathFishingcat);	
		print(pathFishingcat.getFileName() + " File:\t");
		Map<String,String> fishingcatTags = er.getTagsMap();
		assertTrue("Map is null", null != fishingcatTags);
		
		for (Map.Entry<String,String> me:fishingcatTags.entrySet()) {
			println(me.getKey() + "\t" + me.getValue());
		}
		
		er.search(fileFishingcat);	
		print(pathFishingcat.getFileName() + " Path:\t");
		fishingcatTags = er.getTagsMap();
		assertTrue("Map is null", null != fishingcatTags);		
		
		for (Map.Entry<String,String> me:fishingcatTags.entrySet()) {
			println(me.getKey() + "\t" + me.getValue());
		}
		
		er.search(stringFishingcat);	
		print(pathFishingcat.getFileName() + " String:\t");
		fishingcatTags = er.getTagsMap();
		assertTrue("Map is null", null != fishingcatTags);		
		
		for (Map.Entry<String,String> me:fishingcatTags.entrySet()) {
			println(me.getKey() + "\t" + me.getValue());
		}
		
		println("---------------------------------------------------------------------\n");
		
		er.search(pathOcelot);	
		print(pathOcelot.getFileName() + " File:\t");
		Map<String,String> ocelotTags = er.getTagsMap();
		assertTrue("Map is null",null != ocelotTags);
		
		for (Map.Entry<String,String> me:ocelotTags.entrySet()) {
			println(me.getKey() + "\t" + me.getValue());
		}
		
		er.search(fileOcelot);	
		print(pathOcelot.getFileName() + " Path:\t");
		ocelotTags = er.getTagsMap();
		assertTrue("Map is null",null != ocelotTags);
		
		for (Map.Entry<String,String> me:er.getTagsMap().entrySet()) {
			println(me.getKey() + "\t" + me.getValue());
		}
		
		er.search(stringOcelot);	
		print(pathOcelot.getFileName() + " String:\t");
		ocelotTags = er.getTagsMap();
		assertTrue("Map is null",null != ocelotTags);
		
		for (Map.Entry<String,String> me:er.getTagsMap().entrySet()) {
			println(me.getKey() + "\t" + me.getValue());
		}
		
	}
}

