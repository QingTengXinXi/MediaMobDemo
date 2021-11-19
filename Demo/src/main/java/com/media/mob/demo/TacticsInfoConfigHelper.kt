package com.media.mob.demo

import com.media.mob.bean.TacticsInfo
import com.media.mob.platform.IPlatform
import java.util.concurrent.CopyOnWriteArrayList

object TacticsInfoConfigHelper {

    val splashTacticsInfoList: ArrayList<TacticsInfo> by lazy {
        ArrayList<TacticsInfo>().apply {
            add(TacticsInfo("5152507", "887486168", IPlatform.PLATFORM_CSJ))
            add(TacticsInfo("5152507", "887621444", IPlatform.PLATFORM_CSJ))
            add(TacticsInfo("5152507", "887621445", IPlatform.PLATFORM_CSJ))
            add(TacticsInfo("1111543873", "7021586070555663", IPlatform.PLATFORM_YLH))
            add(TacticsInfo("1111543873", "4012162059690637", IPlatform.PLATFORM_YLH))
            add(TacticsInfo("1111543873", "2092864049790813", IPlatform.PLATFORM_YLH))
            add(TacticsInfo("cd5b6b54", "7421681", IPlatform.PLATFORM_BQT))
            add(TacticsInfo("cd5b6b54", "7781972", IPlatform.PLATFORM_BQT))
            add(TacticsInfo("cd5b6b54", "7781973", IPlatform.PLATFORM_BQT))
            add(TacticsInfo("806300001", "8063000001", IPlatform.PLATFORM_KS))
            add(TacticsInfo("806300001", "8063000016", IPlatform.PLATFORM_KS))
            add(TacticsInfo("806300001", "8063000017", IPlatform.PLATFORM_KS))
        }
    }

    val rewardVideoTacticsInfoList: ArrayList<TacticsInfo> by lazy {
        ArrayList<TacticsInfo>().apply {
            add(TacticsInfo("5152507", "946871312", IPlatform.PLATFORM_CSJ))
            add(TacticsInfo("5152507", "947122318", IPlatform.PLATFORM_CSJ))
            add(TacticsInfo("5152507", "947122321", IPlatform.PLATFORM_CSJ))
            add(TacticsInfo("1111543873", "2042846457377656", IPlatform.PLATFORM_YLH))
            add(TacticsInfo("1111543873", "8092860240237936", IPlatform.PLATFORM_YLH))
            add(TacticsInfo("1111543873", "7042062220846002", IPlatform.PLATFORM_YLH))
            add(TacticsInfo("cd5b6b54", "7781025", IPlatform.PLATFORM_BQT))
            add(TacticsInfo("cd5b6b54", "7785543", IPlatform.PLATFORM_BQT))
            add(TacticsInfo("cd5b6b54", "7785548", IPlatform.PLATFORM_BQT))
            add(TacticsInfo("806300001", "8063000002", IPlatform.PLATFORM_KS))
            add(TacticsInfo("806300001", "8063000018", IPlatform.PLATFORM_KS))
            add(TacticsInfo("806300001", "8063000019", IPlatform.PLATFORM_KS))
        }
    }
}