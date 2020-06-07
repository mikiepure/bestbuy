package io.github.mikiepure.bestbuy

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun onCreate() {
        ActivityScenario.launch<MainActivity>(MainActivity::class.java).use { scenario ->
            scenario.onActivity(
                ActivityAction<MainActivity?> { /* do nothing */ })
        }
    }
}
