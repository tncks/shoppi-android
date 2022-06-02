@file:Suppress("DuplicatedCode")

package com.shoppi.app.common

import com.shoppi.app.ui.category.CategoryBoolLiveArray

fun Int.getSomeOriginInfo(): Int {

    var idxTargetingValue = 0
    val fakeIdx = this
    var i = 0

    var matchedCnt = 0
    for (data in CategoryBoolLiveArray.mUpdates) {
        if (!data) {
            if (matchedCnt == fakeIdx) {
                idxTargetingValue = i
                break
            }
            matchedCnt++
        }
        i++
    }

    return idxTargetingValue
}

/** 나중에 아래 주석 풀고 적당히 수정해서 사용하면됨 나중에 작업할것 */
// 나중에 history 여행기록탭에서, 바텀시트로 선택한 것 삭제 할때 나중에 작업할 때 주석 풀고 적용하여 아래 코드 사용 data 조건이 토글됨
// NOT 연산이 있고 없고 차이 위에랑 !data vs data
/*
fun Int.getSomeOriginInfo2(): Int {

    var idxTargetingValue = 0
    val fakeIdx = this
    var i = 0

    var matchedCnt = 0
    for (data in CategoryBoolLiveArray.mUpdates) {
        if (data) {
            if (matchedCnt == fakeIdx) {
                idxTargetingValue = i
                break
            }
            matchedCnt++
        }
        i++
    }

    return idxTargetingValue
}
 */