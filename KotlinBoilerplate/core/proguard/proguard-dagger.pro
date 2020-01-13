#Dagger

-dontwarn dagger.internal.codegen.*

-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection

-if   class **$$ModuleAdapter
-keep class <1>

-if   class **$$InjectAdapter
-keep class <1>

-if   class **$$StaticInjection
-keep class <1>

-dontnote dagger.Lazy
-keepnames class dagger.Lazy
