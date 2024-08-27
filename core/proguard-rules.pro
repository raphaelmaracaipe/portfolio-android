-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile

-keepattributes Signature
-keepattributes *Annotation*

-dontwarn java.lang.invoke.StringConcatFactory

-keep class br.com.raphaelmaracaipe.core.** { *; }
-keep class br.com.raphaelmaracaipe.core.R { *; }
-keep class br.com.raphaelmaracaipe.core.R$* { *; }