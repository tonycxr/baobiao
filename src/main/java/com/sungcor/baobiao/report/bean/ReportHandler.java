package com.sungcor.baobiao.report.bean;

import com.runqian.base4.tool.Segment;
import com.runqian.base4.util.PwdUtils;
import com.runqian.report4.ide.base.ConfigOptions;
import com.runqian.report4.ide.dialog.DialogExportToTextOption;
import com.runqian.report4.model.ReportDefine2;
import com.runqian.report4.model.engine.ExtCellSet;
import com.runqian.report4.usermodel.*;
import com.runqian.report4.view.exceloxml.ExcelOxmlReport;
import com.runqian.report4.view.html.HtmlReport;
import com.runqian.report4.view.pdf.PdfReport;
import com.runqian.report4.view.text.TextFile;
import com.runqian.report4.view.text.TextReport;
import com.runqian.report4.view.word.WordReport;
import com.runqian.report4.view.xml.XMLReport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class ReportHandler {
    public static final byte EXPORT_EXCEL = 1;
    public static final byte EXPORT_EXCEL_PAGE = 2;
    public static final byte EXPORT_EXCEL_FORMULA = 3;
    public static final byte EXPORT_PDF = 4;
    public static final byte EXPORT_PDF_PAGE = 5;
    public static final byte EXPORT_WORD = 6;
    public static final byte EXPORT_WORD_PAGE = 7;
    public static final byte EXPORT_TEXT = 8;
    public static final byte EXPORT_XML = 9;
    public static final byte EXPORT_HTML = 10;
    public static final byte EXPORT_PDF_ANAMORPHIC = 11;
    public static final byte EXPORT_PDF_PAGE_ANAMORPHIC = 12;
    public static final byte EXPORT_PDF_TEXT = 11;
    public static final byte EXPORT_PDF_PAGE_TEXT = 12;
    public static final byte EXPORT_PAJ = 13;
    public static final byte EXPORT_EXCEL2007 = 21;
    public static final byte EXPORT_EXCEL_PAGE2007 = 22;
    public static final byte EXPORT_EXCEL_FORMULA2007 = 23;
    public static final byte EXPORT_EXCEL_OPENXML = 24;
    public static final byte EXPORT_EXCEL_OPENXML_PAGE = 25;
    public static final byte EXPORT_EXCEL_OPENXML_FORMULA = 26;
    private String _$1 = null;
    private OutputStream _$2;
    private byte _$3;
    private Object _$4;

    public ReportHandler(OutputStream var1, byte var2) {
        this._$2 = var1;
        this._$3 = var2;
    }

    public ReportHandler(String var1, byte var2) throws FileNotFoundException {
        this._$1 = var1;
        this._$2 = new FileOutputStream(var1);
        this._$3 = var2;
    }

    private Object _$1(IReport var1, OutputStream var2, boolean var3, String var4) throws Throwable {
        PageBuilder var19;
        switch(this._$3) {
            case 1:
            case 2:
            case 3:
                if (this._$4 == null) {
                    this._$4 = new ExcelExporter();
                }

                ExcelExporter var5 = (ExcelExporter)this._$4;
                if (this._$3 == 2) {
                    var19 = new PageBuilder(var1);
                    var5.setDispRatio(var1.getDispRatio());
                    var5.resetExport();
                    var5.export(var4, var19);
                } else {
                    var5.export(var4, var1);
                    if (this._$3 == 3) {
                        var5.setFomulaExported(true);
                    }
                }

                if (var3) {
                    var5.saveTo(var2);
                }
                break;
            case 4:
            case 5:
            case 11:
            case 12:
                if (this._$4 == null) {
                    this._$4 = new PdfReport(var2);
                }

                PdfReport var20;
                (var20 = (PdfReport)this._$4).setAnamorphic(this._$3 == 11 || this._$3 == 12);
                if (this._$3 != 5 && this._$3 != 12) {
                    var20.export(var1);
                } else {
                    PageBuilder var21 = new PageBuilder(var1);
                    var20.resetExport();
                    ExportConfig var23;
                    if ((var23 = var1.getExportConfig()) != null) {
                        var20.setOwnerPassword(PwdUtils.decrypt(var23.getPDFOwnerPassword()));
                        var20.setUserPassword(var23.getPDFUserPassword());
                        var20.setPrivilege(var23.getPDFPrivilege());
                        var20.setExportConfig(var23);
                    }

                    var20.export(var21);
                }

                if (var3) {
                    var20.save();
                }
                break;
            case 6:
            case 7:
                if (this._$4 == null) {
                    this._$4 = new WordReport();
                }

                WordReport var8 = (WordReport)this._$4;
                if (this._$3 == 7) {
                    PageBuilder var22 = new PageBuilder(var1);
                    var8.export(var22);
                } else {
                    var8.export(var1);
                }

                if (var3) {
                    var8.saveTo(var2);
                }
                break;
            case 8:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            default:
                DialogExportToTextOption var13;
                (var13 = new DialogExportToTextOption()).show();
                if (var13.getOption() != 0) {
                    return null;
                }

                Segment var16;
                String var15 = (var16 = new Segment(var13.get())).get(DialogExportToTextOption.EXPORTLINE);
                String var17;
                if (new Boolean(var15)) {
                    var17 = (new TextReport(var1)).toString();
                } else {
                    var15 = var16.get(DialogExportToTextOption.DISPVALUE);
                    boolean var14 = new Boolean(var15);
                    if ((var15 = var16.get(DialogExportToTextOption.SEPERATOR)).equalsIgnoreCase("tab")) {
                        var15 = "\t";
                    }

                    TextFile var18;
                    (var18 = new TextFile(var1, var15)).setSaveDispValue(var14);
                    var17 = var18.toString();
                }

                var2.write(var17.getBytes());
                break;
            case 9:
                if (this._$4 == null) {
                    this._$4 = new XMLReport();
                }

                XMLReport var9;
                (var9 = (XMLReport)this._$4).export(var4, var1);
                if (var3) {
                    var9.saveTo((FileOutputStream)var2);
                }
                break;
            case 10:
                String var11 = (new HtmlReport(var1, this._$1)).generateHtml();
                var2.write(var11.getBytes());
                break;
//            case 13:
//                (new PajReport(var1)).out(var2);
//                break;
            case 21:
            case 22:
            case 23:
                if (this._$4 == null) {
                    this._$4 = Class.forName("com.runqian.report4.view.excel2007.Excel2007Report").newInstance();
                }

                if (this._$3 == 22) {
                    var19 = new PageBuilder(var1);
                    invokeMethod(this._$4, "setDispRatio", new Object[]{new Integer(var1.getDispRatio())}, new Class[]{Integer.TYPE});
                    invokeMethod(this._$4, "resetExport", new Object[0]);
                    invokeMethod(this._$4, "export", new Object[]{var4, var19});
                } else {
                    invokeMethod(this._$4, "export", new Object[]{var4, var1});
                    if (this._$3 == 23) {
                        invokeMethod(this._$4, "setFomulaExported", new Object[]{Boolean.TRUE});
                    }
                }

                if (var3) {
                    invokeMethod(this._$4, "saveTo", new Object[]{var2});
                }
                break;
            case 24:
            case 25:
            case 26:
                if (this._$4 == null) {
                    this._$4 = new ExcelOxmlReport();
                }

                ExcelOxmlReport var6 = (ExcelOxmlReport)this._$4;
                if (this._$3 == 25) {
                    PageBuilder var7 = new PageBuilder(var1);
                    var6.setDispRatio(var1.getDispRatio());
                    var6.resetExport();
                    var6.export(var4, var7);
                } else {
                    var6.export(var4, var1);
                    if (this._$3 == 26) {
                        var6.setFomulaExported(Boolean.TRUE);
                    }
                }

                if (var3) {
                    var6.saveTo(var2);
                }
        }

        return this._$4;
    }

    private static boolean _$1(Method var0, Object[] var1) {
        Class[] var2;
        if ((var2 = var0.getParameterTypes()).length != var1.length) {
            return false;
        } else {
            for(int var3 = 0; var3 < var1.length; ++var3) {
                if (!var2[var3].isInstance(var1[var3])) {
                    return false;
                }
            }

            return true;
        }
    }

    public void addSheet(IReport var1, String var2) throws Throwable {
        this._$1(var1, this._$2, false, var2);
    }

    public void export(IReport var1) throws Throwable {
        this._$1(var1, this._$2, true, "report");
        if (this._$1 != null) {
            this._$2.close();
        }

    }


    public void exportPageBuilder(PageBuilder var1, String var2) throws Throwable {
        IReport[] var3 = var1.getAllPages();
        byte var4 = this._$3;
        switch(this._$3) {
            case 2:
                var4 = 1;
                break;
            case 5:
                var4 = 4;
                break;
            case 7:
                var4 = 6;
                break;
            case 12:
                var4 = 11;
                break;
            case 22:
                var4 = 21;
                break;
            case 25:
                var4 = 24;
        }

        byte var5 = this._$3;
        this._$3 = var4;

        for(int var6 = 0; var6 < var3.length; ++var6) {
            this.addSheet(var3[var6], var2 + "-" + (var6 + 1));
        }

        this._$3 = var5;
    }

    public static Object invokeMethod(Object var0, String var1, Object[] var2) throws Exception {
        return invokeMethod(var0, var1, var2, (Class[])null);
    }

    public static Object invokeMethod(Object var0, String var1, Object[] var2, Class[] var3) throws Exception {
        Class var4 = var0.getClass();
        if (var3 == null) {
            Method[] var5 = var4.getMethods();

            for(int var6 = 0; var6 < var5.length; ++var6) {
                Method var7;
                if ((var7 = var5[var6]).getName().equals(var1) && _$1(var7, var2)) {
                    return var7.invoke(var0, var2);
                }
            }

            StringBuffer var9;
            (var9 = new StringBuffer()).append("(");

            for(int var8 = 0; var8 < var2.length; ++var8) {
                if (var8 > 0) {
                    var9.append(",");
                }

                var9.append(var2[var8].getClass().getName());
            }

            var9.append(")");
            throw new Exception(var1 + var9 + " not found.");
        } else {
            return var4.getMethod(var1, var3).invoke(var0, var2);
        }
    }

    public static void main(String[] var0) {
        try {
            ConfigOptions.load();
            ExtCellSet.setLicenseFileName(ConfigOptions.sLicenseFile);
            FileOutputStream var1 = new FileOutputStream("c:/test.pdf");
            ReportHandler var2 = new ReportHandler(var1, (byte)4);
            ReportDefine2 var3;
            (var3 = new ReportDefine2(2, 2)).getCell(1, (short)1).setValue("11");
            var3.getCell(1, (short)2).setValue("12");
            var3.getCell(2, (short)1).setValue("21");
            var3.getCell(2, (short)2).setValue("22");
            var2.addSheet(var3, "1");
            (var3 = new ReportDefine2(1, 1)).getCell(1, (short)1).setValue("aaa");
            var2.addSheet(var3, "2");
            (var3 = new ReportDefine2(3, 3)).getCell(1, (short)1).setValue("111");
            var3.getCell(1, (short)2).setValue("12");
            var3.getCell(2, (short)1).setValue("21");
            var3.getCell(3, (short)3).setValue("333");
            var2.addSheet(var3, "3");
            var2.save();
            var1.close();
        } catch (Throwable var4) {
            var4.printStackTrace();
        }
    }

    public void save() throws Exception {
        if (this._$4 != null) {
            switch(this._$3) {
                case 1:
                case 2:
                case 3:
                    ((ExcelExporter)this._$4).saveTo(this._$2);
                    break;
                case 4:
                case 5:
                case 11:
                case 12:
                    ((PdfReport)this._$4).save();
                    break;
                case 6:
                case 7:
                    ((WordReport)this._$4).saveTo(this._$2);
                case 8:
                case 10:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                default:
                    break;
                case 9:
                    ((XMLReport)this._$4).saveTo((FileOutputStream)this._$2);
                    break;
                case 21:
                case 22:
                case 23:
                    invokeMethod(this._$4, "saveTo", new Object[]{this._$2});
                    break;
                case 24:
                case 25:
                case 26:
                    ((ExcelOxmlReport)this._$4).saveTo(this._$2);
            }

            if (this._$1 != null) {
                this._$2.close();
            }

        }
    }

}
