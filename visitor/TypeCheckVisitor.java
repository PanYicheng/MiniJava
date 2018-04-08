package visitor;
import errorinfo.ErrorInfo;
import symbol.*;
import syntaxtree.*;
/**
 * @Author:PanYicheng
 * @Description:
 * @Data: Created in 21:27 07/04/18
 * @Modified By:
 */
public class TypeCheckVisitor extends GJDepthFirst<String,MType>{
    @Override
    public String visit(MainClass n, MType argu) {
        MClass classobj = argu.getMClassObj(n.f1.f0.tokenImage);
        n.f0.accept(this, classobj);
        n.f1.accept(this, classobj);
        n.f2.accept(this, classobj);
        MMethod method = classobj.getMethodByName("main");
        n.f3.accept(this, method);
        n.f4.accept(this, method);
        n.f5.accept(this, method);
        n.f6.accept(this, method);
        n.f7.accept(this, method);
        n.f8.accept(this, method);
        n.f9.accept(this, method);
        n.f10.accept(this, method);
        n.f11.accept(this, method);
        n.f12.accept(this, method);
        n.f13.accept(this, method);
        n.f14.accept(this, method);
        n.f15.accept(this, method);
        n.f16.accept(this, method);
        n.f17.accept(this,classobj);
        return null;
    }

    @Override
    public String visit(ClassDeclaration n, MType argu) {
        MClass mclass = argu.getMClassObj(n.f1.f0.tokenImage);
        n.f0.accept(this, mclass);
        n.f1.accept(this, mclass);
        n.f2.accept(this, mclass);
        n.f3.accept(this, mclass);
        n.f4.accept(this, mclass);
        n.f5.accept(this, mclass);
        return null;
    }

    @Override
    public String visit(ClassExtendsDeclaration n, MType argu) {
        MClass mclass = argu.getMClassObj(n.f1.f0.tokenImage);
        n.f0.accept(this, mclass);
        n.f1.accept(this, mclass);
        n.f2.accept(this, mclass);
        n.f3.accept(this, mclass);
        n.f4.accept(this, mclass);
        n.f5.accept(this, mclass);
        n.f6.accept(this, mclass);
        n.f7.accept(this, mclass);
        return null;
    }

    @Override
    public String visit(MethodDeclaration n, MType argu) {
        String _ret;
        MMethod method = argu.getMethodByName(n.f2.f0.tokenImage);

        n.f0.accept(this, method);
        n.f1.accept(this, method);
        n.f2.accept(this, method);
        n.f3.accept(this, method);
        n.f4.accept(this, method);
        n.f5.accept(this, method);
        n.f6.accept(this, method);
        n.f7.accept(this, method);
        n.f8.accept(this, method);
        n.f9.accept(this, method);

        //retVar represents the return type of this method
        MVar retVar = new MVar("ReturnTypeCheck",method,
                n.f11.beginLine,n.f11.beginColumn,
                "UnknownType",method.getName(),
                method.getClassName());
        _ret = n.f10.accept(this, retVar);
        // return type error
        if (!method.getReturnTypeName().equals(retVar.getType())) {
            ErrorInfo.addInfo(n.f9.beginLine,n.f9.beginColumn,
                    "return(type: "+retVar.getType()+")"+
            "expecting return (type:"+method.getReturnTypeName()+")");
        }

        n.f11.accept(this, method);
        n.f12.accept(this, method);

        return _ret;
    }

