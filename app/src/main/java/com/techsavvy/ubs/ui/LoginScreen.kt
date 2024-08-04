package com.techsavvy.ubs.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    var otpValue by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }


    Box(
    ) {
        Text("Login",
            style = MaterialTheme.typography.titleSmall
            , modifier = Modifier.align(Alignment.TopCenter).padding(5.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom =80.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BasicTextField(value = otpValue, onValueChange = {
                if (it.length <= 6) {
                    otpValue = it
                }
                if (it.length == 6) {
                    //perform next action
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .focusRequester(focusRequester),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hide()
                        // Handle the next button action
                        navController.navigate("dashboard")
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next
                ),
                decorationBox = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            repeat(6) { index ->
                                val char = when {
                                    index >= otpValue.length -> ""
                                    else -> otpValue[index].toString()
                                }

                                Card(
                                    shape = RoundedCornerShape(4.dp),
                                    border = BorderStroke(
                                        1.dp,
                                        if (char.isEmpty()) Color.LightGray else Color.Gray
                                    )
                                ) {
                                    Text(
                                        char,
                                        style = MaterialTheme.typography.titleLarge,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .width(40.dp)
                                            .padding(8.dp)
                                            .padding(top = 3.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            )

            Text(
                "Saisissez le NIP lié au numéro de mobile +*********427.",
                modifier = Modifier.padding(top = 30.dp),
                style = MaterialTheme.typography.bodySmall
            )
            TextButton(
                onClick = { /* Handle forgot pin click */ },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue)
            ) {
                Text("NIP oublié?")
            }
            TextButton(
                onClick = { /* Handle fingerprint login click */ },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue)
            ) {
                Text("Utiliser login avec empreinte digitale")
            }
        }
    }

}