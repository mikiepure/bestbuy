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
  private final String invalidTextMessage;

  public Calculator(
      TextInputEditText textInputEditTextPrice,
      TextInputEditText textInputEditTextVolume,
      TextInputEditText textInputEditTextNumber,
      TextInputEditText textInputEditTextUnitPrice,
      Button buttonClear, String invalidTextMessage) {
    this.textInputEditTextPrice = textInputEditTextPrice;
    this.textInputEditTextPrice.addTextChangedListener(this);
    this.textInputEditTextVolume = textInputEditTextVolume;
    this.textInputEditTextVolume.addTextChangedListener(this);
    this.textInputEditTextNumber = textInputEditTextNumber;
    this.textInputEditTextNumber.addTextChangedListener(this);
    this.textInputEditTextUnitPrice = textInputEditTextUnitPrice;
    this.buttonClear = buttonClear;
    this.buttonClear.setOnClickListener(this);
    this.invalidTextMessage = invalidTextMessage;
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
    this.textInputEditTextUnitPrice.setText(makeUnitPriceStr(priceStr, volumeStr, numberStr));
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
  private String makeUnitPriceStr(String priceStr, String volumeStr, String numberStr) {
    BigDecimal price = BigDecimal.ZERO;
    if (!priceStr.equals("")) {
      try {
        price = new BigDecimal(priceStr);
        this.textInputEditTextPrice.setError(null);
      } catch (NumberFormatException e) {
        this.textInputEditTextPrice.setError(this.invalidTextMessage);
      }
    } else {
      this.textInputEditTextPrice.setError(null);
    }
    BigDecimal volume = BigDecimal.ONE;
    if (!volumeStr.equals("")) {
      try {
        volume = new BigDecimal(volumeStr);
        this.textInputEditTextVolume.setError(null);
      } catch (NumberFormatException e) {
        this.textInputEditTextVolume.setError(this.invalidTextMessage);
      }
    } else {
      this.textInputEditTextVolume.setError(null);
    }
    BigDecimal number = BigDecimal.ONE;
    if (!numberStr.equals("")) {
      try {
        number = new BigDecimal(numberStr);
        this.textInputEditTextNumber.setError(null);
      } catch (NumberFormatException e) {
        this.textInputEditTextNumber.setError("invalid text");
      }
    } else {
      this.textInputEditTextNumber.setError(null);
    }

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
