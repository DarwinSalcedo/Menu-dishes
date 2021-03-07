/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.uala.challenge.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uala.challenge.databinding.GridViewItemBinding
import com.uala.challenge.network.Meal


class MealGridAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Meal, MealGridAdapter.MealsPropertyViewHolder>(DiffCallback) {

    class MealsPropertyViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.property = meal
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.name == newItem.name
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealsPropertyViewHolder {
        return MealsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: MealsPropertyViewHolder, position: Int) {
        val meal = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(meal)
        }
        holder.bind(meal)
    }


    class OnClickListener(val clickListener: (property: Meal) -> Unit) {
        fun onClick(meal: Meal) = clickListener(meal)
    }
}
