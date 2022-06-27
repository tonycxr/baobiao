//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.sungcor.baobiao.report.bean;

import com.runqian.base4.resources.EngineMessage;
import com.runqian.base4.resources.MessageManager;
import com.runqian.base4.util.Sentence;
import com.runqian.report4.control.ControlUtils;
import com.runqian.report4.model.NormalCell;
import com.runqian.report4.usermodel.INormalCell;
import com.runqian.report4.usermodel.IReport;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.runqian.report4.view.excel.ExcelColor;
import com.runqian.report4.view.excel.ExcelPalette;
import org.apache.poi2.hssf.usermodel.HSSFCellStyle;
import org.apache.poi2.hssf.usermodel.HSSFDataFormat;
import org.apache.poi2.hssf.usermodel.HSSFFont;
import org.apache.poi2.hssf.usermodel.HSSFWorkbook;

public class ExcelCellCopy {
    private IReport _$1;
    private int _$2;
    private short _$3;
    private HSSFWorkbook _$4;
    private ArrayList _$5;
    private ArrayList _$6;
    private Hashtable _$7;
    public static int COLOR_TRANSPARENT = 16777215;

    public ExcelCellCopy(IReport var1, int var2, short var3, HSSFWorkbook var4, ArrayList var5, ArrayList var6, Hashtable var7) {
        this._$1 = var1;
        this._$2 = var2;
        this._$3 = var3;
        this._$4 = var4;
        this._$6 = var5;
        this._$5 = var6;
        this._$7 = var7;
    }

    String _$1() {
        return ControlUtils.getCellText(this._$1, this._$2, this._$3, false);
    }

    private short _$1(byte var1, float var2) {
        if (var1 == 80) {
            return 0;
        } else if (var1 == 82) {
            return (short)((double)var2 > 1.0D ? 8 : 3);
        } else if (var1 == 81) {
            return (short)((double)var2 > 1.0D ? 10 : 9);
        } else if (var1 == 84) {
            return 6;
        } else if (var1 == 83) {
            if ((double)var2 < 0.75D) {
                return 1;
            } else if ((double)var2 <= 1.0D) {
                return 1;
            } else if ((double)var2 <= 1.5D) {
                return 2;
            } else {
                return (short)((double)var2 <= 2.0D ? 5 : 5);
            }
        } else {
            return (short)(var1 == 85 ? 7 : 1);
        }
    }

    private short _$1(int var1, ExcelPalette var2) {
        return ExcelColor.transColor(var1, var2);
    }

    private HSSFFont _$1(INormalCell var1, ExcelPalette var2) {
        String var3 = var1.getFontName();
        short var4 = var1.getFontSize();
        short var5 = this._$1(var1.getForeColor(), var2);
        int var6 = var1.isBold() ? 700 : 400;
        boolean var7 = var1.isItalic();
        int var8 = var1.isUnderline() ? 1 : 0;

        HSSFFont var9;
        for(int var10 = 0; var10 < this._$6.size(); ++var10) {
            if ((var9 = (HSSFFont)this._$6.get(var10)).getFontName().equalsIgnoreCase(var3) && var9.getFontHeightInPoints() == var4 && var9.getColor() == var5 && var9.getBoldweight() == var6 && var9.getItalic() == var7 && var9.getUnderline() == var8) {
                return var9;
            }
        }

        (var9 = this._$4.createFont()).setFontName(var3);
        var9.setFontHeightInPoints(var4);
        var9.setColor(var5);
        var9.setBoldweight((short)var6);
        var9.setItalic(var7);
        var9.setUnderline((byte)var8);
        var9.setCharSet((byte)-122);
        this._$6.add(var9);
        return var9;
    }

