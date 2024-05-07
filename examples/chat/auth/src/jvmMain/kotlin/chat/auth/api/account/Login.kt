package chat.auth.api.account

import chat.auth.model.Accounts
import chat.auth.model.auth.Account
import chat.auth.model.auth.LoginResponse
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.readBodyText
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Api
fun login(ctx: ApiContext) {
    if (ctx.req.method != HttpMethod.POST) return
    val account = Json.decodeFromString(Account.serializer(), ctx.req.readBodyText()!!)
    val accounts = ctx.data.getValue<Accounts>()
    ctx.res.setBodyText(Json.encodeToString(LoginResponse(succeeded = accounts.set.contains(account))))
}
