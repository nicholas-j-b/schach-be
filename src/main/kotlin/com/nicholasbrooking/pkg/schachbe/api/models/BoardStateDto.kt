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

import com.nicholasbrooking.pkg.schachbe.api.models.Colour
import com.nicholasbrooking.pkg.schachbe.api.models.ColourStatusDto
import com.nicholasbrooking.pkg.schachbe.api.models.EnPassantDto

import com.squareup.moshi.Json
/**
 * 
 * @param blackStatus 
 * @param whiteStatus 
 * @param turn 
 * @param enPassant 
 */

data class BoardStateDto (
    @Json(name = "blackStatus")
    val blackStatus: ColourStatusDto? = null,
    @Json(name = "whiteStatus")
    val whiteStatus: ColourStatusDto? = null,
    @Json(name = "turn")
    val turn: Colour? = null,
    @Json(name = "enPassant")
    val enPassant: EnPassantDto? = null
)

