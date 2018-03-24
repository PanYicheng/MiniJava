package symbol;

import java.util.ArrayList;
import java.util.HashMap;

public class MMethod extends MIdentifier{
    protected String returnType;
    protected HashMap<String,MVar> varList = new HashMap<String,MVar>();
    protected ArrayList<MVar> paramList = new ArrayList<MVar>();


    private void setReturnType(String _returnType){
        returnType = _returnType;
    }

    public MMethod(String _name,String _returnType,MIdentifier _parent,int _row,int _col){
        super(_name,"method",_row,_col);
        this.setParent(_parent);
        this.setReturnType(_returnType);
    }

    public void addVar(MVar newvar){
        varList.put(newvar.getName(),newvar);
    }

    public void addParam(MVar newvar){
        paramList.add(newvar);
    }
}
