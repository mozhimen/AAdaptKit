package com.mozhimen.adaptk.systembar.annors

/**
 * @ClassName StatusBarAnnor
 * @Description TODO
 * @Author mozhimen
 * @Version 1.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AAdaptKSystemBarPropertyAnd(
    @APropertyFilterAnd
    vararg val propertyAnd: Int,
)
