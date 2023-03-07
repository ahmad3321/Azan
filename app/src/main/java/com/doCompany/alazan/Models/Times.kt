package com.doCompany.alazan.Models



data class Welcome1 (
        val code: Long,
        val status: String,
        val data: List<Datum>
)

data class Datum (
        val timings: Timings,
        val date: Date,
        val meta: Meta
)

data class Date (
        val readable: String,
        val timestamp: String,
        val gregorian: Gregorian,
        val hijri: Hijri
)

data class Gregorian (
        val date: String,
        val format: Format,
        val day: String,
        val weekday: GregorianWeekday,
        val month: GregorianMonth,
        val year: String,
        val designation: Designation
)

data class Designation (
        val abbreviated: Abbreviated,
        val expanded: Expanded
)

enum class Abbreviated(val value: String) {
        Ad("AD"),
        Ah("AH");

        companion object {
                public fun fromValue(value: String): Abbreviated = when (value) {
                        "AD" -> Ad
                        "AH" -> Ah
                        else -> throw IllegalArgumentException()
                }
        }
}

enum class Expanded(val value: String) {
        AnnoDomini("Anno Domini"),
        AnnoHegirae("Anno Hegirae");

        companion object {
                public fun fromValue(value: String): Expanded = when (value) {
                        "Anno Domini"  -> AnnoDomini
                        "Anno Hegirae" -> AnnoHegirae
                        else           -> throw IllegalArgumentException()
                }
        }
}

enum class Format(val value: String) {
        DDMmYyyy("DD-MM-YYYY");

        companion object {
                public fun fromValue(value: String): Format = when (value) {
                        "DD-MM-YYYY" -> DDMmYyyy
                        else         -> throw IllegalArgumentException()
                }
        }
}

data class GregorianMonth (
        val number: Long,
        val en: PurpleEn
)

enum class PurpleEn(val value: String) {
        April("April");

        companion object {
                public fun fromValue(value: String): PurpleEn = when (value) {
                        "April" -> April
                        else    -> throw IllegalArgumentException()
                }
        }
}

data class GregorianWeekday (
        val en: String
)

data class Hijri (
        val date: String,
        val format: Format,
        val day: String,
        val weekday: HijriWeekday,
        val month: HijriMonth,
        val year: String,
        val designation: Designation,
        val holidays: List<String>
)

data class HijriMonth (
        val number: Long,
        val en: FluffyEn,
        val ar: Ar
)

enum class Ar(val value: String) {
        رجب("رَجَب"),
        شعبان("شَعْبان");

        companion object {
                public fun fromValue(value: String): Ar = when (value) {
                        "رَجَب"   -> رجب
                        "شَعْبان" -> شعبان
                        else      -> throw IllegalArgumentException()
                }
        }
}

enum class FluffyEn(val value: String) {
        Rajab("Rajab"),
        SHABān("Shaʿbān");

        companion object {
                public fun fromValue(value: String): FluffyEn = when (value) {
                        "Rajab"   -> Rajab
                        "Shaʿbān" -> SHABān
                        else      -> throw IllegalArgumentException()
                }
        }
}

data class HijriWeekday (
        val en: String,
        val ar: String
)

data class Meta (
        val latitude: Double,
        val longitude: Double,
        val timezone: Timezone,
        val method: Meth? = null,
        val latitudeAdjustmentMethod: LatitudeAdjustmentMethod,
        val midnightMode: MidnightMode,
        val school: MidnightMode,
        val offset: Map<String, Long>,
        val methaod: Meth? = null
)

enum class LatitudeAdjustmentMethod(val value: String) {
        AngleBased("ANGLE_BASED");

        companion object {
                public fun fromValue(value: String): LatitudeAdjustmentMethod = when (value) {
                        "ANGLE_BASED" -> AngleBased
                        else          -> throw IllegalArgumentException()
                }
        }
}

data class Meth (
        val id: Long,
        val name: Name,
        val params: Params,
        val location: Location
)

data class Location (
        val latitude: Double,
        val longitude: Double
)

enum class Name(val value: String) {
        IslamicSocietyOfNorthAmericaISNA("Islamic Society of North America (ISNA)");

        companion object {
                public fun fromValue(value: String): Name = when (value) {
                        "Islamic Society of North America (ISNA)" -> IslamicSocietyOfNorthAmericaISNA
                        else                                      -> throw IllegalArgumentException()
                }
        }
}

data class Params (
        val fajr: Long,

        val isha: Long
)

enum class MidnightMode(val value: String) {
        Standard("STANDARD");

        companion object {
                public fun fromValue(value: String): MidnightMode = when (value) {
                        "STANDARD" -> Standard
                        else       -> throw IllegalArgumentException()
                }
        }
}

enum class Timezone(val value: String) {
        EuropeLondon("Europe/London");
        companion object {
                public fun fromValue(value: String): Timezone = when (value) {
                        "Europe/London" -> EuropeLondon
                        else            -> throw IllegalArgumentException()
                }
        }
}

data class Timings (
        val Fajr: String,

        val Sunrise: String,

        val Dhuhr: String,

        val Asr: String,

        val Sunset: String,

        val Maghrib: String,

        val Isha: String,

        val Imsak: String,

        val Midnight: String,

        val Firstthird: String,

        val Lastthird: String
)
