package chat.auth.model.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import chat.core.components.sections.ExtraNavHeaderAction
import chat.core.components.sections.NavHeaderAction
import com.varabyte.kobweb.navigation.Router
import com.varabyte.kobweb.silk.components.icons.fa.FaRightFromBracket

sealed class LoginState {
    companion object {
        private val mutableLoginState by lazy { mutableStateOf<LoginState>(LoggedOut) }

        var current
            get() = mutableLoginState.value
            set(value) {
                mutableLoginState.value = value

                when (value) {
                    is LoggedIn -> {
                        ExtraNavHeaderAction.current = object : NavHeaderAction() {
                            @Composable
                            override fun render() {
                                FaRightFromBracket()
                            }

                            override fun onActionClicked(router: Router) {
                                LoginState.current = LoggedOut
                                router.navigateTo("/")
                            }
                        }
                    }

                    LoggedOut -> {
                        ExtraNavHeaderAction.current = null
                    }
                }
            }
    }

    object LoggedOut : LoginState()
    class LoggedIn(val account: Account) : LoginState()
}
