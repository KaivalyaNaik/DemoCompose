package com.example.democompose

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.security.InvalidParameterException

enum class Screen{Login,Home}

fun Fragment.navigate(to:Screen,from:Screen){
    if (to==from)
        throw InvalidParameterException("Can't navigate to $to")

    when(to){
        Screen.Login->{
            findNavController().navigate(R.id.loginFragment)
        }
        Screen.Home->{
            findNavController().navigate(R.id.homeFragment)
        }
    }
}