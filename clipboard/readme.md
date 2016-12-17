# Android 剪贴板详解
Android 提供了一个强大的剪贴板框架，用于复制和粘贴。 它支持文本、二进制数据流或其它复杂的数据。

Android 剪贴板框架如图
![copy_paste_framework](http://odsdowehg.bkt.clouddn.com/copy_paste_framework.png)

从图中可以看出，Android 剪贴板框架主要涉及到 [ClipboardManager](https://developer.android.com/reference/android/content/ClipboardManager.html) 、 [ClipData](https://developer.android.com/reference/android/content/ClipData.html) 、 [ClipData.Item](https://developer.android.com/reference/android/content/ClipData.Item.html) 、 [ClipDescription](https://developer.android.com/reference/android/content/ClipDescription.html) 这四个类。

关于这四个类的简介如下：

1. ClipboardManager 是系统全局的剪贴板对象，通过 `context.getSystemService(CLIPBOARD_SERVICE)` 获取。
2. ClipData ，即 clip 对象，在系统剪贴板里只存在一个，当另一个 clip 对象进来时，前一个 clip 对象会消失。
3. ClipData.Item ，即 data item，它包含了文本、 Uri 或者 Intent 数据，一个 clip 对象可以包含一个或多个 Item  对象。通过 `addItem(ClipData.Item item)` 可以实现往 clip 对象中添加 Item。
    - 文本：文本是直接放在 clip 对象中，然后放在剪贴板里；粘贴这个字符串的时候直接从剪贴板拿到这个对象，把字符串放入你的应用存储中。
    - Uri：**对于复杂数据的剪贴拷贝并不是直接将数据放入内存，而是通过 Uri 来实现，毕竟 Uri 的中文名叫：统一资源标识符。通过 Uri 能定位手机上所有资源，这当然能实现拷贝了，只不过需要做一些额外的处理工作。(对于 Uri 不是很理解，如有误，望指正~)**
    - Intent：复制的时候 Intent 会被直接放入 clip 对象，这相当于拷贝了一个快捷方式。

4. ClipDescription ，即 clip metadata，它包含了 ClipData 对象的 metadata 信息。可以通过 `getMimeType(int index)` 获取（一般 index = 0，有兴趣的可以去看下 ClipData 的源码）。MimeType 一般有以下四种类型：

    ```java
    // 对应 ClipData newPlainText(label, text) 的 MimeType
    public static final String MIMETYPE_TEXT_PLAIN = "text/plain";
    // 对应 ClipData.newHtmlText(label, text, htmlText) 的 MimeType
    public static final String MIMETYPE_TEXT_HTML = "text/html";
    // 对应 ClipData.newUri(cr, label, uri) 的 MimeType
    public static final String MIMETYPE_TEXT_URILIST = "text/uri-list";
    // 对应 ClipData.newIntent(label, intent) 的 MimeType
    public static final String MIMETYPE_TEXT_INTENT = "text/vnd.android.intent";
    ```
    但 **MIMETYPE_TEXT_URILIST** 有点特殊，当 Uri 为  `content://uri` 时，它会转为具体的 MimeType ，后面会有例子讲到。

## 剪贴板简单使用
以拷贝文本为例，剪贴板的使用可以分为以下几步：

1. 获取 ClipManager 对象

    ```java
    ClipManager clipManager = (ClipManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    ```
2. 将数据放到 clip 对象中

    ```java
    ClipData clip = ClipData.newPlainText("simple text copy", "Hello World!");
    ```
    类似的方法还有

    ```java
    //创建一个包含 htmlText 的 ClipData
    //一般在浏览器中对网页进行拷贝的时候会调用此方法
    //其中 htmlText 是包含 HTML 标签的字符串
    static public ClipData newHtmlText(CharSequence label, CharSequence text, String htmlText);
    //创建一个包含 Intent 的 ClipData
    static public ClipData newIntent(CharSequence label, Intent intent);
    //创建一个包含 Uri 的 ClipData，MimeType 会根据 Uri 进行修改
    static public ClipData newUri(ContentResolver resolver, CharSequence label, Uri uri);
    //与 newUri 相对应，但是并不会根据 Uri 修改 MimeType
    static public ClipData newRawUri(CharSequence label, Uri uri);
    ```
3. 将 clip 对象放入剪贴板

    ```java
    clipManager.setPrimaryClip(clip);
    ```
4. 从剪贴板中获取 clip 对象

    ```java
    //判断剪贴板里是否有内容
    if(!clipManager.hasPrimaryClip()) {
        return;
    }
    ClipData clip = clipManager.getPrimaryClip();
    //获取 ClipDescription
    ClipDescription description = clip.getPrimaryClipDescription();
    //获取 label
    String label = description.getLabel().toString();
    //获取 text
    String text = clip.getItemAt(0).getText().toString();
    ```

## 实践出真知
讲道理，实践出真知，咱们程序员的实践就是代码，下面上代码。等等，先上 Demo 的运行效果图。第一次做 Gif ，好紧张，哈哈~
![demo](http://odsdowehg.bkt.clouddn.com/demo.gif)


对于剪贴板大部分操作都封装在 `ClipboardUtil.java` 里，使用时请记录调用 `init(Context context)` 方法进行初始化，建议在 `Application.onCreate()` 中进行，否则会造成内存泄漏。

`AndroidManifest.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.littlejie.clipboard">

    <!-- content://contacts/people 需要使用该权限-->
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.WRITE_CONTACTS" />

    <application
        android:name=".ClipboardApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

`ClipboardApplication.java`:

```java
public class ClipboardApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ClipboardUtil.init(this);
    }
}
```

`build.gradle`:

```gradle
apply plugin: 'com.android.application'

android {
    compileSdkVersion 24
    buildToolsVersion "25.0.0"

    defaultConfig {
        applicationId "com.littlejie.clipboard"
        minSdkVersion 16
        //由于Android6.0之后有运行时权限，故修改版本号使其不用运行时获取读取联系人权限
        //关于 compileSdkVersion 、 minSdkVersion 、 targetSdkVersion 三者之间的区别
        //有兴趣的可以自行谷歌、百度
        targetSdkVersion 21
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

    }
    // Something else
}
// Something else
```

`MainActivity.java`:

```java
public class MainActivity extends Activity implements View.OnClickListener,
        ClipboardUtil.OnPrimaryClipChangedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String MIME_CONTACT = "vnd.android.cursor.dir/person";

    private TextView mTvCopied;

    private ClipboardUtil mClipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ClipboardUtil在Application的onCreate中调用init初始化
        mClipboard = ClipboardUtil.getInstance();
        mClipboard.addOnPrimaryClipChangedListener(this);

        mTvCopied = (TextView) findViewById(R.id.tv_copied);

        findViewById(R.id.btn_copy_text).setOnClickListener(this);
        findViewById(R.id.btn_copy_rich_text).setOnClickListener(this);
        findViewById(R.id.btn_copy_intent).setOnClickListener(this);
        findViewById(R.id.btn_copy_uri).setOnClickListener(this);
        findViewById(R.id.btn_copy_multiple).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClipboard.removeOnPrimaryClipChangedListener(this);
    }

    @Override
    public void onPrimaryClipChanged(ClipboardManager clipboardManager) {
        Log.d(TAG, clipboardManager.getPrimaryClip().toString());
        mTvCopied.setText(clipboardManager.getPrimaryClip().toString());

        ClipData data = clipboardManager.getPrimaryClip();
        String mimeType = mClipboard.getPrimaryClipMimeType();
        Log.d(TAG, "mimeType = " + mimeType);
        //一般来说，收到系统 onPrimaryClipChanged() 回调时，剪贴板一定不为空
        //但为了保险起见，在这边还是做了空指针判断
        if (data == null) {
            return;
        }
        //前四种为剪贴板默认的MimeType，但是当拷贝数据为Uri时，会出现其它MimeType，需要特殊处理
        if (ClipDescription.MIMETYPE_TEXT_INTENT.equals(mimeType)) {
            //一个 ClipData 可以有多个 ClipData.Item，这里假设只有一个
            startActivity(data.getItemAt(0).getIntent());
        } else if (ClipDescription.MIMETYPE_TEXT_HTML.equals(mimeType)) {

        } else if (ClipDescription.MIMETYPE_TEXT_PLAIN.equals(mimeType)) {

        } else if (ClipDescription.MIMETYPE_TEXT_URILIST.equals(mimeType)) {
            //当uri=content://media/external时，copyUri会进入此if-else语句
        } else if (MIME_CONTACT.equals(mimeType)) {
            Log.d(TAG, mClipboard.coercePrimaryClipToText().toString());
            //当uri=content://contacts/people时，copyUri会进入此if-else语句
            StringBuilder sb = new StringBuilder(mTvCopied.getText() + "\n\n");
            int index = 1;
            Uri uri = data.getItemAt(0).getUri();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            while (cursor.moveToNext()) {
                //打印所有联系人姓名
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                sb.append("联系人 " + index++ + " : " + name + "\n");
            }
            mTvCopied.setText(sb.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_copy_text:
                //普通的文本拷贝
                mClipboard.copyText("文本拷贝", "我是文本~");
                break;
            case R.id.btn_copy_rich_text:
                //平时在浏览器网页上执行的copy就是这种，一般浏览器会使用 ClipData.newHtmlText(label, text, htmlText)往剪贴板里塞东西
                //所以，将这段内容拷贝到诸如 Google+ 、 Gmail 等能处理富文本内容的应用能看到保留格式的内容
                //补充：测试了 QQ浏览器 、 UC浏览器，发现他们拷贝的内容只是单纯的文本，即使用 ClipData.newPlainText(label, text) 往剪贴板里塞东西
                mClipboard.copyHtmlText("HTML拷贝", "我是HTML",
                        "<strong style=\"margin: 0px; padding: 0px; border: 0px; color: rgb(64, 64, 64); font-family: STHeiti, 'Microsoft YaHei', Helvetica, Arial, sans-serif; font-size: 17px; font-style: normal; font-variant: normal; letter-spacing: normal; line-height: 25.920001983642578px; orphans: auto; text-align: justify; text-indent: 34.560001373291016px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(235, 23, 23);\">央视</strong>");
                break;
            case R.id.btn_copy_intent:
                mClipboard.copyIntent("Intent拷贝", getOpenBrowserIntent());
                break;
            case R.id.btn_copy_uri:
                mClipboard.copyUri(getContentResolver(), "Uri拷贝", Uri.parse("content://contacts/people"));
                //mClipboard.copyUri(getContentResolver(), "Uri拷贝", Uri.parse("content://media/external"));
                break;
            case R.id.btn_copy_multiple:
                copyMultiple();
                break;
        }
    }

    /**
     * 打开浏览器的Intent
     *
     * @return
     */
    private Intent getOpenBrowserIntent() {
        Uri uri = Uri.parse("http://www.baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        return intent;
    }

    /**
     * 拷贝多组数据到剪贴板
     */
    private void copyMultiple() {
        //ClipData 目前仅能设置单个 MimeType
        List<ClipData.Item> items = new ArrayList<>();
        //故 ClipData.Item 的类型必须和 MimeType 设置的相符
        //比如都是文字，都是URI或都是Intent，而不是混合各种形式。
        ClipData.Item item1 = new ClipData.Item("text1");
        ClipData.Item item2 = new ClipData.Item("text2");
        ClipData.Item item3 = new ClipData.Item("text3");
        ClipData.Item item4 = new ClipData.Item("text4");
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        mClipboard.copyMultiple("Multiple Copy", ClipDescription.MIMETYPE_TEXT_PLAIN, items);
    }
}
```

`ClipboardUtil.java`:

```java
/**
 * 剪贴板工具类
 * http://developer.android.com/guide/topics/text/copy-paste.html
 * Created by littlejie on 2016/4/15.
 */
public class ClipboardUtil {

    public static final String TAG = ClipboardUtil.class.getSimpleName();

    private static final String MIME_CONTACT = "vnd.android.cursor.dir/person";
    /**
     * 由于系统剪贴板在某些情况下会多次调用，但调用间隔基本不会超过5ms
     * 考虑到用户操作，将阈值设为100ms，过滤掉前几次无效回调
     */
    private static final int THRESHOLD = 100;

    private Context mContext;
    private static ClipboardUtil mInstance;
    private ClipboardManager mClipboardManager;

    private Handler mHandler = new Handler();

    private ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            mHandler.removeCallbacks(mRunnable);
            mHandler.postDelayed(mRunnable, THRESHOLD);
        }
    };

    private ClipboardChangedRunnable mRunnable = new ClipboardChangedRunnable();

    private List<OnPrimaryClipChangedListener> mOnPrimaryClipChangedListeners = new ArrayList<>();

    /**
     * 自定义 OnPrimaryClipChangedListener
     * 用于处理某些情况下系统多次调用 onPrimaryClipChanged()
     */
    public interface OnPrimaryClipChangedListener {
        void onPrimaryClipChanged(ClipboardManager clipboardManager);
    }

    private class ClipboardChangedRunnable implements Runnable {

        @Override
        public void run() {
            for (OnPrimaryClipChangedListener listener : mOnPrimaryClipChangedListeners) {
                listener.onPrimaryClipChanged(mClipboardManager);
            }
        }
    }

    private ClipboardUtil(Context context) {
        mContext = context;
        mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    /**
     * 单例。暂时不清楚此处传 Activity 的 Context 是否会造成内存泄漏
     * 建议在 Application 的 onCreate 方法中实现
     *
     * @param context
     * @return
     */
    public static ClipboardUtil init(Context context) {
        if (mInstance == null) {
            mInstance = new ClipboardUtil(context);
        }
        return mInstance;
    }

    /**
     * 获取ClipboardUtil实例，记得初始化
     *
     * @return
     */
    public static ClipboardUtil getInstance() {
        return mInstance;
    }

    public void addOnPrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        if (!mOnPrimaryClipChangedListeners.contains(listener)) {
            mOnPrimaryClipChangedListeners.add(listener);
        }
    }

    public void removeOnPrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        mOnPrimaryClipChangedListeners.remove(listener);
    }

    /**
     * 判断剪贴板内是否有数据
     *
     * @return
     */
    public boolean hasPrimaryClip() {
        return mClipboardManager.hasPrimaryClip();
    }

    /**
     * 获取剪贴板中第一条String
     *
     * @return
     */
    public String getClipText() {
        if (!hasPrimaryClip()) {
            return null;
        }
        ClipData data = mClipboardManager.getPrimaryClip();
        if (data != null
                && mClipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            return data.getItemAt(0).getText().toString();
        }
        return null;
    }

    /**
     * 获取剪贴板中第一条String
     *
     * @param context
     * @return
     */
    public String getClipText(Context context) {
        return getClipText(context, 0);
    }

    /**
     * 获取剪贴板中指定位置item的string
     *
     * @param context
     * @param index
     * @return
     */
    public String getClipText(Context context, int index) {
        if (!hasPrimaryClip()) {
            return null;
        }
        ClipData data = mClipboardManager.getPrimaryClip();
        if (data == null) {
            return null;
        }
        if (data.getItemCount() > index) {
            return data.getItemAt(index).coerceToText(context).toString();
        }
        return null;
    }

    /**
     * 将文本拷贝至剪贴板
     *
     * @param text
     */
    public void copyText(String label, String text) {
        ClipData clip = ClipData.newPlainText(label, text);
        mClipboardManager.setPrimaryClip(clip);
    }

    /**
     * 将HTML等富文本拷贝至剪贴板
     *
     * @param label
     * @param text
     * @param htmlText
     */
    public void copyHtmlText(String label, String text, String htmlText) {
        ClipData clip = ClipData.newHtmlText(label, text, htmlText);
        mClipboardManager.setPrimaryClip(clip);
    }

    /**
     * 将Intent拷贝至剪贴板
     *
     * @param label
     * @param intent
     */
    public void copyIntent(String label, Intent intent) {
        ClipData clip = ClipData.newIntent(label, intent);
        mClipboardManager.setPrimaryClip(clip);
    }

    /**
     * 将Uri拷贝至剪贴板
     * If the URI is a content: URI,
     * this will query the content provider for the MIME type of its data and
     * use that as the MIME type.  Otherwise, it will use the MIME type
     * {@link ClipDescription#MIMETYPE_TEXT_URILIST}.
     * 如 uri = "content://contacts/people"，那么返回的MIME type将变成"vnd.android.cursor.dir/person"
     *
     * @param cr    ContentResolver used to get information about the URI.
     * @param label User-visible label for the clip data.
     * @param uri   The URI in the clip.
     */
    public void copyUri(ContentResolver cr, String label, Uri uri) {
        ClipData clip = ClipData.newUri(cr, label, uri);
        mClipboardManager.setPrimaryClip(clip);
    }

    /**
     * 将多组数据放入剪贴板中，如选中ListView多个Item，并将Item的数据一起放入剪贴板
     *
     * @param label    User-visible label for the clip data.
     * @param mimeType mimeType is one of them:{@link android.content.ClipDescription#MIMETYPE_TEXT_PLAIN},
     *                 {@link android.content.ClipDescription#MIMETYPE_TEXT_HTML},
     *                 {@link android.content.ClipDescription#MIMETYPE_TEXT_URILIST},
     *                 {@link android.content.ClipDescription#MIMETYPE_TEXT_INTENT}.
     * @param items    放入剪贴板中的数据
     */
    public void copyMultiple(String label, String mimeType, List<ClipData.Item> items) {
        if (items == null) {
            throw new NullPointerException("items is null");
        }
        int size = items.size();
        ClipData clip = new ClipData(label, new String[]{mimeType}, items.get(0));
        for (int i = 1; i < size; i++) {
            clip.addItem(items.get(i));
        }
        mClipboardManager.setPrimaryClip(clip);
    }

    public CharSequence coercePrimaryClipToText() {
        if (!hasPrimaryClip()) {
            return null;
        }
        return mClipboardManager.getPrimaryClip().getItemAt(0).coerceToText(mContext);
    }

    public CharSequence coercePrimaryClipToStyledText() {
        if (!hasPrimaryClip()) {
            return null;
        }
        return mClipboardManager.getPrimaryClip().getItemAt(0).coerceToStyledText(mContext);
    }

    public CharSequence coercePrimaryClipToHtmlText() {
        if (!hasPrimaryClip()) {
            return null;
        }
        return mClipboardManager.getPrimaryClip().getItemAt(0).coerceToHtmlText(mContext);
    }

    /**
     * 获取当前剪贴板内容的MimeType
     *
     * @return 当前剪贴板内容的MimeType
     */
    public String getPrimaryClipMimeType() {
        if (!hasPrimaryClip()) {
            return null;
        }
        return mClipboardManager.getPrimaryClipDescription().getMimeType(0);
    }

    /**
     * 获取剪贴板内容的MimeType
     *
     * @param clip 剪贴板内容
     * @return 剪贴板内容的MimeType
     */
    public String getClipMimeType(ClipData clip) {
        return clip.getDescription().getMimeType(0);
    }

    /**
     * 获取剪贴板内容的MimeType
     *
     * @param clipDescription 剪贴板内容描述
     * @return 剪贴板内容的MimeType
     */
    public String getClipMimeType(ClipDescription clipDescription) {
        return clipDescription.getMimeType(0);
    }

    /**
     * 清空剪贴板
     */
    public void clearClip() {
        mClipboardManager.setPrimaryClip(null);
    }

}
```

## 补充
以下补充几点，是自己在测试剪贴板的过程中碰到，一是 `OnPrimaryClipChangedListener` 的多次回调，二是将剪贴板中的内容转换为字符串。

### 关于 OnPrimaryClipChangedListener 的多次回调
细心的同学可能已经发现，上述代码中，楼主并没有直接使用 Android 的 `OnPrimaryClipChangedListener` ，而是自己对此进行了再次封装。这是有原因的，在最初测试剪贴板的过程中，楼主发现一次拷贝过程可能会导致多次回调 `onPrimaryClipChanged()` 方法，日志如下：

```
# 第一次
com.littlejie.clipboard D/MainActivity: text/plain
# 第二次
com.littlejie.clipboard D/MainActivity: text/plain
com.littlejie.clipboard D/MainActivity: 央视
# 第三次
com.littlejie.clipboard D/MainActivity: text/html
com.littlejie.clipboard D/MainActivity: <strong style="margin: 0px; padding: 0px; border: 0px; color: rgb(64, 64, 64); font-family: STHeiti, 'Microsoft YaHei', Helvetica, Arial, sans-serif; font-size: 17px; font-style: normal; font-variant: normal; letter-spacing: normal; line-height: 25.920001983642578px; orphans: auto; text-align: justify; text-indent: 34.560001373291016px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(246, 246, 246);">央视</strong>
com.littlejie.clipboard D/MainActivity: 央视
```
这肯定不是我们想要的结果，那么该怎么解决这个问题呢？

多次测试发现，发生多次回调的情况下，正确的拷贝结果都是最后一次回调获取到的数据。

再打印一下 `onPrimaryClipChanged()` 回调时间吧，发现三次的间隔不超过 9ms ，而普通用户一般不可能在如此短时间内完成多次拷贝。故我们可以定义一个变量存储 `onPrimaryClipChanged` 的回调时间，当下次回调时相对前一次的时间间隔小于 100ms(合理假设)，那么判定前一次回调事件无效。

```
# 第一次
onPrimaryClipChanged,time = 1481792153614
# 第二次
onPrimaryClipChanged,time = 1481792153620
# 第三次
onPrimaryClipChanged,time = 1481792153623
```
故才有了上诉的代码。

### 将剪贴板中的数据强转为字符串
一般来说，平时我们拷贝的都是文字，但是从上述内容可知，Android 剪贴板支持的不仅仅是文字，那对于 Uri 、 Intent 数据 Android 是如何把它们转换成字符串的呢？有兴趣的同学可以去查看 ClipData 下述三个方法的源码。这里限于篇幅就不详述了。

```
public CharSequence coerceToText(Context context);

