package HomeworkLection10;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MainSecondTests {
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
    public void testDivision(int a, int b) {
        if (b != 0) {
            int result = a / b;
            Assert.assertEquals(result, a / b, "Division test failed");
        } else {
            Assert.fail("Division by zero");
        }
    }

    @Test(dataProvider = "validData")
    public void testModulo(int a, int b) {
        if (b != 0) {
            int result = a % b;
            Assert.assertEquals(result, a % b, "Modulo test failed");
        } else {
            Assert.fail("Modulo by zero");
        }
    }

    @Test(dataProvider = "invalidData", expectedExceptions = ArithmeticException.class)
    public void testInvalidDivision(int a, int b) {
        int result = a / b;
    }
}
