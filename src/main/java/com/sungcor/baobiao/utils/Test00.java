package com.sungcor.baobiao.utils;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test00 {
    public static void main(String[] args) {
            try {
                ///////////////////////////////////
                //  使用javaassist修改 class/jar 代码
                ///////////////////////////////////
                //  设置jar包路径
                ClassPool.getDefault().insertClassPath("E:/Myprojects/2022.6/baobiao/lib/report4.jar");

                // 获取需要修改的类
                CtClass testJarClass = ClassPool.getDefault().getCtClass("com.runqian.report4.view.excel.ExcelReport");
                // 获取类中的printTest方法
                CtMethod printTestMethod = testJarClass.getDeclaredMethod("saveTo");
                // 修改该方法的内容
                printTestMethod.setBody("{\n" +
                        "        ExtCellSet var2;\n" +
                        "        if ((var2 = ExtCellSet.get()) != null && var2.getExportEnabled() && var2.checkExpiration()) {\n" +
                        "            String var11;\n" +
                        "              \n" +
                        "                HSSFPalette var4 = this._$2.getCustomPalette();\n" +
                        "                ExcelPalette var5 = new ExcelPalette(var4);\n" +
                        "\n" +
                        "                try {\n" +
                        "                    this._$1(var5);\n" +
                        "                } catch (Exception var10) {\n" +
                        "                    var10.printStackTrace();\n" +
                        "                    throw new ReportError(var10.getMessage());\n" +
                        "                }\n" +
                        "\n" +
                        "                if (this._$5 != null && this._$5.size() > 0) {\n" +
                        "                    int var6 = 0;\n" +
                        "\n" +
                        "                    for(int var7 = this._$5.size(); var6 < var7; ++var6) {\n" +
                        "                        String var8 = (String)this._$5.get(var6);\n" +
                        "                        this._$2.setSheetName(var6, var8);\n" +
                        "                    }\n" +
                        "                }\n" +
                        "\n" +
                        "                try {\n" +
                        "                    this._$2.write(var1);\n" +
                        "                } catch (Exception var9) {\n" +
                        "                    throw new ReportError(var9.getMessage());\n" +
                        "                }\n" +
                        "            \n" +
                        "        } \n" +
                        "    }");




                ///////////////////////////////////
                //  使用反射测试输出,查看修改结果
                ///////////////////////////////////
                Class newTestJarClass = testJarClass.toClass();
                // 使用修改过的类创建对象
                Object newTestJar = newTestJarClass.newInstance();
                Method newPrintTestMethod = newTestJarClass.getDeclaredMethod("saveTo(OutputStream paramOutputStream)");
                newPrintTestMethod.invoke(newTestJar);

                // 解除代码锁定,恢复可编辑状态
                testJarClass.defrost();
                // 写出到外存中
                testJarClass.writeFile();
                // testJarClass.writeFile(other path);

            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
