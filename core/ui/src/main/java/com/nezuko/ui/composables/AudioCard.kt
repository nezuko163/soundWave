package com.nezuko.ui.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.nezuko.data.model.Audio
import com.nezuko.ui.R
import com.nezuko.ui.theme.Dimens
import com.nezuko.ui.theme.Spacing


private const val TAG = "AUDIO_CARD"

@Composable
fun AudioCard(
    modifier: Modifier = Modifier,
    audio: Audio,
    rightActionButton: @Composable RowScope.(Audio) -> Unit,
    onClick: (Audio) -> Unit,
) {
    Log.i(TAG, "AudioCard: $audio")

    Row(
        modifier = modifier
            .clickable { onClick(audio) }
            .fillMaxWidth()
            .height(Dimens.default.audioIconSmall)
    ) {
        ImageFromURI(
            modifier = Modifier
                .size(Dimens.default.audioIconSmall)
                .padding(Spacing.default.small),
            url = audio.artUrl,
            errorImageResource = R.drawable.audio
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(
                text = audio.title,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .wrapContentHeight(Alignment.Bottom),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )

            Text(
                text = audio.artist,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .wrapContentHeight(Alignment.Top),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        rightActionButton(audio)

    }
}