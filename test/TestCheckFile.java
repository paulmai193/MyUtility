import java.io.File;

import logia.utility.filechecker.GenericFileChecker;

public class TestCheckFile {

	public static void main(String[] args) {
		File file = new File("C:/Users/Paul Mai/Desktop/90J6eyklVKpinAcVC4PM5v2q");
		System.out.println(GenericFileChecker.getType(file));
	}
}
