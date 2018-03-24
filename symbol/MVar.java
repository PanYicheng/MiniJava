package symbol;

public class MVar extends MIdentifier{
    public MVar(String _name,MIdentifier _parent,int _row,int _col){
        super(_name,"variable",_row,_col);
        this.setParent(_parent);
    }


}
