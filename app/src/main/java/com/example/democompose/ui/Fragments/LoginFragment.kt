package com.example.democompose.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.democompose.R
import com.example.democompose.Screen
import com.example.democompose.navigate
import com.example.democompose.ui.theme.DemoComposeTheme

class LoginFragment : Fragment() {

    private val viewModel:LoginViewModel by viewModels { LoginViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.navigateTo.observe(viewLifecycleOwner){navigateToEvent->
            navigateToEvent.getContentIfNotHandled()?.let {
                navigateTo->navigate(navigateTo,Screen.Login)
            }
        }
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            id=R.id.loginFragment
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                DemoComposeTheme {
                    Login(
                        onNavigationEvent = {
                            event ->
                            when(event){
                                is LoginEvent.Login->{
                                    viewModel.login(event.email,event.password)
                                }
                                LoginEvent.LoginAsGuest->{
                                    viewModel.loginAsGuest()
                                }
                                LoginEvent.NavigateBack->{
                                    activity?.onBackPressedDispatcher?.onBackPressed()
                                }
                            }
                        }
                    )
                }
            }
        }
    }

}