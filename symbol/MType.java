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

    public MType() {
        parent = null;
        setType("nothing");
    }

    public MType(int i){
        identifierAsRet = i == 3;
    }

    public void setType(String _type) {
        type = _type;
    }

    public String getType() {
        return type;
    }

    //whether is a return type of a method
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

    public void printAllClass(){

    }

    public boolean insertVariable(String varName, MVar var) {
        return false;
    }

    public boolean insertMethod(String methodName, MMethod method) {
        return false;
    }

    public void addParam(MVar newvar){

    }

    public String getMethodName() {
        return null;
    }

    public String getClassName(){
        return null;
    }

    public MType getParentClass(){
        return null;
    }

    public String getReturnTypeName(){
        return null;
    }

    public boolean isClassType(){
        return type.equals("Class");
    }

    public MMethod getMethodByName(String mName){
        return null;
    }

    public MClass getMClassObj(String className){
        return null;
    }

    public MVar getVariable(String varName){
        return null;
    }

    public MType getClassList(){
        return null;
    }

    public boolean isTypeMatch(MVar left,MVar right){
        return false;
    }
}
