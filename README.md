# Android Kotlin Boilerplate

Il boilerplate contiene un app base Android scritta in Kotlin che implementa l'architettura MVVM usando Dagger2, RxKotlin e Android Databinding.

## Getting Started

Il boilerplate contiene il file ruby `kotlinboilerplate.rb` che permette di creare un nuovo progetto a partire dal boilerplate fornendo le principali informazioni per l'inizializzazione. Di seguito le istruzioni:

1. Entra nella cartella di questo repo
2. Rendi lo script `kotlinboilerplate.rb` eseguibile
   ```
    chmod +x kotlinboilerplate.rb
   ```    
3. Quindi eseguilo
   ```
   ./kotlinboilerplate.rb
   ```
4. Puoi dargli direttamente i parametri seguenti:
   a. `-n`/`--name` Nome del progetto (es: ProjectName)
   b. `-p`/`--package` Package name (es: com.tiknil.projectname)
5. Oppure:
   `-h`/`--help` Per visualizzare l'help

## Design pattern

### Il pattern Model-View-ViewModel

I principali attori del pattern MVVM sono:
- La _View_ he informa il ViewModel sulle azioni dell'utente e reagisce ai cambi di stato del ViewModel per la visualizzazione delle informazioni
- Il _ViewModel_  - espone flussi di dati rilevanti per la view e agisce in base agli input dell'utente per modificare i dati/avviare business logic
- Il _Model_  - astrae la fonte dei dati. Il ViewModel lavora con il DataModel per ottenere e salvare i dati.

A prima vista, il pattern MVVM sembra molto simile al modello Model-View-Presenter, perché entrambi svolgono un ottimo lavoro nell'astrazione dello stato e del comportamento della vista. Il Presentation Model astrae una View indipendente da una specifica piattaforma di interfaccia utente, mentre il pattern MVVM è stato creato per semplificare la programmazione **event driven** delle interfacce utente.

Se nel pattern MVP il Presenter indica direttamente alla View cosa visualizzare, nel MVVM **il ViewModel mostra i flussi di eventi** a cui le viste possono legarsi. In questo modo, il ViewModel non ha più bisogno di tenere un riferimento alla vista, come il Presenter fa. Ciò significa anche che tutte le interfacce che il pattern MVP richiede sono state eliminate.

Le View comunicano anche al ViewModel le diverse azioni. Pertanto, il pattern MVVM supporta l'associazione dati bidirezionale tra View e ViewModel e vi è una relazione many-to-one tra View e ViewModel. La View ha un riferimento al ViewModel ma **il ViewModel non ha informazioni sulla View**. Il consumatore dovrebbe conoscere il produttore, ma il produttore - il ViewModel - non sa, e non gli importa conoscere il consumatore.

### Model-View-ViewModel @ Tiknil

La parte event driven richiesta da MVVM viene eseguita utilizzando il functional programming, ovvero gli `Observable` di ReactiveX (Rx)
- Un riferimento all'utilizzo gli `Observable` di RxJava con il pattern MVVM è possibile trovarlo a questo link: https://medium.com/upday-devs/android-architecture-patterns-part-3-model-view-viewmodel-e7eeee76b73b


### Inversion of Control e Dependency Injection
L'*Inversion of Control* (**IoC**) è un *software design pattern* secondo il quale ogni componente dell'applicazione deve ricevere il **controllo** da un componente appartenente ad una **libreria riusabile**.<br>
L'obiettivo è quello di rendere ogni componente il più indipendente possibile dagli altri in modo che ognuno sia modificabile singolarmente con conseguente maggior riusabilità e manutenibilità.

