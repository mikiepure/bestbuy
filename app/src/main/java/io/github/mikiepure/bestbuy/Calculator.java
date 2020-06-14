package io.github.mikiepure.bestbuy;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import java.math.BigDecimal;
import java.math.RoundingMode;

final class Calculator implements TextWatcher, View.OnClickListener {

  private final TextInputEditText textInputEditTextPrice;
  private final TextInputEditText textInputEditTextVolume;
  private final TextInputEditText textInputEditTextNumber;
  private final TextInputEditText textInputEditTextUnitPrice;
  private final Button buttonClear;

  public Calculator(
      TextInputEditText textInputEditTextPrice,
      TextInputEditText textInputEditTextVolume,
      TextInputEditText textInputEditTextNumber,
      TextInputEditText textInputEditTextUnitPrice,
      Button buttonClear) {
    this.textInputEditTextPrice = textInputEditTextPrice;
    this.textInputEditTextPrice.addTextChangedListener(this);
    this.textInputEditTextVolume = textInputEditTextVolume;
    this.textInputEditTextVolume.addTextChangedListener(this);
    this.textInputEditTextNumber = textInputEditTextNumber;
    this.textInputEditTextNumber.addTextChangedListener(this);
    this.textInputEditTextUnitPrice = textInputEditTextUnitPrice;
    this.buttonClear = buttonClear;
    this.buttonClear.setOnClickListener(this);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  @Override
  public void afterTextChanged(Editable s) {
    String priceStr = this.textInputEditTextPrice.getText().toString();
    String volumeStr = this.textInputEditTextVolume.getText().toString();
    String numberStr = this.textInputEditTextNumber.getText().toString();
    this.textInputEditTextUnitPrice.setText(getUnitPriceStr(priceStr, volumeStr, numberStr));
  }

  @Override
  public void onClick(View v) {
    this.textInputEditTextPrice.setText("");
    this.textInputEditTextVolume.setText("");
    this.textInputEditTextNumber.setText("");
  }

  /**
   * Get unit price string from price string, volume string, and number string.
   *
   * @param priceStr  String of price
   * @param volumeStr String of volume
   * @param numberStr String of number
   * @return String of unit price or empty string
   */
  private static String getUnitPriceStr(String priceStr, String volumeStr, String numberStr) {
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
   * @param price  Price [> 0]
   * @param volume Volume [> 0]
   * @param number Number [> 0]
   * @return Unit price; price / (volume * number) or null when using invalid parameters
   */
  private static BigDecimal calcUnitPrice(BigDecimal price, BigDecimal volume, BigDecimal number) {
    if (price.compareTo(BigDecimal.ZERO) <= 0) {
      return null;
    }
    if (volume.compareTo(BigDecimal.ZERO) <= 0) {
      return null;
    }
    if (number.compareTo(BigDecimal.ZERO) <= 0) {
      return null;
    }
    return price.divide(volume.multiply(number), 2, RoundingMode.HALF_UP);
  }
}
