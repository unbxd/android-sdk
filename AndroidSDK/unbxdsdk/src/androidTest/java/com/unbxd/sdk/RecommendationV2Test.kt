package com.unbxd.sdk
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecommendationV2Test {
    val client = Client("demo-unbxd700181503576558", "fb853e3332f2645fac9d71dc63e09ec1", InstrumentationRegistry.getTargetContext())
    val kUid = "uid-1527147976993-16311"
    val requestId = "3d3d5ab5-33e6-4706-b86a-dcd233889d0d-demo-unbxd700181503576558"

    fun getAppContext(): Context {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        Assert.assertEquals("com.unbxd.sdk.test", appContext.packageName)
        return appContext
    }
}