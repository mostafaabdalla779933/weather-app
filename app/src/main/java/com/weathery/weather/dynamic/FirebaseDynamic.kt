package com.weathery.weather.dynamic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.iosParameters
import com.google.firebase.ktx.Firebase
import com.weathery.weather.R

//val dynamicLink = Firebase.dynamicLinks.dynamicLink {
//    link = Uri.parse("https://www.example.com/")
//    domainUriPrefix = "https://example.page.link"
//    // Open links with this app on Android
//    androidParameters { "https://weather.page.link"}
//    // Open links with com.example.ios on iOS
//    iosParameters("com.example.ios") { }
//}
//
//val dynamicLinkUri = dynamicLink.uri


object DeepLinkingUtil {



    fun listenToDynamicLinks(
        savedInstanceState: Bundle?, intent: Intent, activity: Activity,
        navigateToDeepLink: (uri: Uri) -> Unit
    ) {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(activity) { pendingDynamicLinkData ->
                if (pendingDynamicLinkData != null) {
                        savedInstanceState?.let {
                            handleIntent(
                                pendingDynamicLinkData.link!!,
                                activity,
                                navigateToDeepLink
                            )
                        }

                } else {
                    handleNestedIntent(intent, activity, navigateToDeepLink)
                }
            }.addOnFailureListener {
                handleNestedIntent(intent, activity, navigateToDeepLink)
            }
    }


    private fun handleNestedIntent(
        intent: Intent,
        activity: Activity,
        navigateToDeepLink: (uri: Uri) -> Unit
    ) {
        var deepLinkKey: String? = null
        intent.extras?.keySet()?.forEach {
            if (intent.extras?.get(it) is Intent &&
                (intent.extras?.get(it) as Intent).data?.queryParameterNames?.contains(
                    Constants.DEEP_LINK_NESTED_URI
                ) == true
                && (intent.extras?.get(it) as Intent).data?.getQueryParameter(Constants.DEEP_LINK_NESTED_URI) != Constants.DEEP_LINK_DEFAULT_NULL_NESTED_URI
            ) deepLinkKey =
                (intent.extras?.get(it) as Intent).data?.getQueryParameter(Constants.DEEP_LINK_NESTED_URI)
        }
        deepLinkKey?.let {
            intent.data = it.toUri()
            activity.intent = intent
            handleIntent(it.toUri(), activity, navigateToDeepLink)

        }
    }


    fun handleNestedIntent(intent: Intent, activity: Activity) {
        var deepLinkKey: String? = null
        intent.extras?.keySet()?.forEach {
            if (intent.extras?.get(it) is Intent &&
                (intent.extras?.get(it) as Intent).data?.queryParameterNames?.contains(
                    Constants.DEEP_LINK_NESTED_URI
                ) == true
                && (intent.extras?.get(it) as Intent).data?.getQueryParameter(Constants.DEEP_LINK_NESTED_URI) != Constants.DEEP_LINK_DEFAULT_NULL_NESTED_URI
            ) deepLinkKey =
                (intent.extras?.get(it) as Intent).data?.getQueryParameter(Constants.DEEP_LINK_NESTED_URI)
        }
        deepLinkKey?.let {
            intent.data = it.toUri()
            activity.intent = intent

        }
    }


    fun handleIntent(uri: Uri?, activity: Activity, navigateToDeepLink: (uri: Uri) -> Unit) {
        uri?.let {
            //Normal deep link uri that follows format https://orange.superapp/destination
            if (it.scheme == Constants.DEEP_LINK_SCHEME && it.host == Constants.DEEP_LINK_HOST) {
                if (it.getQueryParameter(Constants.DEEP_LINK_CUSTOM_URL)
                        .isNullOrBlank()
                ) {
                    navigateToDeepLink.invoke(it)
                } else ChromeCustomTab.launchUrl(
                    activity,
                    it.getQueryParameter(Constants.DEEP_LINK_CUSTOM_URL)
                        ?.toUri()!! //Extract nested url from query
                )
            } else {
                //Full dynamic link url received resolve it and get uri first
                ChromeCustomTab.launchUrl(
                    activity,
                    uri
                )
            }
        }
    }

    fun handleNotificationIntent(
        intent: Intent,
        activity: Activity,
        navigateToDeepLink: (uri: Uri) -> Unit
    ) {
        if (intent.data.toString().contains(Constants.DEEP_LINK_HOST)
                .not()
        ) //uri already resolved and handled do nothing
            handleIntent(intent.data, activity, navigateToDeepLink)
    }

}


object Constants {
    const val DEEP_LINK_SCHEME = "https"
    const val DEEP_LINK_HOST = "weather.page.link"
    const val DEEP_LINK_NESTED_URI = "nestedUri"
    const val DEEP_LINK_DEFAULT_NULL_NESTED_URI = "null"
    const val DEEP_LINK_CUSTOM_URL = "url"
}

object ChromeCustomTab {
    fun launchUrl(context: Context, uri: Uri) {
        val intent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(ContextCompat.getColor(context, R.color.med_orange))
                    .build()
            ).build()

        if (isValidURL(uri.toString())) intent.launchUrl(context, uri)
    }

    private fun isValidURL(url: String): Boolean =
        Patterns.WEB_URL.matcher(url).matches()

}