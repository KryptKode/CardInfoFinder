package com.kryptkode.cardinfofinder.ui.ocrinput

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.databinding.FragmentOcrInputBinding
import com.kryptkode.cardinfofinder.navigator.Navigator
import com.kryptkode.cardinfofinder.util.ocr.TextReaderAnalyzer
import com.kryptkode.cardinfofinder.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class OcrInputFragment : Fragment(R.layout.fragment_ocr_input) {

    @Inject
    lateinit var navigator: Navigator

    private val binding by viewBinding(FragmentOcrInputBinding::bind)

    private val cameraExecutor by lazy { Executors.newSingleThreadExecutor() }

    private val cameraPermission = registerForActivityResult(RequestPermission()) { granted ->
        if (granted) {
            startCamera()
        } else {
            navigator.navigateUp()
        }
    }

    private val imageAnalyzer by lazy {
        ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
            .also {
                it.setAnalyzer(
                    cameraExecutor,
                    TextReaderAnalyzer(::onTextFound)
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ActivityCompat.checkSelfPermission(requireContext(), CAMERA_PERMISSION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), CAMERA_PERMISSION)) {
                // show rationale
            } else {
                cameraPermission.launch(CAMERA_PERMISSION)
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                val preview = Preview.Builder()
                    .build()
                    .also { it.setSurfaceProvider(binding.cameraPreviewView.surfaceProvider) }
                cameraProviderFuture.get().bind(preview, imageAnalyzer)
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun ProcessCameraProvider.bind(
        preview: Preview,
        imageAnalyzer: ImageAnalysis
    ) = try {
        unbindAll()
        bindToLifecycle(
            viewLifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            imageAnalyzer
        )
    } catch (ise: IllegalStateException) {
        // Thrown if binding is not done from the main thread
        Timber.e(ise, "Binding failed")
    }

    private fun onTextFound(foundText: String) {
        Timber.d("We got new text: $foundText")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}
