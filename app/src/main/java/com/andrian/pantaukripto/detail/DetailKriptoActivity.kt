package com.andrian.pantaukripto.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.andrian.core.domain.model.Kripto
import com.andrian.core.utils.formatToRupiah
import com.andrian.pantaukripto.R
import com.andrian.pantaukripto.databinding.ActivityDetailKriptoBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailKriptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKriptoBinding
    private val detailKriptoViewModel: DetailKriptoViewModel by viewModel()

    private var kripto: Kripto? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKriptoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarColor(R.color.black, lightIcons = false)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setNavigationIconColorWhite()

        kripto = intent.getParcelableExtra(EXTRA_DATA)

        initViews()
        showKriptoDetail()
        observeFavoriteStatus()
    }

    private fun initViews() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showKriptoDetail() {
        kripto?.let { data ->
            with(binding) {
                Glide.with(this@DetailKriptoActivity).load(data.logoUrl).into(imageMarketLogo)
                textSymbol.text = data.symbol
                textBaseCurrency.text = data.description
                textLastPrice.text = formatToRupiah(data.last)
                textBuyPrice.text = formatToRupiah(data.buy)
                textSellPrice.text = formatToRupiah(data.sell)
                textHighPrice.text = formatToRupiah(data.high)
                textLowPrice.text = formatToRupiah(data.low)

                val priceChange = data.priceChange
                val formattedChange =
                    if (priceChange >= 0) "+%.2f%%".format(priceChange) else "%.2f%%".format(
                        priceChange
                    )
                textPriceChange.text = "24 Change : $formattedChange"
                val color = if (priceChange >= 0) Color.GREEN else Color.RED
                textPriceChange.setTextColor(color)
            }
        }
    }

    private fun observeFavoriteStatus() {
        kripto?.let { data ->
            detailKriptoViewModel.isFavoriteKripto(data.kriptoId).observe(this) { status ->
                isFavorite = status
                invalidateOptionsMenu()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        val favoriteItem = menu.findItem(R.id.action_favorite)
        val drawableRes = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        val drawable = ContextCompat.getDrawable(this, drawableRes)
        val colorRes = if (isFavorite) R.color.red else R.color.white
        drawable?.let {
            DrawableCompat.setTint(it, ContextCompat.getColor(this, colorRes))
            favoriteItem.icon = it
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.action_favorite -> {
                kripto?.let { data ->
                    val newStatus = !isFavorite
                    detailKriptoViewModel.setFavoriteKripto(data.kriptoId, newStatus)
                    if (isFavorite) {
                        setResult(RESULT_CODE_UPDATE)
                    }
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setNavigationIconColorWhite() {
        val navIcon = binding.topAppBar.navigationIcon
        navIcon?.setTint(ContextCompat.getColor(this, R.color.white))
        binding.topAppBar.navigationIcon = navIcon
    }

    private fun setStatusBarColor(colorRes: Int, lightIcons: Boolean) {
        window.statusBarColor = ContextCompat.getColor(this, colorRes)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
            lightIcons
    }

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val RESULT_CODE_UPDATE = 1001
    }
}

