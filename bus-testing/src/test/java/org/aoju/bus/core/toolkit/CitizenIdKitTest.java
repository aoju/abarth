package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.date.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * 身份证单元测试
 */
public class CitizenIdKitTest {

    private static final String ID_18 = "321083197812162119";
    private static final String ID_15 = "150102880730303";

    @Test
    public void isValidCardTest() {
        boolean valid = CitizenIdKit.isValidCard(ID_18);
        Assert.assertTrue(valid);

        boolean valid15 = CitizenIdKit.isValidCard(ID_15);
        Assert.assertTrue(valid15);

        String idCard = "360198910283844";
        Assert.assertFalse(CitizenIdKit.isValidCard(idCard));
    }

    @Test
    public void convert15To18Test() {
        String convert15To18 = CitizenIdKit.getIdCardTo18(ID_15);
        Assert.assertEquals("150102198807303035", convert15To18);

        String convert15To18Second = CitizenIdKit.getIdCardTo18("330102200403064");
        Assert.assertEquals("33010219200403064x", convert15To18Second);
    }

    @Test
    public void getAgeByIdCardTest() {
        DateTime date = DateKit.parse("2017-04-10");

        int age = CitizenIdKit.getAgeByIdCard(ID_18, date);
        Assert.assertEquals(age, 38);

        int age2 = CitizenIdKit.getAgeByIdCard(ID_15, date);
        Assert.assertEquals(age2, 28);
    }

    @Test
    public void getBirthByIdCardTest() {
        String birth = CitizenIdKit.getBirthByIdCard(ID_18);
        Assert.assertEquals(birth, "19781216");

        String birth2 = CitizenIdKit.getBirthByIdCard(ID_15);
        Assert.assertEquals(birth2, "19880730");
    }

    @Test
    public void getProvinceByIdCardTest() {
        String province = CitizenIdKit.getProvinceByIdCard(ID_18);
        Assert.assertEquals(province, "江苏");

        String province2 = CitizenIdKit.getProvinceByIdCard(ID_15);
        Assert.assertEquals(province2, "内蒙古");
    }

    @Test
    public void getGenderByIdCardTest() {
        int gender = CitizenIdKit.getGenderByIdCard(ID_18);
        Assert.assertEquals(1, gender);
    }

    @Test
    public void isValidCard18Test() {
        final boolean isValidCard18 = CitizenIdKit.isValidCard18("3301022011022000D6");
        Assert.assertFalse(isValidCard18);
    }

}