public CharSequence coerceToStyledText(Context context);

public String coerceToHtmlText(Context context);
```

## 画外：如何高效的复制粘贴
此部分内容原文见 Android 官方文档 [Copy and paste](https://developer.android.com/guide/topics/text/copy-paste.html) 最后一节，翻译摘自 [Android中的复制粘贴](http://www.cnblogs.com/mengdd/p/3572316.html) 。

为了设计有效的复制粘贴功能，以下几点需要注意：

1. 任何时间，都只有一个clip对象在剪贴板里。新的复制操作都会覆盖前一个clip对象，因为用户可能从你的应用中退出，从其他应用中拷贝一个东西，所以你不能假定用户在你的应用中拷贝的上一个东西一定还放在剪贴板里。
2. 一个clip对象，即ClipData中的多个ClipData.Item 对象是为了支持多选项的复制粘贴，而不是为了支持单选的多种形式。你通常需要clip对象中的所有的项目，即ClipData.Item有一样的形式，比如都是文字，都是URI或都是Intent，而不是混合各种形式。
3. 当你提供数据时，你可以提供不同的MIME表达方式。将你支持的MIME类型加入到ClipDescription中去，然后在你的content provider中实现它。
4. 当你从剪贴板得到数据时，你的应用有责任检查可用的MIME类型，然后决定使用哪一个。即便有一个clip对象在剪贴板中并且用户要求粘贴，你的应用有可能不需要进行粘贴操作。你应该在MIME类型兼容的时候执行粘贴操作。你可以选择使用 coerceToText()方法将粘贴的内容转换为文字。如果你的应用支持多种类型，你可以让用户自己选用哪一个。

## 参考
1. [Copy and paste](https://developer.android.com/guide/topics/text/copy-paste.html)
2. [Android中的复制粘贴](http://www.cnblogs.com/mengdd/p/3572316.html)

