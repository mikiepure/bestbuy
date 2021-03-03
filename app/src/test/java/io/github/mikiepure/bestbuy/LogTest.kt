package io.github.mikiepure.bestbuy

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogTest {

    @Test
    fun error() {
        Log.error("message", "abc", 123, null)
    }

    @Test
    fun warn() {
        Log.warn("message", "abc", 123, null)
    }

    @Test
    fun info() {
        Log.info("message", "abc", 123, null)
    }

    @Test
    fun debug() {
        Log.debug("message", "abc", 123, null)
    }

    @Test
    fun verbose() {
        Log.verbose("message", "abc", 123, null)
    }

    @Test
    fun trace() {
        Log.trace("message", "abc", 123, null)
    }
}
