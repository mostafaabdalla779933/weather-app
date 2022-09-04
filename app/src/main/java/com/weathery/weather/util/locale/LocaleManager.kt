package com.weathery.weather.util.locale

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity
import java.util.*

/**
 * LocaleManager is a tool to manage your application locale and language.
 *
 * Once you set a desired locale using [setLocale] method, LocaleManager will enforce your application
 * to provide correctly localized data via [Resources] class.
 */
class LocaleManager private constructor(
    private val store: LocaleStore,
    private val delegate: UpdateLocaleDelegate
) {

    internal var systemLocale: Locale = defaultLocale

    /**
     * Creates and sets a [Locale] using language, country and variant information.
     *
     * See the [Locale] class description for more information about valid language, country
     * and variant values.
     */
    @JvmOverloads
    fun setLocale(context: Context, language: String, country: String = "", variant: String = "") {
        setLocale(context, Locale(language, country, variant))
    }

    /**
     * Sets a [Locale] which will be used to localize all data coming from [Resources] class.
     *
     * <p>Note that you need to update all already fetched locale-based data manually.
     * [LocaleManager] is not responsible for that.
     *
     * <p>Note that any call to [setLocale] stops following the system locale and resets
     * [isFollowingSystemLocale] setting.
     */
    fun setLocale(context: Context, locale: Locale) {
        store.setFollowSystemLocale(false)
        persistAndApply(context, locale)
    }

    /**
     * Returns the active [Locale].
     */
    fun getLocale(): Locale {
        return store.getLocale()
    }

    /**
     * Returns a language code which is a part of the active [Locale].
     *
     * Deprecated ISO language codes "iw", "ji", and "in" are converted
     * to "he", "yi", and "id", respectively.
     */
    fun getLanguage(): String {
        return verifyLanguage(getLocale().language)
    }

    /**
     * Applies the system locale and starts following it whenever it changes.
     */
    fun setFollowSystemLocale(context: Context) {
        store.setFollowSystemLocale(true)
        persistAndApply(context, systemLocale)
    }

    /**
     * Indicates whether the system locale is currently applied.
     */
    fun isFollowingSystemLocale() = store.isFollowingSystemLocale()

    private fun verifyLanguage(language: String): String {
        // get rid of deprecated language tags
        return when (language) {
            "iw" -> "he"
            "ji" -> "yi"
            "in" -> "id"
            else -> language
        }
    }

    internal fun initialize(application: Application) {
        application.registerActivityLifecycleCallbacks(
            LocaleManagerActivityLifecycleCallbacks {
                applyForActivity(it)
            }
        )
        application.registerComponentCallbacks(
            LocaleManagerApplicationCallbacks {
                processConfigurationChange(application, it)
            }
        )
        val locale = if (store.isFollowingSystemLocale()) {
            systemLocale // might be different on every app launch
        } else {
            store.getLocale()
        }
        persistAndApply(application, locale)
    }

    private fun persistAndApply(context: Context, locale: Locale) {
        store.persistLocale(locale)
        delegate.applyLocale(context, locale)
    }

    private fun applyLocale(context: Context) {
        delegate.applyLocale(context, store.getLocale())
    }

    private fun processConfigurationChange(context: Context, config: Configuration) {
        systemLocale = config.getLocaleCompat()
        if (store.isFollowingSystemLocale()) {
            persistAndApply(context, systemLocale)
        } else {
            applyLocale(context)
        }
    }

    private fun applyForActivity(activity: Activity) {
        applyLocale(activity)
        activity.resetTitle()
    }

    companion object {
        @SuppressLint("ConstantLocale")
        private val defaultLocale: Locale = Locale.getDefault()

        private lateinit var instance: LocaleManager

        /**
         * Returns the global instance of [LocaleManager] created via init method.
         *
         * @throws IllegalStateException if it was not initialized properly.
         */
        @JvmStatic
        fun getInstance(): LocaleManager {
            check(Companion::instance.isInitialized) { "LocaleManager should be initialized first" }
            return instance
        }

        /**
         * Creates and sets up the global instance using a provided language and the default store.
         */
        @JvmStatic
        fun init(application: Application, defaultLanguage: String): LocaleManager {
            return init(application, Locale(defaultLanguage))
        }

        /**
         * Creates and sets up the global instance using a provided locale and the default store.
         */
        @JvmStatic
        @JvmOverloads
        fun init(
            application: Application,
            defaultLocale: Locale = Locale.getDefault()
        ): LocaleManager {
            return init(application, PreferenceLocaleStore(application, defaultLocale))
        }

        /**
         * Creates and sets up the global instance.
         *
         * This method must be called before any calls to [LocaleManager] and may only be called once.
         */
        @JvmStatic
        fun init(application: Application, store: LocaleStore): LocaleManager {
            check(!Companion::instance.isInitialized) { "Already initialized" }
            val localeManager = LocaleManager(store, UpdateLocaleDelegate())
            localeManager.initialize(application)
            instance = localeManager
            return localeManager
        }

        internal fun createInstance(
            store: LocaleStore,
            delegate: UpdateLocaleDelegate
        ): LocaleManager {
            return LocaleManager(store, delegate)
        }


        @JvmStatic
        fun initAndFollowSystemLocale(application: Application) {
            init(application, Locale.getDefault())
            getInstance().setFollowSystemLocale(application)
        }

        fun changeAppLang(activity: FragmentActivity) {
            if (getInstance().getLanguage() == AR) {
                getInstance().setLocale(activity, EN, "GB")
            } else {
                getInstance().setLocale(activity, AR, "EG")
            }
            restart(activity)
        }

        fun changeAppLangToSystem(activity: FragmentActivity) {
            getInstance().setLocale(activity, defaultLocale)
            getInstance().setFollowSystemLocale(activity)
            restart(activity)
        }

        private fun restart(activity: FragmentActivity) {
            activity.recreate()
        }

        fun getAppLanguage(): String = getInstance().getLanguage()

        const val EN = "en"
        const val AR = "ar"

        @JvmStatic
        fun fixWebViewLocalEdgeCase(context: Context) {
            WebView(context).destroy() // fixes webview localization edge cases
            getInstance().setLocale(context, getInstance().getLocale())
        }
    }

}
