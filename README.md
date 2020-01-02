# Android Kotlin Boilerplate

Il boilerplate contiene un app base Android scritta in Kotlin che implementa l'architettura MVVM usando Dagger2, RxKotlin e Android Databinding.

## Getting Started

Il boilerplate contiene il file ruby `boilerplatemvvm.rb` che permette di creare un nuovo progetto a partire dal boilerplate fornendo le principali informazioni per l'inizializzazione. Di seguito le istruzioni:

1. Entra nella cartella di questo repo
2. Rendi lo script `boilerplatemvvm.rb` eseguibile
   ```
    chmod +x boilerplatemvvm.rb
   ```    
3. Quindi eseguilo
   ```
   ./boilerplatemvvm.rb
   ```
4. Puoi dargli direttamente i parametri seguenti:
   a. `-n`/`--name` Nome del progetto (es: ProjectName)
   b. `-p`/`--package` Package name (es: com.tiknil.projectname)
5. Oppure:
   `-h`/`--help` Per visualizzare l'help

## Design pattern

### Il pattern Model-View-ViewModel

I principali attori del pattern MVVM sono:
- La _View_ he informa il ViewModel sulle azioni dell'utente
- Il _ViewModel_  - espone flussi di dati rilevanti per la view
- Il _Model_  - astrae la fonte dei dati. Il ViewModel lavora con il DataModel per ottenere e salvare i dati.

A prima vista, il pattern MVVM sembra molto simile al modello Model-View-Presenter, perché entrambi svolgono un ottimo lavoro nell'astrazione dello stato e del comportamento della vista. Il Presentation Model astrae una View indipendente da una specifica piattaforma di interfaccia utente, mentre il pattern MVVM è stato creato per semplificare la programmazione **event driven** delle interfacce utente.

Se nel pattern MVP il Presenter indica direttamente alla View cosa visualizzare, nel MVVM **il ViewModel mostra i flussi di eventi** a cui le viste possono legarsi. In questo modo, il ViewModel non ha più bisogno di tenere un riferimento alla vista, come il Presenter fa. Ciò significa anche che tutte le interfacce che il pattern MVP richiede sono state eliminate.

Le View comunicano anche al ViewModel le diverse azioni. Pertanto, il pattern MVVM supporta l'associazione dati bidirezionale tra View e ViewModel e vi è una relazione many-to-one tra View e ViewModel. La View ha un riferimento al ViewModel ma **il ViewModel non ha informazioni sulla View**. Il consumatore dovrebbe conoscere il produttore, ma il produttore - il ViewModel - non sa, e non gli importa conoscere il consumatore.

## Struttura del progetto

Definiamo in questo capitolo le best practices di Tiknil per l'impostazione del boilerplate **TiknilKotlinBoilerplateMVVM**.

### Struttura MVVM

Nel boilerplate corrente è presente l'implementazione di un esempio di implementazione che segue il pattern Model-View-ViewModel: la *splashscreen*. Nello specifico:
- `viewmodels.SplashScreenViewModel.kt` che rappresenta il *ViewModel*, estende il `BaseViewModel` dal quale eredita il costruttore. Tutti i viewmodel devono estendere il `BaseViewModel`.
- `views.activities.SplashScreenActivity.kt` che rappresente la *View*, essa contiene il riferimento al viewmodel injettato tramite l'annotation `@Inject`. Tutte le activity devono estendere la `BaseActivity`; in egual modo tutti i fragment dovranno estendere il `BaseFragment`.
- `models.BaseModel.kt` è la classe dedicata all'implementazioni della parte Model del pattern, in base ai modelli richiesti del contesto essa verrà implementata di conseguenza. Tutti i model devono estendere il `BaseModel`.

### Cartelle di progetto

La cartella contenente il codice sorgente dell'app avrà la seguente struttura:

```
|--java
  |--com.tiknil.android_kotlin_boilerplate
    |-- di                # Classi per l'implementazione della dependency injection con Dragger2
    |-- models            # Tutti gli oggetti model
    |-- services          # Oggetti che forniscono servizi (providers) come networking, persistenza dei dati, ecc...
    |-- utils             # Classi di generico aiuto per tutto l'app
    |-- views             # Le classi che implementano la ui
        |-- activities    # Tutte le activity eventualmente inseriti in sottocartelle di sezione
        |-- fragments     # Tutte i fragment eventualmente inseriti in sottocartelle di sezione
    |-- viewmodels        # Tutti i viewmodel eventualmente inseriti in sottocartelle di sezione
|-- assets
    |-- fonts             # Contiene i file dei fonts
|-- res                   # Cartella di resources: color, drawable, layout,...
```
