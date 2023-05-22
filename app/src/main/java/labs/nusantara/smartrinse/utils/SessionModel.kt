package labs.nusantara.smartrinse.utils

data class SessionModel(
    val userId: String,
    val email: String,
    val name: String,
    val token: String,
    val isLogin: Boolean
)
