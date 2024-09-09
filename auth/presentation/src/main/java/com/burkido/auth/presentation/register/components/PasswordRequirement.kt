package com.burkido.auth.presentation.register.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.burkido.core.presentation.designsystem.ui.CheckIcon
import com.burkido.core.presentation.designsystem.ui.CrossIcon
import com.burkido.core.presentation.designsystem.ui.RuniqueCloneDarkRed
import com.burkido.core.presentation.designsystem.ui.RuniqueCloneGreen
import com.burkido.core.presentation.designsystem.ui.spacing

@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if(isValid) RuniqueCloneGreen else RuniqueCloneDarkRed
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}