    HSSFCellStyle _$1(ExcelPalette var1, boolean var2) {
        byte var3 = 2;
        byte var4 = 1;
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        boolean var9 = false;
        boolean var10 = true;
        boolean var11 = true;
        boolean var12 = true;
        boolean var13 = true;
        boolean var14 = true;
        short var15 = 49;
        Object var16;
        if ((var16 = this._$1.getCell(this._$2, this._$3)) == null) {
            var16 = new NormalCell();
        }

        byte var17;
        if ((var17 = ((INormalCell)var16).getHAlign()) == -48) {
            var3 = 1;
        } else if (var17 == -46) {
            var3 = 3;
        } else if (var17 == -45) {
            var3 = 5;
        } else if (var17 == -44) {
            var3 = 7;
        }

        byte var18;
        if ((var18 = ((INormalCell)var16).getVAlign()) == -32) {
            var4 = 0;
        } else if (var18 == -30) {
            var4 = 2;
        }

        boolean var5 = ((INormalCell)var16).getTextWrap();
        short var28 = this._$1(((INormalCell)var16).getLBStyle(), ((INormalCell)var16).getLBWidth());
        short var32 = this._$1(((INormalCell)var16).getLBColor(), var1);
        short var30 = this._$1(((INormalCell)var16).getTBStyle(), ((INormalCell)var16).getTBWidth());
        short var34 = this._$1(((INormalCell)var16).getTBColor(), var1);
        short var29 = this._$1(((INormalCell)var16).getRBStyle(), ((INormalCell)var16).getRBWidth());
        short var33 = this._$1(((INormalCell)var16).getRBColor(), var1);
        short var31 = this._$1(((INormalCell)var16).getBBStyle(), ((INormalCell)var16).getBBWidth());
        short var35 = this._$1(((INormalCell)var16).getBBColor(), var1);
        short var36;
        if (((INormalCell)var16).getBackColor() == COLOR_TRANSPARENT) {
            var36 = 64;
        } else {
            var36 = this._$1(((INormalCell)var16).getBackColor(), var1);
        }

        String var19 = this._$1().trim();
        String var20;
        if ((var20 = ((INormalCell)var16).getFormat()) != null && var20.trim().length() > 0 && var19.length() > 0 && var19.length() < 80) {
            var15 = this._$1(var20.trim());
        }

        String var21;
        if (((INormalCell)var16).getValue() instanceof Date && ((Date)((INormalCell)var16).getValue()).getTime() >= 0L) {
            var21 = ((INormalCell)var16).getFormat();
            HSSFDataFormat var39 = this._$4.createDataFormat();
            if (var21 != null && var21.trim().length() > 0) {
                var15 = var39.getFormat(this._$2(var21));
            } else if (((INormalCell)var16).getValue() instanceof Timestamp) {
                var15 = var39.getFormat("yyyy-mm-dd hh:mm:ss.000");
            } else if (((INormalCell)var16).getValue() instanceof Time) {
                var15 = var39.getFormat("hh:mm:ss");
            } else {
                var15 = var39.getFormat("yyyy-mm-dd");
            }
        } else if (!(((INormalCell)var16).getValue() instanceof Number) || var19.startsWith("0") && var19.indexOf(".") < 0 && var19.trim().length() > 1) {
            if (var19.length() == 0 || var19.length() > 80) {
                var15 = 0;
            }
        } else {
            try {
                double var37 = Double.parseDouble(var19);
                if (var19.toLowerCase().indexOf("e") >= 0) {
                    throw new Exception();
                }

                if (var37 <= 9.9999999999999E13D) {
                    String var23;
                    if ((var23 = ((INormalCell)var16).getFormat()) != null && var23.trim().length() > 0) {
                        var15 = this._$1(var23.trim());
                    } else if (var37 == 0.0D) {
                        var23 = "0";
                        var15 = this._$1(var23);
                    } else {
                        var23 = "#";
                        int var41;
                        if ((var41 = var19.indexOf(".")) > 0) {
                            if (var41 == 1 && var19.charAt(0) == '0') {
                                var23 = "0";
                            }

                            var23 = var23 + ".";
                            var19 = var19.trim();

                            for(int var25 = var41; var25 < var19.length() - 1; ++var25) {
                                var23 = var23 + "0";
                            }
                        }

                        var15 = this._$1(var23);
                    }
                }
            } catch (Throwable var27) {
                if (!(var21 = this._$2().trim()).equals(var19)) {
                    try {
                        double var22 = Double.parseDouble(var21);
                        String var24;
                        if ((var24 = ((INormalCell)var16).getFormat()) != null && var24.trim().length() > 0) {
                            var15 = this._$1(var24.trim());
                        }
                    } catch (Throwable var26) {
                        if (var19.length() == 0 || var19.length() > 80) {
                            var15 = 0;
                        }
                    }
                } else if (var19.length() == 0 || var19.length() > 80) {
                    var15 = 0;
                }
            }
        }

        HSSFFont var40 = this._$1((INormalCell)var16, var1);

        HSSFCellStyle var38;
        for(int var42 = 0; var42 < this._$5.size(); ++var42) {
            if ((var38 = (HSSFCellStyle)this._$5.get(var42)).getAlignment() == var3 && var38.getVerticalAlignment() == var4 && var38.getWrapText() == var5 && var38.getBorderLeft() == var28 && var38.getBorderRight() == var29 && var38.getBorderTop() == var30 && var38.getBorderBottom() == var31 && var38.getLeftBorderColor() == var32 && var38.getRightBorderColor() == var33 && var38.getTopBorderColor() == var34 && var38.getBottomBorderColor() == var35 && var38.getFillForegroundColor() == var36 && var38.getDataFormat() == var15 && var38.getFontIndex() == var40.getIndex()) {
                return var38;
            }
        }

        (var38 = this._$4.createCellStyle()).setAlignment(var3);
        var38.setVerticalAlignment(var4);
        var38.setWrapText(var5);
        var38.setBorderLeft(var28);
        var38.setBorderRight(var29);
        var38.setBorderTop(var30);
        var38.setBorderBottom(var31);
        var38.setLeftBorderColor(var32);
        var38.setRightBorderColor(var33);
        var38.setTopBorderColor(var34);
        var38.setBottomBorderColor(var35);
        if (var36 != 64) {
            var38.setFillPattern((short)1);
        }

        var38.setFillForegroundColor(var36);
        var38.setDataFormat(var15);
        if (((INormalCell)var16).getAdjustSizeMode() != 51 && ((INormalCell)var16).getAdjustSizeMode() != 48) {
            if (var5) {
                var38.setShrinkToFit(true);
            } else {
                var38.setShrinkToFit(false);
            }
        } else {
            var38.setShrinkToFit(true);
        }

        var38.setFont(var40);
        this._$5.add(var38);
        return var38;
    }

