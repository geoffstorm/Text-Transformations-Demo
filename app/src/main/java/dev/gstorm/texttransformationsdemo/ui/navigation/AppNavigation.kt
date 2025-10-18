package dev.gstorm.texttransformationsdemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.gstorm.texttransformationsdemo.ui.demos.CaesarCipherDemoScreen
import dev.gstorm.texttransformationsdemo.ui.demos.CreditCardDemoScreen
import dev.gstorm.texttransformationsdemo.ui.HomeScreen
import dev.gstorm.texttransformationsdemo.ui.demos.MarkdownDemoScreen
import dev.gstorm.texttransformationsdemo.ui.demos.MultiTransformDemoScreen
import dev.gstorm.texttransformationsdemo.ui.demos.NumberInputDemoScreen
import dev.gstorm.texttransformationsdemo.ui.demos.PasswordDemoScreen
import dev.gstorm.texttransformationsdemo.ui.demos.PhoneNumberDemoScreen
import kotlinx.serialization.Serializable

sealed interface AppNavKeys : NavKey {
    val description: String
}

@Serializable
private data object HomeKey : AppNavKeys {
    override val description = ""
}

@Serializable
private data object NumberInputDemoKey : AppNavKeys {
    override val description = "Number Input Demo"
}

@Serializable
private data object PasswordDemoKey : AppNavKeys {
    override val description = "Password Demo"
}

@Serializable
private data object PhoneNumberDemoKey : AppNavKeys {
    override val description = "Phone Number Demo"
}

@Serializable
private data object CreditCardDemoKey : AppNavKeys {
    override val description = "Credit Card Demo"
}

@Serializable
private data object CaesarCipherDemoKey : AppNavKeys {
    override val description = "Caesar Cipher Demo"
}

@Serializable
private data object MarkdownDemoKey : AppNavKeys {
    override val description = "Markdown Demo"
}

@Serializable
private data object MultiTransformDemoKey : AppNavKeys {
    override val description = "Multi Transform Demo"
}

val DESTINATIONS: List<AppNavKeys> = listOf(
    NumberInputDemoKey,
    PasswordDemoKey,
    PhoneNumberDemoKey,
    CreditCardDemoKey,
    CaesarCipherDemoKey,
    MarkdownDemoKey,
    MultiTransformDemoKey
)

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(HomeKey)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<HomeKey> {
                HomeScreen { backStack.add(it) }
            }

            entry<NumberInputDemoKey> {
                NumberInputDemoScreen { backStack.removeLastOrNull() }
            }

            entry<PasswordDemoKey> {
                PasswordDemoScreen { backStack.removeLastOrNull() }
            }

            entry<PhoneNumberDemoKey> {
                PhoneNumberDemoScreen { backStack.removeLastOrNull() }
            }

            entry<CreditCardDemoKey> {
                CreditCardDemoScreen { backStack.removeLastOrNull() }
            }

            entry<CaesarCipherDemoKey> {
                CaesarCipherDemoScreen { backStack.removeLastOrNull() }
            }

            entry<MarkdownDemoKey> {
                MarkdownDemoScreen { backStack.removeLastOrNull() }
            }

            entry<MultiTransformDemoKey> {
                MultiTransformDemoScreen { backStack.removeLastOrNull() }
            }
        }
    )
}

