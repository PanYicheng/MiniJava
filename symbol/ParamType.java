package symbol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author:
 * @Description:
 * @Data: Created in 12:28 08/04/18
 * @Modified By:
 */
public class ParamType extends MType {
    private ArrayList<MVar> params;

    public ParamType(String _method,MType _parent,int row,int col){
        params = new ArrayList<>();
        setName(_method);
        setParent(_parent);
        setRow(row);
        setCol(col);
    }

    public void insertParam(MVar obj){
        params.add(obj);
    }

    public int getLength(){
        return params.size();
    }

    public MVar getMVar(int i){
        return params.get(i);
    }
}
