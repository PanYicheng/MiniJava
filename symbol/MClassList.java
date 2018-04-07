package symbol;

import java.util.Hashtable;
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

    public boolean checkInheritanceLoop() {
        HashMap<String, Integer> checkedClasses = new HashMap<>();
        Vector<String> inheritanceClass = new Vector<>();
        Integer now = 1;
        for (MClass myclass: classList.values()) {
            if (checkedClasses.containsKey(myclass.getName())) continue;
            MClass tmp = myclass;
            inheritanceClass.clear();
            while (classList.containsKey(tmp.getParentClassName())) {
                inheritanceClass.add(tmp.getName());
                if (checkedClasses.containsKey(tmp.getName()) &&
                        checkedClasses.get(tmp.getName()).equals(now)) {
                    ErrorInfo.addInfo(myclass.getRow(),myclass.getCol(),
                            "inheritance loop class:"+myclass.getName());
                    System.out.printf("       Extends class:");
                    for (int i = 0; i < inheritanceClass.size(); i++) {
                        System.out.printf(inheritanceClass.get(i)+" ");
                    }
                    System.out.println("");
                    return true;
                }
                checkedClasses.put(tmp.getName(), now);
                tmp = classList.get(tmp.getParentClassName());
            }
            now++;
        }
        return false;
    }

    public boolean checkUndefinedClass() {
        boolean flag = false;
        for (MClass myclass: classList.values()) {
            if (myclass.getName().equals(mainClassName)) continue;
            if (myclass.checkUndefinedClass(this)) flag = true;
        }
        return flag;
    }

    public boolean checkOverrideError() {
        boolean flag = false;
        for (MClass myclass: classList.values()) {
            if (myclass.checkOverrideError(this))
                flag = true;
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
