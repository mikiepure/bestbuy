package io.github.mikiepure.bestbuy;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {

  protected Calculator calc1 = null;
  protected Calculator calc2 = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.trace();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.calc1 =
        new Calculator(
            findViewById(R.id.textInputEditTextPrice1),
            findViewById(R.id.textInputEditTextVolume1),
            findViewById(R.id.textInputEditTextNumber1),
            findViewById(R.id.textInputEditTextUnitPrice1),
            findViewById(R.id.buttonClear1));
    this.calc2 =
        new Calculator(
            findViewById(R.id.textInputEditTextPrice2),
            findViewById(R.id.textInputEditTextVolume2),
            findViewById(R.id.textInputEditTextNumber2),
            findViewById(R.id.textInputEditTextUnitPrice2),
            findViewById(R.id.buttonClear2));
  }

  @Override
  protected void onDestroy() {
    Log.trace();
    super.onDestroy();

    this.calc1 = null;
    this.calc2 = null;
  }
}
