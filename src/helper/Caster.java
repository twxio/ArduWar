package helper;

import java.io.File;
import java.util.Random;
import com.jacob.com.LibraryLoader;
import autoitx4java.AutoItX;

public class Caster {
	
	private String Taste;
	private File file;
	private AutoItX autoitx;
	
	Random ran = new Random();
	int x = ran.nextInt(50) + 200;
			
	public Caster(String Taste) {
		
		
		file = new File("lib", "lib.dll");
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		autoitx = new AutoItX();
		this.Taste = Taste;
	
	}
	
	public void CastSpell() {
		
		autoitx.send(Taste);
		autoitx.sleep(x);
		
	}
}
