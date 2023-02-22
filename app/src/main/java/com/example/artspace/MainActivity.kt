package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

/**
 * Composable that contains the main app composable
 */
@Composable
fun ArtSpaceApp(){
    ArtSpaceScreen()
}

/**
 * Main app itself
 */
@Composable
fun ArtSpaceScreen(){

    var image by remember { mutableStateOf(0) } // index of image in list, to be displayed in app
    val gallery:ArrayList<Art> = ArrayList() // list that contains images and information about them, to be displayed in app
    gallery.add(Art(painterResource(id = R.drawable.naomi),"dog","Mr Johnson","2015"))
    gallery.add(Art(painterResource(id = R.drawable.foto), "random dude","John Doe","2020"))
    gallery.add(Art(painterResource(id = R.drawable.ic_launcher_foreground), "This image","Mr. Google","1955"))

    // Column in which all elements in the app are contained
    Column(modifier = Modifier.padding(bottom = 40.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
        ImageFrameDisplay(gallery[image].image) // Composable that contains the image that is being displayed
        Spacer(modifier = Modifier.height(30.dp))
        ArtworkInfo(title  = gallery[image].title, artist = gallery[image].artist, year = gallery[image].year) // Composable that contains the information card regarding the image
        Spacer(modifier = Modifier.height(30.dp))
        ButtonsRow(previous = {if (image-1>=0) image-=1}, next = {if (image+1<gallery.size) image+=1} ) // Composable that contains next and previous buttons to change image on screen
    }
}

/**
 * Composable that contains and styles the image that is displayed
 * painter:Painter -> image to be displayed
 */
@Composable
fun ImageFrameDisplay(painter:Painter){
        Card(modifier = Modifier
            .border(border = BorderStroke(width = 4.dp, Color.Gray))
            .height(450.dp), elevation = 15.dp,) {
            Image(painter = painter, contentDescription = null, modifier = Modifier.padding(35.dp))
        }
}

/**
 * Composable that contains the information card regarding the displayed image
 * title -> title of the image
 * artist -> artist of the image
 * year -> year of the image
 */
@Composable
fun ArtworkInfo(title:String, artist:String, year:String){
    Row(modifier = Modifier
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp), elevation = 15.dp,) {
            Column(modifier = Modifier.padding(15.dp)) {
                Text(title, fontSize = 32.sp, fontWeight = FontWeight.Light)
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append(artist)
                    }
                    append("\t($year)")
                })
            }
        }
    }
}

/**
 * Composable that contains buttons that allow the user to change the image
 * previous -> function that changes the image to the previous one
 * next -> function that changes the image to the next one
 */
@Composable
fun ButtonsRow(modifier: Modifier = Modifier, previous:() -> Unit, next:() -> Unit){
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
        Button(onClick = previous , modifier = Modifier
            .height(40.dp)
            .width(120.dp)) {
            Text(text = "Previous")
        }
        Button(onClick = next , modifier = Modifier
            .height(40.dp)
            .width(120.dp)) {
            Text(text = "Next")
        }
    }
}

/**
 * class that contains the information of each image
 */
private class Art(val image: Painter, val title:String, val artist:String, val year:String)