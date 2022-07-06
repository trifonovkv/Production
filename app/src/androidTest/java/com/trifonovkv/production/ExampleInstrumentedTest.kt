package com.trifonovkv.production

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trifonovkv.production.ui.journal.ProductionDbHelper
import com.trifonovkv.production.ui.journal.ProductionEntry
import com.trifonovkv.production.ui.journal.ProductionJournal

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.myapplication", appContext.packageName)

        val dbHelper = ProductionDbHelper(appContext)

        val productionJournal = ProductionJournal(dbHelper)

        val testList = listOf(
            // July 13, 1986 2:38:58 AM
            ProductionEntry(521606338000, 0, 1, 2, 3, 4, 5,6, 7),
            // June 24, 2022 2:38:58 AM
            ProductionEntry(1656038338000, 10, 11, 12, 13, 14, 15, 16, 17),
            // June 25, 2022 2:38:58 AM
            ProductionEntry(1656124738000, 111, 111, 111, 111, 111, 111, 111, 111),
            // June 26, 2022 2:38:58 AM
            ProductionEntry(1656211138000, 1, 1, 1, 1, 1, 1, 1, 1),
            // July 13, 2022 2:38:58 AM
            ProductionEntry(1657679938000, 2, 2, 2, 2, 2, 2, 2, 2),
            // July 24, 2022 2:38:58 AM
            ProductionEntry(1658630338000, 3, 3, 3, 3, 3, 3, 3, 3),
            // July 25, 2022 2:38:58 AM
            ProductionEntry(1658716738000, 4, 4, 4, 4, 4, 4, 4, 4),
            // July 26, 2022 2:38:58 AM
            ProductionEntry(1658803138000, 5, 5, 5, 5, 5, 5, 5, 5)
        )

        testList.forEach {
            productionJournal.addEntry(it)
        }
    }
}