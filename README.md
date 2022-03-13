# Sort-The-Clutter
## Inspiration
This weekend as the theme was about cleaning and tidying up our space, we wanted to build something that would allow us to classify what waste goes into which category.  

## What it does
It is an android application that makes use of our machine learning model. It classifies the objects in the snap you took into organic or recyclable category. It provides the percentage of both and takes the higher percentage into account to choose what side does the object lean on.  

## How we built it
1. **Java** -  This is the main language in which this project is created.  

2. **Android Studio** - As it is an android application, we built it using Android Studio.  

3. **Linode** - We created and trained our machine learning model on Linode Cloud Instance.  

4. **Tensorflow/Tensorflow-lite** - We used this to convert our machine learning model into a tf-lite model that can be used in Android Applications.  

5. **XML** - This is used for the designing of our application.  

## Best Use Of Linode
We created our model in python. But we didn't want to train the model from our laptops because that was taking too much time. Thus we used Linode. We used the screen feature of the terminal and ran our training command. This allowed us to train the model without staying online on the Lish Console or our laptop.  
Later after the model was successfully trained, we converted it into a tensorflow-lite model.  

## How To Run/Edit It
1. If you want to run our application, you can directly download the [APK](https://github.com/Khushik514/Sort-The-Clutter/blob/main/sort-the-clutter.apk?raw=true) and run it on your smart phone by clicking on the APK. It will install the application on its own. It can also ask you if you want to download from unauthorised sources as this is downloaded from the Internet, you need to allow that.  
2. Using the application is very simple and is also shown in the demo video. You just need to open the application and click on the **’Click A Snap’** button. After that you would be asked to give camera permissions. Once you have given the permissions, you can click a snap of the object you want to classify.  
3. After clicking the snap, you just need to click ✓ and the model will classify the object into recyclable/organic and provide percentages of both.  
4. Android applications can also be run on PC using Android Emulators such as BlueStacks, GenyMotion, etc.  

If you want to clone the project and make changes to it, you can follow the following steps-  
1. If, you have GitHub CLI installed, you can clone our repository into your system using this command on the terminal -   

```gh repo clone Khushik514/Sort-The-Clutter```  

2. If you don’t have GitHub CLI installed, you can use [HTTPS](https://github.com/Khushik514/Sort-The-Clutter.git) or download the [Zip](https://github.com/Khushik514/Sort-The-Clutter/archive/refs/heads/main.zip) and extract it.  
3. After this, you just need to open Android studio and open this project’s directory and you can start editing the project.  

## Challenges we ran into
1. The ideation itself was a challenge.  
2. The model creation and dataset collection made us struggle a bit. Also Tensorflow+Android was new to us.  
3. Conversion of our model into tf-lite model.  

## Accomplishments that we're proud of
1. We are proud of completing the project on time.  
2. We are also proud of making something presentable and usable.  
3. We are also proud that from now on we won't dump are clutters without sorting them out!  

## What we learned
1. We learnt about Tensorflow, making Tensorflow Models and converting our own model into a Tensorflow Model.  
2. We learnt more about Android Studio and Android Programming.  
3. We watched tutorials to learn Splash Screens so that our project looks a bit cool.  

## What's next for Sort The Clutter
1. Cleaning our room using Sort The Clutter because It's a mess.  
2. Making use of other awesome projects this weekend to clean the mess some more.  
3. Adding features like ability to add more data to train the model and adding more features like tips on how to recycle, etc.  