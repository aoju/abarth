package org.aoju.bus.office.excel;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.aoju.bus.core.convert.Convert;
import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.toolkit.CollKit;
import org.aoju.bus.core.toolkit.StringKit;
import org.aoju.bus.office.support.excel.ExcelKit;
import org.aoju.bus.office.support.excel.sax.Excel03SaxReader;
import org.aoju.bus.office.support.excel.sax.RowHandler;
import org.junit.Assert;
import org.junit.Test;

public class ExcelSaxReadTest {

    @Test
    public void excel07Test() {
        // 工具化快速读取
        ExcelKit.readBySax("test.xlsx", 0, createRowHandler());
    }

    @Test
    public void excel03Test() {
        Excel03SaxReader reader = new Excel03SaxReader(createRowHandler());
        reader.read("aaa.xls", 1);
        // Console.log("Sheet index: [{}], Sheet name: [{}]", reader.getSheetIndex(), reader.getSheetName());
        ExcelKit.readBySax("test.xls", 1, createRowHandler());
    }

    @Test
    @Ignore
    public void readBlankLineTest() {
        ExcelKit.readBySax("test.xlsx", 0, (sheetIndex, rowIndex, rowList) -> {
            if (StringKit.isAllEmpty(Convert.toStrArray(rowList))) {
                return;
            }
            Console.log(rowList);
        });
    }

    @Test
    public void readBySaxTest() {
        ExcelKit.readBySax("test.xlsx", 0, createRowHandler());
    }

    @Test
    @Ignore
    public void readBySaxTest2() {
        ExcelKit.readBySax("test.xlsx", 2, (sheetIndex, rowIndex, rowList) -> Console.log(rowList));
    }

    private RowHandler createRowHandler() {
        return (sheetIndex, rowIndex, rowlist) -> {
            if (5 != rowIndex && 6 != rowIndex) {
                // 测试样例中除第五行、第六行都为非空行
                Assert.assertTrue(CollKit.isNotEmpty(rowlist));
            }
        };
    }

}
