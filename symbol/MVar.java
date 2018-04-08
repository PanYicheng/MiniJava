package symbol;

public class MVar extends MIdentifier{
    protected String typeName;
    protected String methodName;
    protected String className;
    protected boolean used = false;
    int inited = 0;
    public static int UNINITED = 0;
    public static int INITED = 1;
    public static int INITING = 2;
    public MVar(String _name,MType _parent,int _row,int _col,
                String _typeName,String _methodName,String _className){
        super(_name,"variable",_row,_col,_parent);
        typeName   = _typeName;
        methodName = _methodName;
        className = _className;
        //always keep parent is a method or a class
        if(_parent instanceof MVar){
            setParent(_parent.getParent());
        }
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
        //always keep parent is a method or a class
        if(_parent instanceof MVar){
            setParent(_parent.getParent());
        }
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

    public boolean getInited(){
        return (inited == INITED);
    }

    public void setInited(int _inited){
        System.out.println("Change Inited Status of Variables "+getName()
                +" "+String.valueOf(_inited));
        inited = _inited;
    }

    public void setUsed(boolean _used){
        used = _used;
    }

    public boolean getUsed(){
        return used;
    }

    public boolean isClassType(){
        if(typeName.equals("int"))return false;
        if(typeName.equals("int[]"))return false;
        if(typeName.equals("boolean"))return false;
        return true;
    }

    public MType getClassList(){
        MType classlist = getParent().getClassList();

        if(classlist != null){
            return classlist;
        }
        System.out.println("exist one variable without parent class");
        return null;
    }
}
