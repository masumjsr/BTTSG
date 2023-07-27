package com.mssoftinc.bttsg.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ThemeRadioGroupUsage(position :Int ,onItemClick: (Int) -> Unit) {
    val kinds = listOf("Auto",  "Dark","Light")
    val (selected, setSelected) = remember { mutableStateOf(position) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){

        ThemeRadioGroup(
            mItems = kinds,
            selected,
            setSelected={
                setSelected.invoke(it)
                onItemClick.invoke(it)
            }
        )

    }
}

@Composable
fun ThemeRadioGroup(
    mItems: List<String>,
    selected: Int,
    setSelected: (selected: Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        mItems.forEachIndexed { index,  item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selected == index,
                    onClick = {
                        setSelected(index)
                    },
                    enabled = true,
                )
                Text(text = item, modifier = Modifier.clickable {

                    setSelected(index)
                })
            }
        }
    }
}
