# React Native Android Widget Proof Of Concept

Use React Native HeadlessJS to create background services that listen to Android Java Widget Providers (A Broadcast Receiver Service) :robot:

1. Create buttons in Java / Android XML to triggers intents
2. Process these intents from the Javascript side with all the tools and flexibility
from react-native.

It is a bit complicated, and we developed it with 0 knowledge from Android or Java, so I apologize if it feels bad designed. Please help us improve the strategy / code layout with Pull Requests.

### Try
```bash
git clone https://github.com/jsdario/react-native-android-widget-poc
cd react-native-android-widget-poc
npm install # or yarn install
react-native run-android
```

To build your own android widget start from this project as a widget and hack upon or replicate the strategy to make it work.

### How it works
:construction: Needs to be filled

### Can I create widgets using React Native instead of Java?
We haven't done this, but we belive that from this point has to be much easier,
having the strategy laid down, we'd need to create [Native UI Components](https://facebook.github.io/react-native/docs/native-components-android.html)
calling methods from the `RemoteView` class and context instead of the `View` class.
It is not trivial to us, though.
