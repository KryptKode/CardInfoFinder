package com.kryptkode.cardinfofinder.ui.ocrinput

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.getbouncer.cardscan.ui.CardScanActivity
import com.getbouncer.cardscan.ui.CardScanActivityResult
import com.kryptkode.cardinfofinder.BuildConfig
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.util.StringResource
import javax.inject.Inject

sealed class ScanCardNumberResult {

    data class Success(val cardNumber: String) : ScanCardNumberResult()
    object UserCancelled : ScanCardNumberResult()
    object UserEnterManually : ScanCardNumberResult()
    data class Error(val message: String) : ScanCardNumberResult()
}

class ScanCardNumberContract @Inject constructor(
    private val stringResource: StringResource
) : ActivityResultContract<Unit, ScanCardNumberResult>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return CardScanActivity.buildIntent(
            context = context,
            apiKey = BuildConfig.SCAN_CARD_API_KEY,
            enableEnterCardManually = true,
        )!!
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ScanCardNumberResult {
        return if (resultCode == Activity.RESULT_OK) {
            val scanResult = intent?.getParcelableExtra<CardScanActivityResult>(CardScanActivity.RESULT_SCANNED_CARD)
            val cardNumber = scanResult?.pan
            return if (cardNumber != null && cardNumber.isNotEmpty()) {
                ScanCardNumberResult.Success(cardNumber)
            } else {
                ScanCardNumberResult.Error(getString(R.string.scan_card_error_msg))
            }

        } else {
            when {
                intent.isUserCanceled() -> ScanCardNumberResult.UserCancelled
                intent.isCameraError() -> ScanCardNumberResult.Error(getString(R.string.scan_card_camera_error_msg))
                intent.isAnalyzerFailure() -> ScanCardNumberResult.Error(
                    getString(R.string.scan_card_analyser_error_msg)
                )
                intent.isEnterCardManually() -> ScanCardNumberResult.UserEnterManually
                else -> ScanCardNumberResult.Error(getString(R.string.scan_card_error_msg))
            }
        }
    }

    private fun getString(resId: Int): String {
        return stringResource.getString(resId)
    }
}

private fun getCanceledReason(intent: Intent?): Int =
    intent?.getIntExtra(CardScanActivity.RESULT_CANCELED_REASON, Int.MIN_VALUE) ?: Int.MIN_VALUE

private fun Intent?.isUserCanceled(): Boolean = getCanceledReason(this) == CardScanActivity.CANCELED_REASON_USER
private fun Intent?.isCameraError(): Boolean = getCanceledReason(this) == CardScanActivity.CANCELED_REASON_CAMERA_ERROR
private fun Intent?.isAnalyzerFailure(): Boolean =
    getCanceledReason(this) == CardScanActivity.CANCELED_REASON_ANALYZER_FAILURE

private fun Intent?.isEnterCardManually(): Boolean =
    getCanceledReason(this) == CardScanActivity.CANCELED_REASON_ENTER_MANUALLY
