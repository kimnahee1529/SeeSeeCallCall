package com.sparta.seeseecallcall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sparta.seeseecallcall.data.CompatibilityColor
import com.sparta.seeseecallcall.data.Contact
import com.sparta.seeseecallcall.data.ContactManager
import com.sparta.seeseecallcall.data.MbtiManager
import com.sparta.seeseecallcall.databinding.BookmarkRecyclerViewItemBinding

class MyBookMarkAdapter(private var dataset: MutableList<Contact>) :
    RecyclerView.Adapter<MyBookMarkAdapter.MyViewHolder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
        fun onStarClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = BookmarkRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(holder.itemView, holder.adapterPosition)
        }
        holder.starImageView.setOnClickListener {
            itemClick?.onStarClick(holder.itemView, holder.adapterPosition)
        }

        bind(holder, position)
    }

    fun changeDataset(newDataset: MutableList<Contact>) {
        dataset = newDataset
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: BookmarkRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val starImageView = binding.imgStar
        val profileImageView = binding.imgProfile
        val nameTextView = binding.tvName
        val mbtiTextView = binding.tvMbti
    }

    private fun bind(holder: MyViewHolder, position: Int) {
        val contact: Contact = dataset[position]

        holder.run {
            starImageView.setImageResource(
                if (contact.favorite) R.drawable.icon_star_yellow
                else R.drawable.icon_star_gray
            )

            profileImageView.run {
                if (contact.profileImage != null)
                    setImageURI(contact.profileImage)
                else if (contact.mbti == "????")
                    setImageResource(R.drawable.profile_mbti)
                else
                    setImageResource(
                        itemView.resources.getIdentifier(
                            "profile_${contact.mbti.toLowerCase()}",
                            "drawable",
                            "com.sparta.seeseecallcall"
                        )
                    )

                clipToOutline = true
            }

            nameTextView.text = contact.name

            mbtiTextView.text = contact.mbti

            mbtiTextView.background.setTint(
                ContextCompat.getColor(
                    holder.itemView.context,
                    if (contact.mbti == "????") {
                        CompatibilityColor.UN_KNOWN.color
                    } else {
                        val contactId: Int = MbtiManager.mbtiId[contact.mbti] ?: 0
                        val myId: Int = MbtiManager.mbtiId[ContactManager.myContact.mbti] ?: 0

                        MbtiManager.compatibilityColor[contactId][myId].color
                    }
                )
            )


        }

    }
}