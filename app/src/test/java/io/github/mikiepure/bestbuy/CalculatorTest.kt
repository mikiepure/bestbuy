package io.github.mikiepure.bestbuy

import android.content.Context
import android.widget.Button
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputEditText
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class CalculatorTest {

    private lateinit var textInputEditTextPrice: TextInputEditText
    private lateinit var textInputEditTextVolume: TextInputEditText
    private lateinit var textInputEditTextNumber: TextInputEditText
    private lateinit var textInputEditTextUnitPrice: TextInputEditText
    private lateinit var buttonClear: Button
    private lateinit var calculator: Calculator

    @Before
    fun setUp() {
        val c = ApplicationProvider.getApplicationContext<Context>()
        this.textInputEditTextPrice = TextInputEditText(c)
        this.textInputEditTextVolume = TextInputEditText(c)
        this.textInputEditTextNumber = TextInputEditText(c)
        this.textInputEditTextUnitPrice = TextInputEditText(c)
        this.buttonClear = Button(c)
        this.calculator = Calculator(textInputEditTextPrice, textInputEditTextVolume, textInputEditTextNumber, textInputEditTextUnitPrice, buttonClear, "error message")
    }

    @Test
    fun beforeTextChanged() {
        this.calculator.beforeTextChanged("", 0, 0, 0)
    }

    @Test
    fun onTextChanged() {
        this.calculator.onTextChanged("", 0, 0, 0)
    }

    @Test
    fun afterTextChanged() {
        this.calculator.afterTextChanged(this.textInputEditTextUnitPrice.text)
    }

    @Test
    fun onClick() {
        this.calculator.onClick(null);
    }

    @Test
    fun makeUnitPriceStr() {
        val method = Calculator::class.java.getDeclaredMethod(
                "makeUnitPriceStr",
                String::class.java, String::class.java, String::class.java
        )
        method.isAccessible = true

        Assert.assertEquals(method.invoke(this.calculator, "100", "1", "1"), "100.00")
        Assert.assertEquals(method.invoke(this.calculator, "100", "", ""), "100.00")
        Assert.assertEquals(method.invoke(this.calculator, "100", "2", "1"), "50.00")
        Assert.assertEquals(method.invoke(this.calculator, "100", "2", ""), "50.00")
        Assert.assertEquals(method.invoke(this.calculator, "100", "1", "2"), "50.00")
        Assert.assertEquals(method.invoke(this.calculator, "100", "", "2"), "50.00")

        Assert.assertEquals(method.invoke(this.calculator, "", "", ""), "")
        Assert.assertEquals(method.invoke(this.calculator, "100", "0", "1"), "")
        Assert.assertEquals(method.invoke(this.calculator, "100", "1", "0"), "")

        /* NumberFormatException will be occurred at each string */
        Assert.assertEquals(method.invoke(this.calculator, ".", ".", "."), "")
    }

    @Test
    fun calcUnitPrice() {
        val method = Calculator::class.java.getDeclaredMethod(
                "calcUnitPrice",
                BigDecimal::class.java, BigDecimal::class.java, BigDecimal::class.java
        )
        method.isAccessible = true

        val val0 = BigDecimal.ZERO
        val val1 = BigDecimal.ONE
        val val2 = BigDecimal.valueOf(2)
        val val25 = BigDecimal.valueOf(25)
        val val50 = BigDecimal.valueOf(50)
        val val100 = BigDecimal.valueOf(100)

        Assert.assertEquals(method.invoke(null, val100, val1, val1), val100.setScale(2))
        Assert.assertEquals(method.invoke(null, val100, val2, val1), val50.setScale(2))
        Assert.assertEquals(method.invoke(null, val100, val1, val2), val50.setScale(2))
        Assert.assertEquals(method.invoke(null, val100, val2, val2), val25.setScale(2))
        Assert.assertEquals(method.invoke(null, val0, val1, val1), null)
        Assert.assertEquals(method.invoke(null, val100, val0, val1), null)
        Assert.assertEquals(method.invoke(null, val100, val1, val0), null)
    }
}
