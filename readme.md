#Hw1：TypeCheck
######潘宜城 1500062801
   完成情况：

     ·能检查1-6的错误
     ·以warning的形式提示使用未初始化的变量
   分工：
   
     ·个人完成
   使用说明：
   
       先编译minijava文件夹下所有的java文件，我使用的部分测试例子在program文件夹下
       （如果是ubuntu系统可以直接运行rebuild.sh脚本编译）
     Main程序中的
       InputStream in = new FileInputStream("program/TreeVisitor-error.java");
     即为读取文件进行类型检查的入口，如果要用命令行的参数，也可以换成注释里的另一句读取语句。