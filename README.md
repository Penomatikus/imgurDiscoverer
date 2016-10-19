![Icon](https://github.com/Penomatikus/imgurDiscoverer/blob/master/resources/imgurdiscoverer.png?raw=true)
# imgurDiscoverer
The known image hoster and image based community does not provide full privacy protection to its hosted images. The program will exploit this circumstance and generates random hashes which may be a valid image name and displays all those in a user friendly GUI.    
You can find the imgur privacy police at: [Imgur privacy police](https://imgur.com/privacy)

### System requirements   
![Java Icon](http://findicons.com/files/icons/1008/quiet/16/java.png) Java Runtime Environment: [Download current version](https://java.com/de/download/)  
![RAM Icon](https://cdn3.iconfinder.com/data/icons/discovery/16x16/devices/gnome-dev-media-cf.png) RAM locked between: 125MB - 250MB. Average is ~180MB ( This tool handels a lot of image data )
  
### Download
<img algin="left" src="https://cdn4.iconfinder.com/data/icons/gnome-desktop-icons-png/PNG/32/Gnome-System-Software-Installer-32.png" width="16"> Download Linux: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Not available now.  
<img algin="left" src="https://cdn4.iconfinder.com/data/icons/gnome-desktop-icons-png/PNG/32/Gnome-System-Software-Installer-32.png" width="16"> Download OSX: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Not available now.   
<img algin="left" src="https://cdn4.iconfinder.com/data/icons/gnome-desktop-icons-png/PNG/32/Gnome-System-Software-Installer-32.png" width="16"> Download Windows: &nbsp;Not available now.  

### Short preview: Program  
<img align="left" src="https://i.imgur.com/ZzO5hIE.png" width="1100"> 
  
  
### Customizable program settings  
* Threads: Choose between 2, 4, 8, 16, 32 or 64 threads. A single thread handels the generation of hashes, thoose validation and download task. Use more, if you want faster results. However, be carefull. Imgur might notice your heavy connection rate to their server and temporary blocks your IP.
* Maximum megabytes ( mb ) to download: Choose between 50, 100, 150, 200, 250, 300, 350, 400, 450, 500 or 550 megabytes as maximum download size in total. Is this limit reached, all current download tasks will finish and no new one will start.
* Generated hashes to file: Choose if the found and / or not found hashes should be written into a file, after a download session. You might want to reuse them for other purposes. Moreover, the directory for storing can be changed.
* Images to file: All found images will be saved in a custom directory. By default, this is the systems tmp directory. Since the total data size of downloaded images will be accumulate per download session, this directory should not be changed. Choose the images you want to save per single click on their thumbnail and use the save button on the upper hand of the program. This will open a new dialog for choosing a directory to copy them to. 
* Don't download images: In case your system running on low resources or you don't want to waste space on your hard disk, you have the possibility to let the program only check if a certian image (hash) does exists on the server. Then no single image will be downloaded and an empty thumbnail will be added with the hash as description to the overview. 

### Geek stuff

<img align="left" src="https://i.imgur.com/3lHA3x3.png" width="1100">  
The above screenshot shows the programs CPU usage and used heap after ten minutes runtime. There where 64 threads in use. As you can see, the average CPU usage is around 20% - 25%. Thoose high dancing oscillations of the amplitudes marking image copy processes by the user. When constantly scrolled, the CPU usage reaches an average of 25% - 35%, due to a bunch of rendering processes. 

 


### Hack the start parameter
Since this program is written in Java, you can change the JVM arguments of the jar file. 
