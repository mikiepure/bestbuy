package io.github.mikiepure.bestbuy;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class MainActivity extends AppCompatActivity {
  // Create array of calculator as max length
  private final Calculator[] calculators = new Calculator[5];

  // Set initial values
  private int itemCount = 2;
  private int decimalPlaces = 2;

  private Drawable backgroundBorder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.trace();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Create calculators for items
    if (calculators[0] == null) {
      calculators[0] = new Calculator(
          findViewById(R.id.linearLayoutItem1),
          findViewById(R.id.textInputEditTextPrice1),
          findViewById(R.id.textInputEditTextVolume1),
          findViewById(R.id.textInputEditTextNumber1),
          findViewById(R.id.textInputEditTextUnitPrice1),
          findViewById(R.id.buttonClear1),
          findViewById(R.id.textViewBest1),
          getString(R.string.invalidTextMessage));
    }
    if (calculators[1] == null) {
      calculators[1] = new Calculator(
          findViewById(R.id.linearLayoutItem2),
          findViewById(R.id.textInputEditTextPrice2),
          findViewById(R.id.textInputEditTextVolume2),
          findViewById(R.id.textInputEditTextNumber2),
          findViewById(R.id.textInputEditTextUnitPrice2),
          findViewById(R.id.buttonClear2),
          findViewById(R.id.textViewBest2),
          getString(R.string.invalidTextMessage));
    }
    if (calculators[2] == null) {
      calculators[2] = new Calculator(
          findViewById(R.id.linearLayoutItem3),
          findViewById(R.id.textInputEditTextPrice3),
          findViewById(R.id.textInputEditTextVolume3),
          findViewById(R.id.textInputEditTextNumber3),
          findViewById(R.id.textInputEditTextUnitPrice3),
          findViewById(R.id.buttonClear3),
          findViewById(R.id.textViewBest3),
          getString(R.string.invalidTextMessage));
    }
    if (calculators[3] == null) {
      calculators[3] = new Calculator(
          findViewById(R.id.linearLayoutItem4),
          findViewById(R.id.textInputEditTextPrice4),
          findViewById(R.id.textInputEditTextVolume4),
          findViewById(R.id.textInputEditTextNumber4),
          findViewById(R.id.textInputEditTextUnitPrice4),
          findViewById(R.id.buttonClear4),
          findViewById(R.id.textViewBest4),
          getString(R.string.invalidTextMessage));
    }
    if (calculators[4] == null) {
      calculators[4] = new Calculator(
          findViewById(R.id.linearLayoutItem5),
          findViewById(R.id.textInputEditTextPrice5),
          findViewById(R.id.textInputEditTextVolume5),
          findViewById(R.id.textInputEditTextNumber5),
          findViewById(R.id.textInputEditTextUnitPrice5),
          findViewById(R.id.buttonClear5),
          findViewById(R.id.textViewBest5),
          getString(R.string.invalidTextMessage));
    }

    backgroundBorder = ContextCompat.getDrawable(this, R.drawable.border);

    if (savedInstanceState != null) {
      setItemCount(savedInstanceState.getInt("itemCount", itemCount));
      setDecimalPlaces(savedInstanceState.getInt("decimalPlaces", decimalPlaces));
    } else {
      setItemCount(itemCount);
      setDecimalPlaces(decimalPlaces);
    }

    for (Calculator calculator : calculators) {
      calculator.addEventListener(calculatorEventCallback);
    }
  }

  private final Calculator.IEventListener calculatorEventCallback = () -> {
    Log.trace("onUnitPriceChanged");

    // Initialize emphasizing
    for (Calculator calculator : calculators) {
      calculator.setBackground(null);
      calculator.setBestVisibility(false);
    }

    // Get best indexes of the calculator array and best value
    Set<Integer> bestIndexes = new HashSet<>();
    double bestValue = 0.0;
    int availableIndexCount = 0;
    for (int i = 0; i < itemCount; i++) {
      final String unitPrice = calculators[i].getUnitPrice();
      if (!unitPrice.equals("")) {
        try {
          double unitPriceNum = Double.parseDouble(unitPrice);
          if (bestIndexes.size() == 0) {          // add index and value at the first time
            bestIndexes.add(i);
            bestValue = unitPriceNum;
          } else if (unitPriceNum < bestValue) {  // update index and value if best value is found
            bestIndexes.clear();
            bestIndexes.add(i);
            bestValue = unitPriceNum;
          } else if (unitPriceNum == bestValue) { // add index if same value is found
            bestIndexes.add(i);
          }                                       // do nothing if worse value is found
          availableIndexCount++;
        } catch (NumberFormatException e) {
          Log.warn("Unexpected value:", unitPrice);
        }
      }
    }

    if (availableIndexCount > 1) {
      // Emphasize calculators which has best indexes
      if (bestIndexes.size() > 0) {
        for (int index : bestIndexes) {
          calculators[index].setBackground(backgroundBorder);
          calculators[index].setBestVisibility(true);
        }
      }
    }
  };

  @Override
  protected void onDestroy() {
    Log.trace();
    super.onDestroy();

    Arrays.fill(calculators, null);
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    savedInstanceState.putInt("itemCount", itemCount);
    savedInstanceState.putInt("decimalPlaces", decimalPlaces);

    super.onSaveInstanceState(savedInstanceState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    Log.trace();
    getMenuInflater().inflate(R.menu.option, menu);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    Log.trace();

    switch (this.itemCount) {
      case 2:
        menu.findItem(R.id.menuItemCount2).setChecked(true);
        break;
      case 3:
        menu.findItem(R.id.menuItemCount3).setChecked(true);
        break;
      case 4:
        menu.findItem(R.id.menuItemCount4).setChecked(true);
        break;
      case 5:
        menu.findItem(R.id.menuItemCount5).setChecked(true);
        break;
      default:
        /* Do nothing */
    }

    switch (this.decimalPlaces) {
      case 0:
        menu.findItem(R.id.menuItemDecPlaces0).setChecked(true);
        break;
      case 1:
        menu.findItem(R.id.menuItemDecPlaces1).setChecked(true);
        break;
      case 2:
        menu.findItem(R.id.menuItemDecPlaces2).setChecked(true);
        break;
      case 3:
        menu.findItem(R.id.menuItemDecPlaces3).setChecked(true);
        break;
      default:
        /* Do nothing */
    }

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    final int itemId = item.getItemId();
    if (itemId == R.id.menuItemCount2) {
      setItemCount(2);
    } else if (itemId == R.id.menuItemCount3) {
      setItemCount(3);
    } else if (itemId == R.id.menuItemCount4) {
      setItemCount(4);
    } else if (itemId == R.id.menuItemCount5) {
      setItemCount(5);
    } else if (itemId == R.id.menuItemDecPlaces0) {
      setDecimalPlaces(0);
    } else if (itemId == R.id.menuItemDecPlaces1) {
      setDecimalPlaces(1);
    } else if (itemId == R.id.menuItemDecPlaces2) {
      setDecimalPlaces(2);
    } else if (itemId == R.id.menuItemDecPlaces3) {
      setDecimalPlaces(3);
    } else {
      return super.onOptionsItemSelected(item);
    }

    return true;
  }

  private void setItemCount(int itemCount) {
    switch (itemCount) {
      case 2:
        findViewById(R.id.linearLayoutItem3).setVisibility(View.GONE);
        findViewById(R.id.linearLayoutItem4).setVisibility(View.GONE);
        findViewById(R.id.linearLayoutItem5).setVisibility(View.GONE);
        break;
      case 3:
        findViewById(R.id.linearLayoutItem3).setVisibility(View.VISIBLE);
        findViewById(R.id.linearLayoutItem4).setVisibility(View.GONE);
        findViewById(R.id.linearLayoutItem5).setVisibility(View.GONE);
        break;
      case 4:
        findViewById(R.id.linearLayoutItem3).setVisibility(View.VISIBLE);
        findViewById(R.id.linearLayoutItem4).setVisibility(View.VISIBLE);
        findViewById(R.id.linearLayoutItem5).setVisibility(View.GONE);
        break;
      case 5:
        findViewById(R.id.linearLayoutItem3).setVisibility(View.VISIBLE);
        findViewById(R.id.linearLayoutItem4).setVisibility(View.VISIBLE);
        findViewById(R.id.linearLayoutItem5).setVisibility(View.VISIBLE);
        break;
      default:
        /* Do nothing */
    }

    this.itemCount = itemCount;
  }

  private void setDecimalPlaces(int decimalPlaces) {
    for (Calculator calculator : calculators) {
      calculator.setScale(decimalPlaces);
    }

    this.decimalPlaces = decimalPlaces;
  }
}
