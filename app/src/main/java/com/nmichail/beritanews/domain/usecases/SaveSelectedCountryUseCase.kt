package com.nmichail.beritanews.domain.usecases

import com.nmichail.beritanews.data.datastore.PreferenceManager
import javax.inject.Inject

class SaveSelectedCountryUseCase @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke(countryCode: String) {
        preferenceManager.saveSelectedCountry(countryCode)
    }
}