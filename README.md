# Snack

Lifecycle-aware library to show fully customizable snacks.  
Inspired by [Cicerone](https://github.com/terrakok/Cicerone) and [Surf-Navigation](https://github.com/surfstudio/SurfAndroidStandard/tree/dev/G-0.5.0/navigation).

## Connection
  // TODO
  
## Documentation

The main idea is to use fragments as snacks. In that way a user of this library has full controll of creating, rendering, showing and hiding snacks. 

## Quick Start

Create `SnackCommandBus` and register `SnackNavigationLifecycleCallbacks` in your `Application.onCreate` method

```kotlin
class App: Application() {

    val snackCommandBus = SnackCommandBus()

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(
            SnackNavigationLifecycleCallbacks(snackCommandBus)
        )
    }
}
```

Create a fragment file which you want to use as a snack

```kotlin
class ExampleSnackFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_snack_example, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO extract your data from arguments here
    }
}
```

Create a snack class and implement `Snack` interface
```kotlin
data class ExampleSnack(val text: String): Snack {
    override val containerId: Int = android.R.id.content  
    override val timeoutMs: Long = 1500L 
    override val animations: Animations = FromBottomToBottomAnimations
    override val fragmentClass: Class<out Fragment> = ExampleSnackFragment::class.java
    override val tag: String = "ExampleSnackFragment $text"

    override fun prepareBundle(bundle: Bundle) {
        bundle.putString(SNACK_TEXT_ARGS, text)
    }

    companion object {
        const val SNACK_TEXT_ARGS = "SNACK_TEXT_ARGS"
    }
}
```
Show and hide snack
```kotlin
val snack = ExampleSnack(text = "Some useful information for the user here")
snackCommandBus.open(snack)
//snackCommandBus.close(snack)
```


## Tips
1. Use custom `containerId` if you want snacks to appear in some particular container
2. Set `timeoutMs` field as null if you don't want your snacks to hide automatically
3. Be sure to keep tags unique throughout all snacks, especially if they appear on the screen at the same time
4. Check out the `example` module to see more examples of usage
5. You can override `animations` field if you want something extraordinary ;)

## Roadmap

1. Migrate to FragmentFactory for fragment creation instead of using reflection
2. Implement snack queue and config with strategies of adding snacks to queue when the navigator is not able to show them, drop oldest or even show only last added
3. Add tests
4. Add documentation
5. More examples
6. Deploy artefacts to have the ability of using the library with the gradle

## Videos
<details>
  <summary>Top snack with fade animations</summary>
  
  https://user-images.githubusercontent.com/35849702/160282434-e80dadcb-b102-45d0-bda4-8c7441cc5177.mp4
  
</details>

<details>
  <summary>Bottom snack with slide animations</summary>
 
  https://user-images.githubusercontent.com/35849702/160282436-f08c4b83-da59-4147-b436-fe467073cf5f.mp4
  
</details>


<details>
  <summary>Complicated snack</summary>
  
  
   It has it's own state, no timeout and it is shown in custom container
 
  https://user-images.githubusercontent.com/35849702/160282438-21244a17-fca3-412f-9f9a-adbd8c5114cf.mp4
  
</details>
