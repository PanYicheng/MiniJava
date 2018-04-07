package symbol;

import java.util.HashMap;
import java.util.Map;

import errorinfo.*;

public class MClass extends MIdentifier{
    protected String extendClassName;
    protected HashMap<String,MVar> internalVars = new HashMap<>();
    protected HashMap<String,MMethod> methods = new HashMap<>();

    public MClass(String _className,MType _parent, int _row,int _col,
                  String _extendClassName){
        super(_className,"Class",_row,_col,_parent);
        extendClassName = _extendClassName;
    }

    public boolean insertMethod(String methodName,MMethod method){
        if(methods.containsKey(methodName)){
            ErrorInfo.addInfo(method.getRow(),method.getCol(),"multi declaration");
            return false;
        }
        methods.put(methodName,method);
        return true;
    }

    public boolean insertVariable(String variableName,MVar var){
        if(internalVars.containsKey(variableName)){
            ErrorInfo.addInfo(var.getRow(),var.getCol(),"multi declaration of class" +
                    "variable");
            return false;
        }
        internalVars.put(variableName,var);
        return true;
    }

    public void printAllVars(int spaces){
        String sp = "";
        for(int i=0;i<spaces;i++){
            sp = sp + " ";
        }
        if(!internalVars.isEmpty()){
            System.out.println("----------------Class Variables----------------");
        }
        String finalSp = sp;
        internalVars.forEach((key, value) ->
                System.out.println(finalSp+value.getType()+" "+key));
    }

    public void printAllMethods(int spaces){
        String sp = "";
        for(int i=0;i<spaces;i++){
            sp = sp + " ";
        }
        if(!methods.isEmpty()){
            System.out.println("----------------Methods----------------");
        }
        for(Map.Entry<String,MMethod>entry:methods.entrySet()){
            System.out.printf(sp+entry.getValue().getReturnTypeName()+
                   "  "+entry.getKey());
            entry.getValue().printAll(5);
        }
    }

    public boolean hasUndefinedClass(){
        return false;
    }

    public boolean hasOverrideError(){
        return false;
    }

    public boolean hasUnusedVariables(){
        return false;
    }

    public String getParentClassName(){return extendClassName;};

    public void setParentClassName(String _extendClassName){
        extendClassName = _extendClassName;
    }

}
