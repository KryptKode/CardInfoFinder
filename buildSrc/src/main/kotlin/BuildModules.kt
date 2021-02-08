/**
 * Configuration of build modules
 */
object BuildModules {
    const val app = ":app"
    const val core = ":core"

    object Features {
        const val users = ":features:users"
    }

    object Common {
        const val testShared = ":common:testShared"
        const val androidShared = ":common:androidShared"
    }

    object Libs{
        const val baseMvi = ":libs:basemvi"
    }
}
