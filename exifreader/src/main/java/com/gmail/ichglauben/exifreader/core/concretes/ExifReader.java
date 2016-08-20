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
	private Map<String, String> jims = new HashMap<String, String>();
	private JpegImageMetadata jpegMetadata;
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
				getExchangeData();
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
				getExchangeData();
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
				getExchangeData();
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(Paths.get(f).toAbsolutePath().toString()));
			}
		}
	}
	
	private void clearJim() {
		if (null != jims)
			jims.clear();
	}

	/**
	 * Setters */
	private void setPath(String f) {
		path = Paths.get(f);
	}

	/**
	 * Getters */
	private void getExchangeData() {
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
	
	private void getJimTags(JpegImageMetadata jim) {
		for (TagInfo ti : tags) {
			TiffField field = jim.findEXIFValue(ti);
			if (null != field) {
				jims.put(field.getTagName(), field.getValueDescription().toString());
			}
		}
	}

	protected List<String> getTagsList() {
		List<String> list = makeList(jims);
		if (null != list)
			return list;
		return null;
	}
	
	public Map<String,String> getTagsMap() {
		return jims;
	}
	
	public String toString() { return "Abstract Exif Reader"; }
	
}