    @Override
    public String visit(AssignmentStatement n, MType argu) {
        String _ret = null;
        MVar id = argu.getVariable(n.f0.f0.tokenImage) ;

        /**
         * identifier no declaration error
         */
        boolean inited = false;
        if (id == null)
            id = argu.getParentClass().getVariable(n.f0.f0.tokenImage);
        if (id == null) {
            ErrorInfo.addInfo(n.f0.f0.beginLine,n.f0.f0.beginColumn,
                    "identifier:["+n.f0.f0.tokenImage+"]"
            +"of "+argu.getParent().getName()+"."+argu.getMethodName()+
            " not declared");
        } else {
            if (id.getInited()) inited = true;
            else id.setInited(MVar.INITED);
        }

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        if (id != null && !inited) id.setInited(MVar.INITING);

        MVar typeVar= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str1 = n.f2.accept(this, typeVar);

        n.f3.accept(this, argu);

        /**
         * identifier assign type error
         */
        if (!typeVar.getType().equals("UnknownType"))
            if (id != null &&
                    !argu.getClassList().isTypeMatch(id, typeVar)) {
                ErrorInfo.addInfo(n.f0.f0.beginLine,
                        n.f0.f0.beginColumn,
                        "assignment type not match ,"+
                id.getType()+" = "+typeVar.getType());
            }

        if (id != null) id.setInited(MVar.INITED);

        _ret = n.f0.f0.tokenImage +"="+str1;
//        System.out.println(_ret);
        return _ret;
    }
    @Override
    public String visit(ArrayAssignmentStatement n, MType argu) {
        String _ret = null;

        MVar id = argu.getVariable(n.f0.f0.tokenImage);

        /**
         * identifier no declaration error
         */
        if (id == null)
            id = argu.getClassList().getMClassObj(
                    argu.getClassName()).getVariable(
                            n.f0.f0.tokenImage);
        if (id == null) {
            ErrorInfo.addInfo(n.f0.f0.beginLine,n.f0.f0.beginColumn,
                    "identifier not declared ["+
            id.getName()+"]");
        }

        MVar typeVar= new MVar("UnknowIdentifier",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str1 = n.f0.accept(this, typeVar);

        /**
         * id[exp]'s id is not 'int[]'
         */
        if (!typeVar.getType().equals("UnknownType"))
            if (typeVar != null && !typeVar.getType().equals("int[]")) {
            ErrorInfo.addInfo(n.f0.f0.beginLine, n.f0.f0.beginColumn,
                    "identifier is not int[]");
            }

        n.f1.accept(this, argu);

        MVar typeVar2= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str2 = n.f2.accept(this, typeVar2);

        /**
         * id[exp]'s exp is not 'int'
         */
        if (!typeVar2.getType().equals("UnknownType"))
            if (id != null && typeVar.getType().equals("int[]") &&
                    !typeVar2.getType().equals("int")) {
            ErrorInfo.addInfo(n.f1.beginLine,n.f1.beginColumn,
                    n.f0.f0.tokenImage+
                    "["+str2+
                            "] 's index is not int");
            }

        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        MVar typeVar3= new MVar("UnknowExp",
                argu,n.f4.beginLine,n.f4.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());

        String str3 = n.f5.accept(this, typeVar3);

        /**
         * array element assign type error
         */
        if (!typeVar3.getType().equals("UnknownType"))
            if (id != null && typeVar.getType().equals("int[]")
                    && typeVar2.getType().equals("int")
                    && !typeVar3.getType().equals("int")) {
            ErrorInfo.addInfo(n.f4.beginLine,n.f4.beginColumn,
                    str1
                    +"["+str2+"]="+str3+" right is not int");
            }

        n.f6.accept(this, argu);
        _ret = str1 + "["+str2+"]="+str3;
        return _ret;
    }
    @Override
    public String visit(IfStatement n, MType argu) {
        String _ret = null;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        MVar typeVar= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String exp = n.f2.accept(this, typeVar);

        /**
         * if (exp)'s exp is not 'boolean'
         */
        if (!typeVar.getType().equals("UnknownType"))
            if (!typeVar.getType().equals("boolean")) {
            ErrorInfo.addInfo(typeVar.getRow(),typeVar.getCol(),
                    "if(exp) exp is not boolean");
            }

        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);

        _ret ="if("+exp+")...";
        return _ret;
    }
    @Override
    public String visit(WhileStatement n, MType argu) {
        String _ret = null;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        MVar typeVar= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String exp = n.f2.accept(this, typeVar);

        /**
         * while (exp)'s exp is not 'boolean'
         */
        if (!typeVar.getType().equals("UnknownType"))
            if (!typeVar.getType().equals("boolean")) {
                ErrorInfo.addInfo(typeVar.getRow(),typeVar.getCol(),
                        "while(exp) exp is not boolean");
            }

        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        _ret = "while("+exp+")...";
        return _ret;
    }
    @Override
    public String visit(PrintStatement n, MType argu) {
        String _ret=null;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        MVar typeVar= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        _ret = n.f2.accept(this, typeVar);

        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        /**
         * System.out.println(exp)'s exp is not 'int'
         */
        if (!typeVar.getType().equals("UnknownType"))
            if (!typeVar.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar.getRow(),typeVar.getCol(),
                        "print(exp) 's exp is not int");
            }

