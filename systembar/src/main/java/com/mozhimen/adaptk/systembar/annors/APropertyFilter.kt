package com.mozhimen.adaptk.systembar.annors

import androidx.annotation.IntDef
import androidx.annotation.Keep
import com.mozhimen.adaptk.systembar.cons.CProperty

/**
 * @ClassName StatusBarKType
 * @Description 后期还会加, 目前先预制这么多
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
@IntDef(value = [CProperty.NORMAL, CProperty.IMMERSED_SOFT, CProperty.IMMERSED_HARD, CProperty.IMMERSED_HARD_STICKY])
annotation class APropertyFilter