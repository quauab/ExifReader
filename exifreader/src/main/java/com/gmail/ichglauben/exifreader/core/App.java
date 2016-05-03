package com.gmail.ichglauben.exifreader.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.gmail.ichglauben.exifreader.core.concretes.ExifReader;
import com.gmail.ichglauben.exifreader.core.utils.abstracts.CustomClass;
import com.gmail.ichglauben.fileextensionextractor.core.concretes.ExtensionExtractor;

public class App extends CustomClass {
	public static void main(String[] args) {
		Path img1 = Paths.get("K:\\media\\graphics\\jpegs\\cats\\misc\\ocelot.jpg"),
				img2 = Paths.get("K:\\media\\graphics\\jpegs\\cats\\misc\\bobcat.jpg");

		if (null != img2) {
			if (img2.toFile().exists()) {
				testReaderPrintResults(img1);
				String extension = null;
				if (null != (extension = ExtensionExtractor.extract(img2.toAbsolutePath().toString(), true))) {
					print("Image Type:  " + extension);
					print("Image Name:  " + img2.getFileName());
					testReaderGetTagsList(img2.toAbsolutePath().toString());
				}
			}
		}
	}

	private static void testReaderPrintResults(Path jpeg) {
		ExifReader reader = new ExifReader();
		reader.read(jpeg);
		reader.printResults();
	}

	private static void testReaderGetTagsList(String jpeg) {
		ExifReader reader = new ExifReader();
		List<String> tags = null;
		if (null != (tags = reader.getList(jpeg))) {
			for (String tag : tags) {
				String[] split = tag.trim().split("\\|");
				if (split.length == 2) {
					print(new String("       Tag:  ").toUpperCase() + split[0] + new String("\tValue:  ").toUpperCase()
							+ split[1]);
				}
			}
			print("\t---------------------------------------------------\n");
		}
	}

}
