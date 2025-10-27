# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
##
# --- Hilt / Dagger ---
-dontwarn dagger.hilt.internal.**
-dontwarn androidx.hilt.**
-keep class dagger.hilt.** { *; }
-keep class androidx.hilt.** { *; }

# --- Retrofit / OkHttp / RxJava ---
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn io.reactivex.**

# Keep Retrofit annotations
-keepattributes Signature
-keepattributes *Annotation*

# --- Gson / SerializedName ---
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# --- ViewBinding / DataBinding ---
-keep class **.databinding.*Binding { *; }
-keep class **.BR { *; }
-keepattributes *Annotation*, InnerClasses

# --- Glide ---
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** { *; }

# --- App packages ---
-keep class com.example.booksapp.** { *; }
