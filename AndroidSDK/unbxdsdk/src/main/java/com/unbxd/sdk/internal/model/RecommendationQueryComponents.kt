package com.unbxd.sdk.internal.model

import com.unbxd.sdk.internal.enums.RecommendationType
import com.unbxd.sdk.internal.enums.RecsV2PageType
import com.unbxd.sdk.internal.enums.ResponseFormat
import com.unbxd.sdk.internal.enums.Widget


open class RecommendationQueryBase (builder: RecommendationQueryBase.BaseBuilder){
    internal var uid: String
    internal var recommendationType: RecommendationType
    internal var region: String?
    internal var currency: String?
    internal var version: String = "V2"
    internal var responseFormat = ResponseFormat.JSON

    init {
        this.uid = builder.uid
        this.recommendationType = builder.recommendationType
        this.region = builder.region
        this.currency = builder.currency
        this.version = builder.version
        this.responseFormat = builder.responseFormat
    }

//    constructor(uid: String, recommendationType: RecommendationType, region: String, currency: String, version: String, responseFormat: ResponseFormat) {
//        this.uid = uid
//        this.recommendationType = recommendationType
//        this.region = region
//        this.currency = currency
//        this.version = version
//        this.responseFormat = responseFormat
//    }

    abstract class BaseBuilder(uid: String, recommendationType: RecommendationType) {
        var uid: String = uid
            private set
        var recommendationType: RecommendationType = recommendationType
            private set
        var region: String? = null
            private set
        var currency: String? = null
            private set
        var version: String = "V2"
            private set
        var responseFormat = ResponseFormat.JSON
            private  set

        fun region(region: String) = apply { this.region = region }
        fun currency(currency: String) = apply { this.currency = currency }
        fun version(version: String) = apply { this.version = version }
        fun responseFormat(responseFormat: ResponseFormat) = apply { this.responseFormat = responseFormat }

        abstract fun build(): RecommendationQueryBase
    }
}

class RecommendedForYourRecommendation private constructor(builder: RecommendedForYourRecommendation.Builder): RecommendationQueryBase(builder) {
    class Builder(uid: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.RecommendedForYou) {
        override fun build() = RecommendedForYourRecommendation(this)
    }
}

class RecentlyViewedRecommendation private constructor(builder: RecentlyViewedRecommendation.Builder): RecommendationQueryBase(builder) {
    var productId: String

    init {
        this.productId = builder.productId
    }

    class Builder(uid: String, productId: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.RecentlyViewed) {
        var productId = productId

        override fun build() = RecentlyViewedRecommendation(this)
    }
}

class MoreLikeThisRecommendation private constructor(builder: MoreLikeThisRecommendation.Builder): RecommendationQueryBase(builder) {
    var productId: String

    init {
        this.productId = builder.productId
    }

    class Builder(uid: String, productId: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.MoreLikeThis) {
        var productId = productId

        override fun build() = MoreLikeThisRecommendation(this)
    }
}

class ViewedAlsoViewedRecommendation private constructor(builder: ViewedAlsoViewedRecommendation.Builder): RecommendationQueryBase(builder) {
    var productId: String

    init {
        this.productId = builder.productId
    }

    class Builder(uid: String, productId: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.ViewerAlsoViewed) {
        var productId = productId

        override fun build() = ViewedAlsoViewedRecommendation(this)
    }
}

class BoughtAlsoBoughtRecommendation private constructor(builder: BoughtAlsoBoughtRecommendation.Builder): RecommendationQueryBase(builder) {
    var productId: String

    init {
        this.productId = builder.productId
    }

    class Builder(uid: String, productId: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.BoughtAlsoBought) {
        var productId = productId

        override fun build() = BoughtAlsoBoughtRecommendation(this)
    }
}

class CartRecommendation private constructor(builder: CartRecommendation.Builder): RecommendationQueryBase(builder) {
    class Builder(uid: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.CartRecommendation) {
        override fun build() = CartRecommendation(this)
    }
}

class HomePageTopSellersRecommendation private constructor(builder: HomePageTopSellersRecommendation.Builder): RecommendationQueryBase(builder) {
    class Builder(uid: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.HomePageTopSeller) {
        override fun build() = HomePageTopSellersRecommendation(this)
    }
}

class CategoryTopSellersRecommendation private constructor(builder: CategoryTopSellersRecommendation.Builder): RecommendationQueryBase(builder) {
    var categoryName: String

    init {
        this.categoryName = builder.categoryName
    }

    class Builder(uid: String, categoryName: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.CategoryTopSeller) {
        var categoryName = categoryName

        override fun build() = CategoryTopSellersRecommendation(this)
    }
}

class PDPTopSellersRecommendation private constructor(builder: PDPTopSellersRecommendation.Builder): RecommendationQueryBase(builder) {
    var productId: String

    init {
        this.productId = builder.productId
    }

    class Builder(uid: String, productId: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.PDPPageTopSeller) {
        var productId = productId

        override fun build() = PDPTopSellersRecommendation(this)
    }
}

class BrandTopSellersRecommendation private constructor(builder: BrandTopSellersRecommendation.Builder): RecommendationQueryBase(builder) {
    var brand: String

    init {
        this.brand = builder.brand
    }

    class Builder(uid: String, brand: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.BrandTopSeller) {
        var brand = brand

        override fun build() = BrandTopSellersRecommendation(this)
    }
}

class CompleteTheLookRecommendation private constructor(builder: CompleteTheLookRecommendation.Builder): RecommendationQueryBase(builder) {
    var productId: String

    init {
        this.productId = builder.productId
    }

    class Builder(uid: String, productId: String): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.CompleteTheLook) {
        var productId = productId

        override fun build() = CompleteTheLookRecommendation(this)
    }
}

class RecommendationV2 private constructor(builder: Builder): RecommendationQueryBase(builder){
    var pageType: RecsV2PageType = builder.pageType
    var widget: Widget? = null
    var id: String? = null
    var ip: String? = null
    var brand: String? = null
    var categoryLevelNames: Array<String>? = null

    init {
        this.widget = builder.widget
        this.id = builder.id
        this.ip = builder.ip
        this.brand = builder.brand
        this.categoryLevelNames = builder.categoryLevelNames
    }

    class Builder(uid: String, pageType: RecsV2PageType): RecommendationQueryBase.BaseBuilder(uid, RecommendationType.None) {
        var pageType = pageType
            private set

        var widget: Widget? = null
            private set

        var id: String? = null
            private set

        var ip: String? = null
            private set

        var brand: String? = null
            private set

        var categoryLevelNames: Array<String>? = null
            private set

        fun widget(widget: Widget) = apply { this.widget  = widget}
        fun id(id: String) = apply { this.id  = id}
        fun ip(ip: String) = apply { this.ip  = ip}
        fun brand(brand: String) = apply { this.brand  = brand}
        fun categoryLevelNames(categoryLevelNames: Array<String>) = apply { this.categoryLevelNames  = categoryLevelNames}

        override fun build() = RecommendationV2(this)
    }
}

//
//class BrandTopSellersRecomendation constructor(uid: String, brand: String, region: String, currency: String? = null, responseFormat: ResponseFormat = ResponseFormat.JSON) : RecommendationQueryBase(uid, RecommendationType.BrandTopSeller, region, currency, responseFormat) {
//    var brand: String? = brand
//
//}
//
//class CompleteTheLookRecomendation constructor(uid: String, productID: String, region: String, currency: String? = null, responseFormat: ResponseFormat = ResponseFormat.JSON) : RecommendationQueryBase(uid, RecommendationType.CompleteTheLook, region, currency, responseFormat) {
//    var pid: String? = productID
//}