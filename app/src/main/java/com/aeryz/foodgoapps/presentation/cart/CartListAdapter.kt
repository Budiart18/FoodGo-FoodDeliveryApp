package com.aeryz.foodgoapps.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemCartProductBinding
import com.aeryz.foodgoapps.databinding.ItemCartProductOrderBinding
import com.aeryz.foodgoapps.model.Cart
import com.aeryz.foodgoapps.model.CartProduct
import com.aeryz.foodgoapps.utils.doneEditing

class CartListAdapter(private val cartListener: CartListener? = null) :
    RecyclerView.Adapter<ViewHolder>(){

    private val dataDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<CartProduct>(){
            override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
                return oldItem.cart.id == newItem.cart.id && oldItem.cart.productId == newItem.cart.productId
            }

            override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    fun submitData(data: List<CartProduct>){
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (cartListener != null) {
            CartViewHolder(
                ItemCartProductBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), cartListener
            )
        } else {
            CartOrderViewHolder(
                ItemCartProductOrderBinding.inflate(
                    LayoutInflater.from(parent.context) , parent, false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return dataDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<CartProduct>).bind(dataDiffer.currentList[position])
    }
}

class CartOrderViewHolder(
    private val binding: ItemCartProductOrderBinding
) : ViewHolder(binding.root), ViewHolderBinder<CartProduct> {

    override fun bind(item: CartProduct) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartNotes(item: CartProduct) {
        binding.tvNotes.text = item.cart.itemNotes
    }

    private fun setCartData(item: CartProduct) {
        with(binding) {
            sivProductImage.load(item.product.productImageUrl){
                crossfade(true)
            }
            tvTotalQuantity.text = itemView.rootView.context.getString(
                R.string.total_quantity,
                item.cart.itemQuantity.toString()
            )
            tvProductName.text = item.product.productName
            tvProductPrice.text = (item.cart.itemQuantity * item.product.productPrice).toString()
        }
    }
}

class CartViewHolder(
    private val binding: ItemCartProductBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartProduct>{

    override fun bind(item: CartProduct) {
        setCartData(item)
        setCartNotes(item)
        setClickListener(item)
    }

    private fun setClickListener(item: CartProduct) {
        with(binding){
            ivMinus.setOnClickListener{ cartListener?.onMinusTotalItemCartClicked(item.cart)}
            ivPlus.setOnClickListener{ cartListener?.onPlusTotalItemCartClicked(item.cart)}
            ivRemoveCart.setOnClickListener{ cartListener?.onRemoveCartClicked(item.cart)}
        }
    }

    private fun setCartNotes(item: CartProduct) {
        binding.etNotesItem.setText(item.cart.itemNotes)
        binding.etNotesItem.doneEditing {
            binding.etNotesItem.clearFocus()
            val newItem = item.cart.copy().apply {
                itemNotes = binding.etNotesItem.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setCartData(item: CartProduct) {
        with(binding) {
            sivProductImage.load(item.product.productImageUrl){
                crossfade(true)
            }
            tvProductName.text = item.product.productName
            tvProductPrice.text = (item.cart.itemQuantity * item.product.productPrice).toString()
            tvProductCount.text = item.cart.itemQuantity.toString()
        }
    }

}

interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}