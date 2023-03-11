package chat.auth.model

import chat.auth.model.auth.Account
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext

@InitApi
fun initAccounts(ctx: InitApiContext) {
    ctx.data.add(Accounts())
}

class Accounts {
    val set = mutableSetOf<Account>()
}