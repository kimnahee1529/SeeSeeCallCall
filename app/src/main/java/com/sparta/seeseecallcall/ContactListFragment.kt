package com.sparta.seeseecallcall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparta.seeseecallcall.MainActivity.Companion.contactBookmarkList
import com.sparta.seeseecallcall.MainActivity.Companion.contactList
import com.sparta.seeseecallcall.databinding.FragmentContactListBinding

class ContactListFragment : Fragment() {
    private val TAG = "ContactListFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactListBinding.inflate(inflater, container, false)

        val adapter = MyAdapter(contactList)
        binding.recyclerviewList.adapter = adapter
        binding.recyclerviewList.layoutManager = LinearLayoutManager(context)

        adapter.itemClick = object : MyAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                Log.d(TAG, "position: $position")
                //TODO 연락처 객체 넘기며, 연락처 상세 페이지로 이동
            }

            override fun onStarClick(view: View, position: Int) {
                contactList[position].run{
                    favorite = !favorite

                    if(favorite) contactBookmarkList.add(this)
                    else contactBookmarkList.remove(this)
                }
                adapter.notifyItemChanged(position)
            }
        }

        return binding.root
    }

}