import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NominatimResponse(
    @SerialName("place_id") val placeId: Long,
    @SerialName("licence") val licence: String,
    @SerialName("osm_type") val osmType: String,
    @SerialName("osm_id") val osmId: String,
    @SerialName("boundingbox") val boundingBox: List<String>,
    @SerialName("lat") val latitude: String,
    @SerialName("lon") val longitude: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("class") val className: String,
    @SerialName("type") val type: String,
    @SerialName("importance") val importance: Double,
    @SerialName("icon") val icon: String? = null,
    @SerialName("address") val address: Address,
    @SerialName("extratags") val extraTags: ExtraTags? = null
)

@Serializable
data class Address(
    @SerialName("city") val city: String? = null,
    @SerialName("state_district") val stateDistrict: String? = null,
    @SerialName("state") val state: String? = null,
    @SerialName("ISO3166-2-lvl4") val iso3166location: String? = null,
    @SerialName("postcode") val postcode: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("country_code") val countryCode: String? = null
)

@Serializable
data class ExtraTags(
    @SerialName("capital") val capital: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("wikidata") val wikidata: String? = null,
    @SerialName("wikipedia") val wikipedia: String? = null,
    @SerialName("population") val population: String? = null
)