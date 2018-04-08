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
                    "program/TreeVisitor-error.java");
            Node root = new MiniJavaParser(in).Goal();
            MType allClassList = null;
            System.out.println("---0.start building symbol table");
            allClassList = root.accept(new BuildSymbolTableVistor(),
                    allClassList);
            System.out.println("---print all classes");
            allClassList.printAllClass();

            System.out.printf("\n\n");
            System.out.println("---1.check inheritance loop,undefined class," +
                    "override error");
            if(firstCheck((MClassList) allClassList)){
                System.out.println("     Exist Inheritance loop," +
                        "Undefined class,Override error");
            }
            System.out.println("---1.end");

            System.out.println("---2.second time type check");

            TypeCheckVisitor typecheck = new TypeCheckVisitor();
            root.accept(typecheck,allClassList);

            System.out.println("---2.end");


//            root.accept(new TypeCheckVisitor(),allClassList);
            if(ErrorInfo.getSize() == 0){
                System.out.println("$$$  Program type checked successfully");
            }
            else{
                System.out.println("$$$  Type error");
            }
            System.out.println("---Print All Error Info:");
            ErrorInfo.printAll();
        }
        catch(FileNotFoundException e1){
            e1.printStackTrace();
        }
        catch(ParseException e2){
            e2.printStackTrace();
        }
	}

    private static boolean firstCheck(MClassList classlist){
        if(classlist == null)
            return true;
        if(classlist.checkInheritanceLoop())
            return true;
        if(classlist.checkUndefinedClass())
            return true;
        if(classlist.checkOverrideError())
            return true;
        return false;
    }
}

