package io.github.mikiepure.bestbuy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {

  Calculator calc1 = null;
  Calculator calc2 = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.trace();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (this.calc1 == null) {
      this.calc1 = new Calculator(
          findViewById(R.id.textInputEditTextPrice1),
          findViewById(R.id.textInputEditTextVolume1),
          findViewById(R.id.textInputEditTextNumber1),
          findViewById(R.id.textInputEditTextUnitPrice1),
          findViewById(R.id.buttonClear1),
          getString(R.string.invalidTextMessage));
    }
    if (this.calc2 == null) {
      this.calc2 = new Calculator(
          findViewById(R.id.textInputEditTextPrice2),
          findViewById(R.id.textInputEditTextVolume2),
          findViewById(R.id.textInputEditTextNumber2),
          findViewById(R.id.textInputEditTextUnitPrice2),
          findViewById(R.id.buttonClear2),
          getString(R.string.invalidTextMessage));
    }
  }

  @Override
  protected void onDestroy() {
    Log.trace();
    super.onDestroy();

    this.calc1 = null;
    this.calc2 = null;
  }
}
