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
 * @param x 
 * @param y 
 */

data class PositionDto (
    @Json(name = "x")
    val x: kotlin.Int? = null,
    @Json(name = "y")
    val y: kotlin.Int? = null
)

