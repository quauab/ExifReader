package com.gmail.ichglauben.exifreader.core.utils.abstracts;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public abstract class CustomClass {
	private final javax.swing.ImageIcon icon = createImageIcon("/large.gif"); 

	/**
	 * Default constructor
	 */
	public CustomClass() {
		super();
	}

	protected void error(Exception e) {
		if (null != e) {
			String local_message = e.getLocalizedMessage();
			String message = e.getMessage();
			String cause = null;
			if (null != local_message) {
				if (local_message.equalsIgnoreCase(message)) {
					if (null != (cause = e.getCause().toString())) {
						print("\nError: " + local_message + "\nCause: " + cause + "\n");
					} else {
						print("\nError: " + local_message + "\n");
					}
				} else {
					print(local_message);
				}
			}
		}
		return;
	}

	protected void print(Object o) { 
		if (null != o)
			System.out.print(String.valueOf(o));
	}
	
	protected void println(Object o) { 
		if (null != o)
			System.out.println(String.valueOf(o));
	}

	protected List<String> makeList(Map<String, String> hash) {
		List<String> list = null;

		if (null != hash) {
			list = new ArrayList<String>();

			for (Map.Entry<String, String> me : hash.entrySet()) {
				String key = String.valueOf(me.getKey());
				String val = String.valueOf(me.getValue());
				String item = (key + ": " + val);
				list.add(item);
			}
		}
		return list;
	}

	protected  void alert(Object msg) {
		Object[] options = { "Acknowledge" };
		int n = javax.swing.JOptionPane.showOptionDialog(null, String.valueOf(msg), "Alert",
				javax.swing.JOptionPane.OK_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);
	}

	protected  String stamp() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int mon = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DATE);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		String y = String.valueOf(year);
		String m = String.valueOf(mon);
		String d = String.valueOf(day);
		String h = String.valueOf(hour);
		String mi = String.valueOf(min);
		String s = String.valueOf(sec);
		String date = new String(y + ":" + m + ":" + d + " " + h + ":" + mi + ":" + s);
		return date;
	}

	protected  void detectPlatform() {
		final String os = FileSystemConstants.OS;
		String windows = ".*windows.*";
		String linux = ".*linux.*";
		String gtk = ".*gtk.*";

		boolean matches = false;

		if ((matches = Pattern.matches(windows, os))) {
			setWindowsLookAndFeel();
		} else if ((matches = Pattern.matches(linux, os))) {
			setLinuxLookAndFeel();
		} else if ((matches = Pattern.matches(gtk, os))) {
			setGtkLookAndFeel();
		} else {
			setDefaultLookAndFeel();
		}
	}

	private  void setDefaultLookAndFeel() {
		try {
			UIManager.setLookAndFeel(PlatformConstants.METAL);
			return;
		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (UnsupportedLookAndFeelException ulf) {
			return;
		} catch (IllegalAccessException iae) {
			return;
		}
	}

	private  void setGtkLookAndFeel() {
		try {
			UIManager.setLookAndFeel(PlatformConstants.GTK);
			return;
		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (UnsupportedLookAndFeelException ulf) {
			return;
		} catch (IllegalAccessException iae) {
			return;
		}
	}

	private  void setLinuxLookAndFeel() {
		try {
			UIManager.setLookAndFeel(PlatformConstants.LINUX);
			return;
		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (UnsupportedLookAndFeelException ulf) {
			return;
		} catch (IllegalAccessException iae) {
			return;
		}
	}

	private  void setWindowsLookAndFeel() {
		try {
			UIManager.setLookAndFeel(PlatformConstants.WIN);
			return;
		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (UnsupportedLookAndFeelException ulf) {
			try {
				UIManager.setLookAndFeel(PlatformConstants.WINCLASSIC);
				return;
			} catch (ClassNotFoundException e) {
				return;
			} catch (InstantiationException e) {
				return;
			} catch (UnsupportedLookAndFeelException ulaf) {
				return;
			} catch (IllegalAccessException iae) {
				return;
			}
		} catch (IllegalAccessException iae) {
			return;
		}
	}

	private  javax.swing.ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = CustomClass.class.getResource(path);
		if (imgURL != null) {
			return new javax.swing.ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file at: \n" + path);
			return null;
		}
	}

	public String toString() {
		return "Custom Utilities";
	}

	public final static class PlatformConstants {
		protected final static  String GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
		protected final static  String LINUX = "com.sun.java.swing.plat.linux.LinuxLookAndFeel";
		protected final static  String WIN = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		protected final static  String WINCLASSIC = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
		protected final static String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		protected final static  String METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
		protected final static String NIMBUS = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	}

	public final static  class FileSystemConstants {
		protected final static String OS = System.getProperty("os.name").toLowerCase();
		protected final static String OS_ARCH = System.getProperty("os.arch").toLowerCase();
		protected final static String OS_VERSION = System.getProperty("os.version").toLowerCase();
		protected final static String FILESEPARATOR = FileSystems.getDefault().getSeparator();
		protected final static String USRDIR = System.getProperty("user.dir").toLowerCase() + FILESEPARATOR;
		protected final static String USRHOME = System.getProperty("user.home").toLowerCase() + FILESEPARATOR;

	}

}
