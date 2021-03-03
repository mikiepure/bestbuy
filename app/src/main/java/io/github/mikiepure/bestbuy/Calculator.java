package io.github.mikiepure.bestbuy;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class Calculator {
  public interface IEventListener {
    void onUnitPriceChanged();
  }

  private final ViewGroup baseLayout;
  private final TextInputEditText textInputEditTextPrice;
  private final TextInputEditText textInputEditTextVolume;
  private final TextInputEditText textInputEditTextNumber;
  private final TextInputEditText textInputEditTextUnitPrice;
  private final TextView textViewBest;
  private final String invalidTextMessage;
  private int scale = 2;

  private final List<IEventListener> eventListeners = new ArrayList<>();

  Calculator(
      @NotNull ViewGroup baseLayout,
      @NotNull TextInputEditText textInputEditTextPrice,
      @NotNull TextInputEditText textInputEditTextVolume,
      @NotNull TextInputEditText textInputEditTextNumber,
      @NotNull TextInputEditText textInputEditTextUnitPrice,
      @NotNull Button buttonClear,
      @NotNull TextView textViewBest,
      @NotNull String invalidTextMessage) {
    this.baseLayout = baseLayout;
    this.textInputEditTextPrice = textInputEditTextPrice;
    this.textInputEditTextVolume = textInputEditTextVolume;
    this.textInputEditTextNumber = textInputEditTextNumber;
    this.textInputEditTextUnitPrice = textInputEditTextUnitPrice;
    this.textViewBest = textViewBest;
    this.invalidTextMessage = invalidTextMessage;

    textInputEditTextPrice.addTextChangedListener(anyTextChangedListener);
    textInputEditTextVolume.addTextChangedListener(anyTextChangedListener);
    textInputEditTextNumber.addTextChangedListener(anyTextChangedListener);

    buttonClear.setOnClickListener(clearButtonClickListener);
  }

  /**
   * Add event listeners.
   *
   * @param listener A listener object.
   */
  public void addEventListener(IEventListener listener) {
    eventListeners.add(listener);
  }

  /**
   * Set number of decimal places.
   *
   * @param scale Number of decimal places.
   */
  public void setScale(int scale) {
    this.scale = scale;
  }

  /**
   * Get unit price.
   *
   * @return Unit price.
   */
  public String getUnitPrice() {
    return getTextStringFromTextEdit(textInputEditTextUnitPrice);
  }

  /**
   * Set background by drawable resource.
   *
   * @param background A drawable resource.
   */
  public void setBackground(@Nullable Drawable background) {
    baseLayout.setBackground(background);
  }

  /**
   * Set visibility of best text.
   *
   * @param visible A text is visible or not.
   */
  public void setBestVisibility(boolean visible) {
    final int visibility = (visible) ? (View.VISIBLE) : (View.INVISIBLE);
    textViewBest.setVisibility(visibility);
  }

  private final TextWatcher anyTextChangedListener = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      /* do nothing */
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      /* do nothing */
    }

    @Override
    public void afterTextChanged(Editable s) {
      final String priceStr = getTextStringFromTextEdit(textInputEditTextPrice);
      final String volumeStr = getTextStringFromTextEdit(textInputEditTextVolume);
      final String numberStr = getTextStringFromTextEdit(textInputEditTextNumber);
      final String unitPrice = makeUnitPriceStr(priceStr, volumeStr, numberStr);
      if (!getTextStringFromTextEdit(textInputEditTextUnitPrice).equals(unitPrice)) {
        textInputEditTextUnitPrice.setText(unitPrice);
        eventListeners.forEach(IEventListener::onUnitPriceChanged);
      }
    }
  };

  /**
   * Get string of text in the EditText.
   *
   * @param editText A UI of EditText
   * @return String of text
   */
  private static String getTextStringFromTextEdit(@NotNull EditText editText) {
    final Editable editable = editText.getText();
    if (editable != null) {
      return editable.toString();
    }
    return "";
  }

  /**
   * Get unit price string from price string, volume string, and number string.
   *
   * @param priceStr  String of price
   * @param volumeStr String of volume
   * @param numberStr String of number
   * @return String of unit price or empty string
   */
  private String makeUnitPriceStr(@NotNull String priceStr, @NotNull String volumeStr,
                                  @NotNull String numberStr) {
    BigDecimal price = BigDecimal.ZERO;
    if (!priceStr.equals("")) {
      try {
        price = new BigDecimal(priceStr);
        textInputEditTextPrice.setError(null);
      } catch (NumberFormatException e) {
        textInputEditTextPrice.setError(invalidTextMessage);
      }
    } else {
      textInputEditTextPrice.setError(null);
    }
    BigDecimal volume = BigDecimal.ONE;
    if (!volumeStr.equals("")) {
      try {
        volume = new BigDecimal(volumeStr);
        textInputEditTextVolume.setError(null);
      } catch (NumberFormatException e) {
        textInputEditTextVolume.setError(invalidTextMessage);
      }
    } else {
      textInputEditTextVolume.setError(null);
    }
    BigDecimal number = BigDecimal.ONE;
    if (!numberStr.equals("")) {
      try {
        number = new BigDecimal(numberStr);
        textInputEditTextNumber.setError(null);
      } catch (NumberFormatException e) {
        textInputEditTextNumber.setError(invalidTextMessage);
      }
    } else {
      textInputEditTextNumber.setError(null);
    }

    BigDecimal unitPrice = calcUnitPrice(price, volume, number, scale);
    if (unitPrice != null) {
      return unitPrice.setScale(scale, RoundingMode.HALF_UP).toPlainString();
    }
    return "";
  }

  /**
   * Calculate unit price from price, volume, and number.
   *
   * @param price  Price [> 0]
   * @param volume Volume [> 0]
   * @param number Number [> 0]
   * @param scale  Number of decimal places
   * @return Unit price; price / (volume * number) or null when using invalid parameters
   */
  @Nullable
  private static BigDecimal calcUnitPrice(@NotNull BigDecimal price, @NotNull BigDecimal volume,
                                          @NotNull BigDecimal number, int scale) {
    if (price.compareTo(BigDecimal.ZERO) <= 0) {
      return null;
    }
    if (volume.compareTo(BigDecimal.ZERO) <= 0) {
      return null;
    }
    if (number.compareTo(BigDecimal.ZERO) <= 0) {
      return null;
    }
    return price.divide(volume.multiply(number), scale, RoundingMode.HALF_UP);
  }

  private final View.OnClickListener clearButtonClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      textInputEditTextPrice.setText("");
      textInputEditTextVolume.setText("");
      textInputEditTextNumber.setText("");
    }
  };
}
