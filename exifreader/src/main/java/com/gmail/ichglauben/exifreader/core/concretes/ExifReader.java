package com.gmail.ichglauben.exifreader.core.concretes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;

import com.gmail.ichglauben.exifreader.core.utils.abstracts.CustomClass;
import com.gmail.ichglauben.exifreader.core.utils.concrete.JpegValidator;
import com.gmail.ichglauben.fileextensionextractor.core.concretes.ExtensionExtractor;

public class ExifReader extends CustomClass {		
	private Map<String, String> mapJpegImageMetadata = new HashMap<String, String>();
	private JpegImageMetadata jpegImageMetadata;
	private Path path;
	private final TagInfo[] tags = new TagInfo[] { TiffConstants.EXIF_TAG_DATE_TIME_ORIGINAL,
			TiffConstants.EXIF_TAG_XRESOLUTION, TiffConstants.EXIF_TAG_YRESOLUTION, TiffConstants.EXIF_TAG_ORIENTATION,
			TiffConstants.EXIF_TAG_RESOLUTION_UNIT, TiffConstants.EXIF_TAG_GPSINFO, TiffConstants.EXIF_TAG_ANNOTATIONS,
			TiffConstants.EXIF_TAG_APERTURE_VALUE, TiffConstants.EXIF_TAG_MAKE, TiffConstants.EXIF_TAG_MODEL,
			TiffConstants.EXIF_TAG_CAMERA_SERIAL_NUMBER, TiffConstants.GPS_TAG_GPS_LATITUDE_REF,
			TiffConstants.GPS_TAG_GPS_LATITUDE, TiffConstants.GPS_TAG_GPS_DEST_LATITUDE,
			TiffConstants.GPS_TAG_GPS_ALTITUDE_REF, TiffConstants.GPS_TAG_GPS_ALTITUDE,
			TiffConstants.GPS_TAG_GPS_LONGITUDE_REF, TiffConstants.GPS_TAG_GPS_LONGITUDE,
			TiffConstants.GPS_TAG_GPS_DEST_LONGITUDE, TiffConstants.EXIF_TAG_ARTIST };

	public ExifReader() {}

	/**
	 * Primary Actions
	 */
	public void search(Path f) {
		if (null != f) {
			if (JpegValidator.validFile(f)) {
				clearJim();
				setPath(f.toAbsolutePath().toString());
				getEd();
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(f.toAbsolutePath().toString()));
			}
		}
	}

	public void search(File f) {
		clearJim();
		if (null != f) {
			if (JpegValidator.validFile(f)) {
				setPath(f.toPath().toAbsolutePath().toString());
				getEd();
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(f.toPath().toAbsolutePath().toString()));
			}
		}
	}

	public void search(String f) {
		if (null != f) {
			if (JpegValidator.validFile(f)) {
				clearJim();
				setPath(f);
				getEd();
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(Paths.get(f).toAbsolutePath().toString()));
			}
		}
	}
	
	public void search(String f, TagInfo[] tags) {
		if (null != f) {
			if (JpegValidator.validFile(f)) {
				clearJim();
				setPath(f);
				getEd(tags);
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(Paths.get(f).toAbsolutePath().toString()));
			}
		}
	}
	
	private void clearJim() {
		if (null != mapJpegImageMetadata)
			mapJpegImageMetadata.clear();
	}

	/** Setters */
	private void setPath(String f) {
		path = Paths.get(f);
	}

	/** Getters */
	private void getEd() {
		// get the jpeg's exchange data
		IImageMetadata metadata = null;
		try {
			metadata = Sanselan.getMetadata(path.toFile());
			if (metadata instanceof JpegImageMetadata) {
				jpegImageMetadata = (JpegImageMetadata) metadata;
				getJim(jpegImageMetadata);
			}
		} catch (ImageReadException ire) {
			return;
		} catch (IOException ioe) {
			return;
		}
	}
	
	private void getEd(TagInfo[] tags) {
		// get the jpeg's exchange data
		IImageMetadata metadata = null;
		try {
			metadata = Sanselan.getMetadata(path.toFile());
			if (metadata instanceof JpegImageMetadata) {
				jpegImageMetadata = (JpegImageMetadata) metadata;
				getJim(jpegImageMetadata,tags);
			}
		} catch (ImageReadException ire) {
			return;
		} catch (IOException ioe) {
			return;
		}
	}
	
	private void getJim(JpegImageMetadata jim) {
		// get the jpeg's image metadata
		for (TagInfo ti : tags) {
			TiffField field = jim.findEXIFValue(ti);
			if (null != field) {
				mapJpegImageMetadata.put(field.getTagName(), field.getValueDescription().toString());
			}
		}
	}
	
	private void getJim(JpegImageMetadata jim, TagInfo[] tags) {
		// get the jpeg's image metadata
		for (TagInfo ti : tags) {
			TiffField field = jim.findEXIFValue(ti);
			if (null != field) {
				mapJpegImageMetadata.put(field.getTagName(), field.getValueDescription().toString());
			}
		}
	}

	public List<String> getTagsList() {
		List<String> list = makeList(mapJpegImageMetadata);
		if (null != list)
			return list;
		return null;
	}
	
	public Map<String,String> getTagsMap() {
		return mapJpegImageMetadata;
	}
	
	public String toString() { return "Exif Reader"; }
	
}
