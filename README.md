# BottomNavigation

![Alt text](relative/path/to/Hofinity/BottomNavigation/blob/master/screenshots/center_curved_border.jpg?raw=true "Title")

![Alt text](relative/path/to/img.jpg?raw=true "Title")

https://github.com/Hofinity/BottomNavigation/blob/master/screenshots/linear_border.jpg

Gradle :

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Hofinity:BottomNavigation:0.0.1'
	}
  -------------------------------------------------------------------------
  
  Maven :
  
  Step 1. 
  
  	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  
  Step 2. Add the dependency

	<dependency>
	    <groupId>com.github.Hofinity</groupId>
	    <artifactId>BottomNavigation</artifactId>
	    <version>0.0.1</version>
	</dependency>
