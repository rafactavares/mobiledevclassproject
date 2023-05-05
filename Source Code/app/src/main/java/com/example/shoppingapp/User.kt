package com.example.shoppingapp

import android.os.Parcel
import android.os.Parcelable

data class User(
    var id: Int,
    var email: String?,
    var username: String?,
    var password: String?,
    var name: Name,
    var address: Address,
    var phone: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable<Name>(Name::class.java.classLoader)!!,
        parcel.readParcelable<Address>(Address::class.java.classLoader)!!,
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "User(id=$id, email='$email', username='$username', password='$password', name=$name, address=$address, phone='$phone')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(email)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

data class Name(
    val firstname: String?,
    val lastname: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstname)
        parcel.writeString(lastname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Name> {
        override fun createFromParcel(parcel: Parcel): Name {
            return Name(parcel)
        }

        override fun newArray(size: Int): Array<Name?> {
            return arrayOfNulls(size)
        }
    }
}

data class Address(
    val city: String?,
    val street: String?,
    val number: Int,
    val zipcode: String?,
    val geolocation: Geolocation?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Geolocation::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(city)
        parcel.writeString(street)
        parcel.writeInt(number)
        parcel.writeString(zipcode)
        parcel.writeParcelable(geolocation, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }
}

data class Geolocation(
    val lat: String?,
    val long: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lat)
        parcel.writeString(long)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Geolocation> {
        override fun createFromParcel(parcel: Parcel): Geolocation {
            return Geolocation(parcel)
        }

        override fun newArray(size: Int): Array<Geolocation?> {
            return arrayOfNulls(size)
        }
    }
}
