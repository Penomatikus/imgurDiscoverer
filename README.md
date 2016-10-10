![Icon](https://github.com/Penomatikus/imgurDiscoverer/blob/master/resources/imgurdiscoverer.png?raw=true)
# imgurDiscoverer
The known image hoster and image based community does not provide full privacy protection to its hosted images. The program will exploit this circumstance and generates random hashes which may be a valid image name and displays all those in a user friendly GUI.    
You can find the imgur privacy police at: [Imgur privacy police](https://imgur.com/privacy)

### System requirements   
![Java Icon](http://findicons.com/files/icons/1008/quiet/16/java.png) Java Runtime Environment: [Download current version](https://java.com/de/download/)  
![RAM Icon](https://cdn3.iconfinder.com/data/icons/discovery/16x16/devices/gnome-dev-media-cf.png) RAM locked between: 125MB - 250MB. Average is ~180MB ( This tool handels a lot of image data )

  
### Short preview  
<img align="left" src="https://i.imgur.com/k692xXT.png" width="400">
This is how the program window looks like. There is a maximum amount of four images per row. Every image is inside a so called [Imagebox](https://github.com/Penomatikus/imgurDiscoverer/blob/master/src/imgurDiscoverer/frontent/componets/ImageBox.java), which background color is the most common color of the displayed image. <br> Moreover, the [Imageboxes](https://github.com/Penomatikus/imgurDiscoverer/blob/master/src/imgurDiscoverer/frontent/componets/ImageBox.java) responding to a single click, where you can select the images which you want to be saved elsewhere than the default download folderand a double click, where a new window will popup to show the image in full size. <br>
At the top, right to the slogan, you can find some buttons to start, stop, open settings, save selcted images and load a list of found image name hashes to download them again. <br> In addition, at the bottom of the window, you can find a progressbar, showing you the current amount of downloaded megabytes by the imgur discoverer. Of course you can change the maximum megabytes to download. <br> <br><img align="right" src="https://i.imgur.com/j8tTA5I.png" width="230">

There for you have to open the [SettingsWindow](https://github.com/Penomatikus/imgurDiscoverer/blob/master/src/imgurDiscoverer/frontent/frameextra/SettingsWindow.java). There are three different settings types. <br> <b>Advanced:</b> <br> In the advanced settings, you can change the total use of threads ( [Downloaders](https://github.com/Penomatikus/imgurDiscoverer/blob/master/src/imgurDiscoverer/backend/net/Downloader.java) ) to generate a random hash and trying to access an image with this name on imgur. Moreover, you can select the total amount of megabytes to download. <br><i>Note:</i> This works per started searches, not per started program-sessions.<br><b>Storage management:</b><br> Here you can select if the program should remember found valid hashes or those which don't result in an image. In addition you can choose the locations, where to store the images and the hashes, by clicking on the appropriate button. <br><b>Download management: </b> <br> In this area it is possible to choose the behavior only to check, if an hash is valid or not without downloading the image. Moreover, you can select if the [Downloaders](https://github.com/Penomatikus/imgurDiscoverer/blob/master/src/imgurDiscoverer/backend/net/Downloader.java) should stop immediately, when hitting the maximum download mark or when the cancel button was hit. Otherwise, if it's not selected, the [Downloaders](https://github.com/Penomatikus/imgurDiscoverer/blob/master/src/imgurDiscoverer/backend/net/Downloader.java) will take their time to end there current process of downloading and file writing. <br>

### How to use
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut vitae sodales dolor. Nam eget tempus nisl. Donec pulvinar felis a justo pharetra eleifend. Praesent nunc est, fermentum id maximus non, bibendum quis diam. Vivamus id accumsan nisi. Cras at orci pulvinar, tempor eros sed, faucibus metus. Aenean mattis leo in velit maximus, at commodo mauris lobortis. Morbi quis purus arcu. Morbi justo nunc, tincidunt eu dolor a, suscipit condimentum lectus.

Nunc facilisis purus at sem elementum, vel molestie diam accumsan. Nam non purus vel lacus iaculis vulputate. Vestibulum vitae purus et enim malesuada sagittis non non leo. Mauris tristique justo ac quam feugiat, vel imperdiet nunc semper. Fusce consequat ligula purus, vel pretium dui semper vitae. Sed vestibulum tortor dui, at placerat tellus sagittis vitae. Maecenas tempor eget odio ac placerat. Aliquam erat volutpat. Quisque elementum consequat turpis at dapibus. Mauris quis tempor nunc. Donec porttitor turpis vel dolor mollis sollicitudin. Etiam nisi ligula, laoreet at lacinia et, hendrerit eu nisi. Quisque dignissim iaculis imperdiet.
