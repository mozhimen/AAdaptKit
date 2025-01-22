package com.mozhimen.adaptk.systembar.annors

import androidx.annotation.Keep

/**
 * @ClassName StatusBarAnnor
 * @Description TODO
 * @Author mozhimen
 * @Version 1.0
 */
@Keep
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AAdaptKSystemBarPropertyOr(
    @APropertyFilterOr
    vararg val propertyOr: Int,
)
