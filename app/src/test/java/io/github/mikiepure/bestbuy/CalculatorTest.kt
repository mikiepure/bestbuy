package io.github.mikiepure.bestbuy

import android.content.Context
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputEditText
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class CalculatorTest {

    private lateinit var baseLayout: ViewGroup
    private lateinit var textInputEditTextPrice: TextInputEditText
    private lateinit var textInputEditTextVolume: TextInputEditText
    private lateinit var textInputEditTextNumber: TextInputEditText
    private lateinit var textInputEditTextUnitPrice: TextInputEditText
    private lateinit var textViewBest: TextView
    private lateinit var buttonClear: Button
    private lateinit var calculator: Calculator
    private lateinit var calculatorAnyTextChangedListener: TextWatcher
    private lateinit var calculatorClearButtonClickListener: View.OnClickListener

    @Before
    fun setUp() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).get()
        baseLayout = LinearLayout(activity)
        textInputEditTextPrice = TextInputEditText(activity)
        textInputEditTextVolume = TextInputEditText(activity)
        textInputEditTextNumber = TextInputEditText(activity)
        textInputEditTextUnitPrice = TextInputEditText(activity)
        buttonClear = Button(activity)
        textViewBest = TextView(activity)
        calculator = Calculator(baseLayout,
                textInputEditTextPrice, textInputEditTextVolume, textInputEditTextNumber,
                textInputEditTextUnitPrice, buttonClear, textViewBest,
                "error message")
        val anyTextChangedListenerRef = Calculator::class.java.getDeclaredField("anyTextChangedListener")
        anyTextChangedListenerRef.isAccessible = true
        calculatorAnyTextChangedListener = anyTextChangedListenerRef.get(calculator) as TextWatcher
        val clearButtonClickListenerRef = Calculator::class.java.getDeclaredField("clearButtonClickListener")
        clearButtonClickListenerRef.isAccessible = true
        calculatorClearButtonClickListener = clearButtonClickListenerRef.get(calculator) as View.OnClickListener
    }

    @Test
    fun beforeTextChanged() {
        calculatorAnyTextChangedListener.beforeTextChanged("", 0, 0, 0)
    }

    @Test
    fun onTextChanged() {
        calculatorAnyTextChangedListener.onTextChanged("", 0, 0, 0)
    }

    @Test
    fun afterTextChanged() {
        calculatorAnyTextChangedListener.afterTextChanged(textInputEditTextUnitPrice.text)
    }

    @Test
    fun onClick() {
        calculatorClearButtonClickListener.onClick(null);
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
                BigDecimal::class.java, BigDecimal::class.java, BigDecimal::class.java, Int::class.java
        )
        method.isAccessible = true

        val val0 = BigDecimal.ZERO
        val val1 = BigDecimal.ONE
        val val2 = BigDecimal.valueOf(2)
        val val25 = BigDecimal.valueOf(25)
        val val50 = BigDecimal.valueOf(50)
        val val100 = BigDecimal.valueOf(100)
        val scale = 2

        Assert.assertEquals(method.invoke(null, val100, val1, val1, scale), val100.setScale(scale))
        Assert.assertEquals(method.invoke(null, val100, val2, val1, scale), val50.setScale(scale))
        Assert.assertEquals(method.invoke(null, val100, val1, val2, scale), val50.setScale(scale))
        Assert.assertEquals(method.invoke(null, val100, val2, val2, scale), val25.setScale(scale))
        Assert.assertEquals(method.invoke(null, val0, val1, val1, scale), null)
        Assert.assertEquals(method.invoke(null, val100, val0, val1, scale), null)
        Assert.assertEquals(method.invoke(null, val100, val1, val0, scale), null)
    }
}
