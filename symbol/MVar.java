package symbol;

public class MVar extends MIdentifier{
    protected String typeName;
    protected String methodName;
    protected String className;
    public MVar(String _name,MType _parent,int _row,int _col,
                String _typeName,String _methodName,String _className){
        super(_name,"variable",_row,_col,_parent);
        typeName   = _typeName;
        methodName = _methodName;
        className = _className;
    }

    public MVar(String _name,MType _parent,int _row,int _col,
                int _typewhich,String _methodName,String _className){
        super(_name,"variable",_row,_col,_parent);
        if (_typewhich == 0) this.setType("int[]");
        if (_typewhich == 1) this.setType("boolean");
        if (_typewhich == 2) this.setType("int");
        if (_typewhich == 3) this.setType("unnamedclass");
        methodName = _methodName;
        className = _className;
    }

    public String getMethodName(){
        return methodName;
    }

    public String getClassName(){
        return className;
    }

    public String getType(){
        return typeName;
    }

    public void setType(String _typeName){
        typeName = _typeName;
    }

    public boolean isClassType(){
        if(typeName.equals("int"))return false;
        if(typeName.equals("int[]"))return false;
        if(typeName.equals("boolean"))return false;
        return true;
    }
}
