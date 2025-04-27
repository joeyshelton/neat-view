# Neat View

Neat View is an extremely simple Android application that allows you to view webpages with no distractions with just one click.

To use Neat View:

1. Select the "Share" icon in whatever application you're in.
2. When prompted with a share dialog, choose the Neat View app.

It is helpful to [pin Neat View to the Android Share menu](https://www.techrepublic.com/article/how-to-pin-apps-to-the-android-share-menu/), so it is always just one click away.

## How it works

The Android app has one simple MainActivity. This activity responds to [ACTION_SEND intents](https://developer.android.com/training/sharing/receive), looks for URLs in the shared data, and opens those URLs in a WebView browser with JavaScript disabled.

When the user shares a link, Neat View opens the link inside a clean, distraction-free browser view without JavaScript.

If the page does not load properly, a **"Try Archive"** button is available at the top of the screen. Clicking this button will open an archived version of the page through archive.today in the user's default browser.

The entire application is only ~70 lines of added code beyond a basic Android App template.

## Features
- Share any webpage and view it without JavaScript.
- Optionally paste a URL manually into the app UI.
- Auto-detects URLs copied to your clipboard and offers to open them.
- If a webpage does not render correctly, a one-click "Try Archive" button opens an archive.today version.
