package com.example.externaldatabase

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.externaldatabase.databinding.ShayriFileBinding


class ShayriAdapter(
    var context: Context,
    var shayri: ArrayList<ShayriModalClass>,
    var shayriItemClick: (String, Int) -> Unit,
    var DeleteClick: (Int) -> Unit,
    var type: String?,
) :
    RecyclerView.Adapter<ShayriAdapter.MyViewHolder>() {


    class MyViewHolder(var binding: ShayriFileBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        val binding = ShayriFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.txtShayri.text = shayri[position].shayri
        shayriItemClick.invoke(shayri[position].shayri, shayri[position].category)

        holder.binding.imgDelete.setOnClickListener {
            var dialog = Dialog(context)
            dialog.setContentView(R.layout.delete_dialog);
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false);

            var btnDelete = dialog.findViewById<Button>(R.id.btnDelete)
            var btnCencel = dialog.findViewById<Button>(R.id.btnCencel)

            btnDelete.setOnClickListener {
                DeleteClick.invoke(shayri[position].Id)
                dialog.dismiss()
            }
            btnCencel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        if (shayri[position].status == 1) {
            holder.binding.imgEmptyLike.visibility = View.GONE
            holder.binding.imgFillLike.visibility = View.VISIBLE
        }

        holder.binding.imgCopy.setOnClickListener(View.OnClickListener { v: View? ->
            Toast.makeText(context, "Quote Copied in Clipboard.", Toast.LENGTH_SHORT).show()
            val copyQuote: String = holder.binding.txtShayri.text.toString()
            val clipboard =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Text Copty", copyQuote)
            clipboard.setPrimaryClip(clip)
        })
        holder.binding.imgEmptyLike.setOnClickListener(View.OnClickListener { v: View? ->
            holder.binding.imgFillLike.visibility = View.VISIBLE
            holder.binding.imgEmptyLike.visibility = View.GONE
            Toast.makeText(context, "Added to Favourite", Toast.LENGTH_SHORT).show()
            MyDatabase(context).editstatus(1, shayri[position].Id)
        })
        holder.binding.imgFillLike.setOnClickListener(View.OnClickListener { v: View? ->
            holder.binding.imgFillLike.visibility = View.GONE
            holder.binding.imgEmptyLike.visibility = View.VISIBLE
            MyDatabase(context).editstatus(0, shayri[position].Id)
        })
        holder.binding.imgShareAllApp.setOnClickListener(View.OnClickListener { v: View? ->
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody: String = holder.binding.txtShayri.text.toString()
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
        })
        holder.binding.imgShareWhatsapp.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            val shareBody: String = holder.binding.txtShayri.text.toString()
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.whatsapp")
            context.startActivity(sendIntent)
        }
        holder.binding.imgImage.setOnClickListener {
            holder.binding.txtShayri.text.toString()
            var intent = Intent(context, ShayriImageActivity::class.java)
            intent.putExtra("shayriimage", shayri[position].shayri)
            intent.putExtra("category", type)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return shayri.size
    }
}