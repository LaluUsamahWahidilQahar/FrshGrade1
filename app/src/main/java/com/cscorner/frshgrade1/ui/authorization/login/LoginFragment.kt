package com.cscorner.frshgrade1.ui.authorization.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cscorner.frshgrade1.R
import com.cscorner.frshgrade1.databinding.FragmentLoginBinding
import com.cscorner.storyapp.ui.customview.EmailEdit
import com.cscorner.storyapp.ui.customview.LoginButton
import com.cscorner.storyapp.ui.customview.PasswordEdit


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginButton: LoginButton
    private lateinit var emailEdit: EmailEdit
    private lateinit var passwordEdit: PasswordEdit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        showLoading(false)


        loginButton = binding.loginButton
        emailEdit = binding.loginEmailEditText
        passwordEdit = binding.loginPasswordEditText
        setLoginButtonEnable()

        Enabler()


        binding.loginButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.mainActivity))

        binding.RegisterLink.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.registerFragment))

        playAnimation()

        onBackPress()
    }


    private fun setLoginButtonEnable() {
        val email = emailEdit.text
        val password = passwordEdit.text
        loginButton.isEnabled = email != null && email.toString()
            .isNotEmpty() && password != null && password.toString().isNotEmpty()
    }

    private fun Enabler(){
        emailEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.loginEmailEditTextLayout.error = null
                    setLoginButtonEnable()
                } else {
                    binding.loginEmailEditTextLayout.error = getString(R.string.error_invalid_email)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        passwordEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length < 8) {
                    binding.loginPasswordEditTextLayout.error = getString(R.string.error_password)
                } else {
                    binding.loginPasswordEditTextLayout.error = null
                    setLoginButtonEnable()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.loginImageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.loginTitleTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.loginEmailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.loginEmailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.loginPasswordEditText, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.loginPasswordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        val registerText =
            ObjectAnimator.ofFloat(binding.RegisterText, View.ALPHA, 1f).setDuration(100)
        val registerLink =
            ObjectAnimator.ofFloat(binding.RegisterLink, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                registerText,
                registerLink
            )
            startDelay = 100
        }.start()

    }

//    private fun setupView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//    }

    private fun onBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}