    private short _$1(String var1) {
        if (this._$7.containsKey(var1)) {
            return Short.parseShort((String)this._$7.get(var1));
        } else {
            short var2 = this._$4.createDataFormat().getFormat(var1);
            this._$7.put(var1, String.valueOf(var2));
            return var2;
        }
    }

    String _$2() {
        INormalCell var1;
        if ((var1 = this._$1.getCell(this._$2, this._$3)) == null) {
            return "";
        } else {
            Object var2;
            if ((var2 = var1.getValue()) == null) {
                return "";
            } else {
                String var3 = "";
                if (var2 instanceof byte[]) {
                    try {
                        var3 = new String((byte[])var2, "GBK");
                    } catch (Exception var4) {
                    }
                } else {
                    var3 = var2.toString();
                }

                return var3;
            }
        }
    }

    private String _$2(String var1) {
        String var2 = "[DBNum1]";
        MessageManager var3 = EngineMessage.get();
        if (var1.indexOf(97) > 0) {
            while(true) {
                if (var1.indexOf("aa") <= 0) {
                    var1 = Sentence.replace(var1, 0, "a", var3.getMessage("Excel.AMPM"), 0);
                    break;
                }

                var1 = Sentence.replace(var1, 0, "aa", "a", 0);
            }
        }

        if (var1.indexOf(69) > 0) {
            while(var1.indexOf("EE") > 0) {
                var1 = Sentence.replace(var1, 0, "EE", "E", 0);
            }

            var1 = Sentence.replace(var1, 0, "E", "aaaa", 0);
        }

        return var1;
    }

    protected static Font getFont(INormalCell var0) {
        int var1 = 0;
        if (var0.isBold()) {
            ++var1;
        }

        if (var0.isItalic()) {
            var1 += 2;
        }

        return new Font(var0.getFontName(), var1, var0.getFontSize());
    }

    public String getSpaces() {
        Object var1;
        if ((var1 = this._$1.getCell(this._$2, this._$3)) == null) {
            var1 = new NormalCell();
        }

        float var2 = ((INormalCell)var1).getIndent();
        int var4 = (new BufferedImage(10, 10, 2)).getGraphics().getFontMetrics(getFont((INormalCell)var1)).stringWidth(" ");
        int var5 = (int)((double)var2 / 25.4D * 72.0D / (double)var4);
        String var6 = "";

        for(int var7 = 0; var7 < var5; ++var7) {
            var6 = var6 + " ";
        }

        return var6;
    }
}
