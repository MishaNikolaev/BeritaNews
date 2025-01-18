package com.nmichail.beritanews.ui.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.beritanews.data.datastore.PreferenceManager
import com.nmichail.beritanews.ui.navigation.StartUpDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartUpViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<StartUpDestination>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            val isFirstLaunch = preferenceManager.isFirstLaunch.first()
            val selectedCountry = preferenceManager.selectedCountry.first()

            if (isFirstLaunch) {
                _navigationEvent.emit(StartUpDestination.WelcomeScreen)
            } else if (selectedCountry == null) {
                _navigationEvent.emit(StartUpDestination.CountrySelectionScreen)
            } else {
                _navigationEvent.emit(StartUpDestination.HomeScreen)
            }
        }
    }

    fun completeFirstLaunch() {
        viewModelScope.launch {
            preferenceManager.setFirstLaunch(false)
        }
    }

    fun saveSelectedCountry(countryCode: String) {
        viewModelScope.launch {
            preferenceManager.saveSelectedCountry(countryCode)
        }
    }
}
