package nl.hu.tosad.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FilePrinter {

public void print(String name, String output){
	 try {
		 String[] lines = output.split("\\r?\\n");
         File statText = new File("C:/Users/Lucca Huijgens/EclipseProjects/tosad/src/main/java/nl/hu/tosad/writer/"+name+".sql");
         FileOutputStream is = new FileOutputStream(statText);
         OutputStreamWriter osw = new OutputStreamWriter(is);    
         BufferedWriter w = new BufferedWriter(osw);
         for (String s:lines){
         w.write(s);
         System.out.println(s);
         w.newLine();
         }
         w.close();
     } catch (IOException e) {
         System.err.println("Problem writing to the file "+name+".sql");
     }
 }
}
