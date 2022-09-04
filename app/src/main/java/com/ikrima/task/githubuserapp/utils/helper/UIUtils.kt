package com.ikrima.task.githubuserapp.utils.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.ikrima.task.githubuserapp.R
import de.hdodenhof.circleimageview.CircleImageView

object UIUtils {

    fun CircleImageView.loadImage(url : String?, context : Context, progressBar : ProgressBar) {
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false                        }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false                        }

            })
            .placeholder(R.drawable.icon_github)
            .error(R.drawable.icon_github)
            .dontAnimate()
            .into(this)
    }
}