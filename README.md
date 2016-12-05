![Icon](https://github.com/Penomatikus/imgurDiscoverer/blob/master/resources/imgurdiscoverer.png?raw=true)
# imgurDiscoverer
The known image hoster and image based community does not provide full privacy protection to its hosted images. The program will exploit this circumstance and generates random hashes which may be a valid image name and displays all those in a user friendly GUI.    
You can find the imgur privacy police at: [Imgur privacy police](https://imgur.com/privacy)
***
### System requirements   
![Java Icon](http://findicons.com/files/icons/1008/quiet/16/java.png) Java Runtime Environment: [Download current version](https://java.com/de/download/)  
![RAM Icon](https://cdn3.iconfinder.com/data/icons/discovery/16x16/devices/gnome-dev-media-cf.png) RAM locked between: 125MB - 250MB. Average is ~180MB ( This tool handels a lot of image data )
  ***
### Download
<img algin="left" src="https://cdn4.iconfinder.com/data/icons/gnome-desktop-icons-png/PNG/32/Gnome-System-Software-Installer-32.png" width="16"> Download Linux: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Current release](https://github.com/Penomatikus/imgurDiscoverer/releases/tag/1.0beta)   
<img algin="left" src="https://cdn4.iconfinder.com/data/icons/gnome-desktop-icons-png/PNG/32/Gnome-System-Software-Installer-32.png" width="16"> Download OSX: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Not available now.   
<img algin="left" src="https://cdn4.iconfinder.com/data/icons/gnome-desktop-icons-png/PNG/32/Gnome-System-Software-Installer-32.png" width="16"> Download Windows: &nbsp;Not available now.   
<i><u>Or:</u></i>  
<img algin="left" src="https://cdn4.iconfinder.com/data/icons/gnome-desktop-icons-png/PNG/32/Gnome-System-Software-Installer-32.png" width="16"> Download *.jar: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Not available now.   
`By downloading only the single executable .jar file, you have to set the JVM arguments by yourself. This might be helpful, if you encounter some performance issues and / or you want to try something else.`
***
### Short preview: Program  
<img align="left" src="https://i.imgur.com/ZzO5hIE.png" width="1100"> 
***
***
***
***
***
***
***
***
***
***
***
***
***
***
***
***
***
***
### Customizable program settings  
* **Threads**: Choose between 2, 4, 8, 16, 32 or 64 threads. A single thread handels the generation of hashes, thoose validation and download task. Use more, if you want faster results. However, be carefull. Imgur might notice your heavy connection rate to their server and temporary blocks your IP.
* **Maximum megabytes ( mb ) to download**: Choose between 50, 100, 150, 200, 250, 300, 350, 400, 450, 500 or 550 megabytes as maximum download size in total. Is this limit reached, all current download tasks will finish and no new one will start.
* **Generated hashes to file**: Choose if the found and / or not found hashes should be written into a file, after a download session. You might want to reuse them for other purposes. Moreover, the directory for storing can be changed.
* **Images to file**: All found images will be saved in a custom directory. By default, this is the systems tmp directory. Since the total data size of downloaded images will be accumulate per download session, this directory should not be changed. Choose the images you want to save per single click on their thumbnail and use the save button on the upper hand of the program. This will open a new dialog for choosing a directory to copy them to. 
* **Don't download images**: In case your system running on low resources or you don't want to waste space on your hard disk, you have the possibility to let the program only check if a certian image (hash) does exists on the server. Then no single image will be downloaded and an empty thumbnail will be added with the hash as description to the overview. 
  
***
### Geek stuff

<img align="left" src="https://i.imgur.com/3lHA3x3.png" width="1100">  
The above screenshot shows the programs CPU usage and used heap after ten minutes runtime. There where 64 threads in use. As you can see, the average CPU usage is around 20% - 25%. Thoose high dancing oscillations of the amplitudes marking image copy processes by the user. When constantly scrolled, the CPU usage reaches an average of 25% - 35%, due to a bunch of rendering processes. 

#### Img > VRAM
All rendered images are located in the [VRAM](https://docs.oracle.com/javase/7/docs/api/java/awt/image/VolatileImage.html) of the machine. This reduces the consumption of the heap a lot. The picture on the upper right side shows the used/allocated heap and was made when the program found and displayed around 1000 images. When [BufferedImage](https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html) was used, the used/allocated heap went from 50mb to 2000mb in seconds because if its implementation storring huge image rasters via int[] or byte[] within the heap. Also tuning the JVM trying to get the memory situation under control just ended in OOM at some point. 

#### JVM options
The default and recommended JVM arguments used are:   

| Option | Description |  
| --- | --- |   
| `-Xms128m` | The -Xms option sets the initial and minimum Java heap size. The Java heap (the “heap”) is the part of the memory where blocks of memory are allocated to objects and freed during garbage collection. [(link)](https://docs.oracle.com/cd/E13150_01/jrockit_jvm/jrockit/jrdocs/refman/optionX.html) |  
| `-Xmx325m` | This option sets the maximum Java heap size. The Java heap (the “heap”) is the part of the memory where blocks of memory are allocated to objects and freed during garbage collection. Depending upon the kind of operating system you are running, the maximum value you can set for the Java heap can vary. [(link)](https://docs.oracle.com/cd/E13150_01/jrockit_jvm/jrockit/jrdocs/refman/optionX.html) |  
| `-XX:GCTimeRatio=50` | The -XX:GCTimeRatio option specifies the ratio of the time spent outside the garbage collection (for example, the time spent for application execution) to the time spent in the garbage collection. [(link)](https://docs.oracle.com/cd/E15289_01/doc.40/e15062/optionxx.htm#BABBDEIF) |  
| `-XX:+UseParallelOldGC` | Use parallel garbage collection for the full collections. Enabling this option automatically sets -XX:+UseParallelGC. [(link)](http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html#BehavioralOptions) |  
| `-XX:+DisableExplicitGC` | By default calls to System.gc() are enabled (-XX:-DisableExplicitGC). Use -XX:+DisableExplicitGC to disable calls to System.gc(). Note that the JVM still performs garbage collection when necessary. [(link)](http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html#BehavioralOptions)  |  
| `-XX:MaxGCPauseMillis=150` | Sets a target for the maximum GC pause time. This is a soft goal, and the JVM will make its best effort to achieve it. [(link)](http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html#G1Options) |  
| `-XX:InitiatingHeapOccupancyPercent=0` | Percentage of the (entire) heap occupancy to start a concurrent GC cycle. It is used by GCs that trigger a concurrent GC cycle based on the occupancy of the entire heap, not just one of the generations (e.g., G1). A value of 0 denotes 'do constant GC cycles'. The default value is 45. [(link)](http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html#G1Options) |	 
**Note**: Still working on the best options[], since this is new territory for me. Please provide suggestions!
  
#### The math
If you ever wonder why 99% of the found hashes are only five signs long and if you interested in the mathematical explanation, then go-ahead reading.  
( the math is under construction )
  
