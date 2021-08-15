package com.kalemlisipahi.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kalemlisipahi.foodbook.R
import com.kalemlisipahi.foodbook.databinding.FragmentContentBinding
import com.kalemlisipahi.foodbook.viewmodel.ContentFragmentViewModel
import kotlinx.android.synthetic.main.fragment_content.*

class ContentFragment : Fragment() {

    lateinit var viewModel : ContentFragmentViewModel
    private var clickId : Int? = null
    private lateinit var databinding : FragmentContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.fragment_content,container,false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ContentFragmentViewModel::class.java)

        arguments?.let {
            clickId = ContentFragmentArgs.fromBundle(it).clickId
            viewModel.getRoomData(clickId!!)
        }

        observeLiveData()

    }

    private fun observeLiveData()
    {
        viewModel.foodList.observe(viewLifecycleOwner,
            {
                it?.let {

                    databinding.foodConetentXML = it

                    /*contentFoodName.text = it.isim
                    contentFoodCal.text = it.kalori
                    contentFoodCarbonH.text = it.karbonhidrat
                    contentFoodProtein.text = it.protein
                    contentFoodYag.text = it.yag
                    */

                    //contentImageView.downloadImage(it.gorsel!!, myPlaceHolder(requireContext()))
                }
            })
    }

}