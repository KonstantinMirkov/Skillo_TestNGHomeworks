package HomeworkLection10;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MainFirstTests {
    @DataProvider(name = "validData")
    public Object[][] validData() {
        return new Object[][]{
                {2, 3},
                {0, 0},
                {-2, 5},
                {10, 2},
                {7, 3}
        };
    }

    @DataProvider(name = "invalidData")
    public Object[][] invalidData() {
        return new Object[][]{
                {2, 0},
                {5, 0},
                {3, 0},
                {0, 0}
        };
    }

    @Test(dataProvider = "validData")
    public void testAddition(int a, int b) {
        int result = a + b;
        Assert.assertEquals(result, a + b, "Addition test failed");
    }

    @Test(dataProvider = "validData")
    public void testSubtraction(int a, int b) {
        int result = a - b;
        Assert.assertEquals(result, a - b, "Subtraction test failed");
    }

    @Test(dataProvider = "validData")
    public void testMultiplication(int a, int b) {
        int result = a * b;
        Assert.assertEquals(result, a * b, "Multiplication test failed");
    }
}
