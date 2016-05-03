package com.gmail.ichglauben.exifreader.core.abstracts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;

import com.gmail.ichglauben.exifreader.core.utils.abstracts.CustomClass;
import com.gmail.ichglauben.exifreader.core.utils.concrete.JpegValidator;
import com.gmail.ichglauben.fileextensionextractor.core.concretes.ExtensionExtractor;

public abstract class AbstractExifReader extends CustomClass {
		
	private static Map<String, String> jim_tags_values = new HashMap<String, String>();
	private static JpegImageMetadata jpegMetadata;
	private static Path path;
	private final static TagInfo[] tags = new TagInfo[] { TiffConstants.EXIF_TAG_DATE_TIME_ORIGINAL,
			TiffConstants.EXIF_TAG_XRESOLUTION, TiffConstants.EXIF_TAG_YRESOLUTION, TiffConstants.EXIF_TAG_ORIENTATION,
			TiffConstants.EXIF_TAG_RESOLUTION_UNIT, TiffConstants.EXIF_TAG_GPSINFO, TiffConstants.EXIF_TAG_ANNOTATIONS,
			TiffConstants.EXIF_TAG_APERTURE_VALUE, TiffConstants.EXIF_TAG_MAKE, TiffConstants.EXIF_TAG_MODEL,
			TiffConstants.EXIF_TAG_CAMERA_SERIAL_NUMBER, TiffConstants.GPS_TAG_GPS_LATITUDE_REF,
			TiffConstants.GPS_TAG_GPS_LATITUDE, TiffConstants.GPS_TAG_GPS_DEST_LATITUDE,
			TiffConstants.GPS_TAG_GPS_ALTITUDE_REF, TiffConstants.GPS_TAG_GPS_ALTITUDE,
			TiffConstants.GPS_TAG_GPS_LONGITUDE_REF, TiffConstants.GPS_TAG_GPS_LONGITUDE,
			TiffConstants.GPS_TAG_GPS_DEST_LONGITUDE, TiffConstants.EXIF_TAG_ARTIST };

	public AbstractExifReader() {}

	/**
	 * Primary Actions
	 */
	protected static void search(Path f) {
		if (null != f) {
			if (JpegValidator.validFile(f)) {
				clearJim();
				setPath(f.toAbsolutePath().toString());
				getExchangeData();
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(f.toAbsolutePath().toString()));
			}
		}
	}

	protected static void search(File f) {
		clearJim();
		if (null != f) {
			if (JpegValidator.validFile(f)) {
				setPath(f.toPath().toAbsolutePath().toString());
				getExchangeData();
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(f.toPath().toAbsolutePath().toString()));
			}
		}
	}

	protected static void search(String f) {
		if (null != f) {
			if (JpegValidator.validFile(f)) {
				clearJim();
				setPath(f);
				getExchangeData();
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(Paths.get(f).toAbsolutePath().toString()));
			}
		}
	}

	/**
	 * Actions
	 */
	public static void printResults() {
		List<String> list = null;

		if (null != (list = makeList(jim_tags_values))) {
			if (list.size() > 0) {
				print("Image:      " + getFileName(true));
				print("Fullname:   " + getFileName());
				print("Extension:  " + getFileExtension());
				print("Type:       " + getFileExtension(true));
				for (String str : list) {
					String split[] = str.split("\\|");
					if (split.length == 2) {
						print("Tag:  " + split[0] + "\tValue:  " + split[1]);
					}
				}
				print("\t---------------------------------------------------\n");
			} else {
				print(path.getFileName() + " is empty");
			}
		}
	}

	private static void getExchangeData() {
		IImageMetadata metadata = null;
		try {
			metadata = Sanselan.getMetadata(path.toFile());
			if (metadata instanceof JpegImageMetadata) {
				jpegMetadata = (JpegImageMetadata) metadata;
				getJimTags(jpegMetadata);
			}
		} catch (ImageReadException ire) {
			return;
		} catch (IOException ioe) {
			return;
		}
	}

	private static void clearJim() {
		if (null != jim_tags_values)
			jim_tags_values.clear();
	}

	/**
	 * Setters
	 */
	private static void setPath(String f) {
		path = Paths.get(f);
	}

	/**
	 * Getters
	 */
	private static void getJimTags(JpegImageMetadata jim) {
		clearJim();
		for (TagInfo ti : tags) {
			TiffField field = jim.findEXIFValue(ti);
			if (null != field) {
				jim_tags_values.put(field.getTagName(), field.getValueDescription().toString());
			}
		}
	}

	protected static List<String> getTagsList() {
		List<String> list = makeList(jim_tags_values);
		if (null != list)
			return list;
		return null;
	}
	
	protected static Map<String,String> getTagsMap() {
		return jim_tags_values;
	}
	
	public static List getExifDirectories() {
		List list = null;

		try {
			TiffOutputSet outputSet = null;
			IImageMetadata metadata = Sanselan.getMetadata(path.toFile());
			JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

			if (null != jpegMetadata) {
				TiffImageMetadata exif = jpegMetadata.getExif();

				if (null != exif) {
					outputSet = exif.getOutputSet();
				}
			}

			if (null == outputSet)
				outputSet = new TiffOutputSet();

			{
				list = outputSet.getDirectories();
			}
		} catch (IOException ioe) {
			return null;
		} catch (ImageReadException ire) {
			return null;
		} catch (ImageWriteException iwe) {
			return null;
		} catch (Exception e) {
			return null;
		}

		return list;
	}
		
	public static String getFileName() {
		return path.getFileName().toString();
	}
	
	public static String getFileName(boolean withoutExtension) {
		if (withoutExtension) {
			int dot = path.getFileName().toString().lastIndexOf(".");
			if (dot != -1) {
				return path.getFileName().toString().substring(0, dot);
			}
		} else {
			return path.getFileName().toString();
		}
		return null;
	}
	
	public static String getFileExtension() {
		return ExtensionExtractor.extract(path);
	}
	
	public static String getFileExtension(boolean withoutDot) {
		if (withoutDot) {
			int dot = path.getFileName().toString().lastIndexOf(".");
			if (dot != -1) {
				return path.getFileName().toString().substring((dot + 1));
			}
		} else {
			return ExtensionExtractor.extract(path);
		}
		return null;
	}
	
	public static String getFilePath() {
		return path.toAbsolutePath().toString();
	}
	
	public String toString() { return "Abstract Exif Reader"; }
	
}
