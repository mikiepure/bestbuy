package io.github.mikiepure.bestbuy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public final class MainActivity extends AppCompatActivity {

  protected Calculator _calc1 = null;
  protected Calculator _calc2 = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.info("onCreate");
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
    Log.info("onDestroy");
    super.onDestroy();

    this._calc1 = null;
    this._calc2 = null;
  }
}
