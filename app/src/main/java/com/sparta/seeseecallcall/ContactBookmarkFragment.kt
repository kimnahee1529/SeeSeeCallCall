package com.sparta.seeseecallcall

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.sparta.seeseecallcall.data.Contact
import com.sparta.seeseecallcall.data.ContactManager
import com.sparta.seeseecallcall.data.ContactManager.contactBookmarkList
import com.sparta.seeseecallcall.databinding.FragmentContactBookmarkBinding

class ContactBookmarkFragment : Fragment() {

    private val adapter by lazy { MyBookMarkAdapter(contactBookmarkList) }
    private val TAG = "ContactBookmarkFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactBookmarkBinding.inflate(inflater, container, false)

        val itemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        binding.recyclerviewBookmark.addItemDecoration(itemDecoration)

        if (binding.recyclerviewBookmark.itemDecorationCount > 0) {
            binding.recyclerviewBookmark.removeItemDecorationAt(0)
        }

        binding.recyclerviewBookmark.adapter = adapter
        binding.recyclerviewBookmark.layoutManager = GridLayoutManager(context, 2)

        adapter.itemClick = object : MyAdapter.ItemClick{
            override fun onClick(view: View, contact:Contact) {
                //TODO 전화 인텐트 시작하기
            }
            override fun onStarClick(view: View, contact:Contact) {
                ContactManager.toggleFavoriteContact(contact)
                adapter?.notifyDataSetChanged()
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(text: Editable?) {
                Log.d(TAG, "afterTextChanged, ${text.toString()}")
                adapter.changeDataset(
                    if (text.isNullOrBlank()) contactBookmarkList
                    else getFilteredList(text.toString())
                )
            }
        })

        return binding.root
    }

    override fun onResume(){
        Log.d(TAG, "ContactBookmarkFragmentList onResume()")

        adapter?.notifyDataSetChanged()
        super.onResume()
    }

    private fun getFilteredList(searchText: String): MutableList<Contact> {
        val filteredList = mutableListOf<Contact>()
        return filteredList.apply {
            contactBookmarkList.forEach {
                //이름으로 검색
                if (it.name.contains(searchText)) add(it)
                //mbti로 검색
                if (it.mbti.contains(searchText.toUpperCase())) add(it)
            }
        }
    }
}