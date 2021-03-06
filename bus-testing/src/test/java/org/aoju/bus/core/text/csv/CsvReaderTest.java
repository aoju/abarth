package org.aoju.bus.core.text.csv;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.toolkit.FileKit;
import org.junit.Assert;
import org.junit.Test;

public class CsvReaderTest {

    @Test
    public void readTest() {
        CsvReader reader = new CsvReader();
        CsvData data = reader.read(FileKit.getReaders("test.csv", Charset.UTF_8));
        Assert.assertEquals("关注\"对象\"", data.getRow(0).get(2));
    }

}
