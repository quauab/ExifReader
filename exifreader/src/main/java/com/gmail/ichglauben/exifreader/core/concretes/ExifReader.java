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
	
	public void search(Path f, TagInfo[] tags) {
		if (null != f) {
			if (JpegValidator.validFile(f)) {
				clearJim();
				setPath(f.toAbsolutePath().toString());
				getEd(tags);
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(f.toAbsolutePath().toString()));
			}
		}
	}
	
	public void search(File f, TagInfo[] tags) {
		if (null != f) {
			if (JpegValidator.validFile(f)) {
				clearJim();
				setPath(f.getAbsolutePath());
				getEd(tags);
			} else {
				throw new IllegalArgumentException("Unsupported file type: "
						+ ExtensionExtractor.extract(f.toPath().toAbsolutePath().toString()));
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
		for (TagInfo ti : TiffConstants.ALL_TAGS) {
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
