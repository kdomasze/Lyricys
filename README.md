<h1 align="center">
<br>
  <img src="https://raw.githubusercontent.com/kdomasze/Lyricys/master/app/src/main/res/mipmap-xxxhdpi/ic_lyricys_round.png" alt="Lyricys logo" title="Lyricys" height="200" />
  <br>
Lyricys 
<br>
</h1>
<h4 align="center">An Android app for quickly getting the lyrics to your favorite songs.</h4>

<p align="center">
    <img src="https://github.com/kdomasze/Lyricys/blob/master/screenshots/mainInterface.png?raw=true"
         alt="Main Interface example" width="350">
  <img src="https://github.com/kdomasze/Lyricys/blob/master/screenshots/themeSwitchingExample.gif?raw=true"
         alt="Theme changing example" width="350">
  </a>

## What does it do?

Lyricys will display a simple interface with the lyrics to the currently playing song on your device. Simple as that!

## How does it work?

Lyricys makes use of Android's BroadcastReciever functionality in order to recieve metadata changes from different music apps. 
This metadata includes information such as the song's name and artist. 
With these details, [LyricWiki](http://lyrics.wikia.com/wiki/LyricWiki) can be scrapped for song lyrics and displayed on the app.

## Any other features?

Currently included are a toggle-able dark mode and a persistant notification to quickly switch to the app.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development.

### Prerequisites

The project was created using Android Studio Version 3.1.2.

### Instructions for Building

Pull the project and open the project in Android Studio. It should be ready for compilation out of the box.

## Authors

* **Kyle Domaszewicz** - [kdomasze](https://github.com/kdomasze)

## License

Pending
