//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.sungcor.baobiao.report.bean;

import com.runqian.base4.resources.DataSetMessage;
import com.runqian.base4.resources.EngineMessage;
import com.runqian.base4.resources.MessageManager;
import com.runqian.base4.resources.SplitPageMessage;
import com.runqian.base4.util.ImageUtils;
import com.runqian.base4.util.Logger;
import com.runqian.base4.util.PwdUtils;
import com.runqian.base4.util.ReportError;
//import com.view.pdf.PdfCell;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import com.runqian.report4.control.ControlUtils;
import com.runqian.report4.control.LeanLine;
import com.runqian.report4.model.NormalCell;
import com.runqian.report4.model.engine.ExtCellSet;
import com.runqian.report4.model.engine.ExtNormalCell;
import com.runqian.report4.model.engine2.ExtNCell;
import com.runqian.report4.model.engine2.ExtRow;
import com.runqian.report4.model.engine2.RowReport;
import com.runqian.report4.usermodel.*;
import com.runqian.report4.usermodel.dmgraph.DMImageValue;
import com.runqian.report4.usermodel.graph.ImageValue;
import com.runqian.report4.util.*;
import com.runqian.report4.view.LicenseException;
import com.runqian.report4.view.excel.ExcelColor;
import com.runqian.report4.view.excel.ExcelPalette;
import com.runqian.report4.view.excel.IHTMLExport;
import org.apache.poi2.hssf.usermodel.HSSFCell;
import org.apache.poi2.hssf.usermodel.HSSFCellStyle;
import org.apache.poi2.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi2.hssf.usermodel.HSSFComment;
import org.apache.poi2.hssf.usermodel.HSSFFont;
import org.apache.poi2.hssf.usermodel.HSSFHyperlink;
import org.apache.poi2.hssf.usermodel.HSSFPalette;
import org.apache.poi2.hssf.usermodel.HSSFPatriarch;
import org.apache.poi2.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi2.hssf.usermodel.HSSFRichTextString;
import org.apache.poi2.hssf.usermodel.HSSFRow;
import org.apache.poi2.hssf.usermodel.HSSFSheet;
import org.apache.poi2.hssf.usermodel.HSSFWorkbook;
import org.apache.poi2.hssf.util.Region;

public class ExcelExporter {
    private static int _$1 = 0;
    private HSSFWorkbook _$2;
    private PageBuilder _$3;
    private ArrayList _$4 = new ArrayList();
    private ArrayList _$5 = new ArrayList();
    private ArrayList _$6 = new ArrayList();
    private ArrayList _$7 = new ArrayList();
    private int _$8 = 1;
    private String _$9;
    private boolean _$10 = true;
    private boolean _$11 = true;
    private boolean _$12 = false;
    private int _$13 = 100;
    private PagerInfo _$14 = null;
    private final int _$15 = 65535;
    private boolean _$16 = false;
    private Locale _$17;
    public static final float TRANS_CONSTANT_ROW = 19.62F;
    public static final float TRANS_CONSTANT_COL = 42.74F;
    private byte _$18;

    public ExcelExporter() {
        this._$17 = Locale.CHINESE;
        this._$18 = 0;
        this._$2 = new HSSFWorkbook();
    }

    private float _$1(float var1) {
        if (this._$18 == 2) {
            var1 *= 72.0F;
        } else if (this._$18 == 1) {
            var1 = var1 * 72.0F / 25.4F;
        }

        return var1;
    }

    private short _$1(int var1, ExcelPalette var2) {
        return ExcelColor.transColor(var1, var2);
    }

    private static String _$1(ExtCellSet var0) throws LicenseException, Exception {
        ++_$1;
        long var1 = (long) var0.getExpirationTime();
        long var3 = (new Date()).getTime();
        long var5 = (var1 - var3) / 86400000L;
        if (var5 < 7L && var5 > -1L) {
            return "报表系统授权将在" + var5 + "天内过期，请重新申请授权";
        } else if (var0.getVersion() != 20 && var0.getVersion() != 21) {
            return "";
        } else if (var0.getPrompt() == null) {
            MessageManager var7 = EngineMessage.get();
            return var0.getVersion() == 20 ? var7.getMessage("pdfreport.demo1") : var7.getMessage("pdfreport.demo2");
        } else {
            return var0.getPrompt();
        }
    }

    private float _$1(IReport var1, int var2) {
        float var4 = var1.getRowCell(var2).getRowHeight();
        return this._$1(var4);
    }

    private byte[] _$1(IReport var1, int var2, int var3, INormalCell var4) {
        int var5 = 0;
        int var6 = 0;
        ReportParser var7 = new ReportParser(var1);
        int var8 = var4.getBackColor();
        byte var9 = var4.getHAlign();
        byte var10 = var4.getVAlign();
        int var11 = var7.getReportHeight();
        int var12 = var7.getReportWidth();
        BufferedImage var13 = null;
        if (var11 < var3) {
            if (var10 == -31) {
                var6 = (var3 - var11) / 2;
            } else if (var10 == -30) {
                var6 = var3 - var11;
            }

            var11 = var3;
        }

        if (var12 < var2) {
            if (var9 == -47) {
                var5 = (var2 - var12) / 2;
            } else if (var9 == -46) {
                var5 = var2 - var12;
            }

            var12 = var2;
        }

        Graphics2D var14 = (var13 = new BufferedImage(var12, var11, 1)).createGraphics();
        this._$1(var14, var8, 0, 0, var12, var11);

        try {
            short var15 = var7.getColCount();
            int var16 = var7.getRowCount();
            int[] var17 = new int[var15 + 1];
            int var18 = var5;
            var17[0] = var5;

            for (short var19 = 1; var19 <= var15; ++var19) {
                int var20 = var7.getColWidth(var19);
                if (!var7.isColVisible(var19)) {
                    var20 = 0;
                }

                var18 += var20;
                var17[var19] = var18;
            }

            int[] var33 = new int[var16 + 1];
            int var21 = var6;
            int var22 = 0;
            int var23 = 0;
            int var24 = 0;
            int var25 = 0;
            var33[0] = var6;
            Area var26;
            int var28;
            if ((var26 = var7.getPageHeader()) != null) {
                var22 = var26.getBeginRow();
                var23 = var26.getEndRow();
                if (var22 != var23) {
                    var33[var23] = var6;

                    for (int var27 = var23 - 1; var27 >= var22; --var27) {
                        var28 = var7.getRowHeight(var27);
                        if (!var7.isRowVisible(var27)) {
                            var28 = 0;
                        }

                        if ((var21 -= var28) < 0) {
                            var21 = 0;
                        }

                        var33[var27] = var21;
                    }
                }
            }

            Area var34 = var7.getPageFooter();
            var21 = var3 + var6;
            int var29;
            if (var34 != null) {
                var24 = var34.getBeginRow();
                var25 = var34.getEndRow();
                if (var24 != var25) {
                    for (var28 = var24 + 1; var28 <= var25; ++var28) {
                        var33[var28] = var21;
                        var29 = var7.getRowHeight(var28);
                        if (!var7.isRowVisible(var28)) {
                            var29 = 0;
                        }

                        var21 += var29;
                    }

                    var33[var25 + 1] = var21;
                }
            }

            var21 = var6;
            var33[var23 + 1] = var6;

            for (var28 = 1; var28 <= var16; ++var28) {
                if ((var28 < var22 || var28 > var23) && (var28 < var24 || var28 > var25)) {
                    var29 = var7.getRowHeight(var28);
                    if (!var7.isRowVisible(var28)) {
                        var29 = 0;
                    }

                    var21 += var29;
                    var33[var28] = var21;
                }
            }

//            for(var29 = 1; var29 <= var16; ++var29) {
//                if (var7.isRowVisible(var29)) {
//                    for(short var30 = 1; var30 <= var15; ++var30) {
//                        if (var7.isColVisible(var30) && var7.isCellVisible(var29, var30)) {
//                            PdfCell var31;
//                            (var31 = new PdfCell(var7, var29, var30, var14)).drawBackGround(var17, var33);
//                            var31.drawCell(var17, var33);
//                        }
//                    }
//                }
//            }
        } catch (Exception var32) {
            throw new ReportError(var32.getMessage(), var32);
        }

        return getImageByteArray(var13);
    }

