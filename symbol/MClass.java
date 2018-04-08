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
            ErrorInfo.addInfo(method.getRow(),method.getCol(),
                    "multi declaration of method " +
                    "of the same name : [" + methodName+"]");
            return false;
        }
        methods.put(methodName,method);
        return true;
    }

    public boolean insertVariable(String variableName,MVar var){
        if(internalVars.containsKey(variableName)){
            ErrorInfo.addInfo(var.getRow(),var.getCol(),
                    "multi declaration of class" +
                    " variable:["+variableName+"]");
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

    public boolean checkUndefinedClass(MClassList classlist){
        boolean flag = false;
        if( classlist.getMClassObj(extendClassName) == null &&
                !extendClassName.equals("none")){
             ErrorInfo.addInfo(getRow(),getCol(),
                     "extended class not existed");
             flag = true;
        }
        for(MVar internalvariable:internalVars.values()){
            if(internalvariable.isClassType() &&
                    classlist.getMClassObj(internalvariable.getType()) == null){
                ErrorInfo.addInfo(internalvariable.getRow(),
                        internalvariable.getCol(),
                        "internal class variable not exists");
                flag = true;
            }
        }
        for(MMethod method:methods.values()){
            if(method.getName().equals("main"))
                continue;
            if(method.checkUndefinedClass(classlist))
                flag = true;
        }
        return flag;
    }

    public boolean checkOverrideError(MClassList classlist){
        boolean flag = false;
        for(MMethod method:methods.values()){
            MClass parentClass = classlist.getMClassObj(getParentClassName());
            while(parentClass != null){
                if(parentClass.methods.containsKey(method.getName())){
                    MMethod superMethod = parentClass.methods.get(method.getName());
                    if(!method.canOverride(superMethod)){
                        ErrorInfo.addInfo(method.getRow(),method.getCol(),
                                "method:["+getName()+"."
                                        +method.getName()+
                        "] override error");
                        flag = true;
                    }
                }
                parentClass = classlist.getMClassObj(parentClass.getParentClassName());
            }
        }
        return flag;
    }

    public boolean hasUnusedVariables(){
        return false;
    }

    public String getParentClassName(){return extendClassName;};

    public void setParentClassName(String _extendClassName){
        extendClassName = _extendClassName;
    }

    public MMethod getMethodByName(String methodName){
        if(methods.get(methodName)!=null)
            return methods.get(methodName);
        if(getClassList().getMClassObj(extendClassName)!=null)
            return getClassList().getMClassObj(extendClassName)
            .getMethodByName(methodName);
        return null;
    }

    public String getMethodName(){
        return null;
    }

    public String getClassName(){
        return getName();
    }

    public MVar getVariable(String varName){
        if(internalVars.get(varName) != null)
            return internalVars.get(varName);
        if(getClassList().getMClassObj(extendClassName)!= null){
            return getClassList().getMClassObj(extendClassName)
                    .getVariable(varName);
        }
        return null;
    }

    public MType getClassList(){
        return getParent();
    }

}
