package symbol;

import java.util.HashMap;

public class MClass extends MIdentifier{
    protected HashMap<String,MVar> internalVars;
    protected HashMap<String,MMethod> methods;

    public MClass(String _className,MIdentifier _parent, int _row,int _col){
        super(_className,"Class",_row,_col);
        this.setParent(_parent);
        internalVars = new HashMap<String,MVar>();
        methods = new HashMap<String,MMethod>();
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

}
