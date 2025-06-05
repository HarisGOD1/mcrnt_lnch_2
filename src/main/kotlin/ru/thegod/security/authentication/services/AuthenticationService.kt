package ru.thegod.security.authentication.services

import jakarta.inject.Singleton

// AES/GCM
// Set-Cookie: AUTH-TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6...; Path=/; HttpOnly; Secure; SameSite=Strict
// AUTH-TOKEN=header.payload.signature -> Each part is Base64URL-encoded
// VCJ9.eyDB9.rN8xY
// Header: algorithm & token type (e.g., {"alg":"HS256","typ":"JWT"})       /_____  is the
// Payload: claims {"userId":123,"exp":170000000}                           \-----  JSON
// Signature: generated using the header, payload, and secret key

@Singleton
class AuthenticationService {

    fun isUserAuthenticatedReturnsPairOfBooleanAndUser(){

    }

}