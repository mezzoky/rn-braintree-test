# react-native-braintree-android

A react native interface for integrating Braintree's native Drop-in Payment UI for Android using [Braintree's v.zero SDK](https://developers.braintreepayments.com/start/overview).

orignal verison from https://github.com/surialabs/react-native-braintree-android

<img src="https://cloud.githubusercontent.com/assets/503385/11742042/dc4eeaa8-a037-11e5-80ef-549a5f749282.png" alt="Screenshot" width="300" />

## Setup

1. git clone

1. Add the following to android/settings.gradle

```gradle
include ':react-native-braintree'
project(':react-native-braintree').projectDir = new File(settingsDir, 'path/to/clone')
```

1. Add the following to android/app/build.gradle

```gradle
dependencies {
  // ...
  compile project(':react-native-braintree')
}
```

1. Edit android/src/main/java/com/.../MainActivity.java

```diff

...

+ import com.mezzoky.rn.braintree.BraintreePackage;

public class MainActivity extends ReactActivity {

    ...

    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
-            new MainReactPackage()
+            new MainReactPackage(),
+            new BraintreePackage()
        );
    }

```

## Usage

```js
import Braintree from '../path/to/clone/index.android';

async function openBraintreeDropin(token: string) {
    let ret;
    try {
        ret = await Braintree.paymentRequest(token)
    } catch (e) {
        ret = null
        console.error(e)
    }
    return ret
}

const token = "eyJ2ZXJzaW9u..."
openBraintreeDropin(token)
```
