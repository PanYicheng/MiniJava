package symbol;

public class MIdentifier extends MType{



    public MIdentifier(){
        setType("identifier");
        setName("");
        setRow(-1);
        setCol(-1);
        setParent(null);
    }

    public MIdentifier(String _name,String _type,int _row,int _col,MType _parent){
        setType(_type);
        setName(_name);
        setRow(_row);
        setCol(_col);
        setParent(_parent);
    }
}
