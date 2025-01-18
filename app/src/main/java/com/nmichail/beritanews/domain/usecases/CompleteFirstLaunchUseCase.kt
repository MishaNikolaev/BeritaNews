package com.nmichail.beritanews.domain.usecases

import com.nmichail.beritanews.data.datastore.PreferenceManager
import javax.inject.Inject

class CompleteFirstLaunchUseCase @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke() {
        preferenceManager.setFirstLaunch(false)
    }
}