package ru.practicum.composeexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practicum.composeexamples.domain.Contact
import ru.practicum.composeexamples.ui.theme.ComposeExamplesTheme
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    var needKuzyakin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeExamplesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactDetails(
                        contact = if (needKuzyakin) getKuzyakinContact() else getLukashinContact(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

    }
}

@Composable
fun ContactDetails(contact: Contact, modifier: Modifier = Modifier) {
    val normalText = TextStyle(fontSize = 18.sp)
    val largeText = TextStyle(fontSize = 26.sp)
    val smallText = TextStyle(fontSize = 14.sp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        ContactIcon(contact)
        Text(
            text = remember {
                contact.surname?.let { "${contact.name} ${contact.surname}" } ?: contact.name
            },
            textAlign = TextAlign.Center,
            style = normalText
        )

        Row(modifier = Modifier.padding(bottom = 48.dp)) {
            Text(
                text = remember { contact.familyName },
                textAlign = TextAlign.Center,
                style = largeText
            )
            if (contact.isFavorite) {
                Image(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp, 24.dp),
                    painter = painterResource(id = android.R.drawable.star_big_on),
                    contentDescription = stringResource(id = R.string.favourite_contact)
                )
            }
        }

        SmallContactDetails(stringResource(id = R.string.phone), contact.phone, smallText)
        SmallContactDetails(stringResource(id = R.string.address), contact.address, smallText)
        SmallContactDetails(stringResource(id = R.string.email), contact.email, smallText)
    }
}

@Composable
fun SmallContactDetails(leftText: String, rigthText: String?, textStyle: TextStyle) {
    if (rigthText == null)
        return

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(.5f)
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = remember { "${leftText}:" },
                style = textStyle
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(.5f)
                .padding(end = 16.dp),
        ) {
            Text(
                text = remember { rigthText },
                style = textStyle
            )
        }
    }
}

@Composable
fun ContactIcon(contact: Contact) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (contact.imageRes == null) {
            Image(
                modifier = Modifier.size(48.dp, 48.dp),
                painter = painterResource(id = R.drawable.circle),
                contentScale = ContentScale.None,
                alignment = Alignment.Center,
                contentDescription = stringResource(R.string.initials)
            )
            Text(
                text = remember { contact.name.take(1) + contact.familyName.take(1) }
            )
        } else {
            AsyncImage(
                model = R.drawable.kuzyakin,
                contentDescription = stringResource(R.string.photo),
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactLuksahinPreview() {
    ComposeExamplesTheme {
        ContactDetails(getLukashinContact())
    }
}

@Preview(showBackground = true)
@Composable
fun ContactKuzyakinPreview() {
    ComposeExamplesTheme {
        ContactDetails(getKuzyakinContact())
    }
}

fun getLukashinContact(): Contact {
    return Contact(
        name = "Евгений",
        surname = "Андреевич",
        familyName = "Лукашин",
        imageRes = null,
        isFavorite = true,
        phone = "+7 495 495 95 95",
        address = "г. Москва, 3-я улица Строителей, д. 25, кв. 12",
        email = "ELukashin@practicum.ru"
    )
}

fun getKuzyakinContact(): Contact {
    return Contact(
        name = "Василий",
        surname = null,
        familyName = "Кузякин",
        imageRes = R.drawable.kuzyakin,
        isFavorite = false,
        phone = "---",
        address = "Ивановская область, дер. Крутово, д. 4",
        email = null
    )
}