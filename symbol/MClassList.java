package symbol;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import errorinfo.*;

import javax.swing.text.html.HTMLDocument;

public class MClassList  extends MType{
    protected String mainClassName;
    protected HashMap<String,MClass> classList;

    public MClassList(){
        setType("Classlist");
        mainClassName = "";
        classList = new HashMap<>();
    }


    public MClassList(String _mainClassName){
        setType("Classlist");
        mainClassName = _mainClassName;
        classList = new HashMap<>();
    }

    public boolean setMainClassName(String _mainClassName){
        if(!mainClassName.equals("")){
            return false;
        }
        mainClassName = _mainClassName;
        return true;
    }

    public String getMainClassName() {
        return mainClassName;
    }

    public boolean insertClass (String className, MClass classObj) {
        if (classList.containsKey(className)) {
            ErrorInfo.addInfo(classObj.getRow(),classObj.getRow(),
                    "multi declaration of class");
            return false;
        }
        classList.put(className, classObj);
        return true;
    }

    public void printAllClass(){
        System.out.printf("\n\nAll Classes:\n\n");
        for(Map.Entry<String,MClass>entry:classList.entrySet()){
            System.out.println("###########################");
            System.out.println(" "+entry.getKey()+" extend "
                    +entry.getValue().getParentClassName());
            entry.getValue().printAllVars(3);
            entry.getValue().printAllMethods(3);
        }
    }

    public MClass getMClassObj(String className){
        return classList.get(className);
    }

    public boolean hasInheritanceLoop() {
        HashMap<String, Integer> checkedClasses = new HashMap<String, Integer>();
        Vector<String> inheritanceClass = new Vector<String>();
        Integer now = 1;
        for (MClass myclass: classList.values()) {
            if (checkedClasses.containsKey(myclass.getName())) continue;
            MClass tmp = myclass;
            inheritanceClass.clear();
            while (classList.containsKey(tmp.getParentName())) {
//                inheritanceClass.add(tmp.getName());
                if (checkedClasses.containsKey(tmp.getName()) && checkedClasses.get(tmp.getName()).equals(now)) {
//                    ErrorInfo.addlnInfo("Exception in thread \"main\" minijava.typecheck.InheritanceLoopException:");
//                    ErrorInfo.addInfo("\tEncountered Inheritance Loop \"class "+classTable.className);
                    System.out.println("Exists Inheritance Loop");
                    for (int i = 0; i < inheritanceClass.size(); i++) {
                        System.out.println(inheritanceClass.get(i));
                    }
//                        ErrorInfo.addInfo(" extends class "+inheritanceClass.get(i));
//                    ErrorInfo.addlnInfo("\" at line"+classTable.lineNumber);
//                    TypeCheck.setError();
                    return true;
                }
                checkedClasses.put(tmp.getName(), now);
                tmp = classList.get(tmp.getParentName());
            }
            now++;
        }
        return false;
    }

    public boolean hasUndefinedClass() {
        boolean flag = false;
        for (MClass myclass: classList.values()) {
            if (myclass.getName().equals(mainClassName)) continue;
            if (myclass.hasUndefinedClass()) flag = true;
        }
        return flag;
    }

    public boolean hasOverrideError() {
        boolean flag = false;
        for (MClass myclass: classList.values()) {
            if (myclass.hasOverrideError()) flag = true;
        }
        return flag;
    }

    public boolean hasUnusedVariables(){
        boolean flag = false;
        for(MClass myclass:classList.values()){
            if(myclass.hasUnusedVariables())
                flag = true;
        }
        return flag;
    }

}
