package com.abler31.geoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.abler31.geoapp.domain.models.Marker
import com.abler31.geoapp.domain.usecases.AddMarker
import com.abler31.geoapp.domain.usecases.DeleteMarker
import com.abler31.geoapp.domain.usecases.GetMarkers
import com.abler31.geoapp.presentation.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getMarkersUseCase: GetMarkers

    @Mock
    private lateinit var addMarkerUseCase: AddMarker

    @Mock
    private lateinit var deleteMarkerUseCase: DeleteMarker

    private lateinit var viewModel: MainViewModel

    private val dispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var observer: Observer<List<Marker>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = MainViewModel(
            getMarkersUseCase = getMarkersUseCase,
            addMarkerUseCase = addMarkerUseCase,
            deleteMarkerUseCase = deleteMarkerUseCase
        )
        viewModel.markers.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.markers.removeObserver(observer)
    }

    @Test
    fun `test loadMarkers`() = runTest {
        val markers = listOf(Marker(name = "Test", latitude =  0.0, longitude =  0.0))
        Mockito.`when`(getMarkersUseCase.invoke()).thenReturn(markers)

        viewModel.loadMarkers()
        advanceUntilIdle()

        Mockito.verify(observer).onChanged(markers)
    }

    @Test
    fun `test addMarker`() = runTest {
        val marker = Marker(name = "Test", latitude =  0.0, longitude =  0.0)

        viewModel.addMarker(marker.name, marker.latitude, marker.longitude)
        advanceUntilIdle()

        Mockito.verify(addMarkerUseCase).invoke(marker)
    }

    @Test
    fun `test deleteMarker`() = runTest {
        val marker = Marker(name = "Test", latitude =  0.0, longitude =  0.0)

        viewModel.deleteMarker(marker)
        advanceUntilIdle()

        Mockito.verify(deleteMarkerUseCase).invoke(marker)
    }
}