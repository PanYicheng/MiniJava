package symbol;

public class MIdentifier extends MType{
    protected String name;
    protected String type;
    protected int row;
    protected int col;
    protected MIdentifier parent;

    public MIdentifier(){
        name = "";
        type = "";
        row = -1;
        col = -1;
        parent = null;
    }

    public MIdentifier(String _name,String _type,int _row,int _col){
        name = _name;
        type = _type;
        row = _row;
        col = _col;
    }

    public void setParent(MIdentifier _parent){
        parent = _parent;
    }

    public String getName(){return name;};
    public String getParentName(){return parent.getName();};
    public String getType(){return type;};


}
