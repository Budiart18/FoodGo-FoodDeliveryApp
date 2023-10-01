
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemSectionFoodListBinding
import com.aeryz.foodgoapps.model.Food
import com.aeryz.foodgoapps.presentation.home.adapter.model.AdapterLayoutMode
import com.aeryz.foodgoapps.presentation.home.adapter.model.HomeSection
import com.aeryz.foodgoapps.presentation.home.adapter.subadapter.FoodListAdapter

class FoodsSectionViewHolder(
    private val binding: ItemSectionFoodListBinding,
    private val onClickListener: (Food) -> Unit,
    private val onLayoutModeChanged: Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {

    private val adapter: FoodListAdapter by lazy {
        FoodListAdapter(adapterLayoutMode = AdapterLayoutMode.LINEAR) {
            onClickListener.invoke(it)
        }
    }

    private fun setupSwitch() {
        binding.ibSwitchMode.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        when (adapter.adapterLayoutMode) {
            AdapterLayoutMode.LINEAR -> {
                binding.ibSwitchMode.setImageResource(R.drawable.ic_grid_mode)
                (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 2
                adapter.adapterLayoutMode = AdapterLayoutMode.GRID
            }
            AdapterLayoutMode.GRID -> {
                binding.ibSwitchMode.setImageResource(R.drawable.ic_linear_mode)
                (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 1
                adapter.adapterLayoutMode = AdapterLayoutMode.LINEAR
            }
        }
        adapter.refreshList()
    }

    override fun bind(item: HomeSection) {
        if (item is HomeSection.FoodsSection) {
            setupSwitch()
            binding.rvListFoods.apply {
                layoutManager = GridLayoutManager(context, 1)
                adapter = this@FoodsSectionViewHolder.adapter
            }
            adapter.setItems(items = item.data)
        }
    }
}
