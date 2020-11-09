package stackoverflow.LocalJsonConnector;

//import javax.swing.JFileChooser;

public class DefaultDir {
	
private static String OS = System.getProperty("os.name").toLowerCase();

//TODO clean unused code

	public String getDefaultDir() {
		String defaultDir = "";
		defaultDir = System.getProperty("user.home");
//		if (isWindows()) {
//			defaultDir = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();		
//		} else if (isMac()) {
////            System.out.println("This is Mac");
//		} else if (isUnix()) {
////            System.out.println("This is Unix or Linux");
//		} else if (isSolaris()) {
////            System.out.println("This is Solaris");
//		} else {
////            System.out.println("Your OS is not support!!");
//		}

		return defaultDir;
	}
//
//	public static boolean isWindows() {
//
//		return (OS.indexOf("win") >= 0);
//
//	}
//
//	public static boolean isMac() {
//
//		return (OS.indexOf("mac") >= 0);
//
//	}
//
//	public static boolean isUnix() {
//
//		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
//
//	}
//
//	public static boolean isSolaris() {
//
//		return (OS.indexOf("sunos") >= 0);
//
//	}
//	
//	public static void main(String arg[]) {
//		String a = System.getProperty("user.home");
//		System.out.println(a);
//	}

}
