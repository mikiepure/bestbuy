package io.github.mikiepure.bestbuy;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

  protected Calculator _calc1 = null;
  protected Calculator _calc2 = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this._calc1 =
        new Calculator(
            (TextInputEditText) findViewById(R.id.textInputEditTextPrice1),
            (TextInputEditText) findViewById(R.id.textInputEditTextVolume1),
            (TextInputEditText) findViewById(R.id.textInputEditTextNumber1),
            (TextInputEditText) findViewById(R.id.textInputEditTextUnitPrice1));
    this._calc2 =
        new Calculator(
            (TextInputEditText) findViewById(R.id.textInputEditTextPrice2),
            (TextInputEditText) findViewById(R.id.textInputEditTextVolume2),
            (TextInputEditText) findViewById(R.id.textInputEditTextNumber2),
            (TextInputEditText) findViewById(R.id.textInputEditTextUnitPrice2));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    this._calc1 = null;
    this._calc2 = null;
  }
}

class Calculator implements TextWatcher {

  private final TextInputEditText _price;
  private final TextInputEditText _volume;
  private final TextInputEditText _number;
  private final TextInputEditText _unitPrice;

  public Calculator(
      TextInputEditText price,
      TextInputEditText volume,
      TextInputEditText number,
      TextInputEditText unitPrice) {
    this._price = price;
    this._price.addTextChangedListener(this);
    this._volume = volume;
    this._volume.addTextChangedListener(this);
    this._number = number;
    this._number.addTextChangedListener(this);
    this._unitPrice = unitPrice;
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {}

  @Override
  public void afterTextChanged(Editable s) {
    String priceStr = this._price.getText().toString();
    String volumeStr = this._volume.getText().toString();
    String numberStr = this._number.getText().toString();
    this._unitPrice.setText(getUnitPriceStr(priceStr, volumeStr, numberStr));
  }

  /**
   * Get unit price string from price string, volume string, and number string.
   *
   * @param priceStr String of price
   * @param volumeStr String of volume
   * @param numberStr String of number
   * @return String of unit price or empty string
   */
  public static String getUnitPriceStr(String priceStr, String volumeStr, String numberStr) {
    BigDecimal price = priceStr.equals("") ? BigDecimal.ZERO : new BigDecimal(priceStr);
    BigDecimal volume = volumeStr.equals("") ? BigDecimal.ONE : new BigDecimal(volumeStr);
    BigDecimal number = numberStr.equals("") ? BigDecimal.ONE : new BigDecimal(numberStr);
    BigDecimal unitPrice = calcUnitPrice(price, volume, number);
    if (unitPrice != null) {
      return unitPrice.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
    return "";
  }

  /**
   * Calculate unit price from price, volume, and number.
   *
   * @param price Price [> 0]
   * @param volume Volume [> 0]
   * @param number Number [> 0]
   * @return Unit price; price / (volume * number) or null when using invalid parameters
   */
  public static BigDecimal calcUnitPrice(BigDecimal price, BigDecimal volume, BigDecimal number) {
    if (price.compareTo(BigDecimal.ZERO) <= 0) return null;
    if (volume.compareTo(BigDecimal.ZERO) <= 0) return null;
    if (number.compareTo(BigDecimal.ZERO) <= 0) return null;
    return price.divide(volume.multiply(number), 2, RoundingMode.HALF_UP);
  }
}