    private float _$1(IReport var1, short var2) {
        float var4 = var1.getColCell(var2).getColWidth();
        return this._$1(var4);
    }

    private int _$1(ReportParser var1, int var2) {
        return var1.isRowVisible(var2) ? var1.getRowHeight(var2) : 0;
    }

    private boolean _$1(ReportParser var1, int var2, short var3, boolean var4, boolean var5) {
        if (!var1.isMerged(var2, var3)) {
            return false;
        } else {
            Area var6;
            int var7 = (var6 = var1.getMergedArea(var2, var3)).getBeginRow();
            short var8 = var6.getBeginCol();
            if (var4 && var5) {
                return var2 == var7 && var3 == var8;
            } else {
                int var9 = var6.getEndRow();
                short var10 = var6.getEndCol();
                if (var4) {
                    for (short var11 = var8; var11 <= var10; ++var11) {
                        if (var1.isColVisible(var11)) {
                            if (var2 == var7 && var3 == var11) {
                                return true;
                            }

                            return false;
                        }
                    }
                } else {
                    int var13;
                    if (var5) {
                        for (var13 = var7; var13 <= var9; ++var13) {
                            if (var1.isRowVisible(var13)) {
                                if (var2 == var13 && var3 == var8) {
                                    return true;
                                }

                                return false;
                            }
                        }
                    } else {
                        for (var13 = var7; var13 <= var9; ++var13) {
                            if (var1.isRowVisible(var13)) {
                                for (short var12 = var8; var12 <= var10; ++var12) {
                                    if (var1.isColVisible(var12)) {
                                        if (var2 == var13 && var3 == var12) {
                                            return true;
                                        }

                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }

                return false;
            }
        }
    }

    private int _$1(ReportParser var1, short var2) {
        return var1.isColVisible(var2) ? var1.getColWidth(var2) : 0;
    }

    private void _$1(ExcelPalette var1) throws Exception {
        if (this._$5 == null) {
            throw new ReportError("No Custom Excel Label!");
        } else if (this._$5.size() != this._$4.size()) {
            throw new ReportError("Wrong Custom Excel Label Size");
        } else {
            if (this._$9 != null && this._$9.trim().length() > 0) {
                this._$2.setFilePassword(this._$9.trim());
            }

            this._$2.setFormatFontStyle("宋体", (short) 12);
            Hashtable var2 = new Hashtable();
            ArrayList var3 = new ArrayList();
            ArrayList var4 = new ArrayList();
            int var5 = this._$4.size();

            for (int var6 = 0; var6 < this._$4.size(); ++var6) {
                HSSFSheet var7 = this._$2.createSheet((String) this._$5.get(var6));
                IReport var8 = (IReport) this._$4.get(var6);
                UnknownClass var9 = (UnknownClass) this._$6.get(var6);
//                this._$10 = UnknownClass.access$0(var9);
//                this._$11 = UnknownClass.access$1(var9);
//                this._$12 = UnknownClass.access$2(var9);
                ExportConfig var10;
                if ((var10 = var8.getExportConfig()) != null) {
                    this._$10 = var10.getExcelHiddenRowExported() ^ true;
                    this._$11 = var10.getExcelHiddenColExported() ^ true;
                    this._$12 = var10.getFullyPaged();
                }

                if (this._$16) {
                    this._$10 = true;
                    this._$11 = true;
                }

                this._$14 = (PagerInfo) this._$7.get(var6);
                int[] var11 = this._$1(var7, var8, var3, var4, 0, (short) 0, var2, var1);
                if (this._$12) {
                    PageBuilder var12 = null;

                    try {
                        (var12 = new PageBuilder(var8)).createPages();
                        IReport[] var13 = var12.getAllPages();
                        int var14 = var12.getXPageCount();
                        int var15 = var12.getYPageCount();
                        short var16 = 0;
                        int var17 = 0;
                        short var18 = 0;
                        int var19 = 0;
                        short var20 = 1;

                        for (short var21 = var8.getColCount(); var20 <= var21; ++var20) {
                            IColCell var22;
                            if ((var22 = var8.getColCell(var20)) != null) {
                                if (var22.getColType() != -80) {
                                    break;
                                }

                                if (!this._$11 && !this._$16) {
                                    if (var22.getColVisible()) {
                                        ++var16;
                                    }
                                } else {
                                    ++var16;
                                }
                            }
                        }

                        int var32 = 1;

                        for (int var23 = var8.getRowCount(); var32 <= var23; ++var32) {
                            IRowCell var24;
                            if ((var24 = var8.getRowCell(var32)) != null) {
                                byte var25;
                                if ((var25 = var24.getRowType()) != -95 && var25 != -96 && var25 != -90) {
                                    if (var25 != -93) {
                                        break;
                                    }
                                } else if (!this._$10 && !this._$16) {
                                    if (var24.getRowVisible()) {
                                        ++var17;
                                    }
                                } else {
                                    ++var17;
                                }
                            }
                        }

                        int var33 = 0;

                        label156:
                        while (true) {
                            if (var33 >= var14 - 1) {
                                int var35 = 0;

                                while (true) {
                                    if (var35 >= var15 - 1) {
                                        break label156;
                                    }

                                    IReport var36 = var13[var35 * var14];
                                    int var37 = 1;

                                    for (int var38 = var36.getRowCount(); var37 <= var38; ++var37) {
                                        IRowCell var29;
                                        if ((var29 = var36.getRowCell(var37)) != null && var29.getRowType() == -91) {
                                            if (!this._$10 && !this._$16) {
                                                if (var29.getRowVisible()) {
                                                    ++var19;
                                                }
                                            } else {
                                                ++var19;
                                            }
                                        }
                                    }

                                    if (var19 > 0) {
                                        var7.setRowBreak(var19 + var17 - 1);
                                    }

                                    ++var35;
                                }
                            }

                            IReport var34 = var13[var33];
                            short var26 = 1;

                            for (short var27 = var34.getColCount(); var26 <= var27; ++var26) {
                                IColCell var28;
                                if ((var28 = var34.getColCell(var26)) != null && var28.getColType() == -79) {
                                    if (!this._$11 && !this._$16) {
                                        if (var28.getColVisible()) {
                                            ++var18;
                                        }
                                    } else {
                                        ++var18;
                                    }
                                }
                            }

                            if (var18 > 0) {
                                var7.setColumnBreak((short) (var18 + var16 - 1));
                            }

                            ++var33;
                        }
                    } catch (Throwable var31) {
                        throw new ReportError("Can't create pages correctly: " + var31.getMessage());
                    }
                }

                if (this._$14 != null && this._$14.getLayout() == 1) {
                    var7.getPrintSetup().setLeftToRight(false);
                } else {
                    var7.getPrintSetup().setLeftToRight(true);
                }

                var7.setHorizontallyCenter(true);
                this._$1(this._$2, var6, var8, this._$10, this._$11, this._$16);
                if (this._$1(var7, var11[0] + 1)) {
                    int var10002 = var11[0]++;
                }

                if (System.getProperty("java.version").compareTo("1.4") >= 0) {
                    if (var10 != null && !var10.getNoExportPrintArea()) {
                        this._$2.setPrintArea(var6, 0, var11[1], 0, var11[0]);
                    }
                } else {
                    System.out.println("can't setPrintArea using jdk1.3");
                }
            }

        }
    }

    private void _$1(Graphics2D var1, int var2, int var3, int var4, int var5, int var6) {
        if (var2 == 16777215) {
            var2 = -1;
        }

        Color var7 = new Color(var2);

        try {
            var1.setColor(var7);
            var1.fillRect(var3, var4, var5 - var3, var6 - var4);
        } catch (Exception var9) {
            var9.printStackTrace(System.out);
        }
    }

    private HSSFFont _$1(String var1, short var2, int var3, boolean var4, boolean var5, boolean var6, byte var7, ExcelPalette var8, ArrayList var9) {
        short var10 = this._$1(var3, var8);
        int var11 = var4 ? 700 : 400;
        boolean var12 = var5;
        int var13 = var6 ? 1 : 0;
        byte var15 = 0;
        if (var7 == RichTextUtil.RICH_SCRIPT_SUB) {
            var15 = 2;
        } else if (var7 == RichTextUtil.RICH_SCRIPT_SUPER) {
            var15 = 1;
        }

        HSSFFont var14;
        for (int var16 = 0; var16 < var9.size(); ++var16) {
            if ((var14 = (HSSFFont) var9.get(var16)).getFontName().equalsIgnoreCase(var1) && var14.getFontHeightInPoints() == var2 && var14.getColor() == var10 && var14.getBoldweight() == var11 && var14.getItalic() == var12 && var14.getUnderline() == var13 && var14.getTypeOffset() == var15) {
                return var14;
            }
        }

        (var14 = this._$2.createFont()).setFontName(var1);
        var14.setFontHeightInPoints(var2);
        var14.setColor(var10);
        var14.setBoldweight((short) var11);
        var14.setItalic(var12);
        var14.setUnderline((byte) var13);
        var14.setTypeOffset(var15);
        var14.setCharSet((byte) -122);
        var9.add(var14);
        return var14;
    }

    private boolean _$1(HSSFSheet var1, int var2) throws Exception {
        try {
            String var4;
            if ((var4 = _$1(ExtCellSet.get())).length() == 0) {
                return false;
            } else {
                HSSFRow var5 = var1.createRow(var2);
                short var6 = var1.getRow(var2 - 1).getLastCellNum();
                short var7 = 0;

                for (short var8 = 0; var8 < var6; ++var8) {
                    if (var1.getColumnWidth(var8) > 100) {
                        var7 = var8;
                        break;
                    }
                }

                var5.createCell(var7).setCellValue(var4);
                return true;
            }
        } catch (LicenseException var10) {
            throw new Exception(var10.getMessage());
        }
    }

    private int[] _$1(HSSFSheet var1, IReport var2, ArrayList var3, ArrayList var4, int var5, short var6, Hashtable var7, ExcelPalette var8) {
        ExportConfig var9 = var2.getExportConfig();
        ExcelCellCopy var10 = null;
        this._$18 = var2.getUnit();
        ReportParser var11;
        int var12 = (var11 = new ReportParser(var2)).getRowCount();
        int var13;
        if ((var13 = var11.getColCount() + 1) > 255) {
            boolean var14 = false;
            if (!this._$11 && !this._$16) {
                short var15 = 0;

                for (short var16 = 1; var16 < var13; ++var16) {
                    if (var11.isColVisible(var16)) {
                        ++var15;
                    }
                }

                if (var15 > 255) {
                    var14 = true;
                }
            } else {
                var14 = true;
            }

            if (var14) {
                MessageManager var82 = DataSetMessage.get(this._$17);
                throw new ReportError(var82.getMessage("error.Excel.255col"));
            }
        }

        int var79 = 1;
        int var80 = var12;
        Area var81 = var11.getPageHeader();
        Area var17 = var11.getPageFooter();
        if (var81 != null) {
            var79 = var81.getEndRow() + 1;
        }

        if (var17 != null) {
            var80 = var17.getBeginRow() - 1;
        }

        HSSFPatriarch var18 = null;
        short var19 = var6;

        for (short var20 = 1; var20 < var13; ++var20) {
            short var22 = (short) ((int) Math.ceil((double) (this._$1(var2, var20) * 42.74F)));
            if (!var11.isColVisible(var20)) {
                if (!this._$11 && !this._$16) {
                    continue;
                }

                var1.setColumnHidden(var19, true);
            }

            var1.setColumnWidth(var19++, var22);
        }

        if (var9 != null && var9.getExcelGridDisabled()) {
            var1.setDisplayGridlines(false);
        }

        int var21 = var5;
        Context var83 = new Context();

        int var30;
        int var31;
        byte var94;
        int var101;
        short var102;
        for (int var23 = var79; var23 <= var80; ++var23) {
            short var25 = (short) ((int) Math.ceil((double) (this._$1(var2, var23) * 19.62F)));
            HSSFRow var26 = null;
            if (!var11.isRowVisible(var23)) {
                if (!this._$10 && !this._$16) {
                    continue;
                }

                (var26 = var1.createRow(var21)).setZeroHeight(true);
            }

            if (var26 == null) {
                var26 = var1.createRow(var21);
            }

            var26.setHeight(var25);
            var19 = var6;

            for (short var27 = 1; var27 < var13; ++var27) {
                if (var11.isColVisible(var27) || this._$11 || this._$16) {
                    var10 = new ExcelCellCopy(var2, var23, var27, this._$2, var3, var4, var7);
                    HSSFCell var28 = var26.createCell(var19);
                    HSSFCellStyle var29 = var10._$1(var8, this._$16);
                    var28.setCellStyle(var29);
                    if (var11.isMerged(var23, var27)) {
                        if (!this._$1(var11, var23, var27, this._$10, this._$11)) {
                            ++var19;
                            continue;
                        }

                        var30 = var11.getColSpan(var23, var27, this._$11);
                        var31 = var11.getRowSpan(var23, var27, this._$10);
                        if (var30 > 1 || var31 > 1) {
                            var1.addMergedRegion(new Region(var21, var19, var21 + var31 - 1, (short) (var19 + var30 - 1)));
                        }
                    }

                    INormalCell var89;
                    if ((var89 = var2.getCell(var23, var27)) != null && (var89.isVisible() || this._$16)) {
                        String var90;
                        if ((var90 = var89.getTip()) != null && var90.trim().length() > 0 && !var9.getNoExporttips()) {
                            if (var18 == null) {
                                var18 = var1.createDrawingPatriarch();
                            }

                            HSSFComment var32 = null;
                            (var32 = var18.createComment(new HSSFClientAnchor(0, 0, 1023, 255, (short) var13, var12 + 1, (short) (var13 + 5), var12 + 6))).setString(new HSSFRichTextString(var90));
                            var32.setAuthor("report4");
                            var28.setCellComment(var32);
                        }

                        String var91;
                        if ((var91 = var89.getHyperlink()) != null && var91.trim().length() > 0 && (var9 == null || var9.isExportURL(var91))) {
                            HSSFHyperlink var33;
                            (var33 = new HSSFHyperlink(1)).setAddress(var91);
                            var28.setHyperlink(var33);
                        }

                        var94 = var89.getCellType();
                        boolean var35 = var89.getDiagonalStyle() != 80;
                        String var36 = null;
                        ExtRow var37;
                        if (this._$16 || var89.getToExcelMode() == 35) {
                            if (var89 instanceof ExtNormalCell) {
                                ((ExtCellSet) var2).setCurrent((ExtNormalCell) var89);
                                var36 = ((ExtNormalCell) var89).calcExcelExp(var83);
                            } else if (var89 instanceof ExtNCell) {
                                var37 = ((ExtNCell) var89).getRow();
                                ((RowReport) var2).setCurrent(var37, ((ExtNCell) var89).getSource());
                                var36 = var37.calcExcelExp(var83, var27);
                            }
                        }

                        if (var36 != null) {
                            var28.setCellType(2);
                            var28.setCellFormula(var36);
                        } else {
                            String var112;
                            if ((var94 == -64 || var94 == -59) && !var35) {
                                var37 = null;
                                IByteMap var111 = var89.getExpMap();
                                String var105;
                                if (var89 instanceof NormalCell && var111 != null && var111.containsKey((byte) 40)) {
                                    var105 = (String) var111.get((byte) 40);
                                } else {
                                    var105 = var10._$1();
                                }

                                var105 = var10.getSpaces() + var105;
                                Object var116 = var89.getValue();
                                Object var118;
                                if ((var118 = var89.getInputValue()) != null) {
                                    var116 = var118;
                                }

                                String var10000;
                                double var120;
                                char var129;
                                if (var89.getToExcelMode() == 36) {
                                    if (var105.trim().startsWith("0") && var105.indexOf(".") < 0 && var105.trim().length() > 1) {
                                        var28.setCellValue(var105);
                                        var28.setCellType(1);
                                    } else if (var116 instanceof String) {
                                        var28.setCellValue(var105);
                                        var28.setCellType(1);
                                    } else {
                                        try {
                                            if (var116 instanceof Date && ((Date) var116).getTime() >= 0L) {
                                                var112 = var89.getFormat();
                                                SimpleDateFormat var123 = null;
                                                if (var112 != null && var112.trim().length() > 0) {
                                                    var123 = new SimpleDateFormat(var112);
                                                }

                                                if (var123 != null) {
                                                    Date var134 = var123.parse(var105);
                                                    var28.setCellType(0);
                                                    var28.setCellValue(var134);
                                                } else {
                                                    var28.setCellValue(var105);
                                                }
                                            } else {
                                                var120 = Double.parseDouble(var105);
                                                var10000 = var105.trim();
                                                var129 = var10000.charAt(var10000.length() - 1);
                                                if (var105.toLowerCase().indexOf("e") >= 0) {
                                                    throw new Exception();
                                                }

                                                if (var120 <= 9.9999999999999E13D && var129 != 'f' && var129 != 'F' && var129 != 'd' && var129 != 'D') {
                                                    var28.setCellType(0);
                                                    var28.setCellValue(var120);
                                                } else {
                                                    var28.setCellValue(var105);
                                                }
                                            }
                                        } catch (Throwable var78) {
                                            if (var105 != null && var105.length() > 0) {
                                                var28.setCellValue(var105);
                                            }
                                        }
                                    }
                                } else {
                                    double var125;
                                    if (var89.getToExcelMode() == 33) {
                                        if (var116 instanceof Date && ((Date) var116).getTime() >= 0L) {
                                            var28.setCellType(0);
                                            var28.setCellValue((Date) var116);
                                        } else if (var116 instanceof String) {
                                            var28.setCellValue(var116.toString());
                                            var28.setCellType(1);
                                        } else {
                                            try {
                                                var125 = Double.parseDouble(var112 = var116.toString());
                                                var10000 = var112.trim();
                                                char var142 = var10000.charAt(var10000.length() - 1);
                                                if (var112.toLowerCase().indexOf("e") >= 0) {
                                                    throw new Exception();
                                                }

                                                if (var125 <= 9.9999999999999E13D && var142 != 'f' && var142 != 'F' && var142 != 'd' && var142 != 'D') {
                                                    var28.setCellType(0);
                                                    var28.setCellValue(var125);
                                                } else {
                                                    var28.setCellValue(var112);
                                                }
                                            } catch (Throwable var77) {
                                                var28.setCellValue(var116.toString());
                                            }
                                        }
                                    } else if (var116 instanceof Date && ((Date) var116).getTime() >= 0L) {
                                        var112 = var89.getFormat();
                                        var28.setCellType(0);
                                        var28.setCellValue((Date) var116);
                                    } else if (var105.trim().startsWith("0") && var105.indexOf(".") < 0 && var105.trim().length() > 1) {
                                        var28.setCellValue(var105);
                                        var28.setCellType(1);
                                    } else if (var116 instanceof String) {
                                        var28.setCellValue(var105);
                                        var28.setCellType(1);
                                        boolean var150;
                                        if (var105 != null && !var105.equals(var116)) {
                                            var150 = true;
                                        } else {
                                            var150 = false;
                                        }
                                    } else {
                                        try {
                                            var120 = Double.parseDouble(var105);
                                            var10000 = var105.trim();
                                            var129 = var10000.charAt(var10000.length() - 1);
                                            if (var105.toLowerCase().indexOf("e") >= 0) {
                                                throw new Exception();
                                            }

                                            if (var120 <= 9.9999999999999E13D && var129 != 'f' && var129 != 'F' && var129 != 'd' && var129 != 'D') {
                                                if (!var105.equals(var10._$2())) {
                                                    throw new Exception();
                                                }

                                                var28.setCellType(0);
                                                var28.setCellValue(var120);
                                            } else {
                                                var28.setCellValue(var105);
                                            }
                                        } catch (Throwable var76) {
                                            if ((var112 = var10._$2().trim()).equals(var105)) {
                                                if (var105 != null && var105.length() != 0) {
                                                    var28.setCellValue(var105);
                                                } else {
                                                    var28.setCellType(3);
                                                }
                                            } else {
                                                try {
                                                    var125 = Double.parseDouble(var112);
                                                    String var131;
                                                    if ((var131 = var89.getFormat()) != null && var131.trim().length() > 0) {
                                                        var28.setCellType(0);
                                                        var28.setCellValue(var125);
                                                    } else {
                                                        var28.setCellValue(var105);
                                                    }
                                                } catch (Throwable var75) {
                                                    var28.setCellValue(var105);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                String var38;
                                int var43;
                                int var44;
                                int var45;
                                int var46;
                                int var48;
                                int var49;
                                int var52;
                                Object var98;
                                if (var94 == -56) {
                                    if (!((var98 = var89.getValue()) instanceof String)) {
                                        continue;
                                    }

                                    var38 = (String) var98;
                                    ArrayList var39 = RichTextUtil.getRichTextLineList(var2, var89, var38);
                                    ArrayList var40 = new ArrayList();
                                    ArrayList var41 = new ArrayList();
                                    String var42 = "";
                                    var43 = 0;
                                    var44 = 0;
                                    var45 = var39 == null ? 0 : var39.size();

                                    for (var46 = 0; var46 < var45; ++var46) {
                                        RichTextLine var47;
                                        var48 = (var47 = (RichTextLine) var39.get(var46)).count();

                                        for (var49 = 0; var49 < var48; ++var49) {
                                            RichTextElement var50;
                                            String var51;
                                            if ((var50 = var47.getElement(var49)) != null && (var51 = var50.text) != null && var51.length() >= 1) {
                                                var52 = var51.length();
                                                var44 += var52;
                                                HSSFFont var53 = this._$1(var50.fontName, var50.fontSize, var50.fontColor, var50.isBold, var50.isItalic, var50.isUnderline, var50.scriptType, var8, var3);
                                                var42 = var42 + var51;
                                                int[] var54 = new int[]{var43, var44};
                                                var43 += var52;
                                                var40.add(var54);
                                                var41.add(var53);
                                            }
                                        }

                                        if (var46 < var45 - 1) {
                                            var42 = var42 + '\n';
                                            ++var43;
                                            ++var44;
                                        }
                                    }

                                    if (var42 != null && var42.length() > 0) {
                                        HSSFRichTextString var130 = new HSSFRichTextString(var42);
                                        var48 = 0;

                                        for (var49 = var40.size(); var48 < var49; ++var48) {
                                            int[] var136 = (int[]) var40.get(var48);
                                            HSSFFont var139 = (HSSFFont) var41.get(var48);
                                            var130.applyFont(var136[0], var136[1], var139);
                                        }

                                        var28.setCellValue(var130);
                                        var28.setCellType(1);
                                    }
                                } else {
                                    var98 = null;
                                    int var100;
                                    short var104;
                                    Image var124;
                                    if (var94 == -60) {
                                        Object var99 = var89.getValue();
                                        var101 = var11.getMergedHeight(var23, var27, false);
                                        var104 = (short) var11.getMergedWidth(var23, var27, false);
                                        if (var99 instanceof IReport) {
                                            var98 = this._$1((IReport) var99, var104, var101, var89);
                                        }
                                    } else if (var94 == -58) {
                                        if (System.getProperty("runqianReport.excel.html") == null) {
                                            var100 = var11.getMergedHeight(var23, var27, false);
                                            var102 = (short) var11.getMergedWidth(var23, var27, false);
                                            IReport var106;
                                            INormalCell var109 = (var106 = var11.getReport()).getCell(var23, var27);
                                            var124 = ImgUtils.toImage(ControlUtils.getCellText(var106, var23, var27, false), var102, var100, 1, var11.getCellIndent(var23, var27, 1.0F), var109.getBackColor());
                                            var98 = null;

                                            try {
                                                var98 = ImageUtils.writeJPEG(ImageUtils.toBufferedImage(var124));
                                            } catch (Exception var74) {
                                                var74.printStackTrace();
                                            }
                                        } else {
                                            try {
                                                var38 = var10._$1();
                                                var112 = ((IHTMLExport) Class.forName(System.getProperty("runqianReport.excel.html")).newInstance()).toText(var38);
                                                var28.setCellValue(var112);
                                                var28.setCellType(1);
                                            } catch (IllegalAccessException var71) {
                                                throw new RuntimeException(var71.getMessage());
                                            } catch (InstantiationException var72) {
                                                throw new RuntimeException(var72.getMessage());
                                            } catch (ClassNotFoundException var73) {
                                                throw new RuntimeException(var73.getMessage());
                                            }
                                        }
                                    }

                                    int var117;
                                    if (var98 == null) {
                                        if (!var35) {
                                            var98 = var89.getValue();
                                        } else {
                                            var100 = var11.getColWidth(var27);
                                            var101 = var11.getRowHeight(var23);
                                            if (var89.isMerged()) {
                                                Area var107;
                                                var117 = (var107 = var89.getMergedArea()).getEndCol() - var107.getBeginCol() + 1;
                                                int var121 = var107.getEndRow() - var107.getBeginRow() + 1;

                                                for (var43 = 1; var43 < var117; ++var43) {
                                                    if (var11.isColVisible((short) (var27 + var43))) {
                                                        var100 += var11.getColWidth((short) (var27 + var43));
                                                    }
                                                }

                                                for (var44 = 1; var44 < var121; ++var44) {
                                                    if (var11.isRowVisible(var23 + var44)) {
                                                        var101 += var11.getRowHeight(var23 + var44);
                                                    }
                                                }
                                            }

                                            LeanLine var110 = new LeanLine(var11, var23, var27);

                                            try {
                                                var98 = var110.toBytes(var100, var101);
                                            } catch (Exception var70) {
                                                throw new ReportError(var70.getMessage());
                                            }
                                        }
                                    }

                                    if (var98 != null && (var98 instanceof ImageValue || var98 instanceof byte[] || var98 instanceof Image || var98 instanceof DMImageValue) && var98 != null) {
                                        byte[] var103 = new byte[1];
                                        if (var98 instanceof ImageValue) {
                                            var103 = ((ImageValue) var98).getValue();
                                        } else if (var98 instanceof DMImageValue) {
                                            var101 = var11.getMergedWidth(var23, var27, false);
                                            int var113 = var11.getMergedHeight(var23, var27, false);

                                            try {
                                                var103 = ((DMImageValue) var98).calcImageBytes((StringBuffer) null, var101, var113);
                                            } catch (Exception var69) {
                                                var69.printStackTrace();
                                            }
                                        } else if (var98 instanceof byte[]) {
                                            var103 = (byte[]) var98;
                                        } else if (var98 instanceof String) {
                                            try {
                                                new FileInputStream((String) var98);
                                            } catch (Exception var68) {
                                                var68.printStackTrace();
                                            }
                                        }

                                        if (var103 != null) {
                                            byte[] var108 = var103;
                                            if (var18 == null) {
                                                var18 = var1.createDrawingPatriarch();
                                            }

                                            var104 = (short) (var27 + var11.getColSpan(var23, var27, this._$11) - 2);
                                            var117 = var23 + var11.getRowSpan(var23, var27, this._$10) - 2;
                                            byte var122 = var89.getAdjustSizeMode();
                                            var124 = null;
                                            ImageIcon var128 = null;
                                            var45 = var11.getColWidth(var27);
                                            if ((var46 = (int) (1024.0D / (double) var45 + 0.5D)) >= 1024) {
                                                var46 = 1023;
                                            }

                                            int var132 = var11.getRowHeight(var23);
                                            if ((var48 = (int) (256.0D / (double) var132 + 0.5D)) >= 256) {
                                                var48 = 255;
                                            }

                                            HSSFClientAnchor var127;
                                            int var138;
                                            if (var122 == 50) {
                                                var127 = new HSSFClientAnchor(var46, var48, 1023, 255, var19, var21, (short) (var104 + var19 - var27 + 1), var117 + var21 - var23 + 1);
                                            } else {
                                                var49 = var11.getMergedWidth(var23, var27, false);
                                                var138 = var11.getMergedHeight(var23, var27, false);
                                                if (var128 == null) {
                                                    var128 = new ImageIcon(var103);
                                                }

                                                int var140 = var128.getIconWidth();
                                                var52 = var128.getIconHeight();
                                                int var56;
                                                int var57;
                                                int var58;
                                                if (var49 > var140 && var138 > var52) {
                                                    float var61;
                                                    if ((var61 = (float) (var49 - var140) / 2.0F) < 0.0F) {
                                                        var61 = 0.0F;
                                                    }

                                                    byte var62 = var89.getHAlign();
                                                    float var63 = var61;
                                                    short var60;
                                                    int var144;
                                                    int var145;
                                                    short var149;
                                                    if (var62 == -47) {
                                                        var149 = (short) (var27 - 1);

                                                        for (var45 = var11.getColWidth((short) (var149 + 1)); var63 > 0.0F && var149 <= var104; ++var149) {
                                                            if (!this._$11 || var11.isColVisible((short) (var149 + 1))) {
                                                                var45 = this._$1(var11, (short) (var149 + 1));
                                                                if (!(var63 >= (float) var45)) {
                                                                    break;
                                                                }

                                                                var63 -= (float) var45;
                                                            }
                                                        }

                                                        if ((var145 = (int) ((double) var63 * 1024.0D / (double) var45)) < var46) {
                                                            var145 = var46;
                                                        }

                                                        var60 = var104;

                                                        for (var45 = var11.getColWidth((short) (var104 + 1)); var61 > 0.0F && var60 >= var149; --var60) {
                                                            if (!this._$11 || var11.isColVisible((short) (var60 + 1))) {
                                                                var45 = this._$1(var11, (short) (var60 + 1));
                                                                if (!(var61 >= (float) var45)) {
                                                                    break;
                                                                }

                                                                var61 -= (float) var45;
                                                            }
                                                        }

                                                        var144 = (int) ((double) var61 * 1024.0D / (double) var45);
                                                    } else if (var62 == -48) {
                                                        var61 *= 2.0F;
                                                        var60 = var104;

                                                        for (var45 = var11.getColWidth((short) (var104 + 1)); var61 > 0.0F && var60 >= var27 - 1; --var60) {
                                                            if (!this._$11 || var11.isColVisible((short) (var60 + 1))) {
                                                                var45 = this._$1(var11, (short) (var60 + 1));
                                                                if (!(var61 >= (float) var45)) {
                                                                    break;
                                                                }

                                                                var61 -= (float) var45;
                                                            }
                                                        }

                                                        var144 = (int) ((double) var61 * 1024.0D / (double) var45);
                                                        var145 = var46;
                                                        var149 = (short) (var27 - 1);
                                                    } else {
                                                        var63 = var61 * 2.0F;
                                                        var149 = (short) (var27 - 1);

                                                        for (var45 = var11.getColWidth((short) (var149 + 1)); var63 > 0.0F && var149 <= var104; ++var149) {
                                                            if (!this._$11 || var11.isColVisible((short) (var149 + 1))) {
                                                                var45 = this._$1(var11, (short) (var149 + 1));
                                                                if (!(var63 >= (float) var45)) {
                                                                    break;
                                                                }

                                                                var63 -= (float) var45;
                                                            }
                                                        }

                                                        if ((var145 = (int) ((double) var63 * 1024.0D / (double) var45)) < var46) {
                                                            var145 = var46;
                                                        }

                                                        var144 = 0;
                                                        var60 = var104;
                                                    }

                                                    float var64;
                                                    if ((var64 = (float) (var138 - var52) / 2.0F) < 0.0F) {
                                                        var64 = 0.0F;
                                                    }

                                                    byte var65 = var89.getVAlign();
                                                    float var66 = var64;
                                                    int var146;
                                                    if (var65 == -31) {
                                                        var57 = var23 - 1;

                                                        for (var132 = var11.getRowHeight(var57 + 1); var66 > 0.0F && var57 <= var117; ++var57) {
                                                            if (!this._$10 || var11.isRowVisible(var57 + 1)) {
                                                                var132 = this._$1(var11, var57 + 1);
                                                                if (!(var66 >= (float) var132)) {
                                                                    break;
                                                                }

                                                                var66 -= (float) var132;
                                                            }
                                                        }

                                                        if ((var146 = (int) ((double) var66 * 256.0D / (double) var132)) < var48) {
                                                            var146 = var48;
                                                        }

                                                        var58 = var117;

                                                        for (var132 = var11.getRowHeight(var117 + 1); var64 > 0.0F && var58 >= var57; --var58) {
                                                            if (!this._$10 || var11.isRowVisible(var58 + 1)) {
                                                                var132 = this._$1(var11, var58 + 1);
                                                                if (!(var64 >= (float) var132)) {
                                                                    break;
                                                                }

                                                                var64 -= (float) var132;
                                                            }
                                                        }

                                                        var56 = (int) ((double) var64 * 256.0D / (double) var132);
                                                    } else if (var65 == -32) {
                                                        var64 *= 2.0F;
                                                        var57 = var23 - 1;
                                                        var58 = var117;

                                                        for (var132 = var11.getRowHeight(var117 + 1); var64 > 0.0F && var58 >= var57; --var58) {
                                                            if (!this._$10 || var11.isRowVisible(var58 + 1)) {
                                                                var132 = this._$1(var11, var58 + 1);
                                                                if (!(var64 >= (float) var132)) {
                                                                    break;
                                                                }

                                                                var64 -= (float) var132;
                                                            }
                                                        }

                                                        var56 = (int) ((double) var64 * 256.0D / (double) var132);
                                                        var146 = var48;
                                                        var57 = var23 - 1;
                                                    } else {
                                                        var66 = var64 * 2.0F;
                                                        var57 = var23 - 1;

                                                        for (var132 = var11.getRowHeight(var57 + 1); var66 > 0.0F && var57 <= var117; ++var57) {
                                                            if (!this._$10 || var11.isRowVisible(var57 + 1)) {
                                                                var132 = this._$1(var11, var57 + 1);
                                                                if (!(var66 >= (float) var132)) {
                                                                    break;
                                                                }

                                                                var66 -= (float) var132;
                                                            }
                                                        }

                                                        if ((var146 = (int) ((double) var66 * 256.0D / (double) var132)) < var48) {
                                                            var146 = var48;
                                                        }

                                                        var56 = 0;
                                                        var58 = var117;
                                                    }

                                                    var149 = (short) (var149 + var19 - var27 + 1);
                                                    var60 = (short) (var60 + var19 - var27 + 1);
                                                    var57 = var57 + var21 - var23 + 1;
                                                    var58 = var58 + var21 - var23 + 1;
                                                    var127 = new HSSFClientAnchor(var145, var146, 1023 - var144, 255 - var56, var149, var57, var60, var58);
                                                } else {
                                                    float var55;
                                                    int var59;
                                                    float var141;
                                                    byte var143;
                                                    if (var49 * var52 >= var138 * var140) {
                                                        if ((var141 = (float) (var49 - var138 * var140 / var52) / 2.0F) < 0.0F) {
                                                            var141 = 0.0F;
                                                        }

                                                        var143 = var89.getHAlign();
                                                        var55 = var141;
                                                        short var147;
                                                        if (var143 == -47) {
                                                            var147 = (short) (var27 - 1);

                                                            for (var45 = var11.getColWidth((short) (var147 + 1)); var55 > 0.0F && var147 <= var104; ++var147) {
                                                                if (!this._$11 || var11.isColVisible((short) (var147 + 1))) {
                                                                    var45 = this._$1(var11, (short) (var147 + 1));
                                                                    if (!(var55 >= (float) var45)) {
                                                                        break;
                                                                    }

                                                                    var55 -= (float) var45;
                                                                }
                                                            }

                                                            if ((var57 = (int) ((double) var55 * 1024.0D / (double) var45)) < var46) {
                                                                var57 = var46;
                                                            }

                                                            short var148 = var104;

                                                            for (var45 = var11.getColWidth((short) (var104 + 1)); var141 > 0.0F && var148 >= var147; --var148) {
                                                                if (!this._$11 || var11.isColVisible((short) (var148 + 1))) {
                                                                    var45 = this._$1(var11, (short) (var148 + 1));
                                                                    if (!(var141 >= (float) var45)) {
                                                                        break;
                                                                    }

                                                                    var141 -= (float) var45;
                                                                }
                                                            }

                                                            var59 = (int) ((double) var141 * 1024.0D / (double) var45);
                                                            var147 = (short) (var147 + var19 - var27 + 1);
                                                            var148 = (short) (var148 + var19 - var27 + 1);
                                                            var127 = new HSSFClientAnchor(var57, var48, 1023 - var59, 255, var147, var21, var148, var117 + var21 - var23 + 1);
                                                        } else if (var143 == -48) {
                                                            var141 *= 2.0F;
                                                            var147 = var104;

                                                            for (var45 = var11.getColWidth((short) (var104 + 1)); var141 > 0.0F && var147 >= var27 - 1; --var147) {
                                                                if (!this._$11 || var11.isColVisible((short) (var147 + 1))) {
                                                                    var45 = this._$1(var11, (short) (var147 + 1));
                                                                    if (!(var141 >= (float) var45)) {
                                                                        break;
                                                                    }

                                                                    var141 -= (float) var45;
                                                                }
                                                            }

                                                            var57 = (int) ((double) var141 * 1024.0D / (double) var45);
                                                            var147 = (short) (var147 + var19 - var27 + 1);
                                                            var127 = new HSSFClientAnchor(var46, var48, 1023 - var57, 255, var19, var21, var147, var117 + var21 - var23 + 1);
                                                        } else {
                                                            var55 = var141 * 2.0F;
                                                            var147 = (short) (var27 - 1);

                                                            for (var45 = var11.getColWidth((short) (var147 + 1)); var55 > 0.0F && var147 <= var104; ++var147) {
                                                                if (!this._$11 || var11.isColVisible((short) (var147 + 1))) {
                                                                    var45 = this._$1(var11, (short) (var147 + 1));
                                                                    if (!(var55 >= (float) var45)) {
                                                                        break;
                                                                    }

                                                                    var55 -= (float) var45;
                                                                }
                                                            }

                                                            if ((var57 = (int) ((double) var55 * 1024.0D / (double) var45)) < var46) {
                                                                var57 = var46;
                                                            }

                                                            var147 = (short) (var147 + var19 - var27 + 1);
                                                            var127 = new HSSFClientAnchor(var57, var48, 1023, 255, var147, var21, (short) (var104 + var19 - var27 + 1), var117 + var21 - var23 + 1);
                                                        }
                                                    } else {
                                                        if ((var141 = (float) (var138 - var49 * var52 / var140) / 2.0F) < 0.0F) {
                                                            var141 = 0.0F;
                                                        }

                                                        var143 = var89.getVAlign();
                                                        var55 = var141;
                                                        if (var143 == -31) {
                                                            var56 = var23 - 1;

                                                            for (var132 = var11.getRowHeight(var56 + 1); var55 > 0.0F && var56 <= var117; ++var56) {
                                                                if (!this._$10 || var11.isRowVisible(var56 + 1)) {
                                                                    var132 = this._$1(var11, var56 + 1);
                                                                    if (!(var55 >= (float) var132)) {
                                                                        break;
                                                                    }

                                                                    var55 -= (float) var132;
                                                                }
                                                            }

                                                            if ((var57 = (int) ((double) var55 * 256.0D / (double) var132)) < var48) {
                                                                var57 = var48;
                                                            }

                                                            var58 = var117;

                                                            for (var132 = var11.getRowHeight(var117 + 1); var141 > 0.0F && var58 >= var56; --var58) {
                                                                if (!this._$10 || var11.isRowVisible(var58 + 1)) {
                                                                    var132 = this._$1(var11, var58 + 1);
                                                                    if (!(var141 >= (float) var132)) {
                                                                        break;
                                                                    }

                                                                    var141 -= (float) var132;
                                                                }
                                                            }

                                                            var59 = (int) ((double) var141 * 256.0D / (double) var132);
                                                            var56 = var56 + var21 - var23 + 1;
                                                            var58 = var58 + var21 - var23 + 1;
                                                            var127 = new HSSFClientAnchor(var46, var57, 1023, 255 - var59, var19, var56, (short) (var104 + var19 - var27 + 1), var58);
                                                        } else if (var143 == -32) {
                                                            var141 *= 2.0F;
                                                            var56 = var23 - 1;
                                                            var57 = var117;

                                                            for (var132 = var11.getRowHeight(var117 + 1); var141 > 0.0F && var57 >= var56; --var57) {
                                                                if (!this._$10 || var11.isRowVisible(var57 + 1)) {
                                                                    var132 = this._$1(var11, var57 + 1);
                                                                    if (!(var141 >= (float) var132)) {
                                                                        break;
                                                                    }

                                                                    var141 -= (float) var132;
                                                                }
                                                            }

                                                            var58 = (int) ((double) var141 * 256.0D / (double) var132);
                                                            var56 = var56 + var21 - var23 + 1;
                                                            var57 = var57 + var21 - var23 + 1;
                                                            var127 = new HSSFClientAnchor(var46, var48, 1023, 255 - var58, var19, var23 - var79, (short) (var104 + var19 - var27 + 1), var57);
                                                        } else {
                                                            var55 = var141 * 2.0F;
                                                            var56 = var23 - 1;

                                                            for (var132 = var11.getRowHeight(var56 + 1); var55 > 0.0F && var56 <= var117; ++var56) {
                                                                if (!this._$10 || var11.isRowVisible(var56 + 1)) {
                                                                    var132 = this._$1(var11, var56 + 1);
                                                                    if (!(var55 >= (float) var132)) {
                                                                        break;
                                                                    }

                                                                    var55 -= (float) var132;
                                                                }
                                                            }

                                                            if ((var57 = (int) ((double) var55 * 256.0D / (double) var132)) < var48) {
                                                                var57 = var48;
                                                            }

                                                            var56 = var56 + var21 - var23 + 1;
                                                            var127 = new HSSFClientAnchor(var46, var57, 1023, 255, var19, var56, (short) (var104 + var19 - var27 + 1), var117 + var21 - var23 + 1);
                                                        }
                                                    }
                                                }
                                            }

                                            byte var137 = ImgUtils.getImageType(var103);
                                            var138 = 0;
                                            if (var137 == 1) {
                                                var138 = this._$2.addPicture(var103, 5);
                                            } else if (var137 == 2) {
                                                var128 = new ImageIcon(var103);

                                                try {
                                                    var108 = ImageUtils.writePNG(ImageUtils.toBufferedImage(var128.getImage()));
                                                } catch (Exception var67) {
                                                    var67.printStackTrace();
                                                }

                                                var138 = this._$2.addPicture(var108, 6);
                                            } else if (var137 == 3) {
                                                if (var35) {
                                                    var138 = this._$2.addPicture(var103, 6);
                                                } else if (var94 != -61) {
                                                    var138 = this._$2.addPicture(var103, 6);
                                                } else {
                                                    var138 = this._$2.addPicture(var103, 6);
                                                }
                                            }

                                            var18.createPicture(var127, var138);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    ++var19;
                }
            }

            if (var21 == -1) {
                break;
            }

            ++var21;
        }

        boolean var24 = false;
        boolean var84 = false;
        int var85 = 0;
        int var86 = 0;
        int var87 = 0;
        int var88 = 0;
        var30 = 0;

        for (var31 = 1; var31 <= var12; ++var31) {
            IRowCell var92;
            if ((var94 = (var92 = var2.getRowCell(var31)).getRowType()) == -95) {
                var24 = true;
            }

            if (var94 == -93) {
                var30 = var31;
            }

            if (var94 == -90 || var94 == -91 || var94 == -94 || var94 == -89 || var94 == -92) {
                if (var24) {
                    var85 = var31 - 1;
                }
                break;
            }

            if (!this._$10 && !this._$16 && !var92.getRowVisible()) {
                ++var87;
            }
        }

        if (var85 > 0) {
            var85 -= var30;
        }

        for (int var93 = 1; var93 < var13; ++var93) {
            byte var34;
            IColCell var95;
            if ((var34 = (var95 = var2.getColCell((short) var93)).getColType()) == -80) {
                var84 = true;
            }

            if (var34 == -79 || var34 == -78) {
                if (var84) {
                    var86 = var93 - 1;
                }
                break;
            }

            if (!this._$11 && !this._$16 && !var95.getColVisible()) {
                ++var88;
            }
        }

        if (var84 || var24) {
            var1.createFreezePane(var86 - var88, var85 - var87);
        }

        if (this._$13 < 0) {
            var1.setZoom(3, 4);
        } else {
            var1.setZoom(this._$13, 100);
        }

        double var96 = 1.0D;
        byte var97 = var2.getPrintSetup().getZoomMode();
        ReportParser var114 = new ReportParser(var2);
        PageFormat var115 = var2.getPrintSetup().getPageFormat();
        if (var97 == 2) {
            var101 = var114.getReportWidth() + 2;
            var96 = var115.getImageableWidth() / (double) var101;
        } else if (var97 == 3) {
            var101 = var114.getReportHeight() + 2;
            var96 = var115.getImageableHeight() / (double) var101;
        }

        var102 = (short) ((int) (var96 * 100.0D));
        HSSFPrintSetup var119;
        (var119 = var1.getPrintSetup()).setScale(var102);
        var119.setHResolution((short) 600);
        short var126;
        if ((var126 = this._$14.getPaper()) == 256) {
            var126 = 9;
            PrintSetup var135;
            if ((var135 = var2.getPrintSetup()) != null) {
                var126 = var135.getPaper();
            }
        }

        var119.setPaperSize(var126);
        if (this._$14.getOrientation() == 0) {
            var119.setLandscape(true);
        } else {
            var119.setLandscape(false);
        }

        this._$1(var1, (short) 3, (double) this._$14.getBottomMargin());
        this._$1(var1, (short) 0, (double) this._$14.getLeftMargin());
        this._$1(var1, (short) 1, (double) this._$14.getRightMargin());
        this._$1(var1, (short) 2, (double) this._$14.getTopMargin());
        int[] var133;
        (var133 = new int[2])[0] = var21 - 1;
        var133[1] = var19 - 1;
        return var133;
    }

    private void _$1(HSSFSheet var1, short var2, double var3) {
        double var5 = var3 / 25.4D;
        var1.setMargin(var2, var5);
    }

    private void _$1(HSSFWorkbook var1, int var2, IReport var3, boolean var4, boolean var5, boolean var6) {
        ReportParser var7 = new ReportParser(var3);
        PrintSetup var8;
        boolean var9 = (var8 = var3.getPrintSetup()).getTitleYMode() == 0;
        boolean var10 = var8.getRowTableHeaderAndFooterMode() == 0;
        boolean var11 = var8.getColTableHeaderAndFooterMode() == 0;
        int var12 = -1;
        int var13 = -1;
        int var14 = -1;
        int var15 = -1;
        Area var16;
        if ((var16 = var7.getLeftHeader()) != null && var11) {
            var13 = var16.getBeginCol() - 1;
            var15 = var16.getEndCol() - 1;
        }

        Area var17 = var7.getTitleField();
        Area var18 = var7.getTopHeader();
        if (var17 != null && var18 != null) {
            if (var9) {
                var12 = 0;
                if (var10) {
                    var14 = var18.getEndRow() - var17.getBeginRow();
                } else {
                    var14 = var17.getEndRow() - var17.getBeginRow();
                }
            } else if (var10) {
                var12 = var17.getEndRow() - var17.getBeginRow() + 1;
                var14 = var18.getEndRow() - var17.getBeginRow();
            }
        } else if (var17 == null && var18 != null && var10) {
            var12 = 0;
            var14 = var18.getEndRow() - var18.getBeginRow();
        } else if (var17 != null && var18 == null && var9) {
            var12 = 0;
            var14 = var17.getEndRow() - var17.getBeginRow();
        }

        int var19;
        int var20;
        if (!var6 && !var4) {
            var19 = var12;
            var20 = var14;

            int var21;
            for (var21 = 0; var21 < var19; ++var21) {
                if (!var7.isRowVisible(var21 + 1)) {
                    --var12;
                    --var14;
                }
            }

            for (; var21 <= var20; ++var21) {
                if (!var7.isRowVisible(var21 + 1)) {
                    --var14;
                }
            }
        }

        if (!var6 && !var5) {
            var19 = var13;
            var20 = var15;

            short var22;
            for (var22 = 0; var22 < var19; ++var22) {
                if (!var7.isColVisible((short) (var22 + 1))) {
                    --var13;
                    --var15;
                }
            }

            for (; var22 <= var20; ++var22) {
                if (!var7.isColVisible((short) (var22 + 1))) {
                    --var15;
                }
            }
        }

        var1.setRepeatingRowsAndColumns(var2, var13, var15, var12, var14);
    }

    private int _$1(IReport[] var1) {
        int var2 = 0;

        for (int var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3] != null) {
                ReportParser var4 = new ReportParser(var1[var3]);
                int var5 = 1;
                int var6 = var4.getRowCount();
                Area var7 = var4.getPageHeader();
                Area var8 = var4.getPageFooter();
                if (var7 != null) {
                    var5 = var7.getEndRow() + 1;
                }

                if (var8 != null) {
                    var6 = var8.getBeginRow() - 1;
                }

                int var9 = 0;

                for (int var10 = var5; var10 <= var6; ++var10) {
                    if (var4.isRowVisible(var10)) {
                        ++var9;
                    }
                }

                if (var9 > var2) {
                    var2 = var9;
                }
            }
        }

        return var2;
    }

    private short _$2(IReport[] var1) {
        short var2 = 0;

        for (int var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3] != null) {
                ReportParser var4 = new ReportParser(var1[var3]);
                short var5 = 0;

                for (short var6 = 1; var6 < var4.getColCount(); ++var6) {
                    if (var4.isColVisible(var6)) {
                        ++var5;
                    }
                }

                if (var5 > var2) {
                    var2 = var5;
                }
            }
        }

        return var2;
    }

    public HSSFWorkbook createWorkbook() {
        ExtCellSet var1;
        String var9;
        HSSFPalette var3 = this._$2.getCustomPalette();
        ExcelPalette var4 = new ExcelPalette(var3);
        try {
            this._$1(var4);
        } catch (Exception var8) {
            Logger.debug(var8.getMessage(), var8);
            throw new ReportError(var8.getMessage(), var8);
        }

        if (this._$5 != null && this._$5.size() > 0) {
            int var5 = 0;

            for (int var6 = this._$5.size(); var5 < var6; ++var5) {
                String var7 = (String) this._$5.get(var5);
                this._$2.setSheetName(var5, var7);
            }
        }

        return this._$2;


    }

    public void export(IReport var1) {
        int var2 = this._$4.size();
        MessageManager var3 = DataSetMessage.get(this._$17);
        this.export(var3.getMessage("info.page", Integer.toString(var2 + 1)), var1);
    }

    public void export(PageBuilder var1) {
        int var2 = this._$4.size();
        MessageManager var3 = DataSetMessage.get(this._$17);
        this.export(var3.getMessage("info.table", Integer.toString(var2 + 1)), var1);
    }

    public void export(String var1, IReport var2) {
        if (this._$16) {
            MessageManager var3;
            if (var2 instanceof ExtCellSet) {
                if (((ExtCellSet) var2).isLoaded()) {
                    var3 = SplitPageMessage.get(this._$17);
                    throw new ReportError(var3.getMessage("ExcelReport.loadRat"));
                }
            } else if (var2 instanceof RowReport && ((RowReport) var2).isLoaded()) {
                var3 = SplitPageMessage.get(this._$17);
                throw new ReportError(var3.getMessage("ExcelReport.loadRat"));
            }
        }

        int var8 = this._$4.size();
        ExportConfig var4;
        if ((var4 = var2.getExportConfig()) != null) {
            if (var8 == 0) {
                this._$9 = PwdUtils.decrypt(var4.getExcelFilePassword());
            }

            this._$11 = var4.getExcelHiddenColExported() ^ true;
            this._$10 = var4.getExcelHiddenRowExported() ^ true;
            this._$12 = var4.getFullyPaged();
        } else {
            this._$11 = false;
            this._$10 = false;
            this._$12 = false;
        }

        PrintSetup var5;
        if ((var5 = var2.getPrintSetup()) == null) {
            var5 = new PrintSetup();
        }

        PagerInfo var6 = new PagerInfo(var5);
        UnknownClass var7 = new UnknownClass(this, this._$10, this._$11, this._$16, this._$12);
        this._$4.add(var2);
        if (var1 != null && var1.length() > 31) {
            this._$5.add(var1.substring(0, 28) + "...");
            Logger.warn("Excel Sheet Name's length is out of range(32)!");
        } else {
            this._$5.add(var1);
        }

        this._$6.add(var7);
        this._$7.add(var6);
    }

    public void export(String var1, PageBuilder var2) {
        int var3 = var2.getPageCount();
        ExportConfig var4 = null;

        try {
            var4 = var2.getPage(0).getExportConfig();
        } catch (Exception var12) {
        }

        if (var4 != null) {
            this._$9 = PwdUtils.decrypt(var4.getExcelFilePassword());
            this._$11 = var4.getExcelHiddenColExported() ^ true;
            this._$10 = var4.getExcelHiddenRowExported() ^ true;
            this._$12 = false;
        } else {
            this._$11 = false;
            this._$10 = false;
            this._$12 = false;
        }

        UnknownClass var5 = new UnknownClass(this, this._$10, this._$11, false, this._$12);
        PagerInfo var6;
        if ((var6 = var2.getPagerInfo()) == null) {
            var6 = new PagerInfo(new PrintSetup());
        }

        String var7 = "";
        int var8 = var1 == null ? 0 : var1.length();

        for (int var9 = 1; var9 <= var3; ++var9) {
            try {
                this._$4.add(var2.getPage(var9));
                if (var3 > 1) {
                    var7 = String.valueOf(var9);
                    if (var8 + var7.length() > 31) {
                        this._$5.add(var1.substring(0, 28 - var7.length()) + "..." + var7);
                        Logger.warn("Excel Sheet Name's length is out of range(32)!");
                    } else {
                        this._$5.add(var1 + var9);
                    }
                } else if (var8 > 32) {
                    this._$5.add(var1.substring(0, 28) + "...");
                    Logger.warn("Excel Sheet Name's length is out of range(32)!");
                } else {
                    this._$5.add(var1);
                }

                this._$6.add(var5);
                this._$7.add(var6);
            } catch (Exception var11) {
                var11.printStackTrace();
            }
        }

    }

    public int getDispRatio() {
        return this._$13;
    }

    public String getFilePassword() {
        return this._$9;
    }

    public boolean getFomulaExported() {
        return this._$16;
    }

    public static byte[] getImageByteArray(BufferedImage var0) {
        ByteArrayOutputStream var1 = null;

        try {
            Iterator var2;
            ImageWriter var3 = (var2 = ImageIO.getImageWritersByFormatName("jpeg")).hasNext() ? (ImageWriter) var2.next() : null;
            ImageOutputStream var4 = ImageIO.createImageOutputStream(var1 = new ByteArrayOutputStream());
            var3.setOutput(var4);
            var3.write(new IIOImage(var0, (List) null, (IIOMetadata) null));
            var3.dispose();
        } catch (Exception var5) {
            throw new ReportError(var5.getMessage(), var5);
        }

        return var1.toByteArray();
    }

    public void resetExport() {
        this._$16 = false;
        this._$10 = true;
        this._$11 = true;
        this._$9 = null;
    }

    public void saveTo(OutputStream var1) {
        HSSFPalette var4 = this._$2.getCustomPalette();
        ExcelPalette var5 = new ExcelPalette(var4);
        try {
            this._$1(var5);
        } catch (Exception var10) {
            var10.printStackTrace();
            throw new ReportError(var10.getMessage());
        }

        if (this._$5 != null && this._$5.size() > 0) {
            int var6 = 0;

            for (int var7 = this._$5.size(); var6 < var7; ++var6) {
                String var8 = (String) this._$5.get(var6);
                this._$2.setSheetName(var6, var8);
            }
        }

        try {
            this._$2.write(var1);
        } catch (Exception var9) {
            throw new ReportError(var9.getMessage());
        }


    }

    public void saveTo(String var1) {
        FileOutputStream var2 = null;

        try {
            var2 = new FileOutputStream(var1);
            this.saveTo((OutputStream) var2);
        } catch (IOException var11) {
            var11.printStackTrace();
            throw new ReportError(var11.getMessage());
        } finally {
            if (var2 != null) {
                try {
                    var2.close();
                } catch (IOException var10) {
                    var10.printStackTrace();
                    throw new ReportError(var10.getMessage());
                }
            }

        }

    }

    public void setDispRatio(int var1) {
        this._$13 = var1;
    }

    public void setFilePassword(String var1) {
        if (this._$4.size() < 1) {
            this._$9 = var1;
        }

    }

    public void setFomulaExported(boolean var1) {
        this._$16 = var1;
    }

    public void setLocale(Locale var1) {
        this._$17 = var1;
    }
}

