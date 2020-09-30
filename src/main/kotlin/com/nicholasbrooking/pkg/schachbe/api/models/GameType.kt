/**
* Schachfish-Api
* Schachfish is a chess engine for making/querying moves and board evaluations
*
* The version of the OpenAPI document: 0.0.1
* 
*
* NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
* https://openapi-generator.tech
* Do not edit the class manually.
*/
package com.nicholasbrooking.pkg.schachbe.api.models


import com.squareup.moshi.Json

/**
* 
* Values: standard
*/

enum class GameType(val value: kotlin.String){


    @Json(name = "standard")
    standard("standard");



	/**
	This override toString avoids using the enum var name and uses the actual api value instead.
	In cases the var name and value are different, the client would send incorrect enums to the server.
	**/
	override fun toString(): String {
        return value
    }

}

