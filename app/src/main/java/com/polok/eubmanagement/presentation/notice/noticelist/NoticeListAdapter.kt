package com.polok.eubmanagement.presentation.notice.noticelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemNoticeListBinding
import com.polok.eubmanagement.model.NoticeData
import com.polok.eubmanagement.presentation.notice.noticelist.NoticeListAdapter.NoticeViewHolder

class NoticeListAdapter(
    private val onClickListener: ((NoticeData?) -> Unit)?
) : RecyclerView.Adapter<NoticeViewHolder>() {
    private var noticeList: List<NoticeData?>? = null

    fun submitList(noticeList: List<NoticeData?>?) {
        this.noticeList = noticeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        return NoticeViewHolder(
            ItemNoticeListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(noticeList?.get(position))
    }

    override fun getItemCount(): Int = noticeList?.size ?: 0

    inner class NoticeViewHolder(
        private val binding: ItemNoticeListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noticeData: NoticeData?) {
            binding.noticeTitle.text = noticeData?.title ?: ""
            binding.noticeDetails.text = noticeData?.details ?: ""
            binding.createdAt.text = String.format("Created At: %s", noticeData?.createdAt ?: "")

            if (onClickListener != null) {
                itemView.setOnClickListener {
                    onClickListener.invoke(noticeData)
                }
            } else {
                binding.getRoot().isClickable = false
            }
        }
    }
}
