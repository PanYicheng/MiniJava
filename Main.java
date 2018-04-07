import java.io.*;
import symbol.*;
import visitor.*;
import syntaxtree.*;
//import typecheck.*;
//import typecheck.symboltable.*;
//import java.io.File;
//import java.io.InputStream;
import java.io.FileInputStream;
import errorinfo.ErrorInfo;


public class Main{
	public static void main(String args[]){
        try{
//            InputStream in = new FileInputStream(args[0]);
            InputStream in = new FileInputStream(
                    "./program/TreeVisitor-error.java");
            Node root = new MiniJavaParser(in).Goal();
            MType allClassList = null;
            System.out.println("---start building symbol table");
            allClassList = root.accept(new BuildSymbolTableVistor(),
                    allClassList);
            System.out.println("---print all classes");
            allClassList.printAllClass();
//            root.accept(new TypeCheckVisitor(),allClassList);
            if(ErrorInfo.getSize() == 0){
                System.out.println("Program type checked successfully");
            }
            else{
                System.out.println("Type error");
            }
            ErrorInfo.printAll();
        }
        catch(FileNotFoundException e1){
            e1.printStackTrace();
        }
        catch(ParseException e2){
            e2.printStackTrace();
        }
	}
}

