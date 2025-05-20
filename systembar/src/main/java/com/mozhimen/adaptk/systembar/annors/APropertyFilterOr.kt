package com.mozhimen.adaptk.systembar.annors

import androidx.annotation.IntDef
import com.mozhimen.adaptk.systembar.cons.CPropertyOr

/**
 * @ClassName StatusBarKType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
@IntDef(
    value = [
        CPropertyOr.NORMAL,
        CPropertyOr.IMMERSED_OPEN,
        CPropertyOr.IMMERSED_HARD,
        CPropertyOr.IMMERSED_STICKY,
        CPropertyOr.HIDE_STATUS_BAR,
        CPropertyOr.HIDE_NAVIGATION_BAR,
        CPropertyOr.HIDE_TITLE_BAR,
        CPropertyOr.HIDE_ACTION_BAR,
        CPropertyOr.OVERLAY_STATUS_BAR,
        CPropertyOr.OVERLAY_NAVIGATION_BAR,
        CPropertyOr.LAYOUT_STABLE,
        CPropertyOr.FITS_SYSTEM_WINDOWS,
        CPropertyOr.SYSTEM_BAR_BG_TRANSLUCENT,
        CPropertyOr.STATUS_BAR_ICON_LOW_PROFILE,
        CPropertyOr.THEME_CUSTOM,
        CPropertyOr.THEME_DARK
    ]
)
annotation class APropertyFilterOr