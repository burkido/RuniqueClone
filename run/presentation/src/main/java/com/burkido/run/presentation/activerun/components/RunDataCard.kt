package com.burkido.run.presentation.activerun.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burkido.core.presentation.designsystem.ui.RuniqueCloneTheme
import com.burkido.core.presentation.designsystem.ui.spacing
import com.burkido.core.presentation.ui.formatted
import com.burkido.core.presentation.ui.toFormattedKm
import com.burkido.core.presentation.ui.toFormattedPace
import com.burkido.run.domain.model.RunData
import com.burkido.run.presentation.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Composable
fun RunDataCard(
    elapsedTime: Duration,
    runData: RunData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RunDataItem(
            title = stringResource(id = R.string.duration),
            value = elapsedTime.formatted(),
            valueFontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RunDataItem(
                title = stringResource(id = R.string.distance),
                value = (runData.distanceMeters / 1000.0).toFormattedKm(),
                modifier = Modifier.defaultMinSize(minWidth = 75.dp)
            )
            RunDataItem(
                title = stringResource(id = R.string.pace),
                value = elapsedTime.toFormattedPace(
                    distanceKm = (runData.distanceMeters / 1000.0)
                ),
                modifier = Modifier
                    .defaultMinSize(minWidth = 75.dp)
            )
        }
    }
}

@Composable
private fun RunDataItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueFontSize: TextUnit = 16.sp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = valueFontSize
        )
    }
}

@Preview
@Composable
private fun RunDataCardPreview() {
    RuniqueCloneTheme {
        RunDataCard(
            elapsedTime = 10.minutes,
            runData = RunData(
                distanceMeters = 3425,
                pace = 3.minutes
            )
        )
    }
}