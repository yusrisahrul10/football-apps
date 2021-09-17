package com.dscunikom.android.footballmatchschedule.utils

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class CoroutineContextProviderTest : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}