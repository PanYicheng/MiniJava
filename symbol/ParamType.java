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
    private String methodname;
    private String classname;
    private ArrayList<MVar> params;

    public ParamType(String _method,String _classname,MType _parent,int row,int col){
        params = new ArrayList<>();
        methodname = _method;
        classname = _classname;
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

    public MType getClassList(){
        return getParent().getClassList();
    }

    public String getMethodName(){
        return methodname;
    }

    public String getClassName(){
        return classname;
    }
}
