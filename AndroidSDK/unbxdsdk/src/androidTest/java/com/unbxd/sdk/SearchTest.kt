package com.unbxd.sdk
import android.support.test.InstrumentationRegistry
import com.unbxd.sdk.internal.model.SearchQuery
import org.junit.Test

class SearchTest {
    companion object {
        val client = Client("demo-unbxd700181503576558", "fb853e3332f2645fac9d71dc63e09ec1", InstrumentationRegistry.getTargetContext())
    }

    @Test
    fun testSearchWithKey() {
        val searchQuery = SearchQuery.Builder("Shirt").build()
    }
}