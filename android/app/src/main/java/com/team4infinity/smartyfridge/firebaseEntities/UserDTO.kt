package com.team4infinity.smartyfridge.firebaseEntities

data class UserDTO(
        val Id :String = "",
        val Name :String = "",
        val Surname :String = "",
        val Email :String = "",
        val Password :String = "",
        val ImagePath :String = "",
        val GroupID :String = ""
){
        override fun toString(): String {
                return "UserDTO(Id='$Id', Name='$Name', Surname='$Surname', Email='$Email', Password='$Password', ImagePath='$ImagePath', GroupID='$GroupID')"
        }
}
