import java.io.*;
import visitor.*;
import syntaxtree.*;
//import java.io.File;
//import java.io.InputStream;
//import java.io.FileInputStream;

public class Main{
	public static void main(String args[]){
		InputStream in;
		try{
			in = new FileInputStream(args[0]);
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		Node root;
		try {
			root = new MiniJavaParser(in).Goal();
		}
		catch (ParseException e){
			e.printStackTrace();
		}
//		byte b[] = new byte[(int)f.length()];
//		int len = 0;
//		try {
//			len = input.read(b);
//			System.out.println("长度："+len);
//			input.close();
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(new String(b,0,len));
		Visitor visitor = new DepthFirstVisitor();
		try {
			root.accept(visitor);
		}
		catch (ParseException e){
			e.printStackTrace();
		}
	}
}

