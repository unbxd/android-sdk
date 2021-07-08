package com.unbxd.sdk.internal.requestbuilder

import com.unbxd.sdk.internal.model.RecommendationV2

class RecommendationV2RequestBuilder: RequestBuilderBase() {
    override fun parse(query: Any): String? {
        val recommendationQuery = query as? RecommendationV2 ?: return null

        var urlStr = RequestType.RecommendationTypeV2().baseURL()

        urlStr += recommendationQuery.recommendationType.jsonKey

        urlStr += Constants.kRecsPageType + recommendationQuery.pageType.jsonKey

        urlStr += Constants.kAnalyticsUidLabel + recommendationQuery.uid

        if (!recommendationQuery.id.isNullOrEmpty()) {
            urlStr += Constants.kRecsPIDLabel + recommendationQuery.id
        }

        if (!recommendationQuery.brand.isNullOrEmpty()) {
            urlStr += Constants.kRecsBrandLabel + recommendationQuery.brand
        }

        if (!recommendationQuery.ip.isNullOrEmpty()) {
            urlStr += Constants.kRecsIPLabel + recommendationQuery.ip
        }

        if (recommendationQuery.widget != null) {
            urlStr += Constants.kRecsWidgetLabel + recommendationQuery.widget!!.jsonKey
        }

        if (!recommendationQuery.pids.isNullOrEmpty()) {
            urlStr += "&"

            val pids_names = recommendationQuery.pids!!
            var pid_names = arrayListOf<String>()
            pids_names.forEach{ element ->
                pid_names.add("id=" + element)
            }
            urlStr += pid_names.joinToString("&")
        }


        if (!recommendationQuery.categoryLevelNames.isNullOrEmpty()) {
            urlStr += "&"

            val catLevels = recommendationQuery.categoryLevelNames!!
            var catLevelNames = arrayListOf<String>()
            catLevels.forEachIndexed { idx, element ->
                catLevelNames.add("catlevel" + (idx + 1) + "Name=" + element)
            }
            urlStr += catLevelNames.joinToString("&")
        }

        return urlStr
    }
}