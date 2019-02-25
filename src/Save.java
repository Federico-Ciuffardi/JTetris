import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Save{
	public Save(){
		Path file =  Paths.get("saveFile.txt");
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader =
		      new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        GridManager.highScore = Integer.parseInt(line);
		    }
		} catch (IOException x) {
		    System.err.println(x);
		}
	}
	public void setHighScore(){
       try {
            //Whatever the file path is.
            File statText = new File("saveFile.txt");
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(Integer.toString(GridManager.highScore));
            w.close();
        } catch (IOException e) {
            System.err.println("Error"+e);
        }
	}
}
