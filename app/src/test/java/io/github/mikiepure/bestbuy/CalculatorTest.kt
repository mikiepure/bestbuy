package io.github.mikiepure.bestbuy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputEditText
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class CalculatorTest {
    @Test
    fun beforeTextChanged() {
        val c = ApplicationProvider.getApplicationContext<Context>()
        val price = TextInputEditText(c)
        val volume = TextInputEditText(c)
        val number = TextInputEditText(c)
        val unitPrice = TextInputEditText(c)
        Calculator(price, volume, number, unitPrice).beforeTextChanged("", 0, 0, 0)
    }

    @Test
    fun onTextChanged() {
        val c = ApplicationProvider.getApplicationContext<Context>()
        val price = TextInputEditText(c)
        val volume = TextInputEditText(c)
        val number = TextInputEditText(c)
        val unitPrice = TextInputEditText(c)
        Calculator(price, volume, number, unitPrice).onTextChanged("", 0, 0, 0)
    }

    @Test
    fun afterTextChanged() {
        val c = ApplicationProvider.getApplicationContext<Context>()
        val price = TextInputEditText(c)
        val volume = TextInputEditText(c)
        val number = TextInputEditText(c)
        val unitPrice = TextInputEditText(c)
        Calculator(price, volume, number, unitPrice).afterTextChanged(price.text)
    }

    @Test
    fun unitPriceStr() {
        Assert.assertEquals(Calculator.getUnitPriceStr("100", "1", "1"), "100.00")
        Assert.assertEquals(Calculator.getUnitPriceStr("100", "", ""), "100.00")
        Assert.assertEquals(Calculator.getUnitPriceStr("100", "2", "1"), "50.00")
        Assert.assertEquals(Calculator.getUnitPriceStr("100", "2", ""), "50.00")
        Assert.assertEquals(Calculator.getUnitPriceStr("100", "1", "2"), "50.00")
        Assert.assertEquals(Calculator.getUnitPriceStr("100", "", "2"), "50.00")
        Assert.assertEquals(Calculator.getUnitPriceStr("", "", ""), "")
        Assert.assertEquals(Calculator.getUnitPriceStr("100", "0", ""), "")
        Assert.assertEquals(Calculator.getUnitPriceStr("100", "", "0"), "")
    }

    @Test
    fun calcUnitPrice() {
        val val0 = BigDecimal.ZERO
        val val1 = BigDecimal.ONE
        val val2 = BigDecimal.valueOf(2)
        val val25 = BigDecimal.valueOf(25)
        val val50 = BigDecimal.valueOf(50)
        val val100 = BigDecimal.valueOf(100)

        Assert.assertEquals(Calculator.calcUnitPrice(val100, val1, val1), val100.setScale(2))
        Assert.assertEquals(Calculator.calcUnitPrice(val100, val2, val1), val50.setScale(2))
        Assert.assertEquals(Calculator.calcUnitPrice(val100, val1, val2), val50.setScale(2))
        Assert.assertEquals(Calculator.calcUnitPrice(val100, val2, val2), val25.setScale(2))
        Assert.assertEquals(Calculator.calcUnitPrice(val0, val1, val1), null)
        Assert.assertEquals(Calculator.calcUnitPrice(val100, val0, val1), null)
        Assert.assertEquals(Calculator.calcUnitPrice(val100, val1, val0), null)
    }
}
