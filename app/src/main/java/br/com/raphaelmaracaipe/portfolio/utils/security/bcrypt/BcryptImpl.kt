package br.com.raphaelmaracaipe.portfolio.utils.security.bcrypt

import org.mindrot.jbcrypt.BCrypt

class BcryptImpl: Bcrypt {

    override fun crypt(text: String) = BCrypt.hashpw(text, BCrypt.gensalt()) ?: text

}