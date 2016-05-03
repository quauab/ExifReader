package com.gmail.ichglauben.exifreader.core.utils.concrete;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JpegValidator {
	private JpegValidator() {
		super();
	}

	public static boolean validFile(String file) {
		if (null != file) {
			try {
				Path path = Paths.get(file);
				if (null != path) {
					String type = Files.probeContentType(path);
					if (null != type) {
						return type.equals("image/jpeg");
					}
				}
			} catch (IOException x) {
				return false;
			}
		}
		return false;
	}

	public static boolean validFile(File file) {
		if (null != file) {
			try {
				Path path = file.toPath();
				if (null != path) {
					String type = Files.probeContentType(path);
					if (null != type) {
						return type.equals("image/jpeg");
					}
				}
			} catch (IOException x) {
				return false;
			}
		}
		return false;
	}

	public static boolean validFile(Path file) {
		if (null != file) {
			try {
				String type = Files.probeContentType(file);
				if (null != type) {
					return type.equals("image/jpeg");
				}
			} catch (IOException x) {
				return false;
			}
		}
		return false;
	}
}
