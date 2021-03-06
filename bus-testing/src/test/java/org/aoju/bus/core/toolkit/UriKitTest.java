package org.aoju.bus.core.toolkit;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * UriKit单元测试
 */
public class UriKitTest {

    @Test
    public void normalizeTest() {
        String url = "http://www.aoju.org//aaa/bbb";
        String normalize = UriKit.normalize(url);
        Assert.assertEquals("http://www.aoju.org/aaa/bbb", normalize);

        url = "www.aoju.org//aaa/bbb";
        normalize = UriKit.normalize(url);
        Assert.assertEquals("http://www.aoju.org/aaa/bbb", normalize);
    }

    @Test
    public void normalizeTest2() {
        String url = "http://www.aoju.org//aaa/\\bbb?a=1&b=2";
        String normalize = UriKit.normalize(url);
        Assert.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);

        url = "www.aoju.org//aaa/bbb?a=1&b=2";
        normalize = UriKit.normalize(url);
        Assert.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);
    }

    @Test
    public void normalizeTest3() {
        String url = "http://www.aoju.org//aaa/\\bbb?a=1&b=2";
        String normalize = UriKit.normalize(url, false);
        Assert.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);

        url = "www.aoju.org//aaa/bbb?a=1&b=2";
        normalize = UriKit.normalize(url, false);
        Assert.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);

        url = "\\/www.aoju.org//aaa/bbb?a=1&b=2";
        normalize = UriKit.normalize(url, false);
        Assert.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);
    }

    @Test
    public void normalizeIpv6Test() {
        String url = "http://[fe80::8f8:2022:a603:d180]:9439";
        String normalize = UriKit.normalize("http://[fe80::8f8:2022:a603:d180]:9439", true);
        Assert.assertEquals(url, normalize);
    }

    @Test
    public void formatTest() {
        String url = "//www.aoju.org//aaa/\\bbb?a=1&b=2";
        String normalize = UriKit.normalize(url);
        Assert.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);
    }

    @Test
    public void getHostTest() throws MalformedURLException {
        String url = "https://www.aoju.org//aaa/\\bbb?a=1&b=2";
        String normalize = UriKit.normalize(url);
        URI host = UriKit.getHost(new URL(normalize));
        Assert.assertEquals("https://www.aoju.org", host.toString());
    }

    @Test
    public void encodeTest() {
        String body = "366466 - 副本.jpg";
        String encode = UriKit.encode(body);
        Assert.assertEquals("366466%20-%20%E5%89%AF%E6%9C%AC.jpg", encode);
        Assert.assertEquals(body, UriKit.decode(encode));

        String encode2 = UriKit.encodeQuery(body);
        Assert.assertEquals("366466%20-%20%E5%89%AF%E6%9C%AC.jpg", encode2);
    }

}
