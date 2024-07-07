-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile

-keepattributes Signature
-keepattributes *Annotation*

-dontwarn java.lang.invoke.StringConcatFactory
-dontwarn br.com.raphaelmaracaipe.core.R$color
-dontwarn br.com.raphaelmaracaipe.core.R$layout

# Mantenha a classe AndroidJUnitRunner
-keep class androidx.test.runner.AndroidJUnitRunner {
    <init>(...);
    *;
}

# Mantenha sua classe CustomTestRunner
-keep class br.com.raphaelmaracaipe.tests.ruuners.CustomTestRunner {
    <init>(...);
    *;
}