        return _ret;
    }
    @Override
    public String visit(AndExpression n, MType argu) {
        String _ret = null;

        MVar typeVar1= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str1 = n.f0.accept(this, typeVar1);

        n.f1.accept(this, argu);

        MVar typeVar2= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str2 = n.f2.accept(this, typeVar2);

        /**
         * exp1 is not 'boolean'
         */
        if (!typeVar1.getType().equals("UnknownType"))
            if (!typeVar1.getType().equals("boolean")) {
            ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                    "exp1 && exp2 : exp1 is not boolean");
            }

        /**
         * exp2 is not 'boolean'
         */
        if (!typeVar2.getType().equals("UnknownType"))
            if (!typeVar2.getType().equals("boolean")) {
                ErrorInfo.addInfo(typeVar2.getRow(),typeVar2.getCol(),
                        "exp1 && exp2 : exp1 is not boolean");
            }

        if (typeVar1.getType().equals("boolean") &&
                typeVar2.getType().equals("boolean"))
            argu.setType("boolean");
        _ret = str1+"&&"+str2;
        return _ret;
    }
    @Override
    public String visit(CompareExpression n, MType argu) {
        String _ret = null;

        MVar typeVar1= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str1 = n.f0.accept(this, typeVar1);

        n.f1.accept(this, argu);

        MVar typeVar2= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str2 = n.f2.accept(this, typeVar2);

        /**
         * exp1 is not 'int'
         */
        if (!typeVar1.getType().equals("UnknownType"))
            if (!typeVar1.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "exp1 < exp2 : exp1 is not int");
            }

        /**
         * exp2 is not 'int'
         */
        if (!typeVar2.getType().equals("UnknownType"))
            if (!typeVar2.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar2.getRow(),typeVar2.getCol(),
                        "exp1 < exp2 : exp2 is not int");
            }

        if (typeVar1.getType().equals("int")
                && typeVar2.getType().equals("int"))
            argu.setType("boolean");
        _ret = str1+"<"+str2;
        return _ret;
    }
    @Override
    public String visit(PlusExpression n, MType argu) {
        String _ret = null;

        MVar typeVar1= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str1 = n.f0.accept(this, typeVar1);

        n.f1.accept(this, argu);

        MVar typeVar2= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str2 = n.f2.accept(this, typeVar2);
        /**
         * exp1 is not 'int'
         */
        if (!typeVar1.getType().equals("UnknownType"))
            if (!typeVar1.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "exp1 + exp2 : exp1 is not int");
            }

        /**
         * exp2 is not 'int'
         */
        if (!typeVar2.getType().equals("UnknownType"))
            if (!typeVar2.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar2.getRow(),
                        typeVar2.getCol(),
                        str1+"+"+str2+","+
                                str2+" is not int");
            }

        if (typeVar1.getType().equals("int")
                && typeVar2.getType().equals("int"))
            argu.setType("int");
        _ret = str1+"+"+str2;
        return _ret;
    }
    @Override
    public String visit(MinusExpression n, MType argu) {
        String _ret = null;
        MVar typeVar1= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str1 = n.f0.accept(this, typeVar1);
        n.f1.accept(this, argu);
        MVar typeVar2= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str2 = n.f2.accept(this, typeVar2);
        //  exp1 is not 'int'
        if (!typeVar1.getType().equals("UnknownType"))
            if (!typeVar1.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "exp1 - exp2 : exp1 is not int");
            }
        //  exp2 is not 'int'
        if (!typeVar2.getType().equals("UnknownType"))
            if (!typeVar2.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar2.getRow(),typeVar2.getCol(),
                        "exp1 - exp2 : exp2 is not int");
            }
        if (typeVar1.getType().equals("int")
                && typeVar2.getType().equals("int"))
            argu.setType("int");
        _ret = str1+"-"+str2;
        return _ret;
    }
    @Override
    public String visit(TimesExpression n, MType argu) {
        String _ret = null;

        MVar typeVar1= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        n.f0.accept(this, typeVar1);

        n.f1.accept(this, argu);

        MVar typeVar2= new MVar("UnknowExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        n.f2.accept(this, typeVar2);
        /**
         * exp1 is not 'int'
         */
        if (!typeVar1.getType().equals("UnknownType"))
            if (!typeVar1.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "exp1 * exp2 : exp1 is not int");
            }

        /**
         * exp2 is not 'int'
         */
        if (!typeVar2.getType().equals("UnknownType"))
            if (!typeVar2.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar2.getRow(),typeVar2.getCol(),
                        "exp1 * exp2 : exp2 is not int");
            }

        if (typeVar1.getType().equals("int")
                && typeVar2.getType().equals("int"))
            argu.setType("int");
        return _ret;
    }

    @Override
    public String visit(ArrayLookup n, MType argu) {
        String _ret = null;

        MVar typeVar1= new MVar("UnknowPrimaryExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String list = n.f0.accept(this, typeVar1);

        n.f1.accept(this, argu);

        MVar typeVar2= new MVar("UnknowPrimaryExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String index = n.f2.accept(this, typeVar2);
        n.f3.accept(this, argu);
        //  array is not 'int[]'
        if (!typeVar1.getType().equals("UnknownType"))
            if (!typeVar1.getType().equals("int[]")) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "exp1[exp2] : exp1 is not int[]");
            }
        //  exp2 is not 'int'
        if (!typeVar2.getType().equals("UnknownType"))
            if (!typeVar2.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "exp1[exp2] : exp2 is not int");
            }
        //set return type to int
        if (typeVar1.getType().equals("int[]")
                && typeVar2.getType().equals("int"))
            argu.setType("int");
        _ret = list+"["+index+"]";
        return _ret;
    }

    @Override
    public String visit(ArrayLength n, MType argu) {
        MVar typeVar1= new MVar("UnknowPrimaryExp",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String _ret = n.f0.accept(this, typeVar1);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        //exp.length's exp is not 'int[]'
        if (!typeVar1.getType().equals("UnknownType"))
            if (!typeVar1.getType().equals("int[]")) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "a.length : a is not int[]");
            }
        if (typeVar1.getType().equals("int[]"))
            argu.setType("int");
        return _ret;
    }

    @Override
    public String visit(MessageSend n, MType argu) {
        String _ret = null;
        MVar typeVar1= new MVar("UnknowClass",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String strClass = n.f0.accept(this, typeVar1);

        MMethod method = null;
        //  exp.method()'s exp is not class
        if (!typeVar1.getType().equals("UnknownType"))
            if (!(typeVar1.isClassType())) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "exp.method : exp is not class type");
            }
            else {

                MClass classObj = argu.getClassList().
                        getMClassObj(typeVar1.getType());

                /**
                 * exp.method()'s exp's type no declaration
                 */
                if (classObj == null) {
                    ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                            "exp.method : exp is not a " +
                                    "declared class");
                }
                else {

                    method = classObj.getMethodByName(
                            n.f2.f0.tokenImage);

                    /**
                     * exp.method()'s method no declaration
                     */
                    if (method == null) {
                        ErrorInfo.addInfo(typeVar1.getRow(),
                                typeVar1.getCol(),
                                classObj.getClassName()+"."+
                                        n.f2.f0.tokenImage+
                                        " : is not a " +
                                        "declared method");
                    }
                }
            }

        n.f1.accept(this, argu);

        MVar typeVar2= new MVar("UnknowMethod",
                argu,n.f1.beginLine,n.f1.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String strMethod = n.f2.accept(this, typeVar2);

        n.f3.accept(this, argu);

        ParamType paramList = new ParamType(
                argu.getMethodName(),argu.getClassName(), argu,
                n.f3.beginLine,n.f3.beginColumn);
        String strParams = n.f4.accept(this, paramList);

        if (method != null) {
            //  exp.method()'s parameters' type error
            if (!method.isParamListCompatible(paramList)) {
                ErrorInfo.addInfo(paramList.getRow(),
                        paramList.getCol(),
                        "Method call parameter is " +
                                "not matched");
            }
            else {
                argu.setType(method.getReturnTypeName());
            }
        }
        n.f5.accept(this, argu);
        _ret = strClass+"."+strMethod+"("+strParams+")";
        return _ret;
    }

    @Override
    public String visit(ExpressionList n, MType argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return null;
    }
    @Override
    public String visit(Expression n, MType argu) {
        MVar typeVar1= new MVar("UnknowExp",
                argu,argu.getRow(),argu.getCol(),
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String _ret = n.f0.accept(this, typeVar1);

        if (argu instanceof ParamType)
            ((ParamType)argu).insertParam(typeVar1);
        if (argu instanceof MVar)
            argu.setType(typeVar1.getType());

        return _ret;
    }

    @Override
    public String visit(ExpressionRest n, MType argu) {
        n.f0.accept(this, argu);
        MVar typeVar1= new MVar("UnknowExp",
                argu,argu.getRow(),argu.getCol(),
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str1 = n.f1.accept(this, typeVar1);
        if (argu instanceof ParamType)
            ((ParamType)argu).insertParam(typeVar1);
        if (argu instanceof MVar)
            argu.setType(typeVar1.getType());
        return null;
    }

    @Override
    public String visit(AllocationExpression n, MType argu) {
        String _ret = null;
        n.f0.accept(this, argu);

        MVar typeVar1= new MVar("UnknowIdentifier",
                argu,n.f1.f0.beginLine,n.f1.f0.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        n.f1.accept(this, typeVar1);

        n.f2.accept(this, argu);
        n.f3.accept(this, argu);

        MClass classObj = argu.getClassList().getMClassObj(
                n.f1.f0.tokenImage);

        //  new id()'s id no declaration
        if (classObj == null) {
            ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                    "identifier:["+n.f1.f0.tokenImage+"] "+
            "has no declaration!");
        }
        else {
            argu.setType(classObj.getClassName());
        }
        _ret = "new "+n.f1.f0.tokenImage+"()";
        return _ret;
    }

    @Override
    public String visit(ArrayAllocationExpression n, MType argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        MVar typeVar1= new MVar("UnknowExp",
                argu,n.f2.beginLine,n.f2.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String _ret = n.f3.accept(this, typeVar1);

        n.f4.accept(this, argu);
        //new int[exp]'s exp is not 'int'
        if (!typeVar1.getType().equals("UnknownType")) {
            if (!typeVar1.getType().equals("int")) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "new int[exp] 's exp is not int");
            }
        }
        //set argu's type int[] when succeed
        if (typeVar1.getType().equals("int"))
            argu.setType("int[]");
        return _ret;
    }

    @Override
    public String visit(PrimaryExpression n, MType argu) {
        MVar typeVar1= new MVar("UnknowPrimaryExp",
                argu,argu.getRow(),argu.getCol(),
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        typeVar1.setUsed(true);
        String _ret = n.f0.accept(this, typeVar1);
        //use accept return value to pass the identifier 's real name
        typeVar1.setName(_ret);

        if (n.f0.which == 3 && typeVar1.isClassType()) {

            MVar idType = null;

            //  identifier no declaration
            if (argu.getMethodName() != null) {
                idType = argu.getClassList().getMClassObj(
                        argu.getClassName()).getMethodByName(
                        argu.getMethodName()).getVariable(
                        typeVar1.getName());
            }
            if (idType == null)
                idType = argu.getClassList().getMClassObj(
                        argu.getClassName()).getVariable(typeVar1.getName());
            if (idType == null) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "identifier:["+typeVar1.getName()+"] not declared");
            }
            if(idType != null){
                //get class type
                typeVar1.setType(idType.getType());
                //set this MVar is being used for future detecting
                //uninited variables using
                idType.setUsed(true);
            }
        }
        if(n.f0.which == 3 && !typeVar1.isClassType()){
            MVar idType = null;

            //  identifier no declaration
            if (argu.getMethodName() != null) {
                idType = argu.getClassList().getMClassObj(
                        argu.getClassName()).getMethodByName(
                        argu.getMethodName()).getVariable(
                        typeVar1.getName());
            }
            if (idType == null)
                idType = argu.getClassList().getMClassObj(
                        argu.getClassName()).getVariable(typeVar1.getName());
            if(idType != null){
                //get class type
                typeVar1.setType(idType.getType());
                //set this MVar is being used for future detecting
                //uninited variables using
                idType.setUsed(true);
            }
        }

        //set argu's type when the identifier exists
        argu.setType(typeVar1.getType());
        //return identifier's name if possible
        return _ret;
    }

    @Override
    public String visit(NotExpression n, MType argu) {
        String _ret;
        n.f0.accept(this, argu);

        MVar typeVar1= new MVar("UnknowExp",
                argu,n.f0.beginLine,n.f0.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str1 = n.f1.accept(this, typeVar1);

        //  !exp's exp is not 'boolean'
        if (!typeVar1.getType().equals("UnknownType"))
            if (!typeVar1.getType().equals("boolean")) {
                ErrorInfo.addInfo(typeVar1.getRow(),typeVar1.getCol(),
                        "!exp 's exp is not boolean");
            }

        if (typeVar1.getType().equals("boolean"))
            argu.setType("boolean");

        _ret = "!"+str1;
        return _ret;
    }

    @Override
    public String visit(Identifier n, MType argu) {
        String _ret = n.f0.tokenImage;

        n.f0.accept(this, argu);
//        argu.setName(n.f0.tokenImage);

        MVar idType = null;

        //inside the method field
        if (argu.getMethodName() != null ) {
            idType = argu.getClassList().getMClassObj(
                    argu.getClassName()).getMethodByName(
                    argu.getMethodName()).getVariable(n.f0.tokenImage);
//            System.out.println("      visiting identifier:"+_ret+" of "+
//                    argu.getClassName()+"."+
//            argu.getMethodName());
        }
        //out of method field
        //inside the class field
        if (idType == null)
            idType = argu.getClassList().getMClassObj(
                    argu.getClassName()).getVariable(n.f0.tokenImage);
        if (idType != null){
            argu.setType(idType.getType());
            if(argu.getUsed()){
                idType.setUsed(true);
            }
        }



        if(argu.getMethodName() != null &&
                idType!= null &&
                !idType.getInited() &&
                !argu.isParameterByName(_ret) &&
                idType.getUsed()){
            System.out.println("%%% Warning Not inited variables "+
            _ret+" Row:"+n.f0.beginLine+" Col:"+n.f0.beginColumn);
        }

        return _ret;
    }

    @Override
    public String visit(IntegerLiteral n, MType argu) {
        String _ret = n.f0.tokenImage;

        n.f0.accept(this, argu);

        argu.setType("int");

        return _ret;
    }

    @Override
    public String visit(TrueLiteral n, MType argu) {
        String _ret = n.f0.tokenImage;

        n.f0.accept(this, argu);

        argu.setType("boolean");

        return _ret;
    }

    @Override
    public String visit(FalseLiteral n, MType argu) {
        String _ret = n.f0.tokenImage;

        n.f0.accept(this, argu);

        argu.setType("boolean");

        return _ret;
    }

    @Override
    public String visit(ThisExpression n, MType argu) {
        String _ret = n.f0.tokenImage;

        n.f0.accept(this, argu);

        argu.setType(argu.getClassName());

        return _ret;
    }

    @Override
    public String visit(BracketExpression n, MType argu) {
        String _ret = null;

        n.f0.accept(this, argu);

        MVar typeVar1= new MVar("UnknowBracketExp",
                argu,n.f0.beginLine,n.f0.beginColumn,
                "UnknownType", argu.getMethodName(),
                argu.getClassName());
        String str1 = n.f1.accept(this, typeVar1);

        n.f2.accept(this, argu);

        argu.setType(typeVar1.getType());

        _ret = "("+str1+")";
        return _ret;
    }

    @Override
    public String visit(VarDeclaration n, MType argu) {
        String _ret = null;
        n.f0.accept(this, argu);
        if (argu.getMethodName() != null) {
            MMethod method = argu.getClassList().getMClassObj(
                    argu.getClassName()).getMethodByName(
                            argu.getMethodName());
//            if (method != null)
//                method.getVariable(n.f1.f0.tokenImage).setInited(MVar.INITED);
            n.f1.accept(this, argu);
            if (method != null)
                method.getVariable(n.f1.f0.tokenImage).setInited(MVar.UNINITED);
            method.getVariable(n.f1.f0.tokenImage).setUsed(false);
        }
        else
            n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }
}
