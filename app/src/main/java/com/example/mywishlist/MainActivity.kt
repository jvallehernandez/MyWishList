package com.example.mywishlist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private val items = mutableListOf<WishlistItem>()
    private lateinit var adapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Your layout filename from earlier
        setContentView(R.layout.constraintlayout)

        val root: View = findViewById(R.id.root)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val sb = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom)
            insets
        }

        // Views
        val rv: RecyclerView = findViewById(R.id.rvItems)
        val tilName: TextInputLayout = findViewById(R.id.tilName)
        val tilPrice: TextInputLayout = findViewById(R.id.tilPrice)
        val tilUrl: TextInputLayout = findViewById(R.id.tilUrl)
        val etName: TextInputEditText = findViewById(R.id.etName)
        val etPrice: TextInputEditText = findViewById(R.id.etPrice)
        val etUrl: TextInputEditText = findViewById(R.id.etUrl)
        val btnSubmit: View = findViewById(R.id.btnSubmit)

        // RecyclerView
        adapter = WishlistAdapter(
            items,
            onClick = { item, v -> openUrl(v, item) },
            onLongClickRemove = { index -> removeAt(index) }
        )
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // Submit button
        btnSubmit.setOnClickListener {
            clearErrors(tilName, tilPrice, tilUrl)

            val name = etName.text?.toString()?.trim().orEmpty()
            val priceText = etPrice.text?.toString()?.trim().orEmpty()
            val urlRaw = etUrl.text?.toString()?.trim().orEmpty()

            var valid = true
            if (name.isEmpty()) {
                tilName.error = "Required"
                valid = false
            }

            val price = priceText.toDoubleOrNull()
            if (price == null) {
                tilPrice.error = "Enter a number"
                valid = false
            }

            val url = normalizeUrl(urlRaw)
            if (url.isEmpty() || !Patterns.WEB_URL.matcher(url).matches()) {
                tilUrl.error = "Enter a valid URL"
                valid = false
            }

            if (!valid) return@setOnClickListener

            // Add to list
            items.add(WishlistItem(name, price!!, url))
            adapter.notifyItemInserted(items.lastIndex)
            rv.scrollToPosition(items.lastIndex)

            // Reset inputs
            etName.text = null
            etPrice.text = null
            etUrl.text = null
            hideKeyboard()
        }
    }

    private fun removeAt(index: Int) {
        if (index in items.indices) {
            items.removeAt(index)
            adapter.notifyItemRemoved(index)
            Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openUrl(view: View, item: WishlistItem) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
            ContextCompat.startActivity(view.context, intent, null)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(view.context, "Invalid URL for ${item.name}", Toast.LENGTH_LONG).show()
        }
    }

    private fun normalizeUrl(raw: String): String {
        if (raw.isBlank()) return ""
        val hasScheme = raw.startsWith("http://", true) || raw.startsWith("https://", true)
        return if (hasScheme) raw else "http://$raw"
    }

    private fun clearErrors(vararg til: TextInputLayout) {
        til.forEach { it.error = null }
    }

    private fun hideKeyboard() {
        currentFocus?.let { v ->
            val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
            v.clearFocus()
        }
    }
}
