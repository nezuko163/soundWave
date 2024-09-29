package com.nezuko.auth

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nezuko.ui.theme.LightBlue

private const val TAG = "StartRoute"


@Composable
fun StartRoute(
    vm: AuthViewModel = hiltViewModel(),
    onSignInCLick: () -> Unit,
    onSignUpCLick: () -> Unit,
) {
    Log.i(TAG, "StartRoute: recomp")
    val context = LocalContext.current

    vm.setActivity(context as Activity)
    vm.checkNotificationPermission()

    val montserratFont = FontFamily(Font(com.nezuko.ui.R.font.montserrat_light))

    val signUp = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFont
            )
        ) {
            append("регистрация")
        }
    }

    val signIn = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFont
            )
        ) {
            append("войти")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                painter = painterResource(id = R.drawable.auth_logo),
                contentDescription = "лого",
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Добро пожаловать",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontFamily = FontFamily.Cursive,
                style = TextStyle(fontSize = 30.sp),
                color = LightBlue
            )
            Spacer(modifier = Modifier.padding(vertical = 50.dp))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onSignInCLick.invoke() },
                text = signIn,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = montserratFont
                ),
//                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onSignUpCLick.invoke() },
                text = signUp,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = montserratFont
                ),
//                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.padding(vertical = 40.dp))
        }
    }
}