package com.example.coursework


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coursework.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val message = arguments?.getString("icon","trainIcon").toString()
        binding.icontv.text =  message
    }


    companion object {
        fun newInstance(icon:String): FirstFragment {
            val fragment = FirstFragment()

            val args = Bundle()
            args.putString("icon",icon)

            fragment.arguments = args
            return fragment
        }
    }
}