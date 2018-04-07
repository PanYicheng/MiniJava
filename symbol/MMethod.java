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
            ErrorInfo.addInfo(newvar.getRow(),newvar.getCol(),"multi declaration of"+
            "variable in method");
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

    public void printParams(int spaces){
        for(int i=0;i<spaces;i++){
            System.out.printf(" ");
        }
        System.out.printf("params:\n");
        for(int i=0;i<spaces;i++){
            System.out.printf(" ");
        }
        paramList.forEach((value) -> System.out.printf("%s ",
                value.getName()));
        System.out.printf("\n");
    }
}
