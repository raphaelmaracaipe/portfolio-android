package br.com.raphaelmaracaipe.portflio

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.raphaelmaracaipe.portflio.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        animationOfIcon()
        redirect()
    }

    private fun animationOfIcon() {
        binding.root.postDelayed({
            with(binding) {
                imvIcon.visibility = View.VISIBLE
                imvIcon.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@SplashActivity,
                        R.anim.anim_splash_icon
                    )
                )
            }
        }, 1500)
    }

    private fun redirect() {
        binding.root.postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this@SplashActivity,
                binding.imvIcon,
                "icon_app_transition"
            )

            startActivity(intent, options.toBundle())
        }, 3800)
    }


}