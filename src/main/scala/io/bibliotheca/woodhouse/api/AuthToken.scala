package io.bibliotheca.woodhouse.api

case class AuthToken(accessToken: String,
                     tokenType: String,
                     expiresIn: String,
                     scope: String)