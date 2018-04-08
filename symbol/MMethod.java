package symbol;

import errorinfo.ErrorInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MMethod extends MIdentifier{
    protected String returnType;
    protected HashMap<String,MVar> varList = new HashMap<>();
    protected ArrayList<MVar> paramList = new ArrayList<>();


    public void setReturnType(String _returnType){
        returnType = _returnType;
    }

    public String getReturnTypeName(){return returnType;}

    public MMethod(String _name,String _returnType,MType _parent,int _row,int _col){
        super(_name,"method",_row,_col,_parent);
        setReturnType(_returnType);
    }

    public MMethod(String _name,int _typewhich,MType _parent,int _row,int _col){
        super(_name,"method",_row,_col,_parent);
        if (_typewhich == 0) setReturnType("int[]");
        if (_typewhich == 1) setReturnType("boolean");
        if (_typewhich == 2) setReturnType("int");
        if (_typewhich == 3) setReturnType("unnamedclass");
    }

    public boolean insertVariable(String varName,MVar newvar) {
        if(varList.containsKey(varName)){
            ErrorInfo.addInfo(newvar.getRow(),newvar.getCol(),"multi declaration of"
                    + "variable ["+varName+"]in "+getClassName()+"."+
            getMethodName());
            return false;
        }
        varList.put(newvar.getName(),newvar);
        return true;
    }

    public void addParam(MVar newvar){
//        System.out.println("add parameter: "+
//                newvar.getType()+" "+newvar.getName()+
//                " to method: "+ getMethodName());
        paramList.add(newvar);
        //also consider parameters as local variables of the function
        varList.put(newvar.getName(),newvar);
    }

    public void printAll(int spaces){
        String sp = "";
        for(int i=0;i<spaces;i++){
            sp = sp + " ";
        }
//        System.out.printf(sp+"[Params]");
//        System.out.printf("(%d)",paramList.size());
        System.out.printf("( ");
        for(int i=0;i<paramList.size();i++){
            System.out.printf("%s %s,",paramList.get(i).getType()
                    , paramList.get(i).getName());
        }
        System.out.println(")");
        System.out.printf(sp+"[Vars  ]");
        System.out.printf("(%d)",varList.size());
        for(Map.Entry<String,MVar>entry:varList.entrySet()){
            System.out.printf("%s %s ,",
                    entry.getValue().getType()
                    ,entry.getKey());
        }
        System.out.println("");
    }

    public String getMethodName(){
        return getName();
    }

    public MType getParentClass(){
        return getParent();
    }

    public String getClassName(){
        return getParentClass().getName();
    }

    public boolean checkUndefinedClass(MClassList classlist){
        boolean flag = false;
        if(!returnType.equals("int[]") &&
                !returnType.equals("boolean") &&
                !returnType.equals("int") &&
                (classlist.getMClassObj(returnType) == null)){
            ErrorInfo.addInfo(getRow(),getCol(),
                    "return type ["+returnType+"] not defined");
            flag = true;
        }
        for(MVar var:varList.values()){
            if(var.isClassType() &&
                    (classlist.getMClassObj(var.getType()) == null))
            {
                ErrorInfo.addInfo(var.getRow(),var.getCol(),
                        "local variable type not defined:["+var.getType()+
            "]");
                flag = true;
            }
        }
        return flag;
    }

    public boolean canOverride(MMethod superMethod){
        if(!returnType.equals(superMethod.getReturnTypeName()))
            return false;
        if(paramList.size() != superMethod.paramList.size()){
            return false;
        }
        for(int i=0;i<paramList.size();i++){
            if(!paramList.get(i).getType().equals(
                    superMethod.paramList.get(i).getType())){
                return false;
            }
        }
        return true;
    }

    public MVar getVariable(String varName){
        return varList.get(varName);
    }

    public MType getClassList(){
        return getParent().getParent();
    }

    public boolean isParameterByName(String varName){
        for(int i=0;i<paramList.size();i++){
            if(paramList.get(i).getName().equals(varName)){
                return true;
            }
        }
        return false;
    }

    public boolean isParamListCompatible(ParamType params){
        if(params.getLength() != paramList.size())
            return false;
        for(int i =0;i<paramList.size();i++){
            if(!getClassList().isTypeMatch(
                    paramList.get(i),params.getMVar(i))) {
                return false;
            }
        }
        return true;
    }
}