La *Dependency Injection* (**DI**) è una forma di *IoC* dove l'implementazione del pattern avviene stabilendo le dipendenze tra un componente e l'altro tramite delle *interfacce* (chiamate **interface contracts**).<br>
A tali interfacce viene associata un'implementazione in fase di istanziazione del componente (nel *costruttore*) oppure in un secondo momento tramite *setter*.<br>
In ogni caso è generalmente presente un oggetto **container** che si occupa di creare le istanze di ogni *interfaccia*; la configurazione di tale *container* può così influenzare le dipendenze tra i vari componenti.<br>
L'utilizzo della *DI* è molto utile per la realizzazione di test automatici, infatti modificando il *container* è possibile *mockare* le dipendenze che non si desidera testare.

References:
- [Semplice video che chiarisce il concetto di DI](https://www.youtube.com/watch?v=IKD2-MAkXyQ)

## Struttura del progetto

Definiamo in questo capitolo le best practices di Tiknil per l'impostazione del progetto **KotlinBoilerplate**.

### Modularizzazione

L'intero progetto è stato suddiviso in più moduli così da offrire:
- una riduzione del tempo di build poichè solo i moduli modificati vengono ricompilati
- una possibilità di utilizzo di alcune funzioni avanzate di Android come le Instant Apps e le Dynamic Features
- possibilità di utilizzare i singoli moduli in altre applicazioni

Nello specifico sono stati definiti i seguenti moduli:
- `app`: il modulo principale che contiene l'implementazione della Dependency Injection con `Dagger2`, le implemetazione dei viewmodel e delle view
- `core`: il modulo base dell'intera applicazione poichè contiene principalmente le interfacce che verranno utilizzate dai diversi moduli per gli scopi specifici
- `services`: modulo che contiene le implementazione dei services dell'app
- `model`: modulo che contiene i modelli dell'app

> Nota: in base al progetto si può decidere di inserire i modelli (`model`) all'interno del modulo `core` come package `.models`.

### Struttura MVVM

Nel boilerplate corrente è presente un esempio di implementazione che segue il pattern Model-View-ViewModel, nello specifico:
- modulo:`app`, package:`viewmodels` classe:`MainActivityViewModel.kt` rappresenta il *ViewModel*, estende la classe `BaseViewModel` presente nel modulo `core` package `viewmodels` dal quale eredita il costruttore. Tutti i viewmodel devono estendere il `BaseViewModel`.
- modulo:`app`, package:`views.activities`, classe:`MainActivity.kt` rappresente la *View*, essa contiene il riferimento al viewmodel ingettato tramite l'annotation `@Inject`. Tutte le activity devono estendere la `BaseActivity` presente nel modulo `core` package `views`; in egual modo tutti i fragment dovranno estendere il `BaseFragment`.
- modulo:`models`, package: `models` classe:`BaseModel.kt` è la classe dedicata all'implementazioni della parte Model del pattern, in base ai modelli richiesti del contesto essa verrà implementata di conseguenza. Tutti i model devono estendere il `BaseModel`.

### Android View Binding e bind UI => ViewModel

Per l'utilizzo dell'android view binding ([https://developer.android.com/topic/libraries/view-binding]) nei file xml di View che lo richiestono basta inserire all'interno del file il tag `layout` relativo e il tag `data` come definito di seguito:

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".view.activities.SplashScreenActivity">

    <LinearLayout
                  id="@+id/splashscreen_main_layout">
        ... contenuto dell'activity/fragment ...
    </LinearLayout>

</layout>
```

In questo modo, tramite il View Binding, all'interno della classe che lo eredita (es: il Fragment) è possibile semplicemente richiamare gli elementi della UI con il loro nome (in camelcase), ad esempio: `binding.splashscreenMainLayout`. 

Grazie poi a librerie appositite (come ad esempio [https://github.com/JakeWharton/RxBinding]) possiamo stare in ascolto di eventuali eventi che gli elementi della UI mettono a disposizione in modo reattivo. Ad esempio, per reagire al tap su un pulsante da parte dell'utente avremo nel `Fragment` il codice: 

```Kotlin
binding.signUpBtn.clicks()
   .subscribe { 
      //informa il view model dell'evento
      viewModel.userTapOnSignUp.onNext(Unit)
   }
```

##### Binding Dato => UI

Implementato tramite il functional programming e le librerie ReactiveX, ad esempio

```Kotlin
viewModel.user
   .subscribe { user -> 
      binding.userNameTextField.text = user.name
      binding.userSurnameTextField.text = user.surname
   }
```

### Dependency Injection

L'injection viene realizzata tramite le librerie [Dagger2](https://github.com/google/dagger) e [dagger-android](https://google.github.io/dagger/android.html).  La documentazione è possibile trovarla a questo [link](https://google.github.io/dagger/). I componenti principali per la sua implementazione si trovano nel package `com.tiknil.boilerplatemvvm.di`:
- `AppComponent.kt` classe definita tramite l'annotation `@Component` che tramite la specifica dei moduli utilizzati permette di eseguire l'injection. In questo tipo di implementazione viene definita una sola classe `Component` per l'intera applicazione.
- `AppModule.kt` classe i cui metodi forniscono le *dependencies*. Seguendo le style guide, ciascun provider deve essere definito all'interno del modulo:`services`, package:`com.tiknil.services` (es.: `FragmentNavigator.kt` con la relativa interfaccia `IFragmentNavigator.kt`).
- `BuildersModule.kt` classe astratta che, utilizzando l'`AndroidInjection` fornita dalla libreria `dagger-android` permette di injettare i viewmodel all'interno delle activity e nei fragment.
- `ViewModelModule.kt` classe che implementa il submodule utilizzato in `BuildersModule` per la specifica dei viewmodel *"providers"*.

I providers, qui definiti all'interno del modulo:`services`, package:`com.tiknil.services`, hanno un costruttore che, se necessario, viene definito con l'annotation `@Inject` in modo tale da esplicitare a dagger la necessità di injettare gli oggetti passati.

### Services

Chiamiamo **Service** una classe dedicata all'esecuzione di _business logic_ legata a una stesso ambito iniettabile come dipendenza ove necessario, tramite corrispettivo **ServiceProtocol**.

Esempi dei più utilizzati:

* **ApiService:** dedicato alle chiamate network alle API del server.
* **DataService:** dedicato alla storicizzazione di dati (database, portachiavi, file).
* **CacheService:** dedicato al salvataggio di dati temporanei che possono essere persi servono per velocizzare delle operazioni.
* **LocationService:** dedicato alla gestione del geoposizionamento dell'utente.
* **BluetoothService:** dedicato alla gestione della comunicazione bluetooth.
* **WebSocketService:** dedicato alla comunicazione via WebSocket.
* _ecc..._

### Cartelle di progetto

La cartella contenente il codice sorgente dell'app avrà la seguente struttura:

```
|-- java
  |-- com.tiknil.app
    |-- di                    # Classi per l'implementazione della dependency injection con Dragger2
    |-- utils                 # Classi di generico aiuto per il modulo app
    |-- views                 # Le classi che implementano la ui
        |-- activities        # Implemetazione delle activity eventualmente inseriti in sottocartelle di sezione
        |-- fragments         # Implementazione dei fragment eventualmente inseriti in sottocartelle di sezione
    |-- viewmodels            # Tutti i viewmodel eventualmente inseriti in sottocartelle di sezione
    |-- assets
      |-- fonts               # Contiene i file dei fonts
    |-- res                   # Cartella di resources: color, drawable, layout,...
  |-- com.tiknil.app.core
    |-- services              # Interfacce per l'implementazione dei servizi
    |-- views                 # Classi base per l'implementazione delle view Activity e Fragment
    |-- viewmodels            # Classi base per l'implementazione dei viewModel
    |-- (models)              # Le classi dei modelli se non si crea un modulo apposta, dipende dal progetto
  |-- com.tiknil.app.services # Implementazione dei services (providers) come networking, persistenza dei dati,
  |-- com.tiknil.app.models   # Tutti gli oggetti model

```
