# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Optimization
-optimizationpasses 5

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose

#-printseeds seeds.txt
#-printusage unused.txt #Per vedere se ci sono parti di codice non utilizzato
#-printmapping mapping.txt #Per fare il mapping tra codice offuscato e codice iniziale (da uploadare su Crashlytics dopo ciascuna build)

#package path where gson models are stored
-keep class com.tiknil.app.core.** { *; }
-keep class com.tiknil.app.services.** { *; }
-keep class com.tiknil.app.models.** { *; }

# Settings consigliati da Firebase Crashlytics
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.