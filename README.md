

# Neat View
Neat View is an extremely simple Android application that allows you to view webpages with no distractions with one click. 
To use Neat View:

 1. Select the "Share" icon in whatever application you're in
 2. When prompted with a share dialog, choose the Neat View app

It is helpful to [pin Neat View to the Android Share menu](https://www.techrepublic.com/article/how-to-pin-apps-to-the-android-share-menu/), so it is always just one click away.

# How it works
The android app has one simple MainActivity. This activity responds to [ACTION_SEND intents](https://developer.android.com/training/sharing/receive), looks for URLs in the shared data, and opens those URLs in a WebView browser with JavaScript disabled. 

When the user clicks "Share" while on a webpage or in some application that shares a link, an ACTION_SEND intent is sent to whatever app is chosen in the "Share" dialog. If Neat View is chosen, it checks if the data being shared is a string that contains a URL. If it contains a URL, it opens a JavaScript-free version of the shared webpage.

The entire application is only ~60 lines code added to an empty Android App project, including white space and comments.
