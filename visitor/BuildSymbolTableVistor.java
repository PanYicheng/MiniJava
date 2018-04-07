package visitor;

import symbol.*;
import syntaxtree.*;

/**
 * @Author:
 * @Description:
 * @Data: Created in 13:50 06/04/18
 * @Modified By:
 */
public class BuildSymbolTableVistor extends GJDepthFirst<MType,MType>{
    @Override
    public MType visit(Identifier n, MType argu) {
        MType _ret = null;

        if(argu instanceof MVar && argu.isClassType()){
            argu.setType(n.f0.tokenImage);
        }
        if(argu.getType().equals("returntype")){
            argu.setType(n.f0.tokenImage);
            argu.trueReturnFlag();
        }
        n.f0.accept(this,argu);
        return _ret;
    }

    @Override
    public MType visit(Goal n, MType argu) {
        MType _ret = null;
        System.out.println("  building mclasslist : "+
                n.f0.f1.f0.tokenImage);
        argu = new MClassList(n.f0.f1.f0.tokenImage);
        n.f0.accept(this,argu);
        n.f1.accept(this,argu);
        n.f2.accept(this,argu);
        _ret = argu;
        return _ret;
    }
    public MType visit(MainClass n,MType argu){
        MType _ret = null;

        MClass mainClass = new MClass(n.f1.f0.tokenImage,argu,
                n.f1.f0.beginLine,n.f1.f0.beginColumn,
                "none");
        MMethod mainFunc = new MMethod("main","void",
                mainClass,n.f6.beginLine,n.f6.beginColumn);
        mainFunc.addParam(new MVar(n.f11.f0.tokenImage,mainFunc,
                n.f11.f0.beginLine,n.f11.f0.beginColumn,
                "String[]",mainFunc.getMethodName(),
                mainClass.getClassName()));
        mainClass.insertMethod(mainFunc.getName(),mainFunc);
        argu.insertClass(mainClass.getName(),mainClass);

        n.f0.accept(this, mainClass);
        n.f1.accept(this, mainClass);
        n.f2.accept(this, mainClass);
        n.f3.accept(this, mainFunc);
        n.f4.accept(this, mainFunc);
        n.f5.accept(this, mainFunc);
        n.f6.accept(this, mainFunc);
        n.f7.accept(this, mainFunc);
        n.f8.accept(this, mainFunc);
        n.f9.accept(this, mainFunc);
        n.f10.accept(this, mainFunc);
        n.f11.accept(this, mainFunc);
        n.f12.accept(this, mainFunc);
        n.f13.accept(this, mainFunc);
        n.f14.accept(this, mainFunc);
        n.f15.accept(this, mainFunc);
        n.f16.accept(this, mainFunc);
        n.f17.accept(this, mainClass);

        _ret = mainClass;
        return _ret;
    }

    @Override
    public MType visit(ClassDeclaration n, MType argu) {
        MType _ret = null;

        MClass newClass = new MClass(n.f1.f0.tokenImage,argu,
                n.f1.f0.beginLine,n.f1.f0.beginColumn,"none");

        n.f0.accept(this, newClass);
        n.f1.accept(this, newClass);
        n.f2.accept(this, newClass);
        n.f3.accept(this, newClass);
        n.f4.accept(this, newClass);
        n.f5.accept(this, newClass);

        argu.insertClass(newClass.getName(),newClass);
        _ret = newClass;
        return _ret;
    }

    @Override
    public MType visit(ClassExtendsDeclaration n, MType argu) {
        MType _ret = null;

        MClass newClass = new MClass(n.f1.f0.tokenImage,argu,
                n.f1.f0.beginLine,n.f1.f0.beginColumn,n.f3.f0.tokenImage);

        n.f0.accept(this, newClass);
        n.f1.accept(this, newClass);
        n.f2.accept(this, newClass);
        n.f3.accept(this, newClass);
        n.f4.accept(this, newClass);
        n.f5.accept(this, newClass);
        n.f6.accept(this, newClass);
        n.f7.accept(this, newClass);

        argu.insertClass(newClass.getName(),newClass);
        _ret = newClass;
        return _ret;
    }

    @Override
    public MType visit(VarDeclaration n, MType argu) {
        MType _ret = null;

        MVar var = new MVar(n.f1.f0.tokenImage,argu,
                n.f1.f0.beginLine, n.f1.f0.beginColumn,
                n.f0.f0.which,
                argu.getMethodName(), argu.getClassName());

        n.f0.accept(this, var);
        argu.insertVariable(var.getName(),var);
        if(argu instanceof MClass ){
            var.setKind(MType.FIELD);
        }
        if(argu instanceof MMethod){
            var.setKind(MType.LOCAL);
        }

        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        _ret = var;
        return _ret;
    }

    @Override
    public MType visit(MethodDeclaration n, MType argu) {
        MType _ret = null;

        n.f0.accept(this,argu);

        MType type = new MType(n.f1.f0.which);

        n.f1.accept(this,type);

        MMethod method;
        if(type.getReturnFlag()){
            method = new MMethod(n.f2.f0.tokenImage,type.getType(),
                    argu, n.f2.f0.beginLine,n.f2.f0.beginColumn);
        }
        else{
            method = new MMethod(n.f2.f0.tokenImage,n.f1.f0.which,
                    argu, n.f2.f0.beginLine,n.f2.f0.beginColumn);
        }

        n.f2.accept(this, method);
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

        argu.insertMethod(method.getName(),method);

        _ret = method;

        return _ret;
    }

    @Override
    public MType visit(FormalParameter n, MType argu) {
        MType _ret = null;

        MType type;
        type = new MType(n.f0.f0.which);

        n.f0.accept(this,type);

        MVar param = new MVar(n.f1.f0.tokenImage,argu,
                n.f1.f0.beginLine,n.f1.f0.beginColumn,
                type.getType(),argu.getMethodName(),argu.getClassName());


        argu.addParm(param);

        n.f1.accept(this,argu);

        _ret = param;
        return _ret;
    }
}
