package symbol;

public class MType {
    static public final int FIELD = 0;
    static public final int PARAM = 1;
    static public final int LOCAL = 2;
    //Type indicate the type of
    //classlist, class, method, variable
    protected String type;
    private boolean identifierAsRet = false;
    private int kind = -1;
    private String name;
    private int row;
    private int col;
    private MType parent;
    private String parentName;
    private String className;

    public MType() {
        parent = null;
        setType("nothing");
    }

    public MType(int i){
        if (i == 3) {
            identifierAsRet = true;
        }
        else {
            identifierAsRet = false;
        }
    }

    public void setType(String _type) {
        type = _type;
    }

    public String getType() {
        return type;
    }

    public boolean isReturnClass(){return identifierAsRet;}

    public void setKind(int _kind){
        kind = _kind;
    }

    public int getKind(){
        return kind;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setRow(int _row){
        row = _row;
    }

    public int getRow(){
        return row;
    }

    public void setCol(int _col){
        col = _col;
    }

    public int getCol(){
        return col;
    }

    public void setParent(MType _parent) {
        parent = _parent;
    }

    public MType getParent() {
        return parent;
    }

    public void setParentName(String _pName){
        parentName = _pName;
    }

    public String getParentName(){
        return parentName;
    }

    public boolean insertClass(String className,MClass classObj)
    {
        return false;
    }

    public void printAllClass(){return;}

    public boolean insertVariable(String varName,
                                  MVar var) {
        return false;
    }

    public boolean insertMethod(String methodName, MMethod method) {
        return false;
    }

    public void addParam(MVar newvar){
        return;
    }

    public void setClassName(String _className){
        className = _className;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return "";
    }

    public String getReturnTypeName(){
        return "";
    }

    public boolean isClassType(){
        if(type.equals("Class"))return true;
        return false;
    }
}
