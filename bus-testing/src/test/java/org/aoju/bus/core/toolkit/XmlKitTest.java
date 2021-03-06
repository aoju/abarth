package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.map.MapBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link XmlKit} 工具类
 */
public class XmlKitTest {

    @Test
    public void parseTest() {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "</returnsms>";
        Document docResult = XmlKit.parseXml(result);
        String elementText = XmlKit.getText(docResult.getDocumentElement(), "returnstatus");
        Assert.assertEquals("Success", elementText);
    }

    @Test
    public void writeTest() {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success（成功）</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "</returnsms>";
        Document docResult = XmlKit.parseXml(result);
        XmlKit.toFile(docResult, "/data/aaa.xml", "utf-8");
    }

    @Test
    public void xpathTest() {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success（成功）</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "</returnsms>";
        Document docResult = XmlKit.parseXml(result);
        Object value = XmlKit.getByXPath("//returnsms/message", docResult, XPathConstants.STRING);
        Assert.assertEquals("ok", value);
    }

    @Test
    public void xmlToMapTest() {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "<newNode><sub>subText</sub></newNode>"//
                + "</returnsms>";
        Map<String, Object> map = XmlKit.xmlToMap(xml);
        Console.log(map);

        Assert.assertEquals(6, map.size());
        Assert.assertEquals("Success", map.get("returnstatus"));
        Assert.assertEquals("ok", map.get("message"));
        Assert.assertEquals("1490", map.get("remainpoint"));
        Assert.assertEquals("885", map.get("taskID"));
        Assert.assertEquals("1", map.get("successCounts"));
        Assert.assertEquals("subText", map.get("newNode").toString());
    }

    @Test
    public void xmlToMapTest2() {
        String xml = "<root><name>张三</name><name>李四</name></root>";
        Map<String, Object> map = XmlKit.xmlToMap(xml);

        Assert.assertEquals(1, map.size());
        Assert.assertEquals(CollKit.newArrayList("张三", "李四"), map.get("name"));
    }

    @Test
    public void mapToXmlTest() {
        Map<String, Object> map = MapBuilder.create(new LinkedHashMap<String, Object>())//
                .put("name", "张三")//
                .put("age", 12)//
                .put("game", MapKit.builder(new LinkedHashMap<String, Object>()).put("昵称", "老K").put("level", 14).build())//
                .build();
        Document doc = XmlKit.mapToXml(map, "user");
        // Console.log(XmlKit.toStr(doc, false));
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"//
                        + "<user>"//
                        + "<name>张三</name>"//
                        + "<age>12</age>"//
                        + "<game>"//
                        + "<昵称>老K</昵称>"//
                        + "<level>14</level>"//
                        + "</game>"//
                        + "</user>", //
                XmlKit.toString(doc, false));
    }

    @Test
    public void readTest() {
        Document doc = XmlKit.readXML("test.xml");
        Assert.assertNotNull(doc);
    }

}
