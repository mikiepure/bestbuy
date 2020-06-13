package io.github.mikiepure.bestbuy;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {

  protected Calculator _calc1 = null;
  protected Calculator _calc2 = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.trace();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this._calc1 =
        new Calculator(
            findViewById(R.id.textInputEditTextPrice1),
            findViewById(R.id.textInputEditTextVolume1),
            findViewById(R.id.textInputEditTextNumber1),
            findViewById(R.id.textInputEditTextUnitPrice1));
    ((Button) findViewById(R.id.buttonClear1)).setOnClickListener(this._calc1);
    this._calc2 =
        new Calculator(
            findViewById(R.id.textInputEditTextPrice2),
            findViewById(R.id.textInputEditTextVolume2),
            findViewById(R.id.textInputEditTextNumber2),
            findViewById(R.id.textInputEditTextUnitPrice2));
    ((Button) findViewById(R.id.buttonClear2)).setOnClickListener(this._calc2);
  }

  @Override
  protected void onDestroy() {
    Log.trace();
    super.onDestroy();

    this._calc1 = null;
    this._calc2 = null;
  }
}
