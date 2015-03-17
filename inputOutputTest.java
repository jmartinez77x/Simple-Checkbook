/*
Joe Martinez
I/O Test
*/

import java.io.*;

class inputOutputTest {
	public static void main(String[] args) throws IOException {
		FileWriter fw = new FileWriter("Output.java", false);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("class helloWorld{");
		bw.newLine();
		bw.write("     public static void main(String[] args){");
		bw.newLine();
		bw.write("          System.out.print();");
		bw.write("		}}");
		bw.close();
//		bw.write("}");
//		bw.close();
	}
}