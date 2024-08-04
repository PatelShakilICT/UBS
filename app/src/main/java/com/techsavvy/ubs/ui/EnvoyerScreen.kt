package com.techsavvy.ubs.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.techsavvy.ubs.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun EnvoyerScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    ""
                )
            }
            Text(
                "Demander ou partager",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 5.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Text(
                    "Demander",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(
                    color = Color(0xFF795657),
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 2.dp
                )
            }
//            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 5.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {

                Text(
                    "Partager",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(
                    color = Color(0xFFCDCCCC),
                    thickness = 2.dp
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Icon(
                Icons.Default.Info,
                "",
                tint = Color(0xFF3B6673)
            )
            Text(
                "Choisissez <<Partager>> afin de partager le montant entre plusieurs contacts.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 5.dp)
            )
        }

        var amount by remember { mutableStateOf("") }
        var contact by remember { mutableStateOf<Contact?>(null) }
        var isContactDialogOpen by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)
//                    .align(Alignment.Center)
                    .padding(10.dp)
                    .focusRequester(focusRequester),
                textStyle = TextStyle(fontSize = 26.sp, textAlign = TextAlign.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        // Handle the next action
                        if (contact != null && amount.isNotEmpty()) {
                            navController.navigate("success/$amount/${contact?.name}")
                        }
                    }
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        if (amount.isNotEmpty()) {
                            amount = amount.dropLast(1)
                        }
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_backspace),
                            "",
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                }
            )
            if (contact != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(32.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(LightGray)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    contact!!.name.first().toString() ?: "",
                                    modifier = Modifier.align(Alignment.Center),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Column() {
                            Text(
                                contact!!.name,
                                modifier = Modifier.padding(start = 5.dp),
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                contact!!.phoneNumber,
                                modifier = Modifier.padding(start = 5.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            TextButton(
                onClick = {
                    isContactDialogOpen = true
                },
                modifier = Modifier
//                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp, top = 40.dp)
            ) {
                Text(
                    "Selectionner un contact",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
            if (isContactDialogOpen) {
                ContactSelectionDialog(
                    onClose = {
                        isContactDialogOpen = false
                    }, onContactSelected = {
                        contact = it
                        isContactDialogOpen = false
                    })
            }

        }


    }
}

@Composable
fun ContactSelectionDialog(onContactSelected: (Contact) -> Unit, onClose: () -> Unit) {
    var isLoading by remember { mutableStateOf(true) }
    var contacts by remember { mutableStateOf(emptyList<Contact>()) }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        if(context.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            contacts = loadContacts(context)
        }else{
            onClose()
            Toast.makeText(context, "Please Allow Contacts Permission", Toast.LENGTH_SHORT).show()
        }
        isLoading = false
    }
    Dialog(
        onDismissRequest = {
            onClose()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    "Selectionner un contact",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                "Loading...",
                            )
                        }
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(contacts) {
                        Card(
                            onClick = {
                                onContactSelected(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            shape = RectangleShape
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(checked = false, onCheckedChange = { it1 ->
                                    onContactSelected(it)
                                })
                                Spacer(modifier = Modifier.width(5.dp))
                                Column() {
                                    Text(it.name, style = MaterialTheme.typography.titleSmall)
                                    Text(
                                        it.phoneNumber,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                        HorizontalDivider(thickness = .5.dp, color = Gray)

                    }
                }

            }

        }

    }
}


data class Contact(val name: String, val phoneNumber: String)

@SuppressLint("Range")
suspend fun loadContacts(context: Context): List<Contact> {
    val contactList = mutableListOf<Contact>()
    val contentResolver = context.contentResolver
    val cursor = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        null,
        null,
        null,
        null
    )
    if (cursor != null && cursor.moveToFirst()) {
        withContext(Dispatchers.IO) {
            do {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        val phoneNumber =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        contactList.add(Contact(name, phoneNumber))
                        phoneCursor.close()
                    }
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
    return contactList
}