package io.github.mikiepure.bestbuy;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {

  Calculator[] calculators = new Calculator[5];
  int itemCount = 2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.trace();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Create calculators for items
    if (this.calculators[0] == null) {
      this.calculators[0] = new Calculator(
          findViewById(R.id.textInputEditTextPrice1),
          findViewById(R.id.textInputEditTextVolume1),
          findViewById(R.id.textInputEditTextNumber1),
          findViewById(R.id.textInputEditTextUnitPrice1),
          findViewById(R.id.buttonClear1),
          getString(R.string.invalidTextMessage));
    }
    if (this.calculators[1] == null) {
      this.calculators[1] = new Calculator(
          findViewById(R.id.textInputEditTextPrice2),
          findViewById(R.id.textInputEditTextVolume2),
          findViewById(R.id.textInputEditTextNumber2),
          findViewById(R.id.textInputEditTextUnitPrice2),
          findViewById(R.id.buttonClear2),
          getString(R.string.invalidTextMessage));
    }
    if (this.calculators[2] == null) {
      this.calculators[2] = new Calculator(
          findViewById(R.id.textInputEditTextPrice3),
          findViewById(R.id.textInputEditTextVolume3),
          findViewById(R.id.textInputEditTextNumber3),
          findViewById(R.id.textInputEditTextUnitPrice3),
          findViewById(R.id.buttonClear3),
          getString(R.string.invalidTextMessage));
    }
    if (this.calculators[3] == null) {
      this.calculators[3] = new Calculator(
          findViewById(R.id.textInputEditTextPrice4),
          findViewById(R.id.textInputEditTextVolume4),
          findViewById(R.id.textInputEditTextNumber4),
          findViewById(R.id.textInputEditTextUnitPrice4),
          findViewById(R.id.buttonClear4),
          getString(R.string.invalidTextMessage));
    }
    if (this.calculators[4] == null) {
      this.calculators[4] = new Calculator(
          findViewById(R.id.textInputEditTextPrice5),
          findViewById(R.id.textInputEditTextVolume5),
          findViewById(R.id.textInputEditTextNumber5),
          findViewById(R.id.textInputEditTextUnitPrice5),
          findViewById(R.id.buttonClear5),
          getString(R.string.invalidTextMessage));
    }

    if (savedInstanceState != null) {
      this.itemCount = savedInstanceState.getInt("itemCount", 2);
    }

    // Hide layouts for hidden items
    this.setItemCount(itemCount);
  }

  @Override
  protected void onDestroy() {
    Log.trace();
    super.onDestroy();

    for (int i = 0; i < calculators.length; i++) {
      calculators[i] = null;
    }
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    savedInstanceState.putInt("itemCount", this.itemCount);

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

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menuItemCount2:
        this.setItemCount(2);
        return true;
      case R.id.menuItemCount3:
        this.setItemCount(3);
        return true;
      case R.id.menuItemCount4:
        this.setItemCount(4);
        return true;
      case R.id.menuItemCount5:
        this.setItemCount(5);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
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
}
